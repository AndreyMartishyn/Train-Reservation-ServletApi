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
    private final BookingDTO bookingDTO = new BookingDTO();
    private final TrainAndModelDao trainAndModelDao = new TrainModelDaoImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        fillObjectWithData(request);

        Optional<List<Wagon>> coachesByClass = getCoachesByClass();

        if (!coachesByClass.isPresent()) {
            log.info("No coaches found");
            response.sendRedirect("customer-booking.command");
            return;
        } else {
            bookingDTO.setCoachesNumbers(getCoachesNumbers(coachesByClass.get()));
            request.setAttribute("bookingDTO", bookingDTO);
        }
        log.info("DTO object is transferred to the jsp form");
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/view/customer/customer_ticket_buy.jsp");
        requestDispatcher.forward(request, response);
    }

    private Optional<List<Wagon>> getCoachesByClass() {
        return trainAndModelDao.getCoachesByClass(bookingDTO.getComfortClass());
    }

    private void fillObjectWithData(HttpServletRequest request) {
        String train = request.getParameter("train");
        String fromStation = request.getParameter("fromStation");
        String toStation = request.getParameter("toStation");
        String departure = request.getParameter("departure");
        String arrival = request.getParameter("arrival");
        String comfortClass = request.getParameter("class");

        //creating bookingDTO object to transfer data from link to the form. Allows auto-fil of info
        bookingDTO.setTrainId(Integer.parseInt(train));
        bookingDTO.setDepartureStation(fromStation);
        bookingDTO.setArrivalStation(toStation);
        bookingDTO.setDepartureTime(departure);
        bookingDTO.setArrivalTime(arrival);
        bookingDTO.setComfortClass(comfortClass);
    }

    private List<Integer> getCoachesNumbers(List<Wagon> coachesByClass) {
        return coachesByClass.stream()
                .filter(wagon -> wagon.getNumOfSeats() != 0)
                .map(Wagon::getId)
                .collect(Collectors.toList());
    }
}
