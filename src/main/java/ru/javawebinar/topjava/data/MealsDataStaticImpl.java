/*
 * Copyright (c) 26.3.2017
 * Made by stuqs
 */

package ru.javawebinar.topjava.data;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Generate static data
 */
public final class MealsDataStaticImpl implements MealsData {
    private final static Logger LOG = getLogger(MealsDataStaticImpl.class);
    private final static MealsData ourInstance = new MealsDataStaticImpl();
    private final AtomicInteger ID = new AtomicInteger();
    private final Map<Integer, Meal> MEALS;


    public static MealsData getInstance() {
        return ourInstance;
    }

    private MealsDataStaticImpl() {
        List<Meal> mealList = new ArrayList<>();
        mealList.add(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        mealList.add(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
        mealList.add(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
        mealList.add(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
        mealList.add(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
        mealList.add(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
        mealList.add(new Meal(LocalDateTime.of(2017, Month.MAY, 22, 11, 0), "Полудник", 455));
        mealList.add(new Meal(LocalDateTime.of(2017, Month.MAY, 23, 11, 0), "Ужин", 2200));

        MEALS = new ConcurrentHashMap<>();
        mealList.forEach(meal -> {
                    meal.setId(ID.getAndIncrement());
                    MEALS.put(meal.getId(), meal);
                }
        );
    }

    @Override
    public void add(Meal meal) {
        try {
            meal.setId(ID.getAndIncrement());
            MEALS.put(meal.getId(), meal);
            LOG.debug("Add meal with id {}", meal.getId());
        } catch (Exception e) {
            LOG.info("Got Exception while add to meal - {}", e);
        }
    }

    @Override
    public void update(Meal meal) {
        try {
            MEALS.put(meal.getId(), meal);
            LOG.debug("Add meal with id {}", meal.getId());
        } catch (Exception e) {
            LOG.info("Got Exception while update meal - {}", e);
        }
    }

    @Override
    public void remove(int id) {
        try {
            MEALS.remove(id);
        } catch (Exception e) {
            LOG.info("Got Exception while remove meal - {}", e);
        }
    }

    @Override
    public List<Meal> getData() {
        return new ArrayList<>(MEALS.values()).stream()
                .sorted(Comparator.comparingInt(Meal::getId)).collect(Collectors.toList());
    }

    @Override
    public Meal getById(int id) {
        try {
            return MEALS.get(id);
        } catch (Exception e) {
            LOG.debug("Got Exception while search meal by id - {}", e);
        }
        return null;
    }
}