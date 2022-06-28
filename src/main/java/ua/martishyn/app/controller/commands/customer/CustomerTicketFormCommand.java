package ua.martishyn.app.controller.commands.customer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.martishyn.app.controller.commands.ICommand;
import ua.martishyn.app.controller.filters.HasRole;
import ua.martishyn.app.data.entities.TicketFormDto;
import ua.martishyn.app.data.entities.Wagon;
import ua.martishyn.app.data.entities.enums.Role;
import ua.martishyn.app.data.service.TicketService;
import ua.martishyn.app.data.service.WagonService;
import ua.martishyn.app.data.utils.constants.ViewConstants;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@HasRole(role = Role.CUSTOMER)
public class CustomerTicketFormCommand implements ICommand {
    private static final Logger log = LogManager.getLogger(CustomerTicketFormCommand.class);
    private final TicketService ticketService;
    private final WagonService wagonService;

    public CustomerTicketFormCommand() {
        ticketService = new TicketService();
        wagonService = new WagonService();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        TicketFormDto ticketFormDto = ticketService.ticketInfoPreFill(request);
        Optional<List<Wagon>> coachesByClass = wagonService.getWagonsByClass(ticketFormDto);
        if (!coachesByClass.isPresent()) {
            log.info("No coaches found");
            response.sendRedirect("index.command");
            return;
        }
        ticketFormDto.setCoachesNumbers(ticketService.getWagonsIds(coachesByClass.get()));
        request.setAttribute("bookingDTO", ticketFormDto);
        log.info("DTO object is transferred to the buy ticket JSP form");
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(ViewConstants.CUSTOMER_TICKETS_FORM);
        requestDispatcher.forward(request, response);
    }
}



