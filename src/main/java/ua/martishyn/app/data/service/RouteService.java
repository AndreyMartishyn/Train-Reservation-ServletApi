package ua.martishyn.app.data.service;

import ua.martishyn.app.data.dao.impl.RouteDaoImpl;
import ua.martishyn.app.data.dao.interfaces.RouteDao;
import ua.martishyn.app.data.entities.Route;
import ua.martishyn.app.data.entities.RoutePoint;
import ua.martishyn.app.data.utils.constants.DateConstants;
import ua.martishyn.app.data.utils.constants.ViewConstants;
import ua.martishyn.app.data.utils.validator.DataInputValidator;
import ua.martishyn.app.data.utils.validator.DataInputValidatorImpl;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class RouteService {
    private final RouteDao routeDao;
    private final DataInputValidator dataInputValidator;

    public RouteService() {
        routeDao = new RouteDaoImpl();
        dataInputValidator = new DataInputValidatorImpl();
    }

    public boolean createRoutePoint(RoutePoint newRoutePoint) {
        return routeDao.createRoutePoint(newRoutePoint);
    }

    public boolean deleteRoutePointByIdAndStation(int routeId, int stationId) {
        return routeDao.deleteSingleRoute(routeId, stationId);
    }

    public Optional<List<RoutePoint>> getRoutePointsPaginated(int offset, int limit) {
        return routeDao.getAllRoutePoints(offset, limit);
    }

    public Optional<RoutePoint> getRoutePointByRouteAndStation(int routeId, int stationId) {
        return routeDao.getRoutePoint(routeId, stationId);
    }

    public boolean updateRoutePoint(RoutePoint newRoutePoint) {
        return routeDao.updateRoutePoint(newRoutePoint);
    }

    public Optional<List<Route>> getAllRoutes() {
        return routeDao.getAllRoutes();
    }

    public List<Route.IntermediateStation> getStationForRoute(HttpServletRequest request, List<Route> routeList) {
        int routeId = Integer.parseInt(request.getParameter("route"));
        request.setAttribute("routeId", routeId);
        return routeList.stream()
                .filter(complexRoute -> complexRoute.getId() == routeId)
                .map(Route::getIntermediateStations)
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    public boolean isInputForRouteValid(HttpServletRequest request) {
        String id = request.getParameter("id").trim();
        if (!dataInputValidator.isValidNumInput(id)) {
            request.setAttribute(ViewConstants.ERROR_VALIDATION, "Wrong id number");
            return false;
        }
        String trainId = request.getParameter("trainId").trim();
        if (!dataInputValidator.isValidNumInput(trainId)) {
            request.setAttribute(ViewConstants.ERROR_VALIDATION, "Wrong train number");
            return false;
        }
        String stationId = request.getParameter("stationId").trim();
        if (!dataInputValidator.isValidNumInput(stationId)) {
            request.setAttribute(ViewConstants.ERROR_VALIDATION, "Wrong station number");
            return false;
        }
        String departure = request.getParameter("departure").trim();
        String arrival = request.getParameter("arrival").trim();
        if (!dataInputValidator.isValidDateInput(departure, arrival)) {
            request.setAttribute(ViewConstants.ERROR_VALIDATION, "Wrong dates");
            return false;
        }
        LocalDateTime arrivalDate = LocalDateTime.parse(arrival, DateConstants.formatterForLocalDate);
        LocalDateTime departureDate = LocalDateTime.parse(departure, DateConstants.formatterForLocalDate);
        if (departureDate.isBefore(arrivalDate) || arrivalDate.isBefore(LocalDateTime.now()) || departureDate.isEqual(LocalDateTime.now())) {
            request.setAttribute(ViewConstants.ERROR_VALIDATION, "Wrong dates range");
            return false;
        }
        return true;
    }



}
