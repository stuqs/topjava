/*
 * Copyright (c) 26.3.2017
 * Made by stuqs
 */

package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.data.MealsData;
import ru.javawebinar.topjava.data.MealsDataStaticImpl;
import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public class MealDaoStaticImpl implements MealDao {
    private final static MealDao ourInstance = new MealDaoStaticImpl();
    private final MealsData mealsData = MealsDataStaticImpl.getInstance();

    private MealDaoStaticImpl() {
    }

    public static MealDao getInstance() {
        return ourInstance;
    }

    @Override
    public void addMeal(Meal meal) {
        mealsData.add(meal);
    }

    @Override
    public void updateMeal(Meal meal) {
        mealsData.update(meal);
    }

    @Override
    public void removeMeal(int id) {
        mealsData.remove(id);
    }

    @Override
    public List<Meal> listMeal() {
        return mealsData.getData();
    }

    @Override
    public Meal getById(int id) {
        return mealsData.getById(id);
    }
}
