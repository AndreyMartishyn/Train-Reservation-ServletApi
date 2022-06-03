package ua.martishyn.app.controller.commands.admin.station;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.martishyn.app.controller.commands.ICommand;
import ua.martishyn.app.controller.filters.HasRole;
import ua.martishyn.app.data.entities.Station;
import ua.martishyn.app.data.entities.enums.Role;
import ua.martishyn.app.data.service.StationService;
import ua.martishyn.app.data.utils.ViewConstants;
import ua.martishyn.app.data.utils.validator.DataInputValidator;
import ua.martishyn.app.data.utils.validator.DataInputValidatorImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@HasRole(role = Role.ADMIN)
public class StationsAddPOSTCommand implements ICommand {
    private static final Logger log = LogManager.getLogger(StationsAddPOSTCommand.class);
    private final StationService stationService;
    private final DataInputValidator dataInputValidator;

    public StationsAddPOSTCommand() {
        stationService = new StationService();
        dataInputValidator = new DataInputValidatorImpl();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (isStationDataValid(request) && addStation(request)) {
            log.info("Route updated successfully");
            response.sendRedirect("stations-page.command");
            return;
        }
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(ViewConstants.ADMIN_ADD_EDIT_STATIONS);
        requestDispatcher.forward(request, response);
    }

    private boolean addStation(HttpServletRequest request) {
        String name = request.getParameter("name").trim();
        String code = request.getParameter("code").trim();
        Station newStation = Station.builder()
                .name(name)
                .code(code)
                .build();
        return stationService.createStation(newStation);
    }

    private boolean isStationDataValid(HttpServletRequest request) {
       return stationService.isStationDataValid(request);
    }
}
