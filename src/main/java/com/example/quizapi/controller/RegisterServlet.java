package com.example.quizapi.controller;

import com.example.quizapi.model.User;
import com.example.quizapi.service.UserService;
import com.example.quizapi.service.UserServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;

import java.io.IOException;

@WebServlet(name = "registerServlet", value = "/register")
public class RegisterServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init() {
        userService = new UserServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/register.jsp").forward(req, resp);
    }

    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        User created = userService.register(email, password);

        if (created == null) {
            req.setAttribute("error", "Registration failed. The email may already be in use.");
            req.getRequestDispatcher("/register.jsp").forward(req, resp);
            return;
        }

        resp.sendRedirect("login?registered=true");
    }
}
