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
import java.util.Optional;

@HasRole(role = Role.ADMIN)
public class AdminUserEditCommand implements ICommand {
    private static final Logger log = LogManager.getLogger(AdminUserEditCommand.class);
    private final UserService userService;

    public AdminUserEditCommand() {
        userService = new UserService();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher;
        if (isUserExist(request)) {
            requestDispatcher = request.getRequestDispatcher(ViewConstants.ADMIN_USERS_EDIT);
        } else {
            requestDispatcher = request.getRequestDispatcher(ViewConstants.ADMIN_USERS);
        }
        requestDispatcher.forward(request, response);
    }

    private boolean isUserExist(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        Optional<User> userFromDb = userService.getUserById(id);
        if (userFromDb.isPresent()) {
            request.setAttribute("user", userFromDb.get());
            log.info("Editing user with id {}", userFromDb.get().getId());
        }
        return userFromDb.isPresent();
    }
}

