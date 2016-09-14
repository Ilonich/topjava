package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealSimpleRepo;
import ru.javawebinar.topjava.util.Formatters;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by Никола on 11.09.2016.
 */
public class EditServlet extends HttpServlet {

    private static final Logger LOG = getLogger(EditServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        req.setAttribute("meal", MealSimpleRepo.mealById(id));
        getServletContext().getRequestDispatcher("/editl.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        Meal meal = new Meal(Integer.parseInt(req.getParameter("id")), Formatters.parse(req.getParameter("dob")), req.getParameter("description"), Integer.parseInt(req.getParameter("calories")));
        MealSimpleRepo.update(meal);
        resp.sendRedirect("/topjava/meals");
    }
}
