package ua.martishyn.app.controller.commands.admin.user;

import ua.martishyn.app.controller.commands.ICommand;
import ua.martishyn.app.data.dao.impl.UserDaoImpl;
import ua.martishyn.app.data.dao.interfaces.UserDao;
import ua.martishyn.app.data.entities.User;
import ua.martishyn.app.data.entities.enums.Role;
import ua.martishyn.app.data.utils.Constants;
import ua.martishyn.app.data.utils.password_encoding.PasswordEncodingService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AdminUserEditPostCommand implements ICommand {
    private static final UserDao userDao = new UserDaoImpl();
    //TODO VALIDATION OF DATA

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (updateUser(request)) {
            response.sendRedirect("users-page.command");
            return;
        }
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(Constants.ADMIN_USERS);
        requestDispatcher.forward(request, response);
    }

    private boolean updateUser(HttpServletRequest request) {
        int userId = Integer.parseInt(request.getParameter("id"));
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");

        String password = request.getParameter("password");
        String hashPass = null;
        try {
            hashPass = PasswordEncodingService.makeHash(password);
        } catch (Exception e) {
            System.out.println("Something wrong with password hashing " + e);
        }
        Role role = Role.valueOf(request.getParameter("role"));

        User updatedUser = User.builder()
                .id(userId)
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .password(hashPass)
                .role(role)
                .build();
        return userDao.update(updatedUser);

    }
}