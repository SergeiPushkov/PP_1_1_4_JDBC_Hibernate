package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    static Connection conn = Util.getConnection();


    public void createUsersTable() {
        try {
            String create = "CREATE TABLE IF NOT EXISTS users (id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(100), lastname VARCHAR(100), age INT);";
            PreparedStatement preparedStatement = conn.prepareStatement(create);
            preparedStatement.execute(create);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        try {
            String drop = "DROP TABLE IF EXISTS users;";
            PreparedStatement preparedStatement = conn.prepareStatement(drop);
            preparedStatement.execute(drop);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age)  {
        try {
            conn.setAutoCommit(false);
            String save = "INSERT INTO users (name, lastname, age) VALUES(?,?,?);";
            PreparedStatement preparedStatement = conn.prepareStatement(save);
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,lastName);
            preparedStatement.setByte(3,age);
            preparedStatement.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public void removeUserById(long id) {
        try {
            conn.setAutoCommit(false);
            String remove = "DELETE FROM users WHERE id = ?;";
            PreparedStatement preparedStatement = conn.prepareStatement(remove);
            preparedStatement.setLong(1,id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }

    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try {
            String get = "SELECT * FROM users";
            PreparedStatement preparedStatement = conn.prepareStatement(get);
            ResultSet res = preparedStatement.executeQuery(get);
            while (res.next()) {
                String name = res.getString("name");
                String lastname = res.getString("lastname");
                byte age = res.getByte("age");
                users.add(new User(name,lastname,age));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    return users;
    }

    public void cleanUsersTable() {
        try {
            String clean = "TRUNCATE TABLE users";
            PreparedStatement preparedStatement = conn.prepareStatement(clean);
            preparedStatement.execute(clean);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
