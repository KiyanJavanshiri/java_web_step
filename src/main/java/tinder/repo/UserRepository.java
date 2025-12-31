package tinder.repo;

import jakarta.servlet.http.HttpSession;
import tinder.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserRepository {
    private final Connection connection;
    private static final String SQL_SELECT_BY_LOGIN =
    """
    SELECT * FROM users WHERE login = ?
    """;
    private static final String SQL_GET_ALL_USERS =
    """
       SELECT u.*
               FROM users u
               LEFT JOIN matches m1
                 ON m1.user_id = ?
                AND m1.matched_user_id = u.id
               LEFT JOIN matches m2
                 ON m2.user_id = u.id
                AND m2.matched_user_id = ?
               WHERE u.id != ?
                 AND m1.user_id IS NULL
                 AND m2.user_id IS NULL
               LIMIT ?
    """;

    private static final String SQL_GET_LIKED_USERS =
    """
            SELECT liked_users.* FROM (
                     SELECT m.id AS match_id , u.*
                     FROM users u
                     JOIN matches m
                       ON m.matched_user_id = u.id
                       OR m.user_id = u.id
                     WHERE m.user_id = ?
                       OR m.matched_user_id = ?
                       ) AS liked_users WHERE liked_users.id != ?;
    """;

    private static final String SQL_SAVE_USER_LIKE =
    """
    INSERT INTO matches (user_id, matched_user_id) 
       VALUE (?, ?)
    """;

    private static final String SQL_GET_ALL_DIALOG =
    """
            SELECT u.*, m.text, m.created_at FROM users u
            JOIN messages m ON u.id = m.user_id WHERE m.dialog_id = ?
    """;

    public UserRepository(Connection connection) {
        this.connection = connection;
    }

    public User getUserByLogin(String login) throws SQLException {
        try(PreparedStatement ps = connection.prepareStatement(SQL_SELECT_BY_LOGIN)) {
            User user = null;
            ps.setString(1, login);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                user = new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("login"),
                        rs.getString("password"),
                        rs.getString("imageUrl")
                );
            }

            return user;
        }
    }

    public List<User> getAllUsers(int limit, int userId) throws SQLException {
        try(PreparedStatement ps = connection.prepareStatement(SQL_GET_ALL_USERS)) {
            List<User> users = new ArrayList<>();
            ps.setInt(1, userId);
            ps.setInt(2, userId);
            ps.setInt(3, userId);
            ps.setInt(4, limit);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                User user = new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("login"),
                        rs.getString("password"),
                        rs.getString("imageUrl")
                );
                users.add(user);
            }

            return users;
        }
    }

    public void saveLike(int currentUserId, int matchedUserId) throws SQLException {
        try(PreparedStatement ps = connection.prepareStatement(SQL_SAVE_USER_LIKE)) {
            ps.setInt(1, currentUserId);
            ps.setInt(2, matchedUserId);

            ps.executeUpdate();
        }
    }

    public Map<Integer, User> getLikedUsers(int currentUserId) throws SQLException {
        try(PreparedStatement ps = connection.prepareStatement(SQL_GET_LIKED_USERS)) {
            Map<Integer, User> users = new HashMap<>();
            ps.setInt(1, currentUserId);
            ps.setInt(2, currentUserId);
            ps.setInt(3, currentUserId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                User user = new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("login"),
                        rs.getString("password"),
                        rs.getString("imageUrl")
                );
                users.put(rs.getInt("match_id") ,user);
            }

            return users;
        }
    }

    public List<Map<String, Object>> getAllDialogMessages(int dialogId) throws SQLException {
        try(PreparedStatement ps = connection.prepareStatement(SQL_GET_ALL_DIALOG)) {
            List<Map<String, Object>> messages = new ArrayList<>();
            ps.setInt(1, dialogId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Map<String, Object> message = new HashMap<>();
                message.put("user", new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("login"),
                        rs.getString("password"),
                        rs.getString("imageUrl")
                ));
                message.put("text", rs.getString("text"));
                message.put("date", rs.getDate("created_at").toLocalDate());
                messages.add(message);
            }

            return messages;
        }
    }
}
