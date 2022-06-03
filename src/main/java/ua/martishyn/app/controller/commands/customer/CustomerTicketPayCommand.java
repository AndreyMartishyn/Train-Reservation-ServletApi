package ua.martishyn.app.controller.commands.customer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.martishyn.app.controller.commands.ICommand;
import ua.martishyn.app.controller.filters.HasRole;
import ua.martishyn.app.data.entities.enums.Role;
import ua.martishyn.app.data.service.TicketService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@HasRole(role = Role.CUSTOMER)
public class CustomerTicketPayCommand implements ICommand {
    private static final Logger log = LogManager.getLogger(CustomerTicketPayCommand.class);
    private final TicketService ticketService;

    public CustomerTicketPayCommand() {
        ticketService = new TicketService();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (ticketService.isTicketPaid(request)) {
            log.info("Ticket is paid now");
        } else {
            request.setAttribute("notPaid", "Ticket not paid");
        }
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("customer-tickets-page.command");
        requestDispatcher.forward(request, response);
    }
}
