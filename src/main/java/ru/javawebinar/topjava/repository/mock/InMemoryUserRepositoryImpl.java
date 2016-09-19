package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * GKislin
 * 15.06.2015.
 */
@Repository
public class InMemoryUserRepositoryImpl implements UserRepository {
    private static final Logger LOG = LoggerFactory.getLogger(InMemoryUserRepositoryImpl.class);

    private Map<Integer, User> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        Arrays.asList(new User(null, "Vasya", "qwe@ya.ru", "3214", Role.ROLE_USER),
                new User(null, "Petya", "awe@ya.ru", "3215", Role.ROLE_USER),
                new User(null, "Kolya", "tre@ya.ru", "1234", Role.ROLE_ADMIN, Role.ROLE_USER),
                new User(null, "Sanya", "yadr@ya.ru", "xcv24", Role.ROLE_USER),
                new User(null, "Kirya", "vbnm@gmail.com", "cvbn", Role.ROLE_USER)
                ).forEach(this::save);
    }

    @Override
    public boolean delete(int id) {
        if (repository.containsKey(id)){
            repository.remove(id);
            LOG.info("delete " + id);
            return true;
        } else {
            LOG.info("nothing to delete " + id);
            return false;
        }
    }

    @Override
    public User save(User user) {
        if (user.isNew()){
            user.setId(counter.incrementAndGet());
            repository.put(user.getId(), user);
            LOG.info("save " + user);
        } else {
            repository.put(user.getId(), user);
            LOG.info("updated " + user);
        }
        return user;
    }

    @Override
    public User get(int id) {
        LOG.info("get " + id);
        return repository.getOrDefault(id, null);
    }

    @Override
    public List<User> getAll() {
        LOG.info("getAll");
        return Collections.unmodifiableList(repository.values().stream()
                .sorted((a, b) -> a.getName().compareTo(b.getName()))
                .collect(Collectors.toList()));
    }

    @Override
    public User getByEmail(String email) {
        LOG.info("getByEmail " + email);
        return repository.values().stream()
                .filter(user -> user.getEmail().equalsIgnoreCase(email))
                .findFirst().orElse(null);
    }
}
