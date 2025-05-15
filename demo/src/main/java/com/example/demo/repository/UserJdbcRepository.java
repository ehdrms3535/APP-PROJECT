package com.example.demo.repository;

import com.example.demo.entity.User;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.Optional;

@Repository
public class UserJdbcRepository {

    private final String URL = "jdbc:h2:mem:testdb";
    private final String USER = "sa";
    private final String PASSWORD = "";

    public void save(User user) {
        String sql = "INSERT INTO user (username, password) VALUES (?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("회원 저장 실패", e);
        }
    }

    public Optional<User> findByUsername(String username) {
        String sql = "SELECT * FROM user WHERE username = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                User user = new User(
                        rs.getString("username"),
                        rs.getString("password")
                );
                return Optional.of(user);
            }

        } catch (SQLException e) {
            throw new RuntimeException("회원 조회 실패", e);
        }

        return Optional.empty();
    }
}
