package ua.martishyn.app.controller.commands.customer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.martishyn.app.controller.commands.ICommand;
import ua.martishyn.app.controller.filters.HasRole;
import ua.martishyn.app.data.dao.impl.TicketDaoImpl;
import ua.martishyn.app.data.dao.interfaces.TicketDao;
import ua.martishyn.app.data.entities.Ticket;
import ua.martishyn.app.data.entities.User;
import ua.martishyn.app.data.entities.enums.Role;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@HasRole(role = Role.CUSTOMER)
public class CustomerTicketsPageCommand implements ICommand {
    private static final TicketDao ticketDao = new TicketDaoImpl();
    private static final Logger log = LogManager.getLogger(CustomerTicketsPageCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (isUserInSession(request)) {
            User currentUser = (User) request.getSession().getAttribute("user");
            Optional<List<Ticket>> ticketsFromDb = ticketDao.getAllTicketsById(currentUser.getId());
            if (ticketsFromDb.isPresent()) {
                log.info("Appropriate tickets found. Size : {}", ticketsFromDb.get().size());
                request.setAttribute("userTickets", ticketsFromDb.get());
            } else {
                log.info("Tickets not found");
                request.setAttribute("noTickets", "No tickets found for customer");
            }
        } else {
            log.info("User not in session. Redirect to main page");
            response.sendRedirect("index.command");
            return;
        }
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/view/customer/customer_ticket_page.jsp");
        requestDispatcher.forward(request, response);
    }

    boolean isUserInSession(HttpServletRequest request) {
        return request.getSession().getAttribute("user") != null;
    }
}
