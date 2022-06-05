package ua.martishyn.app.controller.commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.martishyn.app.data.service.UserService;
import ua.martishyn.app.data.utils.constants.ViewConstants;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegisterCommand implements ICommand {
    private static final Logger log = LogManager.getLogger(RegisterCommand.class);
    private final UserService userService;

    public RegisterCommand() {
        userService = new UserService();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (userService.isUserInputIsValid(request) && userService.createUser(request)) {
            log.info("User created successfully!");
            response.sendRedirect("index.command");
            return;
        }
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(ViewConstants.REGISTER_PAGE);
        requestDispatcher.forward(request, response);
    }
}