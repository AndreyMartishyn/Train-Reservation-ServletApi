package ua.martishyn.app.controller.commands.customer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.martishyn.app.controller.commands.ICommand;
import ua.martishyn.app.controller.filters.HasRole;
import ua.martishyn.app.data.dao.impl.TicketDaoImpl;
import ua.martishyn.app.data.dao.impl.TrainModelDaoImpl;
import ua.martishyn.app.data.dao.interfaces.TicketDao;
import ua.martishyn.app.data.dao.interfaces.TrainAndModelDao;
import ua.martishyn.app.data.entities.Ticket;
import ua.martishyn.app.data.entities.Train;
import ua.martishyn.app.data.entities.User;
import ua.martishyn.app.data.entities.Wagon;
import ua.martishyn.app.data.entities.enums.Role;
import ua.martishyn.app.data.utils.Constants;
import ua.martishyn.app.data.utils.validator.DataInputValidator;
import ua.martishyn.app.data.utils.validator.DataInputValidatorImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;
import java.util.Random;

@HasRole(role = Role.CUSTOMER)
public class CustomerBuyTicketCommand implements ICommand {
    private static final Logger log = LogManager.getLogger(CustomerBuyTicketCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (isTickerDataValid(request) && !isPlaceOccupied(request)) {
            if (createTicket(request)) {
                log.info("Ticket created successfully");
                response.sendRedirect("customer-tickets-page.command");
            } else {
                log.info("Ticket not created");
            }
        }
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("customer-booking.command");
        log.info("Redirect to view --> {}", Constants.CUSTOMER_BOOK_PAGE);
        requestDispatcher.forward(request, response);
    }

    private boolean isTickerDataValid(HttpServletRequest request) {
        DataInputValidator dataValidator = new DataInputValidatorImpl();
        HttpSession session = request.getSession();
        String firstName = request.getParameter("firstName").trim();
        if (dataValidator.isValidStringInput(firstName)) {
            session.setAttribute(Constants.ERROR_VALIDATION, "Wrong first name input");
            return false;
        }
        String lastName = request.getParameter("lastName").trim();
        if (dataValidator.isValidStringInput(lastName)) {
            session.setAttribute(Constants.ERROR_VALIDATION, "Wrong last name input");
            return false;
        }
        String place = request.getParameter("place").trim();
        if (dataValidator.isValidNumInput(place)) {
            session.setAttribute(Constants.ERROR_VALIDATION, "Enter valid place");
            return false;
        }
        return true;
    }

    private boolean createTicket(HttpServletRequest request) {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String trainId = request.getParameter("trainId");
        String fromStation = request.getParameter("departureStation");
        String toStation = request.getParameter("arrivalStation");
        String departureTime = request.getParameter("departureTime");
        String arrivalTime = request.getParameter("arrivalTime");
        String comfortClass = request.getParameter("class");
        int wagon = Integer.parseInt(request.getParameter("wagon"));
        int place = Integer.parseInt(request.getParameter("place"));

        Random random = new Random();
        Ticket userTicket = new Ticket();
        TicketDao ticketDao = new TicketDaoImpl();
        TrainAndModelDao trainAndModelDao = new TrainModelDaoImpl();
        Optional<Train> trainFromBooking = trainAndModelDao.getTrain(Integer.parseInt(trainId));
        Optional<Wagon> wagonBooked = trainAndModelDao.getWagonById(wagon);
        User currentUser = (User) request.getSession().getAttribute("user");

        //generate random number from 100 to 999
        int randomNum = random.nextInt(900) + 100;
        userTicket.setId(randomNum);
        trainFromBooking.ifPresent(userTicket::setTrain);
        userTicket.setUserId(currentUser.getId());
        userTicket.setFirstName(firstName);
        userTicket.setLastName(lastName);
        userTicket.setDepartureStation(fromStation);
        userTicket.setDepartureTime(departureTime);
        userTicket.setArrivalStation(toStation);
        userTicket.setArrivalTime(arrivalTime);
        userTicket.setPlace(place);
        userTicket.setWagon(wagon);
        userTicket.setComfortClass(comfortClass);
        userTicket.setPaid(false);
        if (comfortClass.equalsIgnoreCase("FIRST")) {
            userTicket.setPrice(600);
        } else {
            userTicket.setPrice(300);
        }
        wagonBooked.ifPresent(value -> updateCoach(trainAndModelDao, value));
        return ticketDao.createTicket(userTicket);
    }

    private boolean isPlaceOccupied(HttpServletRequest request) {
        int wagonNum = Integer.parseInt(request.getParameter("wagon"));
        int placeNum = Integer.parseInt(request.getParameter("place"));
        TicketDao ticketDao = new TicketDaoImpl();
        return ticketDao.getByPlaceAndWagon(wagonNum, placeNum);
    }

    private void updateCoach(TrainAndModelDao trainAndModelDao, Wagon bookedWagon) {
        int availableSeatsNum = bookedWagon.getNumOfSeats();
        bookedWagon.setNumOfSeats(--availableSeatsNum);
        trainAndModelDao.updateCoach(bookedWagon);
    }
}

