package ua.martishyn.app.data.utils.paginator;

import ua.martishyn.app.data.entities.Entity;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface RequestPaginationHelper {

    List<? extends Entity> paginate(HttpServletRequest request, List<? extends Entity> list);
}
