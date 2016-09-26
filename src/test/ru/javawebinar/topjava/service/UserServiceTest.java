package ru.javawebinar.topjava.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.util.DbPopulator;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

@ContextConfiguration({
        "classpath:springtest/spring-app-test-repo-db.xml",
        "classpath:springtest/spring-test-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class UserServiceTest {

    @Autowired
    protected UserService service;

    @Autowired
    private DbPopulator dbPopulator;

    @Before
    public void setUp() throws Exception {
        dbPopulator.execute();
    }

    @Test
    public void testSave() throws Exception {
        User newUser = new User(null, "New", "new@gmail.com", "newPass", 1555, false, Collections.singleton(Role.ROLE_USER));
        User created = service.save(newUser);
        newUser.setId(created.getId());
        UserTestData.MATCHER.assertCollectionEquals(Arrays.asList(UserTestData.ADMIN, newUser, UserTestData.USER), service.getAll());
    }

    @Test(expected = DataAccessException.class)
    public void testDuplicateMailSave() throws Exception {
        service.save(new User(null, "Duplicate", "user@yandex.ru", "newPass", Role.ROLE_USER));
    }

    @Test
    public void testDelete() throws Exception {
        service.delete(UserTestData.USER_ID);
        UserTestData.MATCHER.assertCollectionEquals(Collections.singletonList(UserTestData.ADMIN), service.getAll());
    }

    @Test(expected = NotFoundException.class)
    public void testNotFoundDelete() throws Exception {
        service.delete(1);
    }

    @Test
    public void testGet() throws Exception {
        User user = service.get(UserTestData.USER_ID);
        UserTestData.MATCHER.assertEquals(UserTestData.USER, user);
    }

    @Test(expected = NotFoundException.class)
    public void testGetNotFound() throws Exception {
        service.get(1);
    }

    @Test
    public void testGetByEmail() throws Exception {
        User user = service.getByEmail("user@yandex.ru");
        UserTestData.MATCHER.assertEquals(UserTestData.USER, user);
    }

    @Test
    public void testGetAll() throws Exception {
        Collection<User> all = service.getAll();
        UserTestData.MATCHER.assertCollectionEquals(Arrays.asList(UserTestData.ADMIN, UserTestData.USER), all);
    }

    @Test
    public void testUpdate() throws Exception {
        User updated = new User(UserTestData.USER);
        updated.setName("UpdatedName");
        updated.setCaloriesPerDay(330);
        service.update(updated);
        UserTestData.MATCHER.assertEquals(updated, service.get(UserTestData.USER_ID));
    }
}