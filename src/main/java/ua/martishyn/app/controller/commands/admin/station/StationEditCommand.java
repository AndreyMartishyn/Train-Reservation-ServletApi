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
import java.util.Optional;

public class StationEditCommand implements ICommand {
    private static final StationDao stationDao = new StationDaoImpl();
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
         Optional<Station> stationFromDb = getStation(request);
        if (stationFromDb.isPresent()) {
            System.out.println("Getting station from db");
            request.setAttribute("station", stationFromDb.get());
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(Constants.ADMIN_ADD_EDIT_STATIONS);
            requestDispatcher.forward(request, response);
        } else {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(Constants.ADMIN_STATIONS);
            requestDispatcher.forward(request, response);
        }
    }

    private Optional<Station> getStation(HttpServletRequest request){
        int id = Integer.parseInt(request.getParameter("id"));
        return stationDao.getById(id);
        }
    }

