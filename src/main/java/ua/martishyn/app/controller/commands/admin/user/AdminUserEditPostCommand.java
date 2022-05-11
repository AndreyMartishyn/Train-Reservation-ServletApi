package ua.martishyn.app.controller.commands.admin.user;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.martishyn.app.controller.commands.ICommand;
import ua.martishyn.app.data.dao.impl.UserDaoImpl;
import ua.martishyn.app.data.dao.interfaces.UserDao;
import ua.martishyn.app.data.entities.User;
import ua.martishyn.app.data.entities.enums.Role;
import ua.martishyn.app.data.utils.Constants;
import ua.martishyn.app.data.utils.password_encoding.PasswordEncodingService;
import ua.martishyn.app.data.utils.validator.DataInputValidator;
import ua.martishyn.app.data.utils.validator.DataInputValidatorImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AdminUserEditPostCommand implements ICommand {
    private static final Logger log = LogManager.getLogger(AdminUserEditPostCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (userDataValidation(request)) {
            if (updateUser(request)) {
                log.info("User updated successfully");
                response.sendRedirect("users-page.command");
                return;
            } else {
                request.setAttribute("errorLogic", "Problems with updating route");
            }
        } else {
            request.setAttribute("errorLogic", "Problems with updating route");
        }
        log.info("Unfortunately, user not updated. Redirect to view --> {}", Constants.ADMIN_USERS);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(Constants.ADMIN_USERS);
        requestDispatcher.forward(request, response);
    }

    private boolean userDataValidation(HttpServletRequest request) {
        final DataInputValidator dataValidator = new DataInputValidatorImpl();
        String id = request.getParameter("id").trim();
        if (!dataValidator.isValidNumInput(id)) {
            request.setAttribute(Constants.ERROR_VALIDATION, "Enter valid id");
            return false;
        }
        String firstName = request.getParameter("firstName").trim();
        if (!dataValidator.isValidNameField(firstName)) {
            request.setAttribute(Constants.ERROR_VALIDATION, "Enter valid first-name");
            return false;
        }
        String lastName = request.getParameter("lastName").trim();
        if (!dataValidator.isValidNameField(lastName)) {
            request.setAttribute(Constants.ERROR_VALIDATION, "Enter valid last-name");
            return false;
        }
        String email = request.getParameter("email").trim();
        if (!dataValidator.isValidEmailField(email)) {
            request.setAttribute(Constants.ERROR_VALIDATION, "Enter valid email");
            return false;
        }
        String password = request.getParameter("password").trim();
        if (!dataValidator.isValidPasswordField(password)) {
            request.setAttribute(Constants.ERROR_VALIDATION, "Enter valid password");
            return false;
        }
        return true;
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
            log.error("Something wrong with password hashing {}" , e.getMessage());
        }
        Role role = Role.valueOf(request.getParameter("role"));

        UserDao userDao = new UserDaoImpl();
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