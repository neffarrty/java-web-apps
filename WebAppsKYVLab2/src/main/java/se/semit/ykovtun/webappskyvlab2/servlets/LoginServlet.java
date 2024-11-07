package se.semit.ykovtun.webappskyvlab2.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * @author Yehor Kovtun, CS-222a
 * @version 1.0
 * @since 2024-10-08
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private final String USERNAME = "admin";
    private final String PASSWORD = "admin";
    private final String PATH = "/pages/login-page.jsp";

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        req.getRequestDispatcher(PATH).forward(req, res);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if (username.equals(USERNAME) && password.equals(PASSWORD)) {
            res.sendRedirect("/hospital/list");
        }
        else {
            req.setAttribute("error", "Invalid username or password");
            req.getRequestDispatcher(PATH).forward(req, res);
        }
    }
}

