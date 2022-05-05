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
import ua.martishyn.app.data.utils.Constants;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class CustomerSearchCommand implements ICommand {
    private static final Logger log = LogManager.getLogger(CustomerSearchCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int departureStationId = Integer.parseInt(request.getParameter("stationFrom"));
        int arrivalStationId = Integer.parseInt(request.getParameter("stationTo"));
        if (departureStationId == arrivalStationId) {
            log.error("Same stations chosen by user");
            request.setAttribute("sameStations", "Departure and arrival stations are same");
        }
        DateFormat formatPattern = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        StationDao stationDao = new StationDaoImpl();
        RouteDao routeDao = new RouteDaoImpl();

        Optional<Station> fromStation = stationDao.getById(departureStationId);
        Optional<Station> toStation = stationDao.getById(arrivalStationId);

        //Returns all route-parts gathered in complex-route objects with intermediate stations
        List<PersonalRoute> suitableRoutes = new ArrayList<>();
        Optional<List<ComplexRoute>> routeList = routeDao.getAllComplexRoutes();
        for (ComplexRoute complexRoute : routeList.get()) {
            List<ComplexRoute.IntermediateStation> stations = complexRoute.getIntermediateStations();
            if (stations.stream().anyMatch(st -> st.getStation().equals(fromStation.get())
                    &&
                    stations.stream().anyMatch(st1 -> st1.getStation().equals(toStation.get())))){

                PersonalRoute personalRoute = new PersonalRoute();
                personalRoute.setRouteId(complexRoute.getId());
                personalRoute.setTrainModel(complexRoute.getTrain().getModel().getName());
                int numOfStationsPassed = 0;
                int fromId = 0;
                int toId = 0;
                Date depDate = null;
                Date arrDate = null;
                for (ComplexRoute.IntermediateStation stationObject : stations) {
                    Station station = stationObject.getStation();
                    depDate = stationObject.getDepartureDate();
                    arrDate = stationObject.getArrivalDate();

                    if (station.getId() == departureStationId) {
                        fromId = numOfStationsPassed;
                        personalRoute.setDeparture(formatPattern.format(depDate));
                        personalRoute.setDepartureStation(fromStation.get().getName());
                    }
                    if (station.getId() == arrivalStationId) {
                        toId = numOfStationsPassed;
                        personalRoute.setArrival(formatPattern.format(arrDate));
                        personalRoute.setArrivalStation(toStation.get().getName());
                    }
                    numOfStationsPassed++;
                }
                if (fromId < toId) {
                    long routeDuration = (arrDate.getTime() - depDate.getTime());
                    personalRoute.setRoadTime(new SimpleDateFormat("mm:ss").format(new Date(routeDuration)));
                    personalRoute.setPrice(20 * numOfStationsPassed);
                    suitableRoutes.add(personalRoute);
                }
            }
        }
        if (!suitableRoutes.isEmpty()) {
            log.info("Appropriate routes found. Size : {}", suitableRoutes.size());
            request.getSession().setAttribute("suitableRoutes", suitableRoutes);
            response.sendRedirect("customer-booking.command");
            return;
        }
        log.error("Unfortunately, routes not found");
        request.setAttribute("noRoutes", "There are no routes with such destination and departure");
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("customer-booking.command");
        requestDispatcher.forward(request, response);
    }
}
// if ((!complexRoute.getIntermediateStations().contains(fromStation.get())) &&
//                    (!complexRoute.getIntermediateStations().contains(toStation.get()))) {
//                    log.error("No suitable routes found");
//                    request.setAttribute("noRoutes", "Departure and arrival stations not found in routes");
//                RequestDispatcher requestDispatcher = request.getRequestDispatcher(Constants.CUSTOMER_BOOK_PAGE);
//                log.info("Redirect to view --> {}", Constants.CUSTOMER_BOOK_PAGE);
//                requestDispatcher.forward(request, response);
//            }

   /* List<ComplexRoute.IntermediateStation> intermediateStations =
            routeList.get().stream()
                    .map(ComplexRoute::getIntermediateStations)
                    .flatMap(Collection::stream)
                    .collect(Collectors.toList());
    int numOfStationsOfRoute = 0; */