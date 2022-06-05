package ua.martishyn.app.controller.commands.admin.route;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.martishyn.app.controller.commands.ICommand;
import ua.martishyn.app.controller.filters.HasRole;
import ua.martishyn.app.data.entities.RoutePoint;
import ua.martishyn.app.data.entities.enums.Role;
import ua.martishyn.app.data.service.PaginationService;
import ua.martishyn.app.data.service.RouteService;
import ua.martishyn.app.data.utils.constants.ViewConstants;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@HasRole(role = Role.ADMIN)
public class RoutePointsPageCommand implements ICommand {
    private static final Logger log = LogManager.getLogger(RoutePointsPageCommand.class);
    private final RouteService routeService;
    private final PaginationService paginationService;

    public RoutePointsPageCommand() {
        routeService = new RouteService();
        paginationService = new PaginationService();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        paginate(request);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(ViewConstants.ADMIN_ROUTES);
        requestDispatcher.forward(request, response);
    }

    public void paginate(HttpServletRequest request) {
        int currentPage = Optional.ofNullable(request.getParameter("page"))
                .map(String::toString)
                .map(Integer::parseInt)
                .orElse(1);
        int entriesPerPage = 3;
        int offSet = (currentPage - 1) * entriesPerPage;
        Optional<List<RoutePoint>> routePointsPaginated = routeService.getRoutePointsPaginated(offSet, entriesPerPage);
        if (routePointsPaginated.isPresent()) {
            log.info("Loading routes from db. Routes quantity : {}", routePointsPaginated.get().size());
            int numOfEntries = paginationService.getNumberOfRows(this);
            int numOfPages = (int) Math.ceil(numOfEntries * 1.0
                    / entriesPerPage);
            request.setAttribute("noOfPages", numOfPages);
            request.setAttribute("paginatedEntries", routePointsPaginated.get());
            request.setAttribute("currentPage", currentPage);
        } else {
            request.setAttribute("noRoutes", "No routes found at the moment");
        }
    }
}