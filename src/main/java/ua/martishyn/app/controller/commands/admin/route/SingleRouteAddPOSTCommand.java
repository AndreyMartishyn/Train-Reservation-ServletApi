package ua.martishyn.app.controller.commands.admin.route;

import ua.martishyn.app.controller.commands.ICommand;
import ua.martishyn.app.data.dao.impl.RouteDaoImpl;
import ua.martishyn.app.data.dao.impl.StationDaoImpl;
import ua.martishyn.app.data.dao.impl.TrainModelDaoImpl;
import ua.martishyn.app.data.dao.interfaces.RouteDao;
import ua.martishyn.app.data.dao.interfaces.StationDao;
import ua.martishyn.app.data.dao.interfaces.TrainAndModelDao;
import ua.martishyn.app.data.entities.SingleRoute;
import ua.martishyn.app.data.entities.Station;
import ua.martishyn.app.data.entities.Train;
import ua.martishyn.app.data.utils.Constants;
import ua.martishyn.app.data.utils.validator.DataInputValidator;
import ua.martishyn.app.data.utils.validator.DataInputValidatorImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

public class SingleRouteAddPOSTCommand implements ICommand {
    private static final RouteDao routeDao = new RouteDaoImpl();
    private static final TrainAndModelDao trainAndModelDao = new TrainModelDaoImpl();
    private static final StationDao stationDao = new StationDaoImpl();
    private static final DataInputValidator dataValidator = new DataInputValidatorImpl();


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (routeDataValidation(request)) {
            if (createSingleRoute(request)) {
                request.getSession().setAttribute("success", "Route created");
                response.sendRedirect("routes-page.command");
            } else {
                request.setAttribute("errorLogic", "Problems with creating route");
            }
        } else {
            request.setAttribute("errorLogic", "Problems with creating route");
        }
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(Constants.ADMIN_ROUTE_ADD_EDIT);
        requestDispatcher.forward(request,response);
    }

    private boolean routeDataValidation(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String id = request.getParameter("id").trim();
        if (!dataValidator.isValidNumInput(id)) {
            session.setAttribute(Constants.ERROR_VALIDATION, "Wrong id number");
            return false;
        }
        String trainId = request.getParameter("trainId").trim();
        if (!dataValidator.isValidNumInput(trainId)) {
            session.setAttribute(Constants.ERROR_VALIDATION, "Wrong train number");
            return false;
        }
        String stationId = request.getParameter("stationId").trim();
        if (!dataValidator.isValidNumInput(stationId)) {
            session.setAttribute(Constants.ERROR_VALIDATION, "Wrong station number");
            return false;
        }
        String departure = request.getParameter("departure").trim();
        String arrival = request.getParameter("arrival").trim();
        if (!dataValidator.isValidDateInput(departure, arrival)) {
            session.setAttribute(Constants.ERROR_VALIDATION, "Wrong dates");
            return false;
        }
        return true;
    }

    private boolean createSingleRoute(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        int trainId = Integer.parseInt(request.getParameter("trainId"));
        int stationId = Integer.parseInt(request.getParameter("stationId"));
        Date arrival;
        Date departure;
        DateFormat formatPattern = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        try {
            arrival = formatPattern.parse(request.getParameter("arrival"));
            departure = formatPattern.parse(request.getParameter("departure"));
        } catch (ParseException e) {
            return false;
        }

        if (!checkInputIsValid(trainId, stationId, departure, arrival)) {
            return false;
        }

        SingleRoute newSingleRoute = SingleRoute.builder()
                .id(id)
                .trainId(trainId)
                .stationId(stationId)
                .arrivalDate(arrival)
                .departureDate(departure)
                .build();
        return routeDao.createSingleRoute(newSingleRoute);
    }

    private boolean checkInputIsValid(int trainId, int stationId,
                                      Date departure, Date arrival) {
        Optional<Train> searchedTrain = trainAndModelDao.getTrain(trainId);
        Optional<Station> searchedStation = stationDao.getById(stationId);
        return searchedTrain.isPresent() && searchedStation.isPresent()
                && arrival.getTime() < departure.getTime();
    }
}


