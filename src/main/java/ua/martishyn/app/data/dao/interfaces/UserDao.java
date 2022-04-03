package ua.martishyn.app.data.dao.interfaces;

import ua.martishyn.app.data.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {

    Optional<User> getById(int id);

    Optional<User> getByEmail(String email);

    List<User> getAll();

    boolean createUser(User user);

    void update(User user);

    void delete(User user);

}
