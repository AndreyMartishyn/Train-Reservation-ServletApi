package ua.martishyn.app.data.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.martishyn.app.data.dao.impl.UserDaoImpl;
import ua.martishyn.app.data.dao.interfaces.UserDao;
import ua.martishyn.app.data.entities.RoutePoint;
import ua.martishyn.app.data.entities.User;
import ua.martishyn.app.data.entities.enums.Role;
import ua.martishyn.app.data.utils.constants.ViewConstants;
import ua.martishyn.app.data.utils.constants.UserServiceConstants;
import ua.martishyn.app.data.utils.password_encoding.PasswordEncodingService;
import ua.martishyn.app.data.utils.validator.DataInputValidator;
import ua.martishyn.app.data.utils.validator.DataInputValidatorImpl;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

public class UserService {
    private static final Logger log = LogManager.getLogger(UserService.class);
    private final UserDao userDao;
    private final DataInputValidator dataInputValidator;

    public UserService() {
        userDao = new UserDaoImpl();
        dataInputValidator = new DataInputValidatorImpl();
    }

    public Optional<User> authenticateUserByEmail(String email) {
        return userDao.getByEmail(email);
    }

    public Optional<List<User>> getUsersPaginated(int offSet, int entriesPerPage) {
        return userDao.getUsersPaginated(offSet,entriesPerPage);
    }

    public boolean deleteUserById(int id) {
        return userDao.delete(id);
    }

    public boolean isUserLogged(HttpServletRequest request) {
        String email = request.getParameter(UserServiceConstants.EMAIL);
        String password = request.getParameter(UserServiceConstants.PASSWORD);
        if (isLoginCredentialsInputValid(email, password)) {
            Optional<User> optionalUser = authenticateUserByEmail(email);
            if (!optionalUser.isPresent()) {
                request.setAttribute(ViewConstants.ERROR_VALIDATION, UserServiceConstants.USER_NOT_FOUND);
                log.error("No user found with email --> {}", email);
            } else {
                User loggedUser = optionalUser.get();
                if (loggedUser.getPassword().equals(PasswordEncodingService.makeHash(password))) {
                    log.trace("Found user in DB --> {}, role : {}", email, loggedUser.getRole());
                    request.getSession().setAttribute("user", loggedUser);
                    request.getSession().setAttribute("role", loggedUser.getRole());
                    return true;
                } else {
                    request.setAttribute(ViewConstants.ERROR_VALIDATION, UserServiceConstants.PASSWORD_INCORRECT);
                }
            }
        } else {
            request.setAttribute(ViewConstants.ERROR_VALIDATION, UserServiceConstants.INVALID_INPUT);
        }
        return false;
    }

    public boolean isLoginCredentialsInputValid(String email, String password) {
        return dataInputValidator.isValidEmailField(email) &&
                dataInputValidator.isValidPasswordField(password);
    }

    public void updateUserRoleAdmin(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        Role role = Role.valueOf(request.getParameter("role"));
        userDao.updateUserRole(role, id);
    }

    public boolean createUser(HttpServletRequest request) {
        User userFromRequest = getUserFromRequest(request);
        User createdUser = User.builder()
                .firstName(userFromRequest.getFirstName())
                .lastName(userFromRequest.getLastName())
                .email(userFromRequest.getEmail())
                .password(userFromRequest.getPassword())
                .role(userFromRequest.getRole())
                .build();
        return userDao.createUser(createdUser);
    }

    public User getUserFromRequest(HttpServletRequest request){
        int userId = Integer.parseInt(request.getParameter("id"));
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String hashPass =  PasswordEncodingService.makeHash(password);
        Role role = Role.valueOf(request.getParameter("role"));

        return User.builder()
                .id(userId)
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .password(hashPass)
                .role(role)
                .build();
    }

    public boolean isUserInputIsValid(HttpServletRequest request) {
        String firstName = request.getParameter(UserServiceConstants.FIRST_NAME).trim();
        if (!dataInputValidator.isValidNameField(firstName)) {
            request.setAttribute(ViewConstants.ERROR_VALIDATION, UserServiceConstants.FIRST_NAME_INVALID_MESS);
            return false;
        }
        String lastName = request.getParameter(UserServiceConstants.LAST_NAME).trim();
        if (!dataInputValidator.isValidNameField(lastName)) {
            request.setAttribute(ViewConstants.ERROR_VALIDATION, UserServiceConstants.LAST_NAME_INVALID_MESS);
            return false;
        }
        String email = request.getParameter(UserServiceConstants.EMAIL).trim();
        if (!dataInputValidator.isValidEmailField(email)) {
            request.setAttribute(ViewConstants.ERROR_VALIDATION, UserServiceConstants.EMAIL_INVALID_MESS);
            return false;
        }
        String password = request.getParameter(UserServiceConstants.PASSWORD).trim();
        if (!dataInputValidator.isValidPasswordField(password)) {
            request.setAttribute(ViewConstants.ERROR_VALIDATION, UserServiceConstants.PASSWORD_INVALID_MESS);
            return false;
        }
        return true;
    }


}
