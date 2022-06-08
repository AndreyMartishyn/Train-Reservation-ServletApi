package ua.martishyn.app.data.dao.interfaces;

import ua.martishyn.app.data.entities.RoutePoint;
import ua.martishyn.app.data.entities.User;
import ua.martishyn.app.data.entities.enums.Role;

import java.util.List;
import java.util.Optional;

public interface UserDao {

    Optional<User> getByEmail(String email);

    Optional<List<User>> getUsersPaginated(int offSet, int entriesPerPage);

    boolean createUser(User user);

    boolean update(User user);

    boolean delete(int id);

    public void updateUserRole(Role role, int userId);


}
