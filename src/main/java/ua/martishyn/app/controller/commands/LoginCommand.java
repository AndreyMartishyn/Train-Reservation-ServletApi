package ua.martishyn.app.controller.commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.martishyn.app.data.dao.impl.UserDaoImpl;
import ua.martishyn.app.data.entities.User;
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
import java.util.Optional;


public class LoginCommand implements ICommand {
    private static final Logger log = LogManager.getLogger(LoginCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (isUserLogged(request)) {
            response.sendRedirect("index.command");
        } else {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(Constants.LOGIN_PAGE);
            requestDispatcher.forward(request, response);
        }
    }

    private boolean isUserLogged(HttpServletRequest request) {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        if (checkForValidDataInput(email, password)) {
            Optional<User> optionalUser = getUserByEmail(email);
            if (!checkUserPresenceInDB(email) || !optionalUser.isPresent()) {
                request.setAttribute(Constants.ERROR_VALIDATION, "No user found");
                log.error("No user found with email --> {}", email);
            } else {
                User loggedUser = optionalUser.get();
                if (!loggedUser.getPassword().equals(PasswordEncodingService.makeHash(password))) {
                    request.setAttribute(Constants.ERROR_VALIDATION, "Enter correct password");
                } else {
                    log.trace("Found user in DB --> {}, role : {}", email, loggedUser.getRole());
                    request.getSession().setAttribute("user", loggedUser);
                    request.getSession().setAttribute("role", loggedUser.getRole());
                    return true;
                }
            }
        } else {
            request.setAttribute(Constants.ERROR_VALIDATION, "Enter valid input");
        }
        return false;
    }

    public Optional<User> getUserByEmail(String email) {
        UserService userService = new UserService(new UserDaoImpl());
        return userService.authenticate(email);
    }

    private boolean checkForValidDataInput(String email, String password) {
        DataInputValidator validator = new DataInputValidatorImpl();
        return validator.isValidEmailField(email) &&
                validator.isValidPasswordField(password);
    }

    private boolean checkUserPresenceInDB(String email) {
        Optional<User> user = getUserByEmail(email);
        if (!user.isPresent() || user.get().getEmail() == null) {
            return false;
        }
        return user.get().getEmail().equals(email) && user.get().getId() > 0;
    }


}
