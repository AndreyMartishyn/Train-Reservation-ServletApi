package ua.martishyn.app.controller.commands.customer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.martishyn.app.controller.commands.ICommand;
import ua.martishyn.app.data.dao.impl.RouteDaoImpl;
import ua.martishyn.app.data.dao.impl.StationDaoImpl;
import ua.martishyn.app.data.dao.interfaces.RouteDao;
import ua.martishyn.app.data.dao.interfaces.StationDao;
import ua.martishyn.app.data.entities.ComplexRoute;
import ua.martishyn.app.data.entities.PersonalRoute;
import ua.martishyn.app.data.entities.Station;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class CustomerTrainSearchCommand implements ICommand {
    private static final Logger log = LogManager.getLogger(CustomerTrainSearchCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int departureStationId = Integer.parseInt(request.getParameter("stationFrom"));
        int arrivalStationId = Integer.parseInt(request.getParameter("stationTo"));
        if (departureStationId == arrivalStationId) {
            log.error("Same stations chosen by user");
            request.getSession().setAttribute("sameStations", "Departure and arrival stations are same");
        }
        DateFormat formatPattern = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        StationDao stationDao = new StationDaoImpl();
        RouteDao routeDao = new RouteDaoImpl();

        Optional<Station> fromStation = stationDao.getById(departureStationId);
        Optional<Station> toStation = stationDao.getById(arrivalStationId);

        //Returns all routes with equal id according to the route

        List<PersonalRoute> suitableRoutes = new ArrayList<>();
        Optional<List<ComplexRoute>> routeList = routeDao.getAllComplexRoutes();
        for (ComplexRoute complexRoute : routeList.get()) {
            List<ComplexRoute.IntermediateStation> stations = complexRoute.getIntermediateStations();
            if (stations.stream().anyMatch(station -> station.getStation().equals(fromStation.get()))
                    || stations.stream().anyMatch(station -> station.getStation().equals(toStation.get()))) {
                //iterator-like
                int fromId = -1;
                int toId = -1;

                PersonalRoute personalRoute = new PersonalRoute();
                personalRoute.setRouteId(complexRoute.getId());
                personalRoute.setTrainModel(complexRoute.getTrain().getModel().getName());
                int numOfStationsPassed = 0;
                Date depDate = null;
                ;
                Date arrDate;
                for (ComplexRoute.IntermediateStation stationObject : stations) {
                    Station station = stationObject.getStation();
                    if (station.getId() == departureStationId) {
                        fromId = numOfStationsPassed;
                        depDate = stationObject.getDepartureDate();
                        personalRoute.setDeparture(formatPattern.format(depDate));
                    }
                    if (station.getId() == arrivalStationId) {
                        toId = numOfStationsPassed;
                        arrDate = stationObject.getArrivalDate();
                        personalRoute.setArrival(formatPattern.format(arrDate));

                        long routeDuration = arrDate.getTime() - depDate.getTime();
                        personalRoute.setRoadTime(formatPattern.format(new Date(routeDuration)));
                    }
                    numOfStationsPassed++;
                }
                if (fromId < toId) {
                    personalRoute.setDepartureStation(fromStation.get().getName());
                    personalRoute.setArrivalStation(toStation.get().getName());
                    suitableRoutes.add(personalRoute);
                }
            }
        }
        log.error("Appropriate routes found. Size : {}", suitableRoutes.size());
        request.getSession().setAttribute("suitableRoutes", suitableRoutes);
    }
}

   /* List<ComplexRoute.IntermediateStation> intermediateStations =
            routeList.get().stream()
                    .map(ComplexRoute::getIntermediateStations)
                    .flatMap(Collection::stream)
                    .collect(Collectors.toList());
    int numOfStationsOfRoute = 0; */