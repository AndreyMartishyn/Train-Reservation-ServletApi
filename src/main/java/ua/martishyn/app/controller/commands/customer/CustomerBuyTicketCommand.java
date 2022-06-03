package ua.martishyn.app.controller.commands.customer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.martishyn.app.controller.commands.ICommand;
import ua.martishyn.app.controller.filters.HasRole;
import ua.martishyn.app.data.dao.impl.StationDaoImpl;
import ua.martishyn.app.data.dao.impl.TicketDaoImpl;
import ua.martishyn.app.data.dao.impl.TrainModelDaoImpl;
import ua.martishyn.app.data.dao.interfaces.StationDao;
import ua.martishyn.app.data.dao.interfaces.TicketDao;
import ua.martishyn.app.data.dao.interfaces.TrainAndModelDao;
import ua.martishyn.app.data.entities.*;
import ua.martishyn.app.data.entities.enums.Role;
import ua.martishyn.app.data.service.TicketService;
import ua.martishyn.app.data.utils.ViewConstants;
import ua.martishyn.app.data.utils.validator.DataInputValidator;
import ua.martishyn.app.data.utils.validator.DataInputValidatorImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@HasRole(role = Role.CUSTOMER)
public class CustomerBuyTicketCommand implements ICommand {
    private static final Logger log = LogManager.getLogger(CustomerBuyTicketCommand.class);
    private final TicketService ticketService;

    public CustomerBuyTicketCommand() {
        ticketService = new TicketService();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (ticketService.isTickerDataValid(request) && ticketService.createTicket(request)){
            log.info("Ticket created successfully");
            response.sendRedirect("customer-tickets-page.command");
        } else {
            log.info("Ticket not created");
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("index.command");
            requestDispatcher.forward(request, response);
        }
    }
}

