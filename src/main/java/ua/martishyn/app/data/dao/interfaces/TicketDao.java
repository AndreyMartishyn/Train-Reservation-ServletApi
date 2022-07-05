package ua.martishyn.app.data.dao.interfaces;

import ua.martishyn.app.data.entities.Ticket;

import java.util.List;
import java.util.Optional;

public interface TicketDao {

    boolean createTicket(Ticket ticket);

    Optional<List<Ticket>> getUsersTickets(int id);

    Optional<List<Ticket>> getAllTickets();

    boolean updateTicketToPaid(int ticketId);
}
