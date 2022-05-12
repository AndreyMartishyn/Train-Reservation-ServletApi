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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@HasRole(role = Role.ADMIN)
public class SingleRouteEditPOSTCommand implements ICommand {
    private static final Logger log = LogManager.getLogger(SingleRouteEditPOSTCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (routeDataValidation(request)) {
            if (updateSingleRoute(request)) {
                log.info("Route updated successfully");
                response.sendRedirect("routes-page.command");
                return;
            } else {
                request.setAttribute("errorLogic", "Problems with updating route");
            }
        } else {
            request.setAttribute("errorLogic", "Problems with updating route");
        }
        log.info("Unfortunately, route not updated. Redirect to view --> {}", Constants.ADMIN_ROUTE_ADD_EDIT);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(Constants.ADMIN_ROUTE_ADD_EDIT);
        requestDispatcher.forward(request, response);
    }

    private boolean routeDataValidation(HttpServletRequest request) {
        final DataInputValidator dataValidator = new DataInputValidatorImpl();
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
        return true;
    }

    private boolean updateSingleRoute(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        int trainId = Integer.parseInt(request.getParameter("trainId"));
        int stationId = Integer.parseInt(request.getParameter("stationId"));

        DateFormat formatPattern = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        Date arrival;
        Date departure;
        try {
            arrival = formatPattern.parse(request.getParameter("arrival"));
            departure = formatPattern.parse(request.getParameter("departure"));
        } catch (ParseException e) {
            return false;
        }
        if (!checkInputIsValid(trainId, stationId, departure, arrival)) {
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
        return routeDao.updateSingleRoute(newSingleRoute);
    }

    private boolean checkInputIsValid(int trainId, int stationId, Date departure, Date arrival) {
        TrainAndModelDao trainAndModelDao = new TrainModelDaoImpl();
        StationDao stationDao = new StationDaoImpl();
        Optional<Train> searchedTrain = trainAndModelDao.getTrain(trainId);
        Optional<Station> searchedStation = stationDao.getById(stationId);
        return searchedTrain.isPresent() && searchedStation.isPresent()
                && arrival.getTime() < departure.getTime();
    }
}
