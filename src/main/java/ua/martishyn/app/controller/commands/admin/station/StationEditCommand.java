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

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@HasRole(role = Role.ADMIN)
public class StationEditCommand implements ICommand {
    private static final Logger log = LogManager.getLogger(StationEditCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher;
        Optional<Station> stationFromDb = getStation(request);
        if (stationFromDb.isPresent()) {
            log.info("Editing station with id {}", stationFromDb.get().getId());
            request.setAttribute("station", stationFromDb.get());
            requestDispatcher = request.getRequestDispatcher(Constants.ADMIN_ADD_EDIT_STATIONS);
        } else {
            requestDispatcher = request.getRequestDispatcher(Constants.ADMIN_STATIONS);
        }
        requestDispatcher.forward(request, response);
    }

    private Optional<Station> getStation(HttpServletRequest request) {
        StationDao stationDao = new StationDaoImpl();
        int id = Integer.parseInt(request.getParameter("id"));
        return stationDao.getById(id);
    }
}

