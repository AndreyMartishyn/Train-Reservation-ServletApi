package ua.martishyn.app.controller.commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.martishyn.app.data.entities.Station;
import ua.martishyn.app.data.service.StationService;
import ua.martishyn.app.data.utils.constants.ViewConstants;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class IndexPageCommand implements ICommand {
    private static final Logger log = LogManager.getLogger(IndexPageCommand.class);
    private final StationService stationService;

    public IndexPageCommand() {
        stationService = new StationService();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        prepareStationsForSearch(request);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(ViewConstants.HOME_PAGE);
        requestDispatcher.forward(request, response);
    }

    private void prepareStationsForSearch(HttpServletRequest request) {
        List<Station> stations = stationService.getAllStations();
        if (stations.isEmpty()) {
            request.setAttribute("noStations", "No stations found");
        } else {
            log.info("Loading stations from db. Stations quantity : {}", stations.size());
            request.setAttribute("stations", stations);
        }
    }
}
