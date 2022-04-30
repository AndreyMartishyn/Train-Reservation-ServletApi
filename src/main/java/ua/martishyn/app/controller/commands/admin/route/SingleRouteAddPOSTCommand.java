package ua.martishyn.app.controller.commands.admin.route;

import ua.martishyn.app.controller.commands.ICommand;
import ua.martishyn.app.data.dao.impl.RouteDaoImpl;
import ua.martishyn.app.data.dao.interfaces.RouteDao;
import ua.martishyn.app.data.entities.SingleRoute;
import ua.martishyn.app.data.utils.Constants;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SingleRouteAddPOSTCommand implements ICommand {
    private static final RouteDao routeDao = new RouteDaoImpl();
    private static final DateFormat formatPattern = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            if (createSingleRoute(request)) {
                response.sendRedirect("routes-page.command");
                return;
            }
        } catch (ParseException e) {
            System.out.println("parse exception while parsing data field from form " + e);
        }
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(Constants.ADMIN_MAIN);
        requestDispatcher.forward(request, response);
    }

    private boolean createSingleRoute(HttpServletRequest request) throws ParseException {
        int id = Integer.parseInt(request.getParameter("id"));
        int trainId = Integer.parseInt(request.getParameter("trainId"));
        int stationId = Integer.parseInt(request.getParameter("stationId"));
        Date arrival = formatPattern.parse(request.getParameter("arrival"));
        Date departure = formatPattern.parse(request.getParameter("departure"));

        SingleRoute newSingleRoute = SingleRoute.builder()
                .id(id)
                .trainId(trainId)
                .stationId(stationId)
                .arrivalDate(arrival)
                .departureDate(departure)
                .build();
        return routeDao.updateSingleRoute(newSingleRoute);
    }
}

