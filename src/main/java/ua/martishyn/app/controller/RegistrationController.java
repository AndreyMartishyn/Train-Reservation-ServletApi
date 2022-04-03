package ua.martishyn.app.controller;

import ua.martishyn.app.data.dao.impl.UserDaoImpl;
import ua.martishyn.app.data.dao.interfaces.UserDao;
import ua.martishyn.app.data.entities.User;
import ua.martishyn.app.data.entities.enums.Role;
import ua.martishyn.app.data.utils.password_encoding.PasswordEncodingService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/register")
public class RegistrationController extends HttpServlet {
    private UserDao userDao;

    @Override
    public void init() {
        userDao = new UserDaoImpl();
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("pages/user-register.jsp");

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String password = request.getParameter("password");
        String email = request.getParameter("email");

        String encodedPassword = PasswordEncodingService.makeHash(password);

        User user = User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .password(encodedPassword)
                .email(email)
                .role(Role.CUSTOMER)
                .build();
        userDao.createUser(user);
        System.out.println("User created");
        response.sendRedirect("pages/user-registered-ok.jsp");
    }

}



