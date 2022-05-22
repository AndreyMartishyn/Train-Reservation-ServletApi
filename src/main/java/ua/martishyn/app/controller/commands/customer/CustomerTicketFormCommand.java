package ua.martishyn.app.controller.commands.customer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.martishyn.app.controller.commands.ICommand;
import ua.martishyn.app.controller.filters.HasRole;
import ua.martishyn.app.data.dao.impl.TrainModelDaoImpl;
import ua.martishyn.app.data.dao.interfaces.TrainAndModelDao;
import ua.martishyn.app.data.entities.BookingDTO;
import ua.martishyn.app.data.entities.Wagon;
import ua.martishyn.app.data.entities.enums.Role;
import ua.martishyn.app.data.utils.Constants;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@HasRole(role = Role.CUSTOMER)
public class CustomerTicketFormCommand implements ICommand {
    private static final Logger log = LogManager.getLogger(CustomerTicketFormCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BookingDTO bookingDTO = makeDTOObject(request);
        Optional<List<Wagon>> coachesByClass = getCoachesByClass(bookingDTO);
        if (coachesByClass.isPresent()) {
            bookingDTO.setCoachesNumbers(getCoachesNumbers(coachesByClass.get()));
            request.setAttribute("bookingDTO", bookingDTO);
        } else {
            log.info("No coaches found");
            response.sendRedirect("customer-booking.command");
            return;
        }
        log.info("DTO object is transferred to the jsp form");
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(Constants.CUSTOMER_TICKETS_FORM);
        requestDispatcher.forward(request, response);
    }

    private Optional<List<Wagon>> getCoachesByClass(BookingDTO bookingDTO) {
        TrainAndModelDao trainAndModelDao = new TrainModelDaoImpl();
        return trainAndModelDao.getWagonsByClass(bookingDTO.getComfortClass());
    }

    private BookingDTO makeDTOObject(HttpServletRequest request) {
        String train = request.getParameter("train");
        String fromStation = request.getParameter("fromStation");
        String toStation = request.getParameter("toStation");
        String departure = request.getParameter("departure");
        String arrival = request.getParameter("arrival");
        String comfortClass = request.getParameter("class");

        //creating bookingDTO object to transfer data from link to the form. Allows auto-fil of info
        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setTrainId(Integer.parseInt(train));
        bookingDTO.setDepartureStation(fromStation);
        bookingDTO.setArrivalStation(toStation);
        bookingDTO.setDepartureTime(departure);
        bookingDTO.setArrivalTime(arrival);
        bookingDTO.setComfortClass(comfortClass);
        return bookingDTO;
    }

    private List<Integer> getCoachesNumbers(List<Wagon> coachesByClass) {
        return coachesByClass.stream()
                .filter(wagon -> wagon.getNumOfSeats() != 0)
                .map(Wagon::getId)
                .collect(Collectors.toList());
    }
}
