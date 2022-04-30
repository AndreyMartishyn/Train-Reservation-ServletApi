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
import java.util.List;
import java.util.Optional;

public class AdminUsersPageCommand implements ICommand {
    private static final UserDao userDao = new UserDaoImpl();
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Optional<List<User>> usersFromDb = userDao.getAll();
        if (usersFromDb.isPresent()){
            request.setAttribute("users", usersFromDb.get());
        }else {
            request.setAttribute("no-users", "No users found at the moment");
        }
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(Constants.ADMIN_USERS);
        requestDispatcher.forward(request,response);

    }
}
