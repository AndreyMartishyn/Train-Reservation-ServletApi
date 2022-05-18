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
import ua.martishyn.app.data.service.TrainHelper;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@HasRole(role = Role.CUSTOMER)
public class CustomerSearchTicketsCommand implements ICommand {
    private static final Logger log = LogManager.getLogger(CustomerSearchTicketsCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (ifSameStations(request)) {
            log.error("Same stations chosen by user");
            request.setAttribute("sameStations", "Departure and arrival stations are same");
        } else {
            Station fromStation = getStation(request, "stationFrom");
            Station toStation = getStation(request, "stationTo");
            RouteDao routeDao = new RouteDaoImpl();
            Optional<List<ComplexRoute>> routeList = getComplexRoutes(routeDao);
            if (routeList.isPresent()) {
                TrainAndModelDao trainAndModelDao = new TrainModelDaoImpl();
                List<Wagon> wagons =  trainAndModelDao.getAllWagons();
                TrainHelper trainSearcher = new TrainHelper(routeList.get(), fromStation, toStation, wagons);
                List<PersonalRoute> suitableRoutes = trainSearcher.getSuitableRoutes();
                if (!suitableRoutes.isEmpty()) {
                    log.info("Appropriate routes found. Size : {}", suitableRoutes.size());
                    request.setAttribute("suitableRoutes", suitableRoutes);
                }
            } else {
                log.error("Unfortunately, routes not found");
                request.setAttribute("noRoutes", "There are no routes with such destination and departure");
            }
        }
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("customer-booking.command");
        requestDispatcher.forward(request, response);
    }

    private Optional<List<ComplexRoute>> getComplexRoutes(RouteDao routeDao) {
        return routeDao.getAllComplexRoutes();
    }

    private boolean ifSameStations(HttpServletRequest request) {
        int departureStationId = Integer.parseInt(request.getParameter("stationFrom"));
        int arrivalStationId = Integer.parseInt(request.getParameter("stationTo"));
        return departureStationId == arrivalStationId;
    }

    private Station getStation(HttpServletRequest request, String parameter) {
        StationDao stationDao = new StationDaoImpl();
        int departureStationId = Integer.parseInt(request.getParameter(parameter));
        Optional<Station> station = stationDao.getById(departureStationId);
        return station.get();
    }
}