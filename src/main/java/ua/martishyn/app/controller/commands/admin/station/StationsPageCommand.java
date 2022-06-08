package ua.martishyn.app.controller.commands.admin.station;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.martishyn.app.controller.commands.ICommand;
import ua.martishyn.app.controller.filters.HasRole;
import ua.martishyn.app.data.entities.Station;
import ua.martishyn.app.data.entities.enums.Role;
import ua.martishyn.app.data.service.PaginationService;
import ua.martishyn.app.data.service.StationService;
import ua.martishyn.app.data.utils.constants.ViewConstants;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@HasRole(role = Role.ADMIN)
public class StationsPageCommand implements ICommand {
    private static final Logger log = LogManager.getLogger(StationsPageCommand.class);
    private final StationService stationService;
    private final PaginationService paginationService;

    public StationsPageCommand() {
        stationService = new StationService();
        paginationService = new PaginationService();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        paginate(request);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(ViewConstants.ADMIN_STATIONS);
        requestDispatcher.forward(request, response);

    }

    public void paginate(HttpServletRequest request) {
        int currentPage = Optional.ofNullable(request.getParameter("page"))
                .map(String::toString)
                .map(Integer::parseInt)
                .orElse(1);
        int entriesPerPage = 3;
        int offSet = (currentPage - 1) * entriesPerPage;
        final List<Station> stationsPaginated = stationService.getAllStationsPaginated(offSet, entriesPerPage);
        if (!stationsPaginated.isEmpty()) {
            log.info("Loading stations from db. Users quantity : {}", stationsPaginated.size());
            int numOfEntries = paginationService.getNumberOfRows(this);
            int numOfPages = (int) Math.ceil(numOfEntries * 1.0
                    / entriesPerPage);
            request.setAttribute("noOfPages", numOfPages);
            request.setAttribute("paginatedEntries", stationsPaginated);
            request.setAttribute("currentPage", currentPage);
        } else {
            request.setAttribute("noStations", "No stations found at the moment");
        }
    }
}
