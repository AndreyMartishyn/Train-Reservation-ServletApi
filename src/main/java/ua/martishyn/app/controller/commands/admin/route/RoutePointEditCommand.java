package ua.martishyn.app.controller.commands.admin.route;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.martishyn.app.controller.commands.ICommand;
import ua.martishyn.app.controller.filters.HasRole;
import ua.martishyn.app.data.entities.RoutePoint;
import ua.martishyn.app.data.entities.enums.Role;
import ua.martishyn.app.data.service.RouteService;
import ua.martishyn.app.data.utils.ViewConstants;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@HasRole(role = Role.ADMIN)
public class RoutePointEditCommand implements ICommand {
    private static final Logger log = LogManager.getLogger(RoutePointEditCommand.class);
    private final RouteService routeService;

    public RoutePointEditCommand() {
        routeService = new RouteService();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher;
        if (isRoutePointExist(request)) {
            requestDispatcher = request.getRequestDispatcher(ViewConstants.ADMIN_ROUTE_ADD_EDIT);
        } else {
            requestDispatcher = request.getRequestDispatcher(ViewConstants.ADMIN_ROUTES);
        }
        requestDispatcher.forward(request, response);
    }

    private boolean isRoutePointExist(HttpServletRequest request) {
        int routeId = Integer.parseInt(request.getParameter("id"));
        int stationId = Integer.parseInt(request.getParameter("stationId"));
        Optional<RoutePoint> routePointByRouteAndStation = routeService.getRoutePointByRouteAndStation(routeId, stationId);
        if (routePointByRouteAndStation.isPresent()){
            request.setAttribute("singleRoute", routePointByRouteAndStation.get());
            log.info("Editing route with id {}", routePointByRouteAndStation.get().getId());
        }
        return routePointByRouteAndStation.isPresent();
    }
}
