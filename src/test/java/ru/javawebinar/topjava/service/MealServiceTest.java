package ru.javawebinar.topjava.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.DbPopulator;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collections;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;


@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class MealServiceTest {

    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Autowired
    private DbPopulator dbPopulator;

    @Before
    public void setUp() throws Exception {
        dbPopulator.execute();
    }


    @Test
    public void testGet() throws Exception {
        Meal meal = service.get(MEAL_ID_OF_USER, USER_ID);
        MATCHER.assertEquals(MEAL_OF_USER, meal);
    }

    @Test(expected = NotFoundException.class)
    public void testNotFoundGet() throws Exception {
        service.get(1, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void testWrongUserGet() throws Exception {
        service.get(MEAL_ID_OF_USER, 1);
    }

    @Test
    public void testDelete() throws Exception {
        service.delete(MEAL_ID_OF_ADMIN, ADMIN_ID);
        MATCHER.assertCollectionEquals(Collections.singletonList(MEAL_OF_ADMIN), service.getAll(ADMIN_ID));
    }

    @Test(expected = NotFoundException.class)
    public void testNotFoundDelete() throws Exception {
        service.delete(1, USER_ID);
    }


    @Test(expected = NotFoundException.class)
    public void testWrongUserDelete() throws Exception {
        service.delete(MEAL_ID_OF_USER, 1);
    }

    @Test
    public void testUpdate() throws Exception {
        Meal updated = new Meal(MEAL_OF_USER.getId(), MEAL_OF_USER.getDateTime(), MEAL_OF_USER.getDescription(), MEAL_OF_USER.getCalories());
        updated.setId(MEAL_ID_OF_ADMIN + 1);
        updated.setDescription("Админ ужин");
        updated.setDateTime(LocalDateTime.of(2015, Month.JUNE, 1, 21, 0));
        updated.setCalories(1500);
        service.update(updated, USER_ID);
        MATCHER.assertEquals(updated, MEAL_OF_ADMIN);
    }

    // TODO: 14.04.2017 not working
    @Test(expected = NotFoundException.class)
    public void testNotFoundUpdate() throws Exception {
        Meal updated = new Meal(MEAL_OF_USER.getId(), MEAL_OF_USER.getDateTime(), MEAL_OF_USER.getDescription(), MEAL_OF_USER.getCalories());
        updated.setDescription("Админ ужин");
        updated.setDateTime(LocalDateTime.of(2015, Month.JUNE, 1, 21, 0));
        updated.setCalories(1500);
        service.update(updated, ADMIN_ID);
    }

    @Test
    public void getBetweenDates() throws Exception {
        MATCHER.assertCollectionEquals(service.getBetweenDateTimes(LocalDateTime.of(2015, Month.JUNE, 1, 17, 0),
                LocalDateTime.of(2015, Month.JUNE, 1, 22, 0), ADMIN_ID), Collections.singletonList(MEAL_OF_ADMIN));
    }
}