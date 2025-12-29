package tinder.repo;

import tinder.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private final Connection connection;
    private static final String SQL_SELECT_BY_LOGIN =
    """
    SELECT * FROM users WHERE login = ?
    """;
    private static final String SQL_GET_ALL_USERS =
    """
       SELECT * FROM users LIMIT ?
    """;

    public UserRepository(Connection connection) {
        this.connection = connection;
    }

    public User getUserByLogin(String login) throws SQLException {
        try(PreparedStatement ps = connection.prepareStatement(SQL_SELECT_BY_LOGIN)) {
            User user = null;
            ps.setString(1, login);
            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
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

    public List<User> getAllUsers(int limit) throws SQLException {
        try(PreparedStatement ps = connection.prepareStatement(SQL_GET_ALL_USERS)) {
            List<User> users = new ArrayList<>();
            ps.setInt(1, limit);
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
}
