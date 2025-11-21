package com.example.quizapi.service;

import com.example.quizapi.dao.UserDao;
import com.example.quizapi.dao.UserDaoJdbc;
import com.example.quizapi.model.User;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.SQLException;

public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    public UserServiceImpl(){
        this.userDao = new UserDaoJdbc();
    }

    @Override
    public User authenticate(String username, String password) throws SQLException {
        if(username == null || password == null){
            return null;
        }

        User user = userDao.findUser(username);
        if(user == null){
            return null;
        }

        if(BCrypt.checkpw(password, user.getPassword())){
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

}
