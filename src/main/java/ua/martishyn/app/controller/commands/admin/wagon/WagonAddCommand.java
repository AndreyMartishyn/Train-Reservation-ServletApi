package ua.martishyn.app.controller.commands.admin.wagon;

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
public class WagonAddCommand implements ICommand {
    private final WagonService wagonService;

    public WagonAddCommand(){
        this.wagonService = new WagonService();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("routeIds", wagonService.getRouteIds());
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(ViewConstants.ADMIN_ADD_WAGON);
        requestDispatcher.forward(request, response);
    }
}