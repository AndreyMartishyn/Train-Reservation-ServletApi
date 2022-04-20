package ua.martishyn.app.controller.commands;

import ua.martishyn.app.data.dao.impl.UserDaoImpl;
import ua.martishyn.app.data.dao.interfaces.UserDao;
import ua.martishyn.app.data.entities.User;
import ua.martishyn.app.data.utils.ViewPath;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

public class LoginCommand implements ICommand {
    private final UserDao userDao = new UserDaoImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession currentSession = request.getSession();
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        //  TODO : validation of password
        Optional<User> user = userDao.getByEmail(email);

        String redirect = ViewPath.LOGIN_PAGE;

        if (checkUserPresenceInDB(email)) {
            User userView = user.get();
            currentSession.setAttribute("user", userView);
            if (user.get().getRole().toString().equals("ADMIN")) {
                redirect = "/admin-main.command";
                System.out.println("Admin logged in");
            } else {
                System.out.println("Customer " + user.get().getEmail() + " logged in");
                redirect = ViewPath.CUSTOMER_MAIN;
            }
        } else {
            request.setAttribute("invalidData", "Invalid user credentials");
            System.out.println("Wrong user credentials");

        }
        response.sendRedirect(request.getContextPath() + redirect);
    }

    private boolean checkUserPresenceInDB(String email) {
        Optional<User> user = userDao.getByEmail(email);
        if (!user.isPresent() || user.get().getEmail() == null) {
            return false;
        }
        return user.get().getEmail().equals(email) && user.get().getId() > 0;
    }
}
