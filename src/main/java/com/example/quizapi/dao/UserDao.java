package com.example.quizapi.dao;

import com.example.quizapi.model.User;

import java.sql.SQLException;

public interface UserDao {
    User findById(int id);
    User findUser(String username) throws SQLException;
    User addUser(User user);

}
