package ua.martishyn.app.controller.commands.admin.user;

import ua.martishyn.app.controller.commands.ICommand;
import ua.martishyn.app.data.dao.impl.UserDaoImpl;
import ua.martishyn.app.data.dao.interfaces.UserDao;
import ua.martishyn.app.data.entities.User;
import ua.martishyn.app.data.utils.Constants;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class AdminUserEditCommand implements ICommand {
    private static final UserDao userDao = new UserDaoImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Optional<User> userFromDb = getUser(request);
        if (userFromDb.isPresent()) {
            System.out.println("Getting user from db " + userFromDb.get().getEmail() );
            request.setAttribute("user", userFromDb.get());
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(Constants.ADMIN_USERS_EDIT);
            requestDispatcher.forward(request, response);
        } else {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(Constants.ADMIN_USERS);
            requestDispatcher.forward(request, response);
        }
    }

    private Optional<User> getUser(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        return userDao.getById(id);
    }
}

