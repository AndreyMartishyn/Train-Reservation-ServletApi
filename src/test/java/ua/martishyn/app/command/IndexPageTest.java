package ua.martishyn.app.command;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ua.martishyn.app.controller.commands.IndexPageCommand;
import ua.martishyn.app.data.utils.Constants;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.Mockito.*;

public class IndexPageTest {
    HttpServletRequest request;
    HttpServletResponse response;
    RequestDispatcher requestDispatcher;
    IndexPageCommand indexPage;

    @Before
    public void setUp() {
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        requestDispatcher = mock(RequestDispatcher.class);
        indexPage = new IndexPageCommand();
    }

    @Test
    public void shouldReturnIndexPageOnRequest() throws ServletException, IOException {
        when(request.getRequestDispatcher(Constants.HOME_PAGE)).thenReturn(requestDispatcher);
        indexPage.execute(request, response);

        verify(request, times(1)).getRequestDispatcher(Constants.HOME_PAGE);
        verify(requestDispatcher).forward(request, response);
    }

    @After
    public void tearDown() {
        request = null;
        response = null;
        requestDispatcher = null;
        indexPage = null;
    }
}
