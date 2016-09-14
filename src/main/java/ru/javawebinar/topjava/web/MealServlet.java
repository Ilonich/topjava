package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealSimpleRepo;
import ru.javawebinar.topjava.util.Formatters;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalTime;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by Никола on 11.09.2016.
 */

public class MealServlet extends HttpServlet {

    private static final Logger LOG = getLogger(MealServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!req.getParameterMap().containsKey("id")) {
            req.setAttribute("list", MealsUtil.getFilteredWithExceeded(MealSimpleRepo.showAll(), LocalTime.MIN, LocalTime.MAX, 2000));
            getServletContext().getRequestDispatcher("/mealList.jsp").forward(req, resp);
        } else {
            MealSimpleRepo.delete(Integer.parseInt(req.getParameter("id")));
            resp.sendRedirect("/topjava/meals");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        Meal meal = new Meal(0, Formatters.parse(req.getParameter("dob")), req.getParameter("description"), Integer.parseInt(req.getParameter("calories")));
        MealSimpleRepo.add(meal);
        resp.sendRedirect("/topjava/meals");
    }

}
