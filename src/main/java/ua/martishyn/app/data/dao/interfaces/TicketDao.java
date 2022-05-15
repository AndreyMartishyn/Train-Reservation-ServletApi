package ua.martishyn.app.data.dao.interfaces;

import ua.martishyn.app.data.entities.Ticket;

import java.util.List;
import java.util.Optional;

public interface TicketDao {

    boolean createTicket(Ticket ticket);

    Optional<List<Ticket>> getAllTicketsById(int id);

    boolean getByPlaceAndWagon(int wagon, int place);

    boolean updateTicketToPaid(int ticketId);
}
