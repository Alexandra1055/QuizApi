package com.example.quizapi.controller;

import com.example.quizapi.model.User;
import com.example.quizapi.service.UserService;
import com.example.quizapi.service.UserServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "registerServlet", value = "/register")
public class RegisterServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init(){
        userService = new UserServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/register.jps").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,  HttpServletResponse response) throws IOException, ServletException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");


        try {
            User created = userService.register(username, password);
        } catch (SQLException e) {
            request.setAttribute("error", "Registration failed. The email may already be in use.");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }

        response.sendRedirect("login?registered=true");
    }
}
