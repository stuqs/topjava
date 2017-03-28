/*
 * Copyright (c) 26.3.2017
 * Made by stuqs
 */

package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.dao.MealDaoStaticImpl;
import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public class MealService {
    private final static MealService ourInstance = new MealService();
    private final static MealDao mealDao = MealDaoStaticImpl.getInstance();


    private MealService() {
    }

    public static MealService getInstance(){
        return ourInstance;
    }

    public void addMeal(Meal meal) {
        mealDao.addMeal(meal);
    }

    public void updateMeal(Meal meal) {
        mealDao.updateMeal(meal);
    }

    public void removeMeal(int id) {
        mealDao.removeMeal(id);
    }

    public List<Meal> listMeal() {
        return mealDao.listMeal();
    }

    public Meal getById(int id) {
        return mealDao.getById(id);
    }
}