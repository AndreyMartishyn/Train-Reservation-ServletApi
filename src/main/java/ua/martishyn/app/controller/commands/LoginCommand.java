package ua.martishyn.app.controller.commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

public class LoginCommand implements ICommand {
    private static final Logger log = LogManager.getLogger(LoginCommand.class);
    private final UserDao userDao = new UserDaoImpl();
    private final DataInputValidator validator = new DataInputValidatorImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");


        if (checkForValidDataInput(email, password)) {
            Optional<User> optionalUser = userDao.getByEmail(email);
            if (!checkUserPresenceInDB(email)) {
                request.getSession().setAttribute("noSuchUser", "No user found");
                log.error("No user found with email --> {}", optionalUser.get().getEmail());
            } else {
                User loggedUser = optionalUser.get();
                if (loggedUser.getPassword().equals(PasswordEncodingService.makeHash(password))) {
                    log.trace("Found user in DB --> {}", loggedUser.getEmail());
                    request.getSession().setAttribute("user", loggedUser);
                 //   roleRedirect = getRole(loggedUser);
                    log.trace("User ROLE  --> {}", loggedUser.getRole());
                    response.sendRedirect("index.command");
                    return;
                } else {
                    log.error("Wrong password for user in DB --> {}", loggedUser.getEmail());
                    request.setAttribute("notCorrectPass", "Enter correct password");
                }
            }
        } else {
            request.setAttribute("notValidInput", "Enter valid input");
        }
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(Constants.LOGIN_PAGE);
        requestDispatcher.forward(request,response);
    }

    private boolean checkForValidDataInput(String email, String password) {
        return validator.isValidEmailField(email) &&
                validator.isValidPasswordField(password);
    }

    private String getRole(User user) {
        if (user.getRole().equals(Role.ADMIN)) {
            return "admin-main.command";
        }
        return "user-main.command";
    }

    private boolean checkUserPresenceInDB(String email) {
        Optional<User> user = userDao.getByEmail(email);
        if (!user.isPresent() || user.get().getEmail() == null) {
            return false;
        }
        return user.get().getEmail().equals(email) && user.get().getId() > 0;
    }
}
