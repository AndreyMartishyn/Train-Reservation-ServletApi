package ua.martishyn.app.controller.commands.admin.station;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.martishyn.app.controller.commands.ICommand;
import ua.martishyn.app.controller.filters.HasRole;
import ua.martishyn.app.data.entities.Station;
import ua.martishyn.app.data.entities.enums.Role;
import ua.martishyn.app.data.service.StationService;
import ua.martishyn.app.data.utils.ViewConstants;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@HasRole(role = Role.ADMIN)
public class StationsPageCommand implements ICommand {
    private static final Logger log = LogManager.getLogger(StationsPageCommand.class);
    private final StationService stationService;

    public StationsPageCommand() {
        stationService = new StationService();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Station> stationsFromDb = stationService.getAllStations();
        if (stationsFromDb.isEmpty()) {
            request.setAttribute("no-stations", "No stations found at the moment");
        } else {
            log.info("Loading stations from db. Stations quantity : {}", stationsFromDb.size());
            request.setAttribute("stations", stationsFromDb);
        }
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(ViewConstants.ADMIN_STATIONS);
        requestDispatcher.forward(request, response);

    }
}
