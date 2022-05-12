package ua.martishyn.app.controller.commands.customer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.martishyn.app.controller.commands.ICommand;
import ua.martishyn.app.controller.filters.HasRole;
import ua.martishyn.app.data.dao.impl.RouteDaoImpl;
import ua.martishyn.app.data.dao.impl.StationDaoImpl;
import ua.martishyn.app.data.dao.impl.TrainModelDaoImpl;
import ua.martishyn.app.data.dao.interfaces.RouteDao;
import ua.martishyn.app.data.dao.interfaces.StationDao;
import ua.martishyn.app.data.dao.interfaces.TrainAndModelDao;
import ua.martishyn.app.data.entities.ComplexRoute;
import ua.martishyn.app.data.entities.PersonalRoute;
import ua.martishyn.app.data.entities.Station;
import ua.martishyn.app.data.entities.Wagon;
import ua.martishyn.app.data.entities.enums.Role;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@HasRole(role = Role.CUSTOMER)
public class CustomerSearchTicketsCommand implements ICommand {
    private static final Logger log = LogManager.getLogger(CustomerSearchTicketsCommand.class);
    private final DateFormat formatPattern = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    private final DateFormat routePattern = new SimpleDateFormat("HH:mm");
    private final StationDao stationDao = new StationDaoImpl();
    private final RouteDao routeDao = new RouteDaoImpl();
    private final TrainAndModelDao trainAndModelDao = new TrainModelDaoImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int departureStationId = Integer.parseInt(request.getParameter("stationFrom"));
        int arrivalStationId = Integer.parseInt(request.getParameter("stationTo"));

        if (departureStationId == arrivalStationId) {
            log.error("Same stations chosen by user");
            request.setAttribute("sameStations", "Departure and arrival stations are same");
        }

        Optional<Station> fromStation = stationDao.getById(departureStationId);
        Optional<Station> toStation = stationDao.getById(arrivalStationId);
        if (fromStation.isPresent() && toStation.isPresent()) {
            //Returns all route-parts gathered in complex-route objects with intermediate stations
            List<PersonalRoute> suitableRoutes = new ArrayList<>();
            Optional<List<ComplexRoute>> routeList = routeDao.getAllComplexRoutes();
            for (ComplexRoute complexRoute : routeList.get()) {
                List<ComplexRoute.IntermediateStation> stations = complexRoute.getIntermediateStations();

                //checks if arrival and departure station are in route
                if (stations.stream().anyMatch(st -> st.getStation().equals(fromStation.get())
                        &&
                        stations.stream().anyMatch(st1 -> st1.getStation().equals(toStation.get())))) {

                    PersonalRoute personalRoute = new PersonalRoute();
                    personalRoute.setRouteId(complexRoute.getId());
                    personalRoute.setTrain(complexRoute.getTrain());
                    StringBuilder redirectLink = new StringBuilder();
                    redirectLink.append("?train=")
                            .append(complexRoute.getTrain().getId());

                    int numOfStationsPassed = 0;
                    int fromId = 0;
                    int toId = 0;
                    Date depDate = null;
                    Date arrDate = null;

                    for (ComplexRoute.IntermediateStation stationObject : stations) {
                        Station station = stationObject.getStation();

                        if (station.getId() == departureStationId) {
                            depDate = stationObject.getDepartureDate();
                            fromId = numOfStationsPassed;
                            personalRoute.setDeparture(formatPattern.format(depDate));
                            personalRoute.setDepartureStation(fromStation.get().getName());
                            redirectLink.append("&fromStation=")
                                    .append(station.getName())
                                    .append("&departure=")
                                    .append(formatPattern.format(depDate));
                        }

                        if (station.getId() == arrivalStationId) {
                            arrDate = stationObject.getArrivalDate();
                            toId = numOfStationsPassed;
                            personalRoute.setArrival(formatPattern.format(arrDate));
                            personalRoute.setArrivalStation(toStation.get().getName());
                            redirectLink.append("&toStation=")
                                    .append(station.getName())
                                    .append("&arrival=")
                                    .append(formatPattern.format(arrDate));
                        }
                        numOfStationsPassed++;
                    }
                    if (fromId < toId) {
                        long routeDuration = Objects.requireNonNull(arrDate).getTime() - Objects.requireNonNull(depDate).getTime();
                        personalRoute.setRoadTime(routePattern.format(new Date(routeDuration)));
                        Optional<List<Wagon>> trainCoaches = trainAndModelDao.getCoachesForTrain(complexRoute.getId());
                        if (trainCoaches.isPresent()) {
                            int firstClassPlaces = getClassPlaces(trainCoaches.get(), "FIRST");
                            personalRoute.setFirstClassSeats(firstClassPlaces);
                            int secondClassPlaces = getClassPlaces(trainCoaches.get(), "SECOND");
                            personalRoute.setSecondClassSeats(secondClassPlaces);

                        }
                        personalRoute.setRedirectLink(redirectLink);
                        suitableRoutes.add(personalRoute);
                    }
                }
            }
            if (!suitableRoutes.isEmpty()) {
                log.info("Appropriate routes found. Size : {}", suitableRoutes.size());
                request.setAttribute("suitableRoutes", suitableRoutes);
            } else {
                log.error("Unfortunately, routes not found");
                request.setAttribute("noRoutes", "There are no routes with such destination and departure");
            }
        }
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("customer-booking.command");
        requestDispatcher.forward(request, response);
    }

    //gets train_coaches objects and makes stream with check if class is appropriate and sums seats for same class
    private int getClassPlaces(List<Wagon> wagons, String type) {
        return wagons.stream()
                .filter(trainCoach -> trainCoach.getComfortClass().name().equals(type))
                .mapToInt(Wagon::getNumOfSeats)
                .reduce(0, Integer::sum);
    }
}