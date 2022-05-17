package ua.martishyn.app.utils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.martishyn.app.data.entities.Entity;
import ua.martishyn.app.data.entities.User;
import ua.martishyn.app.data.utils.paginator.RequestPaginationImpl;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PaginatorTest<T> {
    private List<User> userList;
    @Mock
    HttpServletRequest mockedRequest;

    @InjectMocks
    RequestPaginationImpl paginator;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        paginator = new RequestPaginationImpl();
        userList = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            userList.add(
                    User.builder()
                            .id(i)
                            .firstName("FIRST" + i)
                            .lastName("LAST" + i)
                            .build()
            );
        }
    }

    @Test
    public void shouldPaginatePageAndAddAttributesToRequestWhenFirstPageWithAllEnties() {
        when(mockedRequest.getParameter("page")).thenReturn("1");
        List<? extends Entity> paginatedList = paginator.paginate(mockedRequest, userList);
        int expected = 3;
        int actual = paginatedList.size();
        Assert.assertEquals(expected, actual);
        verify(mockedRequest).setAttribute("paginatedEntries", paginatedList);
        verify(mockedRequest).setAttribute("noOfPages", RequestPaginationImpl.ENTRIES_PER_PAGE);
        verify(mockedRequest).setAttribute("currentPage", 1);
    }

    @Test
    public void shouldPaginatePageAndAddAttributesToRequestWhenLastPageWithSingleEntry() {
        when(mockedRequest.getParameter("page")).thenReturn("3");
        List<? extends Entity> paginatedList = paginator.paginate(mockedRequest, userList);
        int expected = 1;
        int actual = paginatedList.size();
        Assert.assertEquals(expected, actual);
        verify(mockedRequest).setAttribute("paginatedEntries", paginatedList);
        verify(mockedRequest).setAttribute("noOfPages", RequestPaginationImpl.ENTRIES_PER_PAGE);
        verify(mockedRequest).setAttribute("currentPage", 3);
    }
}
