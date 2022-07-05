package ua.martishyn.app.controller.commands.admin.status;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.martishyn.app.controller.commands.ICommand;
import ua.martishyn.app.controller.filters.HasRole;
import ua.martishyn.app.data.entities.Ticket;
import ua.martishyn.app.data.entities.enums.Role;
import ua.martishyn.app.data.service.TicketService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@HasRole(role = Role.ADMIN)
public class CustomerTicketsStatusPageCommand implements ICommand {
    private static final Logger log = LogManager.getLogger(CustomerTicketsStatusPageCommand.class);
    private final TicketService ticketService;

    public CustomerTicketsStatusPageCommand() {
        ticketService = new TicketService();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Ticket> ticketsFromDb = ticketService.getAllTickets();
        HttpSession session = request.getSession();
        session.setAttribute("tickets", ticketsFromDb);
        log.info("Tickets found - {}", ticketsFromDb.size());
        response.sendRedirect("pdf-report");
    }
}
