package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl extends Util implements UserDao {
    private final Connection connection = Util.getConnection();
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {

        String sqlQuery = "CREATE TABLE IF NOT EXISTS users " +
                "(id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(64), lastname VARCHAR(64), age TINYINT)";

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sqlQuery);
            System.out.println("Таблица создана");
        } catch (SQLException e) {
            System.out.println("createUsersTable EXCEPTION");

        }


    }

    public void dropUsersTable() {
        String sqlQuery = "DROP TABLE IF EXISTS users";

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sqlQuery);
            System.out.println("Таблица удалена");
        } catch (SQLException e) {
            System.out.println("dropUsersTable EXCEPTION");
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sqlQuery = "INSERT INTO users (name, lastname, age) VALUES (?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);

            preparedStatement.executeUpdate();
            System.out.println("User с именем – " + name + " добавлен в базу данных");

        } catch (SQLException e) {
            System.out.println("saveUser EXCEPTION");
            e.printStackTrace();

        }
    }

    public void removeUserById(long id) {
        String sqlQuery = "DELETE FROM users WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {

            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();

            System.out.println("User удален");

        } catch (SQLException e) {
            System.out.println("removeUserByID EXCEPTION");
            e.printStackTrace();

        }
    }

    public List<User> getAllUsers() {
        List<User> allUsersList = new ArrayList<>();
        String sqlQuery = "SELECT * FROM users";

        try (ResultSet result = connection.createStatement().executeQuery(sqlQuery)) {

            while(result.next()) {
                User user = new User();
                user.setId(result.getLong("id"));
                user.setName(result.getString("name"));
                user.setLastName(result.getString("lastname"));
                user.setAge(result.getByte("age"));

                allUsersList.add(user);
            }

        } catch (SQLException e) {
            System.out.println("getAllUsers EXCEPTION");
            e.printStackTrace();

        }
        return allUsersList;
    }

    public void cleanUsersTable() {
        String sqlQuery = "TRUNCATE users";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("cleanUsersTable EXCEPTION");
            e.printStackTrace();

        }
    }
}
