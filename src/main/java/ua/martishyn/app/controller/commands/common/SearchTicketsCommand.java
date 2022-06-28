package ua.martishyn.app.controller.commands.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.martishyn.app.controller.commands.ICommand;
import ua.martishyn.app.data.entities.PersonalRoute;
import ua.martishyn.app.data.service.BookingService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


public class SearchTicketsCommand implements ICommand {
    private static final Logger log = LogManager.getLogger(SearchTicketsCommand.class);
    private final BookingService bookingService;

    public SearchTicketsCommand() {
        bookingService = new BookingService();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (bookingService.areStationsEqual(request)) {
            log.error("Same stations chosen by user");
            request.setAttribute("sameStations", true);
        } else {
            List<PersonalRoute> suitableRoutesFound = bookingService.findSuitableRoutes(request);
            if (suitableRoutesFound.isEmpty()) {
                log.error("Unfortunately, routes not found");
                request.setAttribute("noRoutes", true);
            } else {
                log.info("Appropriate routes found. Size : {}", suitableRoutesFound.size());
                request.setAttribute("suitableRoutes", suitableRoutesFound);
            }
        }
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("index.command");
        requestDispatcher.forward(request, response);
    }
}