package ua.martishyn.app.controller.commands.admin.route;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.martishyn.app.controller.commands.ICommand;
import ua.martishyn.app.controller.filters.HasRole;
import ua.martishyn.app.data.entities.RoutePoint;
import ua.martishyn.app.data.entities.enums.Role;
import ua.martishyn.app.data.service.RouteService;
import ua.martishyn.app.data.service.StationService;
import ua.martishyn.app.data.service.TrainService;
import ua.martishyn.app.data.utils.constants.ViewConstants;
import ua.martishyn.app.data.utils.constants.DateConstants;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@HasRole(role = Role.ADMIN)
public class RoutePointAddPostCommand implements ICommand {
    private static final Logger log = LogManager.getLogger(RoutePointAddPostCommand.class);
    private final RouteService routeService;
    private final TrainService trainService;
    private final StationService stationService;

    public RoutePointAddPostCommand() {
        routeService = new RouteService();
        trainService = new TrainService();
        stationService = new StationService();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (routeInputIsValid(request) && createRoutePoint(request)){
            log.info("Route added successfully");
            response.sendRedirect("routes-page.command");
            return;
        }
        log.error("Unfortunately, route not added");
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(ViewConstants.ADMIN_ROUTE_ADD_EDIT);
        requestDispatcher.forward(request, response);
    }

    private boolean routeInputIsValid(HttpServletRequest request) {
        return routeService.isInputForRouteValid(request);
    }

    private boolean createRoutePoint(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        int trainId = Integer.parseInt(request.getParameter("trainId"));
        int stationId = Integer.parseInt(request.getParameter("stationId"));

        if (!stationService.isStationExist(stationId) && !trainService.isTrainExist(trainId)) {
            request.setAttribute(ViewConstants.ERROR_VALIDATION, "Train/Station does`t exist even");
            return false;
        }
            LocalDateTime arrival = LocalDateTime.parse(request.getParameter("arrival"), DateConstants.formatterForLocalDate);
            LocalDateTime departure = LocalDateTime.parse(request.getParameter("departure"), DateConstants.formatterForLocalDate);
            RoutePoint newRoutePoint = RoutePoint.builder()
                    .id(id)
                    .trainId(trainId)
                    .stationId(stationId)
                    .arrivalDate(arrival)
                    .departureDate(departure)
                    .build();
            return routeService.createRoutePoint(newRoutePoint);
    }
}


