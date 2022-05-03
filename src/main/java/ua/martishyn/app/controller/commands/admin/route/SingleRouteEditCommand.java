package ua.martishyn.app.controller.commands.admin.route;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
import java.util.Optional;

public class SingleRouteEditCommand implements ICommand {
    private static final Logger log = LogManager.getLogger(SingleRouteEditCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Optional<SingleRoute> singleRoute = getSingleRoute(request);
        RequestDispatcher requestDispatcher;
        if (singleRoute.isPresent()) {
            request.setAttribute("singleRoute", singleRoute.get());
            log.info("Editing route with id {}", singleRoute.get().getId());
            requestDispatcher = request.getRequestDispatcher(Constants.ADMIN_ROUTE_ADD_EDIT);
        } else {
            requestDispatcher = request.getRequestDispatcher(Constants.ADMIN_ROUTES);

        }
        requestDispatcher.forward(request, response);
    }

    private Optional<SingleRoute> getSingleRoute(HttpServletRequest request) {
        RouteDao routeDao = new RouteDaoImpl();
        int id = Integer.parseInt(request.getParameter("id"));
        return routeDao.getSingleRoute(id);
    }
}
