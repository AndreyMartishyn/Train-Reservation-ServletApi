package ua.martishyn.app.controller.commands.admin.route;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.martishyn.app.controller.commands.ICommand;
import ua.martishyn.app.controller.filters.HasRole;
import ua.martishyn.app.data.dao.impl.RouteDaoImpl;
import ua.martishyn.app.data.dao.interfaces.RouteDao;
import ua.martishyn.app.data.entities.enums.Role;
import ua.martishyn.app.data.utils.Constants;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@HasRole(role = Role.ADMIN)
public class SingleRouteDeleteCommand implements ICommand {
    private static final Logger log = LogManager.getLogger(SingleRouteDeleteCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (deleteRoutePart(request)) {
            log.info("Deleting route done");
            response.sendRedirect("routes-page.command");
        } else {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(Constants.ADMIN_ROUTES);
            requestDispatcher.forward(request, response);
        }
    }

    private boolean deleteRoutePart(HttpServletRequest request) {
        RouteDao singleRouteDao = new RouteDaoImpl();
        int id = Integer.parseInt(request.getParameter("id"));
        int stationId = Integer.parseInt(request.getParameter("stationId"));
        return singleRouteDao.deleteSingleRoute(id, stationId);
    }
}
