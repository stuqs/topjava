package ru.javawebinar.topjava.db;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

/**
 * Generate static data
 */
public class MealsData {
    private static final List<Meal> MEALS;


    static {
//        MEALS = new CopyOnWriteArrayList<>();
        MEALS = new ArrayList<>();
        MEALS.add(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        MEALS.add(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
        MEALS.add(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
        MEALS.add(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
        MEALS.add(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
        MEALS.add(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
        MEALS.add(new Meal(LocalDateTime.of(2017, Month.MAY, 22, 11, 0), "Полудник", 455));
    }

    private MealsData() {
    }

    /**
     * Extract static data
     *
     * @return ArrayList of Meal
     */
    public static List<Meal> getData() {
        return MEALS;
    }
}