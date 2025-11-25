package com.example.quizapi.dao;

import com.example.quizapi.model.Ranking;
import com.example.quizapi.util.JdbcConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RankingDaoJdbc implements RankingDao{

    @Override
    public Ranking addRanking(Ranking ranking) {
        if(ranking == null) return null;

        try(Connection connection = JdbcConnector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO Ranking (wrong_answers, correct_answers, user_id, time) VALUES (?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, ranking.getWrongAnswers());
            preparedStatement.setInt(2, ranking.getCorrectAnswers());
            preparedStatement.setInt(3, ranking.getUser().getId());
            preparedStatement.setLong(4, ranking.getTime());

            int affectedRows = preparedStatement.executeUpdate();

            if(affectedRows > 0){
                return ranking;
            }

            return null;

        } catch (SQLException e) {
            throw new RuntimeException("Error inserting ranking", e);
        }
    }

    @Override
    public List<Ranking> findTop10() {
        List<Ranking> top10 = new ArrayList<>();

        try(Connection connection = JdbcConnector.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Ranking ORDER BY time DESC LIMIT 10;")) {
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                while(resultSet.next()){
                    top10.add(toRankingEntity(resultSet));
                }
            }

            return top10;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Ranking toRankingEntity(ResultSet resultSet) throws SQLException {
        Ranking ranking = new Ranking();
        ranking.setId(resultSet.getInt("id"));
        ranking.setWrongAnswers(resultSet.getInt("wrong_answers"));
        ranking.setCorrectAnswers(resultSet.getInt("correct_answers"));

        return ranking;
    }
}
