package ua.martishyn.app.controller.commands.admin.station;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.martishyn.app.controller.commands.ICommand;
import ua.martishyn.app.controller.filters.HasRole;
import ua.martishyn.app.data.dao.impl.StationDaoImpl;
import ua.martishyn.app.data.dao.interfaces.StationDao;
import ua.martishyn.app.data.entities.Station;
import ua.martishyn.app.data.entities.enums.Role;
import ua.martishyn.app.data.utils.Constants;
import ua.martishyn.app.data.utils.validator.DataInputValidator;
import ua.martishyn.app.data.utils.validator.DataInputValidatorImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@HasRole(role = Role.ADMIN)
public class StationEditPOSTCommand implements ICommand {
    private static final Logger log = LogManager.getLogger(StationEditPOSTCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (updateStation(request)) {
            log.info("Route updated successfully");
            response.sendRedirect("stations-page.command");
        } else {
            log.info("Unfortunately, route not updated. Redirect to view --> {}", Constants.ADMIN_ADD_EDIT_STATIONS);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(Constants.ADMIN_ADD_EDIT_STATIONS);
            requestDispatcher.forward(request, response);
        }
    }

    private boolean updateStation(HttpServletRequest request) {
        int stationId = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name").trim();
        String code = request.getParameter("code").trim();
        if (!isStationDataValid(name, code, request)) {
            return false;
        }
        StationDao stationDao = new StationDaoImpl();
        Station newStation = Station.builder()
                .id(stationId)
                .name(name)
                .code(code)
                .build();
        return stationDao.update(newStation);
    }

    private boolean isStationDataValid(String name, String code,
                                       HttpServletRequest request) {
        DataInputValidator dataValidator = new DataInputValidatorImpl();
        if (dataValidator.isValidStringInput(name)) {
            request.setAttribute(Constants.ERROR_VALIDATION, "Wrong name input");
            return false;
        }
        if (dataValidator.isValidStringInput(code)) {
            request.setAttribute(Constants.ERROR_VALIDATION, "Wrong code input");
            return false;
        }
        return true;
    }
}
