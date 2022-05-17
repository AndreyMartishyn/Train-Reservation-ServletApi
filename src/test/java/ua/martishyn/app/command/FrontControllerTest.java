package ua.martishyn.app.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.martishyn.app.controller.FrontController;
import ua.martishyn.app.controller.commands.CommandContainer;
import ua.martishyn.app.data.utils.Constants;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.mockito.Mockito.*;

public class FrontControllerTest extends FrontController {
    @Mock
    RequestDispatcher mockedDispatcher;

    @Mock
    HttpServletResponse mockedResponse;

    @Mock
    HttpServletRequest mockedRequest;

    @InjectMocks
    FrontController frontController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        frontController = new FrontController();

    }

    @Test
    public void shouldRedirectToMainIfEmptyOrSlashCommand() throws IOException, ServletException {
        when(mockedRequest.getRequestDispatcher(Constants.HOME_PAGE)).thenReturn(mockedDispatcher);
        when(mockedRequest.getRequestURI()).thenReturn("/train-reservation/");
        when(mockedRequest.getContextPath()).thenReturn("/train-reservation");

        frontController.doGet(mockedRequest, mockedResponse);

        verify(mockedRequest, times(1)).getRequestDispatcher(Constants.HOME_PAGE);
        verify(mockedDispatcher).forward(mockedRequest, mockedResponse);
    }

    @Test
    public void shouldNotExecuteCommandWhenCommandNotExist() throws IOException {
        when(mockedRequest.getRequestURI()).thenReturn("/train-reservation/hello.command");
        when(mockedRequest.getContextPath()).thenReturn("/train-reservation");

        frontController.doGet(mockedRequest, mockedResponse);
        verify(mockedRequest, times(0)).getRequestDispatcher(Constants.HOME_PAGE);
    }

    @Test
    public void shouldExecuteCommandWhenCommandExist() throws IOException, ServletException {
        when(mockedRequest.getRequestDispatcher(Constants.LOGIN_PAGE)).thenReturn(mockedDispatcher);
        when(mockedRequest.getRequestURI()).thenReturn("/train-reservation/login-page.command");
        when(mockedRequest.getContextPath()).thenReturn("/train-reservation");

        frontController.doGet(mockedRequest, mockedResponse);
        verify(mockedRequest, times(1)).getRequestDispatcher(Constants.LOGIN_PAGE);
        verify(mockedDispatcher).forward(mockedRequest, mockedResponse);
    }
}
