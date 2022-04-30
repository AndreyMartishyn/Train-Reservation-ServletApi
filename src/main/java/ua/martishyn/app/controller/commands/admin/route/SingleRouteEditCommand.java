package ua.martishyn.app.controller.commands.admin.route;

import ua.martishyn.app.controller.commands.ICommand;
import ua.martishyn.app.data.dao.impl.RouteDaoImpl;
import ua.martishyn.app.data.dao.interfaces.RouteDao;
import ua.martishyn.app.data.entities.SingleRoute;
import ua.martishyn.app.data.utils.Constants;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class SingleRouteEditCommand implements ICommand {
    private static final RouteDao routeDao = new RouteDaoImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Optional<SingleRoute> singleRoute = getSingleRoute(request);
        if (singleRoute.isPresent()){
            request.setAttribute("singleRoute", singleRoute.get());
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(Constants.ADMIN_ROUTE_ADD_EDIT);
            requestDispatcher.forward(request, response);
        } else {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(Constants.ADMIN_ROUTES);
            requestDispatcher.forward(request, response);
        }
    }

    private Optional<SingleRoute> getSingleRoute(HttpServletRequest request){
        int id = Integer.parseInt(request.getParameter("id"));
        return routeDao.getSingleRoute(id);
    }
}
