package ua.martishyn.app.controller.commands.customer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.martishyn.app.controller.commands.ICommand;
import ua.martishyn.app.data.dao.impl.StationDaoImpl;
import ua.martishyn.app.data.dao.interfaces.StationDao;
import ua.martishyn.app.data.entities.Station;
import ua.martishyn.app.data.utils.Constants;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class CustomerBookingCommand implements ICommand {
    private static final Logger log = LogManager.getLogger(CustomerBookingCommand.class);
    private static final StationDao stationDao = new StationDaoImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Optional<List<Station>> stations = stationDao.getAll();
        HttpSession session  = request.getSession();
        if (stations.isPresent()){
            session.setAttribute("stations", stations.get());
        }
        else {
            session.setAttribute("noStations", "No stations found");
        }
        log.info("Loading booking page");
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(Constants.CUSTOMER_BOOK_PAGE);
        requestDispatcher.forward(request, response);
    }

}
