package ua.martishyn.app.controller.commands.admin.user;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.martishyn.app.controller.commands.ICommand;
import ua.martishyn.app.controller.filters.HasRole;
import ua.martishyn.app.data.entities.User;
import ua.martishyn.app.data.entities.enums.Role;
import ua.martishyn.app.data.service.PaginationService;
import ua.martishyn.app.data.service.UserService;
import ua.martishyn.app.data.utils.constants.ViewConstants;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@HasRole(role = Role.ADMIN)
public class AdminUsersPageCommand implements ICommand {
    private static final Logger log = LogManager.getLogger(AdminUsersPageCommand.class);
    private final UserService userService;
    private final PaginationService paginationService;

    public AdminUsersPageCommand() {
        userService = new UserService();
        paginationService = new PaginationService();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        paginate(request);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(ViewConstants.ADMIN_USERS);
        requestDispatcher.forward(request, response);
    }

    public void paginate(HttpServletRequest request) {
        int currentPage = Optional.ofNullable(request.getParameter("page"))
                .map(String::toString)
                .map(Integer::parseInt)
                .orElse(1);
        int entriesPerPage = 3;
        int offSet = (currentPage - 1) * entriesPerPage;
        Optional<List<User>> usersPaginated = userService.getUsersPaginated(offSet, entriesPerPage);
        if (usersPaginated.isPresent()) {
            log.info("Loading users from db. Users quantity : {}", usersPaginated.get().size());
            int numOfEntries = paginationService.getNumberOfRows(this);
            int numOfPages = (int) Math.ceil(numOfEntries * 1.0
                    / entriesPerPage);
            request.setAttribute("noOfPages", numOfPages);
            request.setAttribute("paginatedEntries", usersPaginated.get());
            request.setAttribute("currentPage", currentPage);
        } else {
            request.setAttribute("noUsers", "No users found at the moment");
        }
    }
}
