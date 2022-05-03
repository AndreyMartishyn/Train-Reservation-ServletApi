package ua.martishyn.app.controller.commands.admin.station;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.martishyn.app.controller.commands.ICommand;
import ua.martishyn.app.controller.commands.admin.route.SingleRoutesPageCommand;
import ua.martishyn.app.data.dao.impl.RouteDaoImpl;
import ua.martishyn.app.data.dao.impl.StationDaoImpl;
import ua.martishyn.app.data.dao.interfaces.RouteDao;
import ua.martishyn.app.data.dao.interfaces.StationDao;
import ua.martishyn.app.data.entities.SingleRoute;
import ua.martishyn.app.data.entities.Station;
import ua.martishyn.app.data.utils.Constants;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class StationsPageCommand implements ICommand {
    private static final Logger log = LogManager.getLogger(StationsPageCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Optional<List<Station>> stationsFromDb = getStations();
        if (stationsFromDb.isPresent()) {
            log.info("Loading stations from db. Stations quantity : {}", stationsFromDb.get().size());
            request.setAttribute("stations", stationsFromDb.get());
        } else {
            request.setAttribute("no-stations", "No stations found at the moment");
        }
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(Constants.ADMIN_STATIONS);
        log.info("Redirect to view --> {}", Constants.ADMIN_STATIONS);
        requestDispatcher.forward(request, response);

    }

    private Optional<List<Station>> getStations() {
        StationDao stationDao = new StationDaoImpl();
        return stationDao.getAll();
    }
}
