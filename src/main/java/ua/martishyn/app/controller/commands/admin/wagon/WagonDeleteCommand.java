package ua.martishyn.app.controller.commands.admin.wagon;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.martishyn.app.controller.commands.ICommand;
import ua.martishyn.app.controller.filters.HasRole;
import ua.martishyn.app.data.entities.enums.Role;
import ua.martishyn.app.data.service.WagonService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@HasRole(role = Role.ADMIN)
public class WagonDeleteCommand implements ICommand {
    private static final Logger log = LogManager.getLogger(WagonDeleteCommand.class);
    private final WagonService wagonService;

    public WagonDeleteCommand() {
        wagonService = new WagonService();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        deleteWagon(request);
        log.info("Wagon deleted successfully");
        response.sendRedirect("wagons-page.command");
    }

    private void deleteWagon(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        wagonService.deleteWagonById(id);
    }
}
