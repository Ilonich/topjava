package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.User;

import java.util.List;

/**
 * gkislin
 * 02.10.2016
 */
@Transactional(readOnly = true)
public interface CrudUserRepository extends JpaRepository<User, Integer> {
    @Transactional
    @Modifying
    @Query("DELETE FROM User u WHERE u.id=:id")
    int delete(@Param("id") int id);

    @Override
    @Transactional
    User save(User user);

    @EntityGraph(value = "graph.User.roles", type = EntityGraph.EntityGraphType.FETCH)
    @Override
    User findOne(Integer id);

    @Override
    @EntityGraph(value = "graph.User.roles", type = EntityGraph.EntityGraphType.FETCH)
    @Query("SELECT DISTINCT u FROM User u ORDER BY u.name, u.email")
    List<User> findAll();

    @EntityGraph(value = "graph.User.roles", type = EntityGraph.EntityGraphType.FETCH)
    User getByEmail(String email);

    @EntityGraph(value = "graph.User.roles", type = EntityGraph.EntityGraphType.FETCH)
    @Query("SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.meals WHERE u.id = ?1")
    User getWithMeals(int id);
}
