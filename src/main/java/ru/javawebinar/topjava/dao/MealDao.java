/*
 * Copyright (c) 26.3.2017
 * Made by stuqs
 */

package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealDao {
    void addMeal(Meal meal);
    void updateMeal(Meal meal);
    void removeMeal(int id);
    Meal getById(int id);
    List<Meal> listMeal();
}
