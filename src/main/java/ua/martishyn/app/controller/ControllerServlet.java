package ua.martishyn.app.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.martishyn.app.controller.commands.*;
import ua.martishyn.app.controller.commands.admin.AdminMainCommand;
import ua.martishyn.app.controller.commands.admin.route.*;
import ua.martishyn.app.controller.commands.admin.station.*;
import ua.martishyn.app.controller.commands.admin.user.AdminUserDeleteCommand;
import ua.martishyn.app.controller.commands.admin.user.AdminUserEditCommand;
import ua.martishyn.app.controller.commands.admin.user.AdminUserEditPostCommand;
import ua.martishyn.app.controller.commands.admin.user.AdminUsersPageCommand;
import ua.martishyn.app.controller.commands.customer.CustomerBookingCommand;
import ua.martishyn.app.controller.commands.customer.CustomerTrainSearchCommand;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet({"*.command"})
public class ControllerServlet extends HttpServlet {
    private static final Logger log = LogManager.getLogger(ControllerServlet.class);
    static Map<String, ICommand> commandContainer = new HashMap<>();

    static {
        commandContainer.put("/index.command", new HomePageCommand());
        commandContainer.put("/login-page.command", new LoginPageCommand());
        commandContainer.put("/login.command", new LoginCommand());
        commandContainer.put("/register-page.command", new RegisterPageCommand());
        commandContainer.put("/register.command", new RegisterCommand());
        commandContainer.put("/logout.command", new LogoutCommand());

        commandContainer.put("/admin-main.command", new AdminMainCommand());
        //stations admin crud
        commandContainer.put("/stations-page.command", new StationsPageCommand());
        commandContainer.put("/station-add.command", new StationAddCommand());
        commandContainer.put("/station-add-post.command", new StationsAddPOSTCommand());
        commandContainer.put("/station-edit.command", new StationEditCommand());
        commandContainer.put("/station-edit-post.command", new StationEditPOSTCommand());
        commandContainer.put("/station-delete.command", new StationDeleteCommand());
        //users admin crud
        commandContainer.put("/users-page.command", new AdminUsersPageCommand());
        commandContainer.put("/user-edit.command", new AdminUserEditCommand());
        commandContainer.put("/user-edit-post.command", new AdminUserEditPostCommand());
        commandContainer.put("/user-delete.command", new AdminUserDeleteCommand());
        //routes admin crud
        commandContainer.put("/routes-page.command", new SingleRoutesPageCommand());
        commandContainer.put("/route-edit.command", new SingleRouteEditCommand());
        commandContainer.put("/route-edit-post.command", new SingleRouteEditPOSTCommand());
        commandContainer.put("/route-delete.command", new SingleRouteDeleteCommand());
        commandContainer.put("/route-add.command", new SingleRouteAddCommand());
        commandContainer.put("/route-add-post.command", new SingleRouteAddPOSTCommand());
        //users booking
        commandContainer.put("/customer-booking.command", new CustomerBookingCommand());
        commandContainer.put("/customer-train-search.command", new CustomerTrainSearchCommand());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        log.info("doGet working");
        processRequest(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)  {
        log.info("doPost working");
        processRequest(request, response);
    }

    protected void processRequest(HttpServletRequest req, HttpServletResponse resp)  {
        String actionCommand = req.getRequestURI().substring(req.getContextPath().length());
        if (actionCommand.equals("/") || actionCommand.isEmpty()) {
            actionCommand = "/index.command";
        }
        log.info("Request command name --> {}", actionCommand);

        if (commandContainer.containsKey(actionCommand)) {
            ICommand command = commandContainer.get(actionCommand);
            log.trace("Active command name --> {}", actionCommand);

            try {
                command.execute(req, resp);
            } catch (ServletException | IOException exception) {
                log.error("Something wrong with command {}", actionCommand);
                exception.printStackTrace();
            }
        } else {
            log.error("Not suitable command --> {}", actionCommand);
        }
    }
}
