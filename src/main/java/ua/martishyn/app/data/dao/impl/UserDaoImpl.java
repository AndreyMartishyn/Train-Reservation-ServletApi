package ua.martishyn.app.data.dao.impl;

import ua.martishyn.app.data.dao.interfaces.UserDao;
import ua.martishyn.app.data.entities.User;
import ua.martishyn.app.data.entities.enums.Role;
import ua.martishyn.app.data.utils.DataBasePoolManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDaoImpl implements UserDao {
    private static final String CREATE_USER = "INSERT INTO users VALUES (DEFAULT, ?, ?, ?, ?, ?);";
    private static final String GET_USER_BY_ID = "SELECT * FROM users WHERE id = ?;";
    private static final String GET_USER_BY_EMAIL = "SELECT * FROM users WHERE email = ?;";
    private static final String GET_ALL_USERS = "SELECT * FROM users;";
    private static final String UPDATE_USER = "UPDATE users SET first_name = ?, last_name = ?, " +
            "pass_encoded = ?, email = ?, role = ? WHERE id = ?;";
    private static final String DELETE_USER = "DELETE FROM users WHERE id =?";

    @Override
    public Optional<User> getById(int id) {
        User userFromDb = null;
        try (Connection connection = DataBasePoolManager.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_BY_ID);) {
            preparedStatement.setInt(1, id);
            ResultSet userResultSet = preparedStatement.executeQuery();
            while (userResultSet.next()) {
                userFromDb = getUserFromResultSet(userResultSet);
            }
        } catch (SQLException exception) {
            System.out.println("Unable to get user from db " + exception);
        }
        return Optional.ofNullable(userFromDb);
    }

    @Override
    public Optional<User> getByEmail(String email) {
        User userFromDb = new User();
        try (Connection connection = DataBasePoolManager.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_BY_EMAIL);) {
            preparedStatement.setString(1, email);
            ResultSet userResultSet = preparedStatement.executeQuery();
            while (userResultSet.next()) {
                userFromDb = getUserFromResultSet(userResultSet);
            }
        } catch (SQLException exception) {
            System.out.println("Unable to get user from db by email" + exception);
        }
        return Optional.ofNullable(userFromDb);
    }

    @Override
    public Optional<List<User>> getAll() {
        List<User> usersList = new ArrayList<>();
        try(Connection connection = DataBasePoolManager.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_USERS)){
            ResultSet usersResultSet = preparedStatement.executeQuery();
            while (usersResultSet.next()){
                usersList.add(getUserFromResultSet(usersResultSet));
            }
        } catch (SQLException exception) {
            System.out.println("Unable to get stations from db" + exception);
        }
        return Optional.of(usersList);
    }

    @Override
    public boolean createUser(User user) {
        try (Connection connection = DataBasePoolManager.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_USER)) {
            createUserStatement(preparedStatement, user);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException exception) {
            System.out.println("Something wrong with creating user " + exception);
        }
        return false;
    }

    private void createUserStatement(PreparedStatement preparedStatement, User user) throws SQLException {
        preparedStatement.setString(1, user.getFirstName());
        preparedStatement.setString(2, user.getLastName());
        preparedStatement.setString(3, user.getPassword());
        preparedStatement.setString(4, user.getEmail());
        preparedStatement.setString(5, user.getRole().name());
    }

    private User getUserFromResultSet(ResultSet resultSet) throws SQLException {
        return User.builder()
                .id(resultSet.getInt(1))
                .firstName(resultSet.getString(2))
                .lastName(resultSet.getString(3))
                .password(resultSet.getString(4))
                .email(resultSet.getString(5))
                .role(Role.valueOf(resultSet.getString(6)))
                .build();
    }

    @Override
    public boolean update(User user) {
        try (Connection connection = DataBasePoolManager.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER)) {
            connection.setAutoCommit(false);
            createUserStatement(preparedStatement, user);
            preparedStatement.setInt(6, user.getId());
            if (preparedStatement.executeUpdate() > 0) {
                connection.commit();
                return true;
            }
            connection.rollback();
        } catch (SQLException exception) {
            System.out.println("Unable to update user " + exception);
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        boolean deleted = false;
        try (Connection connection = DataBasePoolManager.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER)) {
            preparedStatement.setInt(1, id);
            deleted = preparedStatement.executeUpdate() > 0;
        } catch (SQLException exception) {
            System.out.println("Something wrong with deleting of user " + exception);
        }
        return deleted;
    }
}
