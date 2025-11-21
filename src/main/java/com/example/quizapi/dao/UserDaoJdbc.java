package com.example.quizapi.dao;

import com.example.quizapi.model.User;
import com.example.quizapi.util.JdbcConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class UserDaoJdbc implements UserDao{
    public static final Connection connection;

    static {
        try {
            connection = JdbcConnector.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User findById(int id) {
        return null;
    }

    @Override
    public User findUser(String username) throws SQLException {
        String sql = "SELECT * FROM Users WHERE username = ?";

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, username);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return new User(resultSet.getString("username"), resultSet.getString("password"));
            } else {
                return null;
            }

        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public User addUser(User user) {
        String sql = "INSERT INTO User(username, password) VALUES (?, ?)";

        if(user == null) return null;

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());

            if(preparedStatement.executeUpdate() > 0){
                return user;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error inserting user", e);
        }

        return null;
    }
}
