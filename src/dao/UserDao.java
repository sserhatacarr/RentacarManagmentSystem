package dao;

import core.Db;
import entity.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDao {
    private final Connection connection;

    public UserDao() {
        this.connection = Db.getInstance();
    }

    public ArrayList<User> findAll() {
        ArrayList<User> userList = new ArrayList<>();
        String query = "SELECT * FROM user";
        try {
            ResultSet resultSet = this.connection.createStatement().executeQuery(query);
            while (resultSet.next()) {
                userList.add(this.match(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    public User findByLogin(String username, String password) {
        User obj = null;
        String query = "SELECT * FROM user WHERE user_name = ? AND user_pass = ?";
        try {
            java.sql.PreparedStatement statement = this.connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);
            java.sql.ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                obj = this.match(resultSet);
            }
        } catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
        }
        return obj;
    }

    public User match(ResultSet rs) {

        User user = new User();
        try {
            user.setId(rs.getInt("user_id"));
            user.setUsername(rs.getString("user_name"));
            user.setPassword(rs.getString("user_pass"));
            user.setRole(rs.getString("user_role"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
}
