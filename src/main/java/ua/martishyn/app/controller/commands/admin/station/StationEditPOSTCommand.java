package ua.martishyn.app.controller.commands.admin.station;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.martishyn.app.controller.commands.ICommand;
import ua.martishyn.app.controller.filters.HasRole;
import ua.martishyn.app.data.entities.Station;
import ua.martishyn.app.data.entities.enums.Role;
import ua.martishyn.app.data.service.StationService;
import ua.martishyn.app.data.utils.constants.ViewConstants;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@HasRole(role = Role.ADMIN)
public class StationEditPOSTCommand implements ICommand {
    private static final Logger log = LogManager.getLogger(StationEditPOSTCommand.class);
    private final StationService stationService;

    public StationEditPOSTCommand() {
        stationService = new StationService();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (stationService.isStationDataValid(request) && stationService.updateStationFromRequest(request)) {
            log.info("Route updated successfully");
            response.sendRedirect("stations-page.command");
            return;
        }
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(ViewConstants.ADMIN_ADD_EDIT_STATIONS);
            requestDispatcher.forward(request, response);
    }
}
