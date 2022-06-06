package ua.martishyn.app.controller.commands.common;

import ua.martishyn.app.controller.commands.ICommand;
import ua.martishyn.app.data.entities.Route;
import ua.martishyn.app.data.service.RouteService;
import ua.martishyn.app.data.utils.constants.ViewConstants;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class ShowRouteCommand implements ICommand {
    private final RouteService routeService;

    public ShowRouteCommand() {
        routeService = new RouteService();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //TODO MAKE TAKING ROUTES BY PARTICULAR ROUTE, NOT FROM DB FULLY BY STREAM
        Optional<List<Route>> routesFromDb = routeService.getAllRoutes();
        if (routesFromDb.isPresent()) {
            List<Route.IntermediateStation> stationForRoute = routeService.getStationForRoute(request, routesFromDb.get());
            request.setAttribute("routeInfo", stationForRoute);
        }
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(ViewConstants.CUSTOMER_ROUTE_VIEW);
        requestDispatcher.forward(request, response);
    }
}
