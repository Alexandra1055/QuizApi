package com.example.quizapi.interceptor;

import com.example.quizapi.model.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter("/game")
public class AuthInterceptor implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") :null;
        System.out.println(user);
        System.out.println(session);

        if (user != null) {
            filterChain.doFilter(request,response);
            return;
        }

        response.sendRedirect(request.getContextPath() + "/login");
    }
}

