package ua.martishyn.app.controller.commands;

import ua.martishyn.app.controller.commands.admin.route.*;
import ua.martishyn.app.controller.commands.admin.station.*;
import ua.martishyn.app.controller.commands.admin.status.CustomerTicketsStatusPageCommand;
import ua.martishyn.app.controller.commands.admin.user.AdminUserDeleteCommand;
import ua.martishyn.app.controller.commands.admin.user.AdminUserEditCommand;
import ua.martishyn.app.controller.commands.admin.user.AdminUserEditPostCommand;
import ua.martishyn.app.controller.commands.admin.user.AdminUsersPageCommand;
import ua.martishyn.app.controller.commands.admin.wagon.*;
import ua.martishyn.app.controller.commands.common.AboutUsCommand;
import ua.martishyn.app.controller.commands.common.SearchTicketsCommand;
import ua.martishyn.app.controller.commands.common.ShowRouteCommand;
import ua.martishyn.app.controller.commands.customer.CustomerBuyTicketCommand;
import ua.martishyn.app.controller.commands.customer.CustomerTicketFormCommand;
import ua.martishyn.app.controller.commands.customer.CustomerTicketPayCommand;
import ua.martishyn.app.controller.commands.customer.CustomerTicketsPageCommand;

import java.util.HashMap;
import java.util.Map;

public class CommandContainer {
    static Map<String, ICommand> commands = new HashMap<>();

    private CommandContainer() {
    }

    static {
        commands.put("/index.command", new IndexPageCommand());
        commands.put("/about-us.command", new AboutUsCommand());
        commands.put("/login-page.command", new LoginPageCommand());
        commands.put("/login.command", new LoginCommand());
        commands.put("/register-page.command", new RegisterPageCommand());
        commands.put("/register.command", new RegisterCommand());
        commands.put("/logout.command", new LogoutCommand());

        //stations admin crud
        commands.put("/stations-page.command", new StationsPageCommand());
        commands.put("/station-add.command", new StationAddCommand());
        commands.put("/station-add-post.command", new StationsAddPOSTCommand());
        commands.put("/station-edit.command", new StationEditCommand());
        commands.put("/station-edit-post.command", new StationEditPOSTCommand());
        commands.put("/station-delete.command", new StationDeleteCommand());
        //wagons admin crud
        commands.put("/wagons-page.command", new WagonsPageCommand());
        commands.put("/wagon-edit.command", new WagonEditCommand());
        commands.put("/wagon-add.command", new WagonAddCommand());
        commands.put("/wagon-add-post.command", new WagonAddPostCommand());
        commands.put("/wagon-edit-post.command", new WagonEditPostCommand());
        commands.put("/wagon-delete.command", new WagonDeleteCommand());
        //users admin crud
        commands.put("/users-page.command", new AdminUsersPageCommand());
        commands.put("/user-edit.command", new AdminUserEditCommand());
        commands.put("/user-edit-post.command", new AdminUserEditPostCommand());
        commands.put("/user-delete.command", new AdminUserDeleteCommand());
        //routes admin crud
        commands.put("/routes-page.command", new RoutePointsPageCommand());
        commands.put("/route-edit.command", new RoutePointEditCommand());
        commands.put("/route-edit-post.command", new RoutePointEditPostCommand());
        commands.put("/route-delete.command", new RoutePointDeleteCommand());
        commands.put("/route-add.command", new RoutePointAddCommand());
        commands.put("/route-add-post.command", new RoutePointAddPostCommand());
        //admin report
        commands.put("/customer-ticket-status.command", new CustomerTicketsStatusPageCommand());
        //users booking
        commands.put("/search-tickets.command", new SearchTicketsCommand());
        commands.put("/customer-ticket-form.command", new CustomerTicketFormCommand());
        commands.put("/customer-buy-ticket.command", new CustomerBuyTicketCommand());
        commands.put("/customer-tickets-page.command", new CustomerTicketsPageCommand());
        commands.put("/customer-ticket-pay.command", new CustomerTicketPayCommand());
        commands.put("/show-route.command", new ShowRouteCommand());
    }

    public static boolean isCommandExists(String command) {
        return commands.containsKey(command);
    }

    public static ICommand getCommand(String command) {
        return commands.get(command);
    }
}
