package ua.martishyn.app.controller.commands.admin.user;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.martishyn.app.controller.commands.ICommand;
import ua.martishyn.app.controller.filters.HasRole;
import ua.martishyn.app.data.dao.impl.UserDaoImpl;
import ua.martishyn.app.data.dao.interfaces.UserDao;
import ua.martishyn.app.data.entities.User;
import ua.martishyn.app.data.entities.enums.Role;
import ua.martishyn.app.data.service.UserService;
import ua.martishyn.app.data.utils.Constants;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@HasRole(role = Role.ADMIN)
public class AdminUsersPageCommand implements ICommand {
    private static final Logger log = LogManager.getLogger(AdminUsersPageCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Optional<List<User>> usersFromDb = getUsers();
        if (usersFromDb.isPresent()) {
            log.info("Loading stations from db. Stations quantity : {}", usersFromDb.get().size());
            request.setAttribute("users", usersFromDb.get());
        } else {
            request.setAttribute("no-users", "No users found at the moment");
        }
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(Constants.ADMIN_USERS);
        requestDispatcher.forward(request, response);
    }

    private Optional<List<User>> getUsers() {
        UserService userService = new UserService(new UserDaoImpl());
        return userService.getAll();
    }
}
