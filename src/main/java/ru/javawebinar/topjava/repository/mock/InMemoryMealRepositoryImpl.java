package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.TimeUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * GKislin
 * 15.09.2015.
 */
@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {

    private static final Logger LOG = getLogger(InMemoryMealRepositoryImpl.class);

    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);
    {
        for (int i = 1; i <=5; i++){
            Arrays.asList(new Meal(i, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак" + i, 500),
                    new Meal(i, LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед" + i, 1000),
                    new Meal(i, LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин" + i, 500),
                    new Meal(i, LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак" + i, 1000),
                    new Meal(i, LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед" + i, 500),
                    new Meal(i, LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин" + i, 510)).forEach(this::save);
        }
    }

    @Override
    public Meal save(Meal meal) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
            LOG.info("saved " + meal);
        } else {
            repository.put(meal.getId(), meal);
            LOG.info("updated " + meal);
        }
        return meal;
    }

    @Override
    public boolean delete(int id, int ownerId) {
        if (repository.remove(id, get(id, ownerId))){
            LOG.info("removed meal" + id);
            return true;
        } else {
            LOG.info("not removed meal " + id + ", owner " + ownerId);
            return false;
        }
    }

    @Override
    public Meal get(int id, int ownerId) {
        return Optional.ofNullable(repository.get(id)).filter(a -> a.getOwnerId() == ownerId).orElse(null);
    }

    @Override
    public Collection<Meal> getAll(int ownerId) {
        LOG.info("getAll for user id = " + ownerId);
        return repository.values().stream()
                .filter(m -> m.getOwnerId() == ownerId)
                .sorted((a, b) -> a.getDateTime().compareTo(b.getDateTime()))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Meal> getFiltered(int ownerID, LocalDate startDate, LocalDate endDate) {
        LOG.info("getFiltered for user id = " + ownerID);
        return repository.values().stream()
                .filter(m -> m.getOwnerId() == ownerID &&
                        TimeUtil.isBetweenDate(m.getDate(), startDate, endDate))
                .sorted((a, b) -> a.getDateTime().compareTo(b.getDateTime()))
                .collect(Collectors.toList());
    }
}

