package ua.martishyn.app.controller.commands.admin.station;

import ua.martishyn.app.controller.commands.ICommand;
import ua.martishyn.app.data.utils.ViewPath;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class StationAddCommand implements ICommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(ViewPath.ADMIN_ADD_EDIT_STATIONS);
        requestDispatcher.forward(request,response);
    }
}