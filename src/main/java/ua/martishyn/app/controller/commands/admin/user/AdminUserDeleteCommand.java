package ua.martishyn.app.controller.commands.admin.user;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.martishyn.app.controller.commands.ICommand;
import ua.martishyn.app.controller.filters.HasRole;
import ua.martishyn.app.data.dao.impl.UserDaoImpl;
import ua.martishyn.app.data.entities.enums.Role;
import ua.martishyn.app.data.service.UserService;
import ua.martishyn.app.data.utils.Constants;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@HasRole(role = Role.ADMIN)
public class AdminUserDeleteCommand implements ICommand {
    private static final Logger log = LogManager.getLogger(AdminUserDeleteCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (deleteUser(request)) {
            log.info("Deleting user done");
            response.sendRedirect("users-page.command");
        } else {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(Constants.ADMIN_USERS);
            requestDispatcher.forward(request, response);
        }
    }

    private boolean deleteUser(HttpServletRequest request) {
        UserService userService = new UserService(new UserDaoImpl());
        int id = Integer.parseInt(request.getParameter("id"));
        return userService.delete(id);
    }
}
