package com.example.quizapi.service;

import com.example.quizapi.dao.UserDao;
import com.example.quizapi.dao.UserDaoJdbc;
import com.example.quizapi.dao.UserDaoOrm;
import com.example.quizapi.model.User;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.SQLException;

public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    public UserServiceImpl(){
        this.userDao = new UserDaoOrm();
    }

    @Override
    public User authenticate(String username, String password) throws SQLException {
        if(username == null || password == null){
            return null;
        }

        User user = userDao.findUser(username);

        boolean match = BCrypt.checkpw(password, user.getPassword());

        if (match) {
            return user;
        }

        return null;

    }

    @Override
    public User register(String username, String password) throws SQLException {

        if(username == null || password == null){
            return null;
        }

        User existing = userDao.findUser(username);
        if(existing != null){
            return null;
        }

        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        User user = new User(username, hashedPassword);

        return userDao.addUser(user);
    }

    @Override
    public User findByUsername(String username) throws SQLException {
        if (username == null) return null;
        return userDao.findUser(username);
    }
}
