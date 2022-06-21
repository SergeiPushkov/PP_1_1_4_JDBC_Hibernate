package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try {
            String create = "CREATE TABLE IF NOT EXISTS users (id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(100), lastname VARCHAR(100), age INT);";
            Statement statement = Util.getConnection().createStatement();
            statement.execute(create);
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        try {
            String drop = "DROP TABLE IF EXISTS users;";
            Statement statement = Util.getConnection().createStatement();
            statement.execute(drop);
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {

        try {
            String save = "INSERT INTO users (name, lastname, age) VALUES(?,?,?);";
            PreparedStatement preparedStatement = Util.getConnection().prepareStatement(save);
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,lastName);
            preparedStatement.setByte(3,age);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        try {
            String remove = "DELETE FROM users WHERE id = ?;";
            PreparedStatement preparedStatement = Util.getConnection().prepareStatement(remove);
            preparedStatement.setLong(1,id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try {
            Statement statement = Util.getConnection().createStatement();
            ResultSet res = statement.executeQuery("SELECT * FROM users");

            while (res.next()) {
                String name = res.getString("name");
                String lastname = res.getString("lastname");
                byte age = res.getByte("age");

                users.add(new User(name,lastname,age));

            }
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    return users;
    }

    public void cleanUsersTable() {
        try {
            Statement statement = Util.getConnection().createStatement();
            statement.execute("TRUNCATE TABLE users");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
