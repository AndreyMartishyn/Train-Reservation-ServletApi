package ua.martishyn.app.controller.commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.martishyn.app.data.dao.impl.UserDaoImpl;
import ua.martishyn.app.data.dao.interfaces.UserDao;
import ua.martishyn.app.data.entities.User;
import ua.martishyn.app.data.entities.enums.Role;
import ua.martishyn.app.data.utils.password_encoding.PasswordEncodingService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegisterCommand implements ICommand {
    private static final Logger log = LogManager.getLogger(RegisterCommand.class);
    private final UserDao userDao = new UserDaoImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String password = request.getParameter("password");
        String email = request.getParameter("email");

        String encodedPassword = PasswordEncodingService.makeHash(password);
        String redirect = "index.command";

        User user = User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .password(encodedPassword)
                .email(email)
                .role(Role.CUSTOMER)
                .build();
        userDao.createUser(user);
        log.info("User {} created successfully!", user.getEmail());
        response.sendRedirect(redirect);
    }
}