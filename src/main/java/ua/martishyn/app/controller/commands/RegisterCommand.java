package ua.martishyn.app.controller.commands;

import ua.martishyn.app.data.dao.impl.UserDaoImpl;
import ua.martishyn.app.data.dao.interfaces.UserDao;
import ua.martishyn.app.data.entities.User;
import ua.martishyn.app.data.entities.enums.Role;
import ua.martishyn.app.data.utils.ViewPath;
import ua.martishyn.app.data.utils.password_encoding.PasswordEncodingService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

public class RegisterCommand implements ICommand {
    private final UserDao userDao = new UserDaoImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String password = request.getParameter("password");
        String email = request.getParameter("email");

        String encodedPassword = PasswordEncodingService.makeHash(password);
        String redirect = ViewPath.LOGIN_PAGE;

        User user = User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .password(encodedPassword)
                .email(email)
                .role(Role.CUSTOMER)
                .build();
        userDao.createUser(user);
        System.out.println("User created");

        response.sendRedirect(redirect);
    }
}