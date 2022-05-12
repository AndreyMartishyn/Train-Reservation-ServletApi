package ua.martishyn.app.controller.commands.customer;

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
import java.util.List;
import java.util.Optional;

@HasRole(role = Role.CUSTOMER)
public class CustomerBookingCommand implements ICommand {
    private static final Logger log = LogManager.getLogger(CustomerBookingCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Optional<List<Station>> stations = getStations();
        if (stations.isPresent()) {
            log.info("Loading stations from db. Stations quantity : {}", stations.get().size());
            request.setAttribute("stations", stations.get());
        } else {
            request.setAttribute("noStations", "No stations found");
        }
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(Constants.CUSTOMER_BOOK_PAGE);
        log.info("Redirect to view --> {}", Constants.CUSTOMER_BOOK_PAGE);
        requestDispatcher.forward(request, response);
    }

    private Optional<List<Station>> getStations() {
        StationDao stationDao = new StationDaoImpl();
        return stationDao.getAll();
    }
}
