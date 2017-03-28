

/*
 * Copyright (c) 26.3.2017
 * Made by stuqs
 */

package ru.javawebinar.topjava.data;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealsData {
    void add(Meal meal);
    void update(Meal meal);
    void remove(int id);
    Meal getById(int id);
    List<Meal> getData();
}