package ua.martishyn.app.data.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    private static final Logger log = LogManager.getLogger(UserDaoImpl.class);
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
             PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet userResultSet = preparedStatement.executeQuery();
            while (userResultSet.next()) {
                userFromDb = getUserFromResultSet(userResultSet);
            }
        } catch (SQLException exception) {
            log.error("Problems with getting user by id {}", exception.toString());
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
            log.error("Problems with getting user by email {}", exception.toString());
        }
        return Optional.ofNullable(userFromDb);
    }

    @Override
    public Optional<List<User>> getAll() {
        List<User> usersList = new ArrayList<>();
        try (Connection connection = DataBasePoolManager.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_USERS)) {
            ResultSet usersResultSet = preparedStatement.executeQuery();
            while (usersResultSet.next()) {
                usersList.add(getUserFromResultSet(usersResultSet));
            }
        } catch (SQLException exception) {
            log.error("Problems with getting all users {}", exception.toString());
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
            log.error("Problems with creating user {}", exception.toString());
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
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DataBasePoolManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(UPDATE_USER);
            connection.setAutoCommit(false);
            createUserStatement(preparedStatement, user);
            preparedStatement.setInt(6, user.getId());
            preparedStatement.executeUpdate();
            connection.commit();

        } catch (SQLException exception) {
            log.error("Problems with updating user by id {}", exception.toString());
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException e) {
                    log.error("Problems with transaction {}", e.toString());
                }
            }
        } finally {
            close(connection);
            close(preparedStatement);
        }
        return true;
    }

    @Override
    public boolean delete(int id) {
        boolean deleted = false;
        try (Connection connection = DataBasePoolManager.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER)) {
            preparedStatement.setInt(1, id);
            deleted = preparedStatement.executeUpdate() > 0;
        } catch (SQLException exception) {
            log.error("Problems with deleting user by id {}", exception.toString());
        }
        return deleted;
    }

    private static void close(AutoCloseable ac) {
        if (ac != null) {
            try {
                ac.close();
            } catch (Exception e) {
                log.error("Failed closing resource {}", e.toString());
            }
        }
    }
}
