package ua.martishyn.app.controller.commands.admin.wagon;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.martishyn.app.controller.commands.ICommand;
import ua.martishyn.app.controller.filters.HasRole;
import ua.martishyn.app.data.entities.enums.Role;
import ua.martishyn.app.data.service.WagonService;
import ua.martishyn.app.data.utils.constants.ViewConstants;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@HasRole(role = Role.ADMIN)
public class WagonAddPostCommand implements ICommand {
    private static final Logger log = LogManager.getLogger(WagonAddPostCommand.class);
    private final WagonService wagonService;

    public WagonAddPostCommand() {
        wagonService = new WagonService();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (wagonService.isWagonInputValid(request) && wagonService.createWagonFromRequestData(request)) {
            log.info("Wagon created successfully");
            response.sendRedirect("wagons-page.command");
            return;
        }
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("wagon-add.command");
        requestDispatcher.forward(request, response);
    }
}
