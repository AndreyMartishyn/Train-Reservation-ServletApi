package ua.martishyn.app.controller.commands.admin.route;

import ua.martishyn.app.controller.commands.ICommand;
import ua.martishyn.app.data.dao.impl.RouteDaoImpl;
import ua.martishyn.app.data.dao.interfaces.RouteDao;
import ua.martishyn.app.data.utils.Constants;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SingleRouteDeleteCommand implements ICommand {
    private static final RouteDao singleRouteDao = new RouteDaoImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (deleteRoutePart(request)) {
            response.sendRedirect("routes-page.command");
            return;
        }
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(Constants.ADMIN_ROUTES);
        requestDispatcher.forward(request, response);
    }


    private boolean deleteRoutePart(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        return singleRouteDao.deleteSingleRoute(id);
    }
}
