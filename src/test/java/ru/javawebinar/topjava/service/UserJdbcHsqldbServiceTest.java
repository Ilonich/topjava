package ru.javawebinar.topjava.service;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;

/**
 * Created by Никола on 08.10.2016.
 */
@ActiveProfiles({Profiles.HSQLDB, Profiles.JDBC})
public class UserJdbcHsqldbServiceTest extends UserServiceTest{
}
