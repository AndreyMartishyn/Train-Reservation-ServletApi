package ua.martishyn.app.controller.commands.admin.wagon;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.martishyn.app.controller.commands.ICommand;
import ua.martishyn.app.controller.commands.admin.station.StationEditCommand;
import ua.martishyn.app.controller.filters.HasRole;
import ua.martishyn.app.data.entities.Wagon;
import ua.martishyn.app.data.entities.enums.Role;
import ua.martishyn.app.data.service.WagonService;
import ua.martishyn.app.data.utils.constants.ViewConstants;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@HasRole(role = Role.ADMIN)
public class WagonEditCommand implements ICommand {
    private static final Logger log = LogManager.getLogger(WagonEditPostCommand.class);
    private final WagonService wagonService;

    public WagonEditCommand() {
        wagonService = new WagonService();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher;
        if (isWagonExist(request)) {
            requestDispatcher = request.getRequestDispatcher(ViewConstants.ADMIN_EDIT_WAGON);
        } else {
            log.error("No wagon found");
            requestDispatcher = request.getRequestDispatcher(ViewConstants.ADMIN_WAGONS);
        }
        requestDispatcher.forward(request, response);
    }

    private boolean isWagonExist(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        Optional<Wagon> wagonFromDb = wagonService.getWagonById(id);
        if (wagonFromDb.isPresent()) {
            log.info("Editing wagon with id {}", wagonFromDb.get().getId());
            request.setAttribute("wagon", wagonFromDb.get());
        }
        return wagonFromDb.isPresent();
    }
}
