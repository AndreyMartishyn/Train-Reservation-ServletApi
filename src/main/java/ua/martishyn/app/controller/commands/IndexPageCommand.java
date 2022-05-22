package ua.martishyn.app.controller.commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.martishyn.app.data.dao.impl.StationDaoImpl;
import ua.martishyn.app.data.dao.interfaces.StationDao;
import ua.martishyn.app.data.entities.Station;
import ua.martishyn.app.data.utils.Constants;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class IndexPageCommand implements ICommand {
    private static final Logger log = LogManager.getLogger(IndexPageCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Station> stations = getStations();
        showStationsForm(request, stations);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(Constants.HOME_PAGE);
        requestDispatcher.forward(request, response);
    }

    private void showStationsForm(HttpServletRequest request, List<Station> stations) {
        if (!stations.isEmpty()) {
            log.info("Loading stations from db. Stations quantity : {}", stations.size());
            request.setAttribute("stations", stations);
        } else {
            request.setAttribute("noStations", "No stations found");
        }
    }

    private List<Station> getStations() {
        StationDao stationDao = new StationDaoImpl();
        Optional<List<Station>> all = stationDao.getAll();
        return all.orElse(Collections.emptyList());
    }
}
