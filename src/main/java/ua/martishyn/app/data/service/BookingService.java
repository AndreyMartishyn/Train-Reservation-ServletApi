package ua.martishyn.app.data.service;

import ua.martishyn.app.data.entities.PersonalRoute;
import ua.martishyn.app.data.entities.Route;
import ua.martishyn.app.data.entities.Station;
import ua.martishyn.app.data.entities.Wagon;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class BookingService {
    private final RouteService routeService;
    private final TrainService trainService;
    private final StationService stationService;


    public BookingService() {
        routeService = new RouteService();
        trainService = new TrainService();
        stationService = new StationService();
    }

    public List<PersonalRoute> findSuitableRoutes(HttpServletRequest request) {
        Optional<List<Route>> routeList = routeService.getAllRoutes();
        if (routeList.isPresent()) {
            List<Wagon> wagons = trainService.getAllWagons();
            Station fromStation = getStation(request, "stationFrom");
            Station toStation = getStation(request, "stationTo");
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

    private Station getStation(HttpServletRequest request, String parameter) {
        int station = Integer.parseInt(request.getParameter(parameter));
        Optional<Station> foundStation = stationService.getStationById(station);
        return foundStation.get();
    }
}
