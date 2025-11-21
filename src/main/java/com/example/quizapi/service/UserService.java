package com.example.quizapi.service;

import com.example.quizapi.model.User;

import java.sql.SQLException;

public interface UserService {
    User authenticate(String email, String password) throws SQLException;
    User register(String email, String password) throws SQLException;
}
