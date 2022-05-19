package ua.martishyn.app.controller.commands.admin.route;

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
import ua.martishyn.app.data.entities.SingleRoute;
import ua.martishyn.app.data.entities.Station;
import ua.martishyn.app.data.entities.Train;
import ua.martishyn.app.data.entities.enums.Role;
import ua.martishyn.app.data.utils.Constants;
import ua.martishyn.app.data.utils.validator.DataInputValidator;
import ua.martishyn.app.data.utils.validator.DataInputValidatorImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@HasRole(role = Role.ADMIN)
public class SingleRouteAddPOSTCommand implements ICommand {
    private static final Logger log = LogManager.getLogger(SingleRouteAddPOSTCommand.class);
    DateTimeFormatter formatPattern = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (routeDataValidation(request) && createSingleRoute(request)) {
            log.info("Route added successfully");
            response.sendRedirect("routes-page.command");
            return;
        }
        log.error("Unfortunately, route not added");
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(Constants.ADMIN_ROUTE_ADD_EDIT);
        requestDispatcher.forward(request, response);
    }

    private boolean routeDataValidation(HttpServletRequest request) {
        DataInputValidator dataValidator = new DataInputValidatorImpl();
        String id = request.getParameter("id").trim();
        if (!dataValidator.isValidNumInput(id)) {
            request.setAttribute(Constants.ERROR_VALIDATION, "Wrong id number");
            return false;
        }
        String trainId = request.getParameter("trainId").trim();
        if (!dataValidator.isValidNumInput(trainId)) {
            request.setAttribute(Constants.ERROR_VALIDATION, "Wrong train number");
            return false;
        }
        String stationId = request.getParameter("stationId").trim();
        if (!dataValidator.isValidNumInput(stationId)) {
            request.setAttribute(Constants.ERROR_VALIDATION, "Wrong station number");
            return false;
        }
        String departure = request.getParameter("departure").trim();
        String arrival = request.getParameter("arrival").trim();
        if (!dataValidator.isValidDateInput(departure, arrival)) {
            request.setAttribute(Constants.ERROR_VALIDATION, "Wrong dates");
            return false;
        }
        LocalDateTime arrivalDate = LocalDateTime.parse(arrival, formatPattern);
        LocalDateTime departureDate = LocalDateTime.parse(departure, formatPattern);
        if (departureDate.isBefore(arrivalDate) || arrivalDate.isBefore(LocalDateTime.now()) || departureDate.isEqual(LocalDateTime.now())) {
            request.setAttribute(Constants.ERROR_VALIDATION, "Wrong dates range");
            return false;
        }
        return true;
    }

    private boolean createSingleRoute(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        int trainId = Integer.parseInt(request.getParameter("trainId"));
        int stationId = Integer.parseInt(request.getParameter("stationId"));
        LocalDateTime arrival;
        LocalDateTime departure;
        arrival = LocalDateTime.parse(request.getParameter("arrival"), formatPattern);
        departure = LocalDateTime.parse(request.getParameter("departure"), formatPattern);
        if (!trainAndStationExist(trainId, stationId)) {
            request.setAttribute(Constants.ERROR_VALIDATION, "Train/Station does`t exist even");
            return false;
        }

        RouteDao routeDao = new RouteDaoImpl();
        SingleRoute newSingleRoute = SingleRoute.builder()
                .id(id)
                .trainId(trainId)
                .stationId(stationId)
                .arrivalDate(arrival)
                .departureDate(departure)
                .build();
        return routeDao.createSingleRoute(newSingleRoute);
    }

    private boolean trainAndStationExist(int trainId, int stationId) {
        TrainAndModelDao trainAndModelDao = new TrainModelDaoImpl();
        StationDao stationDao = new StationDaoImpl();
        Optional<Train> searchedTrain = trainAndModelDao.getTrain(trainId);
        Optional<Station> searchedStation = stationDao.getById(stationId);
        return searchedTrain.isPresent() && searchedStation.isPresent();
    }
}


