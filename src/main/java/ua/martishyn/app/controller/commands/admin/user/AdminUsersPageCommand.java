package ua.martishyn.app.controller.commands.admin.user;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.martishyn.app.controller.commands.ICommand;
import ua.martishyn.app.controller.filters.HasRole;
import ua.martishyn.app.data.entities.User;
import ua.martishyn.app.data.entities.enums.Role;
import ua.martishyn.app.data.service.UserService;
import ua.martishyn.app.data.utils.ViewConstants;

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
    private final UserService userService;

    public AdminUsersPageCommand() {
        userService = new UserService();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Optional<List<User>> usersFromDb = userService.getAllUsers();
        if (usersFromDb.isPresent()) {
            log.info("Loading stations from db. Stations quantity : {}", usersFromDb.get().size());
            request.setAttribute("users", usersFromDb.get());
        } else {
            request.setAttribute("no-users", "No users found at the moment");
        }
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(ViewConstants.ADMIN_USERS);
        requestDispatcher.forward(request, response);
    }
}
