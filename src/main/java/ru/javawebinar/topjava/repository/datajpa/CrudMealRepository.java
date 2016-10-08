package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;

import java.time.LocalDateTime;
import java.util.List;

/**
 * gkislin
 * 02.10.2016
 */
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public interface CrudMealRepository extends JpaRepository<Meal, Integer> {

    @Transactional
    @Modifying
    @Query("UPDATE Meal m SET m.description=?1, m.calories=?2, m.dateTime=?3 WHERE m.id=?4 AND m.user.id=?5")
    int update(String desc, int calories, LocalDateTime dateTime, int id, int userId);

    @Transactional
    @Modifying
    int deleteByIdAndUser_Id(int id, int userId);

    Meal findByIdAndUser_Id(int id, int userId);

    List<Meal> findByUser_IdOrderByDateTimeDesc(int userId);

    List<Meal> findByUser_IdAndDateTimeIsBetweenOrderByDateTimeDesc(int userId, LocalDateTime start, LocalDateTime end);


}
