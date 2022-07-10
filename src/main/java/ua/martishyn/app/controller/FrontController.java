package ua.martishyn.app.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.martishyn.app.controller.commands.CommandContainer;
import ua.martishyn.app.controller.commands.ICommand;
import ua.martishyn.app.controller.filters.HasRole;
import ua.martishyn.app.data.entities.enums.Role;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.annotation.Annotation;

@WebServlet({"*.command"})
public class FrontController extends HttpServlet {
    private static final Logger log = LogManager.getLogger(FrontController.class);


    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("doGet working");
        processRequest(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("doPost working");
        processRequest(request, response);
    }

    public void processRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String actionCommand = req.getRequestURI().substring(req.getContextPath().length());
        if (actionCommand.equals("/") || actionCommand.isEmpty()) {
            actionCommand = "/index.command";
        }
        log.info("Request command name --> {}", actionCommand);

        if (CommandContainer.isCommandExists(actionCommand)) {
            ICommand command = CommandContainer.getCommand(actionCommand);
            log.trace("Active command name --> {}", actionCommand);
            Annotation[] annotations = command.getClass().getDeclaredAnnotations();
            for (Annotation annotation : annotations) {
                if (annotation instanceof HasRole) {
                    Role requiredRole = ((HasRole) annotation).role();
                    Role currentRole = (Role) req.getSession().getAttribute("role");
                    if (differentRoles(requiredRole, currentRole)) {
                        resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                        return;
                    }
                }
            }
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

    private boolean differentRoles(Role requiredRole, Role currentRole) {
        return !requiredRole.equals(currentRole);
    }
}