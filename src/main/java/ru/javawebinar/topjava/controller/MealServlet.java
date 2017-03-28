package ru.javawebinar.topjava.controller;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

import static org.slf4j.LoggerFactory.getLogger;


public class MealServlet extends HttpServlet {
    private final static Logger LOG = getLogger(MealServlet.class);
    private final static MealService mealService = MealService.getInstance();
    private final static String INSERT_OR_EDIT_JSP = "pages/meal.jsp";
    private final static String LIST_MEAL_JSP = "pages/mealList.jsp";
    private final static String MEALS_LINK = "meals";


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forward;

        String action = request.getParameter("action");
        action = action == null ? "" : action.toLowerCase();

        try {
            switch (action) {
                case "delete": {
                    int id = Integer.parseInt(request.getParameter("id"));
                    mealService.removeMeal(id);
                    forward = LIST_MEAL_JSP;
                    request.setAttribute("meals", MealsUtil.getWithExceed(mealService.listMeal()));
                    break;
                }
                case "edit": {
                    int id = Integer.parseInt(request.getParameter("id"));
                    Meal meal = mealService.getById(id);
                    forward = INSERT_OR_EDIT_JSP;
                    request.setAttribute("meal", meal);
                    break;
                }
                case "add":
                    forward = INSERT_OR_EDIT_JSP;
                    break;
                default:
                    forward = LIST_MEAL_JSP;
                    request.setAttribute("meals", MealsUtil.getWithExceed(mealService.listMeal()));
            }
        } catch (Exception e) {
            LOG.debug("Got exception " + e.toString());
            forward = LIST_MEAL_JSP;
            request.setAttribute("meals", MealsUtil.getWithExceed(mealService.listMeal()));
        }
        LOG.debug("Got action " + action + "Forward to " + forward);

        request.getRequestDispatcher(forward).forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        try {
            LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("date")
                    .trim().replace(" ", "T"));

            String description = request.getParameter("description");
            int calories = Integer.parseInt(request.getParameter("calories"));
            String idStr = request.getParameter("id");

            Meal meal = new Meal(dateTime, description, calories);

            if (idStr == null || idStr.isEmpty()) {
                mealService.addMeal(meal);
            } else {
                meal.setId(Integer.parseInt(idStr));
                mealService.updateMeal(meal);
            }
        } catch (Exception e) {
            LOG.debug("Got exception " + e.toString());
        }

        LOG.debug("Redirect to " + MEALS_LINK);

        response.sendRedirect(MEALS_LINK);
    }
}