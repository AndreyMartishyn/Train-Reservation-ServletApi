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
import java.util.List;
import java.util.Optional;

public class SingleRoutesPageCommand implements ICommand {
    private static final Logger log = LogManager.getLogger(SingleRoutesPageCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Optional<List<SingleRoute>> routesFromDb = getSingleRoutes();
        if (routesFromDb.isPresent()) {
            log.info("Loading routes from db. Routes quantity : {}", routesFromDb.get().size());
            request.setAttribute("routes", routesFromDb.get());
        } else {
            request.setAttribute("noRoutes", "No routes found at the moment");
        }
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(Constants.ADMIN_ROUTES);
        log.info("Redirect to view --> {}", Constants.ADMIN_ROUTES);
        requestDispatcher.forward(request, response);
    }

    private Optional<List<SingleRoute>> getSingleRoutes(){
        RouteDao routeDao = new RouteDaoImpl();
        return routeDao.getAllIntermediateStationRoutes();
    }
}