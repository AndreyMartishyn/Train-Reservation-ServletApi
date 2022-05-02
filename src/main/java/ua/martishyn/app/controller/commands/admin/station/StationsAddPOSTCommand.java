package ua.martishyn.app.controller.commands.admin.station;

import ua.martishyn.app.controller.commands.ICommand;
import ua.martishyn.app.data.dao.impl.StationDaoImpl;
import ua.martishyn.app.data.dao.interfaces.StationDao;
import ua.martishyn.app.data.entities.Station;
import ua.martishyn.app.data.utils.Constants;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class StationsAddPOSTCommand implements ICommand {
    private static final StationDao stationDao = new StationDaoImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!addStation(request)){
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(Constants.HOME_PAGE);
            requestDispatcher.forward(request,response);
        }
        System.out.println("Station added");
        response.sendRedirect("stations-page.command");
    }

    private boolean addStation(HttpServletRequest request) {
        String name = request.getParameter("name");
        String code = request.getParameter("code");
        Station newStation = Station.builder()
                .name(name)
                .code(code)
                .build();

       return  stationDao.createStation(newStation);
    }
}
