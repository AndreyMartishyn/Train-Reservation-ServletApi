package ua.martishyn.app.controller.commands.admin.route;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.martishyn.app.controller.commands.ICommand;
import ua.martishyn.app.controller.filters.HasRole;
import ua.martishyn.app.data.entities.RoutePoint;
import ua.martishyn.app.data.entities.enums.Role;
import ua.martishyn.app.data.service.RouteService;
import ua.martishyn.app.data.utils.ViewConstants;
import ua.martishyn.app.data.utils.paginator.RequestPaginationHelper;
import ua.martishyn.app.data.utils.paginator.RequestPaginationImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@HasRole(role = Role.ADMIN)
public class RoutePointsPage implements ICommand {
    private static final Logger log = LogManager.getLogger(RoutePointsPage.class);
    private static final RequestPaginationHelper paginationHelper = new RequestPaginationImpl();
    private final RouteService routeService;

    public RoutePointsPage() {
        routeService = new RouteService();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Optional<List<RoutePoint>> routesFromDb = routeService.getAllRoutePoints();
        if (routesFromDb.isPresent()) {
            log.info("Loading routes from db. Routes quantity : {}", routesFromDb.get().size());
            paginationHelper.paginate(request, routesFromDb.get());
        } else {
            request.setAttribute("noRoutes", "No routes found at the moment");
        }
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(ViewConstants.ADMIN_ROUTES);
        requestDispatcher.forward(request, response);
    }
}