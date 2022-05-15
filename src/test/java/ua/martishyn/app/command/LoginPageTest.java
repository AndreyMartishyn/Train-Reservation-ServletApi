package ua.martishyn.app.command;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ua.martishyn.app.controller.commands.LoginPageCommand;
import ua.martishyn.app.data.utils.Constants;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.Mockito.*;

public class LoginPageTest {
    HttpServletRequest request;
    HttpServletResponse response;
    RequestDispatcher requestDispatcher;
    LoginPageCommand loginPage;

    @Before
    public void setUp() {
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        requestDispatcher = mock(RequestDispatcher.class);
        loginPage = new LoginPageCommand();
    }

    @Test
    public void shouldReturnIndexPageOnRequest() throws ServletException, IOException {
        when(request.getRequestDispatcher(Constants.LOGIN_PAGE)).thenReturn(requestDispatcher);
        loginPage.execute(request, response);

        verify(request, times(1)).getRequestDispatcher(Constants.LOGIN_PAGE);
        verify(requestDispatcher).forward(request, response);
    }

    @After
    public void tearDown() {
        request = null;
        response = null;
        requestDispatcher = null;
        loginPage = null;
    }
}
