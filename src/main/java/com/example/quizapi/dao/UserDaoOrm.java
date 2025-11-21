package com.example.quizapi.dao;

import com.example.quizapi.model.User;
import com.example.quizapi.util.ConnectionManager;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class UserDaoOrm implements UserDao{
    EntityManager em = ConnectionManager.getEntityManager();

    @Override
    public User findById(int id) {


        User user= em.find(User.class, id);
        em.close();

        return user;
    }

    @Override
    public User findUser(String username) {
        try{
            TypedQuery<User> query = em.createQuery(
                    "SELECT u FROM User u WHERE u.username = :username"
            ,User.class);
            query.setParameter("username", username);
            List<User> results = query.getResultList();
            if(results.isEmpty()){
                return null;
            }

            return results.get(0);
        }finally{
            em.close();
        }
    }

    @Override
    public User addUser(User user) {
        try{
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
            return user;
        } catch (Exception e){
            if(em.getTransaction().isActive()){
                em.getTransaction().rollback();
            }
            throw new RuntimeException(e);
        }finally {
            em.close();
        }
    }
}
