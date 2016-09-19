package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserServiceImpl;
import ru.javawebinar.topjava.web.user.AdminRestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * User: gkislin
 * Date: 19.08.2014
 */
public class UserServlet extends HttpServlet {
    private static final Logger LOG = getLogger(UserServlet.class);

    private UserServiceImpl service;
    private ConfigurableApplicationContext appCtx;

    @Override
    public void init() throws ServletException {
        super.init();
        appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        service = appCtx.getBean(UserServiceImpl.class);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.debug("forward to userList");
        request.getRequestDispatcher("/userList.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String pass = req.getParameter("password");
        User user = service.getByEmail(email);
        if (user.getPassword().equals(pass)){
            AuthorizedUser.setId(user.getId());
            resp.sendRedirect("meals");
        } else {
            resp.sendRedirect("index.jsp");
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        appCtx.close();
    }
}
