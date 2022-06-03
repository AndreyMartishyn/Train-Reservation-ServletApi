package ua.martishyn.app.controller.commands.admin.route;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.martishyn.app.controller.commands.ICommand;
import ua.martishyn.app.controller.filters.HasRole;
import ua.martishyn.app.data.entities.enums.Role;
import ua.martishyn.app.data.service.RouteService;
import ua.martishyn.app.data.utils.ViewConstants;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@HasRole(role = Role.ADMIN)
public class RoutePointDeleteCommand implements ICommand {
    private static final Logger log = LogManager.getLogger(RoutePointDeleteCommand.class);
    private final RouteService routeService;

    public RoutePointDeleteCommand() {
        routeService = new RouteService();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (deleteRoutePoint(request)) {
            log.info("Deleting route done");
            response.sendRedirect("routes-page.command");
            return;
        }
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(ViewConstants.ADMIN_ROUTES);
        requestDispatcher.forward(request, response);
    }

    private boolean deleteRoutePoint(HttpServletRequest request) {
        int routeId = Integer.parseInt(request.getParameter("id"));
        int stationId = Integer.parseInt(request.getParameter("stationId"));
        return routeService.deleteRoutePointByIdAndStation(routeId, stationId);
    }
}
