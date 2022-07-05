package ua.martishyn.app.data.service;

import ua.martishyn.app.data.entities.PersonalRoute;
import ua.martishyn.app.data.entities.Route;
import ua.martishyn.app.data.entities.Station;
import ua.martishyn.app.data.entities.Wagon;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class BookingService {
    private final RouteService routeService;
    private final WagonService wagonService;
    private final StationService stationService;


    public BookingService() {
        routeService = new RouteService();
        wagonService = new WagonService();
        stationService = new StationService();
    }

    public List<PersonalRoute> findSuitableRoutes(HttpServletRequest request) {
        Optional<List<Route>> routeList = routeService.getAllRoutes();
        if (routeList.isPresent()) {
            List<Wagon> wagons = wagonService.getAllWagons();
            Station fromStation = stationService.getStationFromRequest(request, "stationFrom");
            Station toStation = stationService.getStationFromRequest(request, "stationTo");
            BookingSearcher trainSearcher = new BookingSearcher(routeList.get(), fromStation, toStation, wagons);
            return trainSearcher.getSuitableRoutes();
        }
        return Collections.emptyList();
    }

    public boolean areStationsEqual(HttpServletRequest request) {
        int departureStationId = Integer.parseInt(request.getParameter("stationFrom"));
        int arrivalStationId = Integer.parseInt(request.getParameter("stationTo"));
        return departureStationId == arrivalStationId;
    }
}
