package ua.martishyn.app.commands;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.martishyn.app.controller.commands.LogoutCommand;
import ua.martishyn.app.data.utils.Constants;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static org.mockito.Mockito.*;

public class LogoutTest {
    @Mock
    HttpServletResponse mockResponse;
    @Mock
    HttpServletRequest mockRequest;
    @Mock
    RequestDispatcher mockDispatcher;
    @Mock
    HttpSession mockSession;

    LogoutCommand logoutCommand;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        logoutCommand = new LogoutCommand();
    }

    @Test
    public void shouldInvalidateNotNullSessionWhenLogout() throws ServletException, IOException {
        when(mockRequest.getRequestDispatcher("index.command")).thenReturn(mockDispatcher);
        when(mockRequest.getSession(false)).thenReturn(mockSession);

        logoutCommand.execute(mockRequest,mockResponse);
        verify(mockSession).invalidate();
        verify(mockRequest, times(1)).getRequestDispatcher("index.command");
        verify(mockDispatcher).forward(mockRequest,mockResponse);
    }
}
