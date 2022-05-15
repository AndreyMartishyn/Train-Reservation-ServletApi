package ua.martishyn.app.command;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ua.martishyn.app.controller.commands.RegisterPageCommand;
import ua.martishyn.app.data.utils.Constants;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.Mockito.*;

public class RegisterPageTest {
    HttpServletRequest mockRequest;
    HttpServletResponse mockResponse;
    RequestDispatcher mockDispatcher;
    RegisterPageCommand registerPageCommand;

    @Before
    public void setUp() {
        mockRequest = mock(HttpServletRequest.class);
        mockResponse = mock(HttpServletResponse.class);
        mockDispatcher = mock(RequestDispatcher.class);
        registerPageCommand = new RegisterPageCommand();
    }

    @Test
    public void shouldReturnIndexPageOnRequest() throws ServletException, IOException {
        when(mockRequest.getRequestDispatcher(Constants.REGISTER_PAGE)).thenReturn(mockDispatcher);
        registerPageCommand.execute(mockRequest, mockResponse);

        verify(mockRequest, times(1)).getRequestDispatcher(Constants.REGISTER_PAGE);
        verify(mockDispatcher).forward(mockRequest, mockResponse);
    }

    @After
    public void tearDown() {
        mockRequest = null;
        mockResponse = null;
        mockDispatcher = null;
        registerPageCommand = null;
    }
}
