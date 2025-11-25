package com.example.quizapi.service;

import com.example.quizapi.model.User;

import java.sql.SQLException;

public interface UserService {
    User authenticate(String username, String password) throws SQLException;
    User register(String username, String password) throws SQLException;
    User findByUsername(String username) throws SQLException;
}
