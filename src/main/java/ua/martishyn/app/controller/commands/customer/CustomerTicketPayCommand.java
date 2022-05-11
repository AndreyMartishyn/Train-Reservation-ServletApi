package ua.martishyn.app.controller.commands.customer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.martishyn.app.controller.commands.ICommand;
import ua.martishyn.app.data.dao.impl.TicketDaoImpl;
import ua.martishyn.app.data.dao.interfaces.TicketDao;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomerTicketPayCommand implements ICommand {
    private static final Logger log = LogManager.getLogger(CustomerTicketPayCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int ticketId = Integer.parseInt(request.getParameter("ticketId"));
        if (payForTicket(request)) {
            log.info("Ticket # {} is paid now", ticketId);
        } else {
            log.info("Ticket # {} not paid ", ticketId);
            request.setAttribute("notPaid", "Ticket not paid");
        }
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("customer-tickets-page.command");
        requestDispatcher.forward(request, response);
    }

    private boolean payForTicket(HttpServletRequest request) {
        int ticketId = Integer.parseInt(request.getParameter("ticketId"));
        TicketDao ticketDao = new TicketDaoImpl();
        return ticketDao.updateTicketToPaid(ticketId);
    }
}
