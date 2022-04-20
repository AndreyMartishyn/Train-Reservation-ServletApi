package ua.martishyn.app.controller.commands.admin.user;

import ua.martishyn.app.controller.commands.ICommand;
import ua.martishyn.app.data.dao.impl.StationDaoImpl;
import ua.martishyn.app.data.dao.impl.UserDaoImpl;
import ua.martishyn.app.data.dao.interfaces.StationDao;
import ua.martishyn.app.data.dao.interfaces.UserDao;
import ua.martishyn.app.data.utils.ViewPath;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserDeleteCommand implements ICommand {
    private static final UserDao userDao = new UserDaoImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (deleteStation(request)) {
            response.sendRedirect("users-page.command");
            return;
        }
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(ViewPath.ADMIN_STATIONS);
        requestDispatcher.forward(request, response);
    }

    private boolean deleteStation(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        return userDao.delete(id);
    }
}
