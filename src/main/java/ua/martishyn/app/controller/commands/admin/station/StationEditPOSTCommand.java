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

public class StationEditPOSTCommand implements ICommand {
    private static final StationDao stationDao = new StationDaoImpl();
    //TODO VALIDATION OF DATA

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (updateStation(request)) {
            response.sendRedirect("stations-page.command");
            return;
        }
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(Constants.ADMIN_STATIONS);
        requestDispatcher.forward(request, response);
    }

    private boolean updateStation(HttpServletRequest request) {
        int stationId = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String code = request.getParameter("code");

        Station newStation = Station.builder()
                .id(stationId)
                .name(name)
                .code(code)
                .build();
        return stationDao.update(newStation);

    }
}
