package com.example.quizapi.dao;

import com.example.quizapi.model.Ranking;
import com.example.quizapi.model.User;
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
                try (ResultSet keys = preparedStatement.getGeneratedKeys()) {
                    if (keys.next()) {
                        ranking.setId(keys.getInt(1));
                    }
                }
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

        String sql = "SELECT r.id, r.wrong_answers, r.correct_answers,r.time,u.id AS user_id,u.username FROM Ranking r JOIN User u ON r.user_id = u.id  ORDER BY r.time DESC, r.correct_answers DESC LIMIT 10";

        try(Connection connection = JdbcConnector.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
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
        ranking.setTime(resultSet.getLong("time"));

        User user = new User();
        user.setId(resultSet.getInt("user_id"));
        user.setUsername(resultSet.getString("username"));
        ranking.setUser(user);

        return ranking;
    }
}
