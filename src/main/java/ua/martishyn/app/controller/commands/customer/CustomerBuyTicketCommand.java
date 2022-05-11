package ua.martishyn.app.controller.commands.customer;

import ua.martishyn.app.controller.commands.ICommand;
import ua.martishyn.app.data.dao.impl.TicketDaoImpl;
import ua.martishyn.app.data.dao.impl.TrainModelDaoImpl;
import ua.martishyn.app.data.dao.interfaces.TicketDao;
import ua.martishyn.app.data.dao.interfaces.TrainAndModelDao;
import ua.martishyn.app.data.entities.Ticket;
import ua.martishyn.app.data.entities.Train;
import ua.martishyn.app.data.entities.Wagon;
import ua.martishyn.app.data.entities.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import java.util.Random;

public class CustomerBuyTicketCommand implements ICommand {
    private static final Random random = new Random();
    TrainAndModelDao trainAndModelDao = new TrainModelDaoImpl();
    TicketDao ticketDao = new TicketDaoImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Ticket userTicket = createTicket(request);
        if (isPlaceOccupied(userTicket.getWagon(), userTicket.getPlace())) {
            request.setAttribute("busyPlace", "Choose another seat. Seat # " + userTicket.getPlace() + " is busy");
        } else {
            Optional<Wagon> coachBooked = trainAndModelDao.getCoachById(userTicket.getWagon());
            coachBooked.ifPresent(this::updateCoach);
            ticketDao.createTicket(userTicket);
        }
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("customer-booking.command");
        requestDispatcher.forward(request, response);
    }

    private Ticket createTicket(HttpServletRequest request) {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String trainId = request.getParameter("trainId");
        String fromStation = request.getParameter("departureStation");
        String toStation = request.getParameter("arrivalStation");
        String departureTime = request.getParameter("departureTime");
        String arrivalTime = request.getParameter("arrivalTime");
        String comfortClass = request.getParameter("class");
        String wagon = request.getParameter("wagon");
        String place = request.getParameter("place");

        Ticket userTicket = new Ticket();
        Optional<Train> trainFromBooking = trainAndModelDao.getTrain(Integer.parseInt(trainId));
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
        userTicket.setPlace(Integer.parseInt(place));
        userTicket.setWagon(Integer.parseInt(wagon));
        userTicket.setComfortClass(comfortClass);
        userTicket.setPaid(false);
        if (comfortClass.equalsIgnoreCase("FIRST")) {
            userTicket.setPrice(600);
        } else {
            userTicket.setPrice(300);
        }
        return userTicket;
    }

    private void updateCoach(Wagon coachBooked) {
        int availableSeatsNum = coachBooked.getNumOfSeats();
        coachBooked.setNumOfSeats(--availableSeatsNum);
        trainAndModelDao.updateCoach(coachBooked);
    }

    private boolean isPlaceOccupied(int wagon, int place) {
        return ticketDao.isPlaceOccupied(wagon, place);
    }
}
