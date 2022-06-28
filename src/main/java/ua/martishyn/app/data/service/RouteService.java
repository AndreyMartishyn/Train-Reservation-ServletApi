package ua.martishyn.app.data.service;

import ua.martishyn.app.data.dao.impl.RouteDaoImpl;
import ua.martishyn.app.data.dao.interfaces.RouteDao;
import ua.martishyn.app.data.entities.Route;
import ua.martishyn.app.data.entities.RoutePoint;
import ua.martishyn.app.data.utils.constants.DateConstants;
import ua.martishyn.app.data.utils.constants.RouteServiceConstants;
import ua.martishyn.app.data.utils.validator.DataInputValidator;
import ua.martishyn.app.data.utils.validator.DataInputValidatorImpl;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class RouteService {
    private final RouteDao routeDao;
    private final TrainService trainService;
    private final StationService stationService;
    private final DataInputValidator dataInputValidator;


    public RouteService() {
        routeDao = new RouteDaoImpl();
        trainService = new TrainService();
        stationService = new StationService();
        dataInputValidator = new DataInputValidatorImpl();
    }

    public List<RoutePoint> makeEntriesSubList(int offSet, int limit) {
        return routeDao.getAllRoutePointsPaginated(offSet, limit).orElse(Collections.emptyList());
    }

    public boolean deleteRoutePointByIdAndStation(HttpServletRequest request) {
        int routeId = Integer.parseInt(request.getParameter("id"));
        int stationId = Integer.parseInt(request.getParameter("stationId"));
        return routeDao.deleteSingleRoute(routeId, stationId);
    }

    public Optional<RoutePoint> getRoutePointByRouteAndStation(int routeId, int stationId) {
        return routeDao.getRoutePoint(routeId, stationId);
    }

    public Optional<List<Route>> getAllRoutes() {
        return routeDao.getAllRoutes();
    }

    public boolean isRoutePointExist(HttpServletRequest request) {
        int routeId = Integer.parseInt(request.getParameter("id"));
        int stationId = Integer.parseInt(request.getParameter("stationId"));
        Optional<RoutePoint> routePointByRouteAndStation = getRoutePointByRouteAndStation(routeId, stationId);
        routePointByRouteAndStation.ifPresent(routePoint -> request.setAttribute("singleRoute", routePoint));
        return routePointByRouteAndStation.isPresent();
    }

    public boolean createRoutePointFromRequest(HttpServletRequest request) {
        RoutePoint newRoutePoint = getRoutePointFromRequest(request);
        return routeDao.createRoutePoint(newRoutePoint);
    }

    public boolean updateRoutePointFromRequest(HttpServletRequest request) {
        RoutePoint updatedRoutePoint = getRoutePointFromRequest(request);
        return routeDao.updateRoutePoint(updatedRoutePoint);
    }

    private RoutePoint getRoutePointFromRequest(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter(RouteServiceConstants.ID).trim());
        int trainId = Integer.parseInt(request.getParameter(RouteServiceConstants.TRAIN_ID));
        int stationId = Integer.parseInt(request.getParameter(RouteServiceConstants.STATION_ID));

        LocalDateTime arrival = LocalDateTime.parse(request.getParameter(RouteServiceConstants.ROUTE_ARRIVAL), DateConstants.formatterForLocalDate);
        LocalDateTime departure = LocalDateTime.parse(request.getParameter(RouteServiceConstants.ROUTE_DEPARTURE), DateConstants.formatterForLocalDate);
        return RoutePoint.builder()
                .id(id)
                .trainId(trainId)
                .stationId(stationId)
                .arrivalDate(arrival)
                .departureDate(departure)
                .build();
    }

    public List<Route.IntermediateStation> getStationsForRoute(HttpServletRequest request, List<Route> routeList) {
        int routeId = Integer.parseInt(request.getParameter("route"));
        request.setAttribute("routeId", routeId);
        return routeList.stream()
                .filter(complexRoute -> complexRoute.getId() == routeId)
                .map(Route::getIntermediateStations)
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    public boolean isInputForRouteValid(HttpServletRequest request) {
        String id = request.getParameter(RouteServiceConstants.ID).trim();
        if (!dataInputValidator.isValidNumInput(id)) {
            request.setAttribute("wrongId", true);
            return false;
        }
        String departure = request.getParameter(RouteServiceConstants.ROUTE_DEPARTURE).trim();
        String arrival = request.getParameter(RouteServiceConstants.ROUTE_ARRIVAL).trim();
        if (!dataInputValidator.isValidDateInput(departure, arrival)) {
            request.setAttribute("wrongDates", true);
            return false;
        }
        return true;
    }
}
