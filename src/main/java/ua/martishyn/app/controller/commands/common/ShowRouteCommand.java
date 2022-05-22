package ua.martishyn.app.controller.commands.common;

import ua.martishyn.app.controller.commands.ICommand;
import ua.martishyn.app.data.dao.impl.RouteDaoImpl;
import ua.martishyn.app.data.dao.interfaces.RouteDao;
import ua.martishyn.app.data.entities.ComplexRoute;
import ua.martishyn.app.data.utils.Constants;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ShowRouteCommand implements ICommand {
    private static final RouteDao routeDao = new RouteDaoImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Optional<List<ComplexRoute>> routesFromDb = routeDao.getAllComplexRoutes();
        if (routesFromDb.isPresent()) {
            List<ComplexRoute.IntermediateStation> stationForRoute = getStationForRoute(request, routesFromDb.get());
            request.setAttribute("routeInfo", stationForRoute);
        }
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(Constants.CUSTOMER_ROUTE_VIEW);
        requestDispatcher.forward(request, response);
    }

    private List<ComplexRoute.IntermediateStation> getStationForRoute(HttpServletRequest request, List<ComplexRoute> routeList) {
        int routeId = Integer.parseInt(request.getParameter("route"));
        request.setAttribute("routeId", routeId);
        return routeList.stream()
                .filter(complexRoute -> complexRoute.getId() == routeId)
                .map(ComplexRoute::getIntermediateStations)
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }
}
