package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

/**
 * User: gkislin
 * Date: 19.08.2014
 */
public class MealServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(MealServlet.class);
    private final static String INSERT_OR_EDIT_JSP = "pages/meal.jsp";
    private final static String LIST_MEAL_JSP = "pages/mealList.jsp";
    private final static String MEALS_LINK = "meals";

    private MealRestController restController;
    private ConfigurableApplicationContext ctx;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ctx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        restController = ctx.getBean(MealRestController.class);
    }

    @Override
    public void destroy() {
        super.destroy();
        ctx.close();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");

        String userId = request.getParameter("userId");
        if (userId != null) {
            AuthorizedUser.setId(Integer.parseInt(userId));
            response.sendRedirect(MEALS_LINK);
            return;
        }

        Meal meal = new Meal(id.isEmpty() ? null : getId(request),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.valueOf(request.getParameter("calories")), AuthorizedUser.getId());

        LOG.info(meal.isNew() ? "Create {}" : "Update {}", meal);
        if (meal.isNew()) {
            restController.create(meal);
        } else {
            restController.update(meal);
        }
        response.sendRedirect(MEALS_LINK);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action == null ? "all" : action) {
            case "delete":
                int id = getId(request);
                LOG.info("Delete {}", id);
                restController.delete(id);
                response.sendRedirect(MEALS_LINK);
                break;
            case "create":
            case "update":
                final Meal meal = "create".equals(action) ?
                        new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000, AuthorizedUser.getId()) :
                        restController.get(getId(request));
                request.setAttribute("meal", meal);
                request.getRequestDispatcher(INSERT_OR_EDIT_JSP).forward(request, response);
                break;
            case "filtered":
                LocalDate startDate = null;
                LocalDate endDate = null;
                LocalTime startTime = null;
                LocalTime endTime = null;
                try {
                    String startDateStr = request.getParameter("startDate");
                    String endDateStr = request.getParameter("endDate");
                    String startTimeStr = request.getParameter("startTime");
                    String endTimeStr = request.getParameter("endTime");

                    if (startDateStr != null && !startDateStr.isEmpty()) {
                        startDate = LocalDate.parse(startDateStr);
                    }
                    if (endDateStr != null && !endDateStr.isEmpty()) {
                        endDate = LocalDate.parse(endDateStr);
                    }
                    if (startTimeStr != null && !startTimeStr.isEmpty()) {
                        startTime = LocalTime.parse(startTimeStr);
                    }
                    if (endTimeStr != null && !endTimeStr.isEmpty()) {
                        endTime = LocalTime.parse(endTimeStr);
                    }
                } catch (Exception e) {
                    LOG.error("Wrong filter parameters");
                    request.setAttribute("meals", restController.getAll());
                    request.getRequestDispatcher(LIST_MEAL_JSP).forward(request, response);
                }

                LOG.info("getFiltered");
                request.setAttribute("meals", restController.getFiltered(startDate, endDate, startTime, endTime));
                request.getRequestDispatcher(LIST_MEAL_JSP).forward(request, response);
                break;
            case "all":
            default:
                LOG.info("getAll");
                request.setAttribute("meals", restController.getAll());
                request.getRequestDispatcher(LIST_MEAL_JSP).forward(request, response);
                break;
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.valueOf(paramId);
    }
}