package ru.javawebinar.topjava.service.hsql.jpa;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.service.UserServiceTest;

/**
 * Created by Никола on 08.10.2016.
 */
@ActiveProfiles({Profiles.HSQLDB, Profiles.JPA})
public class UserJpaHsqldbServiceTest extends UserServiceTest {
}
