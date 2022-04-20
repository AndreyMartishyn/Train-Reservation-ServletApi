package ua.martishyn.app.controller.commands.admin;

import ua.martishyn.app.controller.commands.ICommand;
import ua.martishyn.app.data.utils.ViewPath;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AdminMainCommand implements ICommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(ViewPath.ADMIN_MAIN);
        requestDispatcher.forward(request,response);
    }
}
