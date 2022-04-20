package ua.martishyn.app.controller.commands.admin.station;

import ua.martishyn.app.controller.commands.ICommand;
import ua.martishyn.app.data.dao.impl.StationDaoImpl;
import ua.martishyn.app.data.dao.interfaces.StationDao;
import ua.martishyn.app.data.entities.Station;
import ua.martishyn.app.data.utils.DataBasePoolManager;
import ua.martishyn.app.data.utils.ViewPath;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class StationsPageCommand implements ICommand {
    private static final StationDao stationDao = new StationDaoImpl();
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Optional<List<Station>> stationsFromDb = stationDao.getAll();
        if (stationsFromDb.isPresent()){
                request.setAttribute("stations", stationsFromDb.get());
        }else {
            request.setAttribute("no-stations", "No stations found at the moment");
        }

        RequestDispatcher requestDispatcher = request.getRequestDispatcher(ViewPath.ADMIN_STATIONS);
        requestDispatcher.forward(request,response);

    }
}
