package ua.martishyn.app.controller;

import ua.martishyn.app.data.dao.impl.UserDaoImpl;
import ua.martishyn.app.data.dao.interfaces.UserDao;
import ua.martishyn.app.data.entities.User;
import ua.martishyn.app.data.entities.enums.Role;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/login")
public class LoginController extends HttpServlet {
    private UserDao userDao;

    @Override
    public void init() {
        userDao = new UserDaoImpl();
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("pages/user-login.jsp");

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession currentSession = request.getSession();
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        //  TODO : validation of password

        Optional<User> user = userDao.getByEmail(email);
        if (checkUserPresenceInDB(email)) {
            currentSession.setAttribute("user", user);
            System.out.println("User " + user.get().getEmail() + " taken from db");
            response.sendRedirect("pages/user-login-ok.jsp");

        } else {
            request.setAttribute("invalidData", "Invalid user credentials");
            System.out.println("Wrong user credentials");
            request.getRequestDispatcher("pages/user-login.jsp").forward(request, response);
        }
    }

    private boolean checkUserPresenceInDB(String email) {
        Optional<User> user = userDao.getByEmail(email);
        if (!user.isPresent() || user.get().getEmail() == null) {
            return false;
        }
        return user.get().getEmail().equals(email) && user.get().getId() > 0;
    }
}



