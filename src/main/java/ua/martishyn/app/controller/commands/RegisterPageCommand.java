package ua.martishyn.app.controller.commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.martishyn.app.data.utils.Constants;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegisterPageCommand implements ICommand {
    private static final Logger log = LogManager.getLogger(RegisterPageCommand.class);


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.info("Redirecting to registration form");
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(Constants.REGISTER_PAGE);
        requestDispatcher.forward(request, response);

    }
}