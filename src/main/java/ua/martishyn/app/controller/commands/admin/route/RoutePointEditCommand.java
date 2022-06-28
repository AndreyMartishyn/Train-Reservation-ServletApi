package ua.martishyn.app.controller.commands.admin.route;

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
public class RoutePointEditCommand implements ICommand {
    private final RouteService routeService;
    private final TrainService trainService;
    private final StationService stationService;


    public RoutePointEditCommand() {
        routeService = new RouteService();
        trainService = new TrainService();
        stationService = new StationService();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher;
        if (routeService.isRoutePointExist(request)) {
            request.setAttribute("trains", trainService.getAllTrains());
            request.setAttribute("stations", stationService.getAllStations());
            requestDispatcher = request.getRequestDispatcher(ViewConstants.ADMIN_ROUTE_ADD_EDIT);
        } else {
            requestDispatcher = request.getRequestDispatcher(ViewConstants.ADMIN_ROUTES);
        }
        requestDispatcher.forward(request, response);
    }
}
