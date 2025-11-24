package com.example.quizapi.dao;

import com.example.quizapi.model.Ranking;
import com.example.quizapi.util.ConnectionManager;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;

import java.util.List;

public class RankingDaoOrm implements RankingDao {
    @Override
    public Ranking addRanking(Ranking ranking) {
        if(ranking == null) return null;

        EntityManager entityManager = ConnectionManager.getEntityManager();

        try{
            entityManager.getTransaction().begin();
            entityManager.persist(ranking);
            entityManager.getTransaction().commit();
            return ranking;
        } catch (Exception e){
            if(entityManager.getTransaction().isActive()){
                entityManager.getTransaction().rollback();
            }
            throw e;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<Ranking> findTop10() {
        EntityManager entityManager = ConnectionManager.getEntityManager();

        List<Ranking> rankings = entityManager.createQuery("SELECT * FROM Ranking ORDER BY correct_answers DESC LIMIT 10;", Ranking.class).getResultList();

        entityManager.close();

        return rankings;
    }
}
