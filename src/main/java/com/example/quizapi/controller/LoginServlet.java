package com.example.quizapi.controller;

import com.example.quizapi.model.User;
import com.example.quizapi.service.UserService;
import com.example.quizapi.service.UserServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.SneakyThrows;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet(name ="loginServlet", value = "/login")
public class LoginServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init(){
        userService = new UserServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request,  HttpServletResponse response) throws ServletException, IOException {
        if(request.getParameter("registered") != null){
            request.setAttribute("message", "Registration completed. Please sign in");
        }

        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        System.out.println(username);
        System.out.println(password);

        User user = userService.authenticate(username, password);



        if (user == null) {
            request.setAttribute("error", "Invalid username or password.");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }

        HttpSession session = request.getSession(true);
        session.setAttribute("username", user.getUsername());
        session.setMaxInactiveInterval(60 * 5);

        response.sendRedirect("game");
    }
}
