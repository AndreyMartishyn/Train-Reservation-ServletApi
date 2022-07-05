package ua.martishyn.app.controller.commands.customer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.martishyn.app.controller.commands.ICommand;
import ua.martishyn.app.controller.filters.HasRole;
import ua.martishyn.app.data.entities.Ticket;
import ua.martishyn.app.data.entities.enums.Role;
import ua.martishyn.app.data.service.TicketService;
import ua.martishyn.app.data.utils.constants.ViewConstants;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@HasRole(role = Role.CUSTOMER)
public class CustomerTicketsPageCommand implements ICommand {
    private static final Logger log = LogManager.getLogger(CustomerTicketsPageCommand.class);
    private final TicketService ticketService;

    public CustomerTicketsPageCommand() {
        ticketService = new TicketService();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Ticket> ticketsFromDb = ticketService.getUsersTickets(request);
        if (ticketsFromDb.isEmpty()) {
            log.info("Tickets not found");
            request.setAttribute("noTickets", true);
        } else {
            log.info("Appropriate tickets found. Size : {}", ticketsFromDb.size());
            request.setAttribute("userTickets", ticketsFromDb);
        }
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(ViewConstants.CUSTOMER_TICKETS_PAGE);
        requestDispatcher.forward(request, response);
    }
}
