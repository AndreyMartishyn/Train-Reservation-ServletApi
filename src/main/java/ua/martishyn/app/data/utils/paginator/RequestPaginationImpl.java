package ua.martishyn.app.data.utils.paginator;

import ua.martishyn.app.data.entities.Entity;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

public final class RequestPaginationImpl implements RequestPaginationHelper {
    public static final int ENTRIES_PER_PAGE = 3;

    public RequestPaginationImpl() {
    }

    public List<? extends Entity> paginate(HttpServletRequest request, List<? extends Entity> list) {
        int page = 1;
        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }
        int offSet = (page - 1) * ENTRIES_PER_PAGE;
        int noOfRecords = list.size();
        int numOfPages = (int) Math.ceil(noOfRecords * 1.0
                / ENTRIES_PER_PAGE);
        List<? extends Entity> paginatedList = list.stream().skip(offSet)
                .limit(ENTRIES_PER_PAGE)
                .collect(Collectors.toList());
        request.setAttribute("paginatedEntries", paginatedList);
        request.setAttribute("noOfPages", numOfPages);
        request.setAttribute("currentPage", page);
        return list.stream().skip(offSet)
                .limit(ENTRIES_PER_PAGE)
                .collect(Collectors.toList());
    }
}
