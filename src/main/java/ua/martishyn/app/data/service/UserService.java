package ua.martishyn.app.data.service;

import ua.martishyn.app.data.dao.interfaces.UserDao;
import ua.martishyn.app.data.entities.User;

import java.util.List;
import java.util.Optional;

public class UserService {
    private UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public Optional<User> getById(int id) {
        return userDao.getById(id);
    }

    public Optional<User> authenticate(String email) {
        return userDao.getByEmail(email);
    }

    public Optional<List<User>> getAll() {
        return userDao.getAll();
    }

    public boolean createUser(User user) {
        return userDao.createUser(user);
    }

    public boolean update(User user) {
        return userDao.update(user);
    }

    public boolean delete(int id) {
        return userDao.delete(id);
    }
}
