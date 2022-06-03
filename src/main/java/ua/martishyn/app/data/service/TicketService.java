package ua.martishyn.app.data.service;

import ua.martishyn.app.data.dao.impl.TicketDaoImpl;
import ua.martishyn.app.data.dao.interfaces.TicketDao;
import ua.martishyn.app.data.entities.*;
import ua.martishyn.app.data.utils.ViewConstants;
import ua.martishyn.app.data.utils.validator.DataInputValidator;
import ua.martishyn.app.data.utils.validator.DataInputValidatorImpl;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

public class TicketService {
    private final TicketDao ticketDao;
    private final TrainService trainService;
    private final StationService stationService;
    private final DataInputValidator dataInputValidator;

    public TicketService() {
        ticketDao = new TicketDaoImpl();
        trainService = new TrainService();
        stationService = new StationService();
        dataInputValidator = new DataInputValidatorImpl();
    }


    public List<Ticket> getAllTickets(HttpServletRequest request) {
        User currentUser = (User) request.getSession().getAttribute("user");
        final Optional<List<Ticket>> allTicketsById = ticketDao.getAllTicketsById(currentUser.getId());
        return allTicketsById.orElse(Collections.emptyList());
    }

    public boolean isTicketPaid(HttpServletRequest request) {
        int ticketId = Integer.parseInt(request.getParameter("ticketId"));
        return ticketDao.updateTicketToPaid(ticketId);
    }

    public List<Integer> getCoachesNumbers(List<Wagon> coachesByClass) {
        return coachesByClass.stream()
                .filter(wagon -> wagon.getNumOfSeats() != 0)
                .map(Wagon::getId)
                .collect(Collectors.toList());
    }


    private void updateCoach(Wagon bookedWagon) {
        int availableSeatsNum = bookedWagon.getNumOfSeats();
        bookedWagon.setNumOfSeats(--availableSeatsNum);
        trainService.updateWagon(bookedWagon);
    }

    public boolean createTicket(HttpServletRequest request) {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String trainId = request.getParameter("trainId");
        String fromStation = request.getParameter("departureStation");
        String toStation = request.getParameter("arrivalStation");
        String departureTime = request.getParameter("departureTime");
        String arrivalTime = request.getParameter("arrivalTime");
        int wagon = Integer.parseInt(request.getParameter("wagon"));
        int place = Integer.parseInt(request.getParameter("place"));
        int cost = Integer.parseInt(request.getParameter("price"));
        String duration = request.getParameter("duration");

        Random random = new Random();
        Ticket userTicket = new Ticket();
        Optional<Train> trainFromBooking = trainService.getTrainById(Integer.parseInt(trainId));
        Optional<Wagon> wagonBooked = trainService.getWagonById(wagon);
        Optional<Station> departureStation = stationService.getStationByName(fromStation);
        Optional<Station> arrivalStation = stationService.getStationByName(toStation);

        if (trainFromBooking.isPresent()
                && wagonBooked.isPresent()
                && departureStation.isPresent()
                && arrivalStation.isPresent()) {
            User currentUser = (User) request.getSession().getAttribute("user");
            //generate random number from 100 to 999
            int randomNum = random.nextInt(900) + 100;
            userTicket.setId(randomNum);
            trainFromBooking.ifPresent(userTicket::setTrain);
            userTicket.setUserId(currentUser.getId());
            userTicket.setFirstName(firstName);
            userTicket.setLastName(lastName);
            userTicket.setDepartureStation(departureStation.get());
            userTicket.setDepartureTime(departureTime);
            userTicket.setArrivalStation(arrivalStation.get());
            userTicket.setArrivalTime(arrivalTime);
            userTicket.setWagon(wagonBooked.get());
            userTicket.setPlace(place);
            updateCoach(wagonBooked.get());
            userTicket.setPaid(false);
            userTicket.setPrice(cost);
            userTicket.setDuration(duration);
        } else {
            return false;
        }
        return ticketDao.createTicket(userTicket);
    }


    public TicketFormDto ticketInfoPreFill(HttpServletRequest request) {
        String train = request.getParameter("train");
        String fromStation = request.getParameter("fromStation");
        String toStation = request.getParameter("toStation");
        String departure = request.getParameter("departure");
        String arrival = request.getParameter("arrival");
        String comfortClass = request.getParameter("class");
        int cost = Integer.parseInt(request.getParameter("price"));
        String duration = request.getParameter("duration");

        //creating bookingDTO object to transfer data from link to the form. Allows auto-fil of info
        TicketFormDto ticketFormDto = new TicketFormDto();
        ticketFormDto.setTrainId(Integer.parseInt(train));
        ticketFormDto.setDepartureStation(fromStation);
        ticketFormDto.setArrivalStation(toStation);
        ticketFormDto.setDepartureTime(departure);
        ticketFormDto.setArrivalTime(arrival);
        ticketFormDto.setComfortClass(comfortClass);
        ticketFormDto.setCost(cost);
        ticketFormDto.setDuration(duration);
        return ticketFormDto;
    }

    private boolean isSelectedPlaceOccupied(String wagon, String place) {
        int wagonNum = Integer.parseInt(wagon);
        int placeNum = Integer.parseInt(place);
        Optional<List<Ticket>> allTickets = ticketDao.getAllTickets();
        return allTickets.get().stream()
                .anyMatch(ticket -> ticket.getWagon().getId() == wagonNum
                        && ticket.getPlace() == placeNum);
    }

    private boolean hasAvailablePlace(HttpServletRequest request, String wagon, String place) {
        int selectedWagon = Integer.parseInt(wagon);
        int selectedPlace = Integer.parseInt(place);
        Optional<Wagon> wagonBooked = trainService.getWagonById(selectedWagon);
        if (!wagonBooked.isPresent()) {
            return false;
        }
        int numOfPlaces = wagonBooked.get().getNumOfSeats();
        if (selectedPlace <= numOfPlaces && selectedPlace > 0) {
            return true;
        }
        String result = "Enter valid place between 1 and " + numOfPlaces;
        request.setAttribute(ViewConstants.ERROR_VALIDATION, result);
        return false;
    }

    public boolean isTickerDataValid(HttpServletRequest request) {
        String firstName = request.getParameter("firstName").trim();
        if (!dataInputValidator.isValidStringInput(firstName)) {
            request.setAttribute(ViewConstants.ERROR_VALIDATION, "Wrong first name input");
            return false;
        }
        String lastName = request.getParameter("lastName").trim();
        if (!dataInputValidator.isValidStringInput(lastName)) {
            request.setAttribute(ViewConstants.ERROR_VALIDATION, "Wrong last name input");
            return false;
        }
        String place = request.getParameter("place").trim();
        if (!dataInputValidator.isValidNumInput(place)) {
            request.setAttribute(ViewConstants.ERROR_VALIDATION, "Enter valid place");
            return false;
        }
        String wagon = request.getParameter("wagon").trim();
        if (!hasAvailablePlace(request, wagon, place)) {
            return false;
        }
        if (isSelectedPlaceOccupied(wagon, place)) {
            request.setAttribute(ViewConstants.ERROR_VALIDATION, "Selected place is occupied");
            return false;
        }
        return true;
    }
}
