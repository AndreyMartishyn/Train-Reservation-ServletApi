package ua.martishyn.app.controller;

import ua.martishyn.app.controller.commands.*;
import ua.martishyn.app.controller.commands.admin.AdminMainCommand;
import ua.martishyn.app.controller.commands.admin.station.*;
import ua.martishyn.app.controller.commands.admin.user.UserDeleteCommand;
import ua.martishyn.app.controller.commands.admin.user.UserEditCommand;
import ua.martishyn.app.controller.commands.admin.user.UserEditPostCommand;
import ua.martishyn.app.controller.commands.admin.user.UsersPageCommand;

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
    static Map<String, ICommand> commandContainer = new HashMap<>();

    static{
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
        commandContainer.put("/station-add-post.command", new StationsAddPostCommand());
        commandContainer.put("/station-edit.command", new StationEditCommand());
        commandContainer.put("/station-edit-post.command", new StationEditPostCommand());
        commandContainer.put("/station-delete.command", new StationDeleteCommand());
        //users admin crud
        commandContainer.put("/users-page.command", new UsersPageCommand());
        commandContainer.put("/user-edit.command", new UserEditCommand());
        commandContainer.put("/user-edit-post.command", new UserEditPostCommand());
        commandContainer.put("/user-delete.command", new UserDeleteCommand());
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("doGet working");
        processRequest(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("doPost working");
        processRequest(request, response);
    }

    protected void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String actionCommand = req.getRequestURI().substring(req.getContextPath().length());
        if (actionCommand.equals("/") || actionCommand.isEmpty()){
            actionCommand = "/index.command";
        }
        System.out.println("Request command name - " + actionCommand);

        if (commandContainer.containsKey(actionCommand)) {
            ICommand command = commandContainer.get(actionCommand);
            System.out.println("Actual command name -" + actionCommand);
            try {
               command.execute(req, resp);
            } catch (ServletException | IOException exception) {
                System.out.println("Command Error");
                exception.printStackTrace();
            }
        } else {
            System.out.println("No command found in container");

        }
    }
}
