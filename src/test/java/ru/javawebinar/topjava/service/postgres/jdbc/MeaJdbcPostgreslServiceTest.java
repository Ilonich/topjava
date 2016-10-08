package ru.javawebinar.topjava.service.postgres.jdbc;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.service.MealServiceTest;

/**
 * Created by Никола on 08.10.2016.
 */
@ActiveProfiles({Profiles.POSTGRES, Profiles.JDBC})
public class MeaJdbcPostgreslServiceTest extends MealServiceTest {
}
