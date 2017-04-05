package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

public class MealServiceImpl implements MealService {
    private final MealRepository repository;


    public MealServiceImpl(MealRepository repository) {
        this.repository = repository;
    }

    @Override
    public Meal save(Meal meal) {
        return repository.save(meal);
    }

    @Override
    public void delete(int id, int userId) throws NotFoundException {
        checkNotFoundWithId(repository.delete(id, userId), id);
    }

    @Override
    public Meal get(int id, int userId) throws NotFoundException {
        return checkNotFoundWithId(repository.get(id, userId), id);
    }

    @Override
    public List<MealWithExceed> getAll(int userId) {
        return MealsUtil.getFilteredWithExceeded(repository.getAll(userId), LocalTime.MIN, LocalTime.MAX,
                AuthorizedUser.getCaloriesPerDay());
    }

    @Override
    public List<MealWithExceed> getFiltered(int userId, LocalDate startDate, LocalDate endDate, LocalTime startTime,
                                            LocalTime endTime) {
        startDate = (startDate == null) ? LocalDate.MIN : startDate;
        endDate =   (endDate == null)   ? LocalDate.MAX : endDate;
        startTime = (startTime == null) ? LocalTime.MIN : startTime;
        endTime =   (endTime == null)   ? LocalTime.MAX : endTime;

        return MealsUtil.getFilteredWithExceeded(repository.getFilteredByDate(userId, startDate, endDate),
                startTime, endTime, AuthorizedUser.getCaloriesPerDay());
    }
}