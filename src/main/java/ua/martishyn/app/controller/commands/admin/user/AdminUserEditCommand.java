package ua.martishyn.app.controller.commands.admin.user;

import ua.martishyn.app.controller.commands.ICommand;
import ua.martishyn.app.controller.filters.HasRole;
import ua.martishyn.app.data.entities.enums.Role;
import ua.martishyn.app.data.utils.constants.ViewConstants;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@HasRole(role = Role.ADMIN)
public class AdminUserEditCommand implements ICommand {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        passIdToRequest(request);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(ViewConstants.ADMIN_USERS_EDIT);
        requestDispatcher.forward(request, response);
    }

    private void passIdToRequest(HttpServletRequest request) {
        String userId = (request.getParameter("id"));
        request.setAttribute("id", userId);
    }
}

