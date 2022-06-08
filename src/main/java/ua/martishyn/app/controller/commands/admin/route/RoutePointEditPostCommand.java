package ua.martishyn.app.controller.commands.admin.route;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.martishyn.app.controller.commands.ICommand;
import ua.martishyn.app.controller.filters.HasRole;
import ua.martishyn.app.data.entities.enums.Role;
import ua.martishyn.app.data.service.RouteService;
import ua.martishyn.app.data.service.StationService;
import ua.martishyn.app.data.service.TrainService;
import ua.martishyn.app.data.utils.constants.ViewConstants;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@HasRole(role = Role.ADMIN)
public class RoutePointEditPostCommand implements ICommand {
    private static final Logger log = LogManager.getLogger(RoutePointEditPostCommand.class);
    private final RouteService routeService;

    public RoutePointEditPostCommand() {
        routeService = new RouteService();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (routeService.isInputForRouteValid(request) && routeService.updateRoutePointFromRequest(request)) {
            log.info("Route updated successfully");
            response.sendRedirect("routes-page.command");
            return;
        }
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(ViewConstants.ADMIN_ROUTE_ADD_EDIT);
        requestDispatcher.forward(request, response);
    }
}
