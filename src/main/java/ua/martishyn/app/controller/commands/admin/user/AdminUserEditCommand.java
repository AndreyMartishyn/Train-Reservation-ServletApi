package ua.martishyn.app.controller.commands.admin.user;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    private static final Logger log = LogManager.getLogger(AdminUserEditCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher;
        Optional<User> userFromDb = getUser(request);
        if (userFromDb.isPresent()) {
            request.setAttribute("user", userFromDb.get());
            log.info("Editing user with id {}", userFromDb.get().getId());
            requestDispatcher = request.getRequestDispatcher(Constants.ADMIN_USERS_EDIT);
        } else {
            requestDispatcher = request.getRequestDispatcher(Constants.ADMIN_USERS);
        }
        requestDispatcher.forward(request, response);
    }

    private Optional<User> getUser(HttpServletRequest request) {
        UserDao userDao = new UserDaoImpl();
        int id = Integer.parseInt(request.getParameter("id"));
        return userDao.getById(id);
    }
}

