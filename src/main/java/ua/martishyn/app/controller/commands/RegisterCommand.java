package ua.martishyn.app.controller.commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.martishyn.app.data.dao.impl.UserDaoImpl;
import ua.martishyn.app.data.entities.User;
import ua.martishyn.app.data.entities.enums.Role;
import ua.martishyn.app.data.service.UserService;
import ua.martishyn.app.data.utils.Constants;
import ua.martishyn.app.data.utils.password_encoding.PasswordEncodingService;
import ua.martishyn.app.data.utils.validator.DataInputValidator;
import ua.martishyn.app.data.utils.validator.DataInputValidatorImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegisterCommand implements ICommand {
    private static final Logger log = LogManager.getLogger(RegisterCommand.class);
    private static final DataInputValidator dataValidator = new DataInputValidatorImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (createUser(request)) {
            log.info("User created successfully!");
            response.sendRedirect("index.command");
        } else {
            log.info("User not created");
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(Constants.REGISTER_PAGE);
            requestDispatcher.forward(request, response);
        }
    }

    private boolean createUser(HttpServletRequest request) {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        if (!isValidUserInput(firstName, lastName, email, password, request)) {
            return false;
        }

        String encodedPassword = PasswordEncodingService.makeHash(password);
        User user = User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .password(encodedPassword)
                .email(email)
                .role(Role.CUSTOMER)
                .build();
        UserService userService = new UserService(new UserDaoImpl());
        return userService.createUser(user);
    }

    protected boolean isValidUserInput(String firstName, String lastName,
                                       String email, String password, HttpServletRequest request) {
        if (!dataValidator.isValidNameField(firstName.trim())) {
            request.setAttribute(Constants.ERROR_VALIDATION, "Enter valid first name ");
            return false;
        }
        if (!dataValidator.isValidNameField(lastName.trim())) {
            request.setAttribute(Constants.ERROR_VALIDATION, "Enter valid last name ");
            return false;
        }
        if (!dataValidator.isValidPasswordField(password.trim())) {
            request.setAttribute(Constants.ERROR_VALIDATION, "Enter valid password ");
            return false;
        }
        if (!dataValidator.isValidEmailField(email.trim())) {
            request.setAttribute(Constants.ERROR_VALIDATION, "Enter valid email ");
            return false;
        }
        return true;
    }
}