package ua.martishyn.app.controller.commands.admin.route;

import ua.martishyn.app.controller.commands.ICommand;
import ua.martishyn.app.data.dao.impl.RouteDaoImpl;
import ua.martishyn.app.data.dao.interfaces.RouteDao;
import ua.martishyn.app.data.entities.SingleRoute;
import ua.martishyn.app.data.utils.Constants;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class SingleRoutesPageCommand implements ICommand {
    private static final RouteDao routeDao = new RouteDaoImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Optional<List<SingleRoute>> routesFromDb = routeDao.getAllIntermediateStationRoutes();
        if (routesFromDb.isPresent()) {
            request.setAttribute("routes", routesFromDb.get());
        } else {
            request.setAttribute("no-routes", "No routes found at the moment");
        }
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(Constants.ADMIN_ROUTES);
        requestDispatcher.forward(request, response);
    }
}