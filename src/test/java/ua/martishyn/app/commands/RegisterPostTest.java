package ua.martishyn.app.commands;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.martishyn.app.controller.commands.RegisterCommand;
import ua.martishyn.app.data.entities.User;
import ua.martishyn.app.data.utils.Constants;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

public class RegisterPostTest extends RegisterCommand{
    @Mock
    HttpServletResponse mockResponse;

    @Mock
    HttpServletRequest mockRequest;

    @Mock
    RequestDispatcher mockDispatcher;

    @InjectMocks
    RegisterCommand registerCommand;
    User user;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        when(mockRequest.getRequestDispatcher(Constants.REGISTER_PAGE)).thenReturn(mockDispatcher);
        registerCommand = new RegisterCommand();
        user = new User();
    }

    @Test
    public void shouldReturnToRegisterPageWhenInvalidNameInput() throws ServletException, IOException {
        when(mockRequest.getParameter("firstName")).thenReturn("NotCorrect1  111aa");
        when(mockRequest.getParameter("lastName")).thenReturn("Test");
        when(mockRequest.getParameter("email")).thenReturn("gmail@gmail.com");
        when(mockRequest.getParameter("password")).thenReturn("password2");
        registerCommand.execute(mockRequest, mockResponse);
        verify(mockRequest).setAttribute(Constants.ERROR_VALIDATION, "Enter valid first name ");
        verify(mockDispatcher).forward(mockRequest, mockResponse);
        verify(mockRequest, times(1)).getRequestDispatcher(Constants.REGISTER_PAGE);
    }
    @Test
    public void shouldReturnToRegisterPageWhenInvalidEmailInput() throws ServletException, IOException {
        when(mockRequest.getParameter("firstName")).thenReturn("Correct");
        when(mockRequest.getParameter("lastName")).thenReturn("Correct");
        when(mockRequest.getParameter("email")).thenReturn("gmail@gmail1.com   11");
        when(mockRequest.getParameter("password")).thenReturn("password1");
        registerCommand.execute(mockRequest, mockResponse);
        verify(mockRequest).setAttribute(Constants.ERROR_VALIDATION, "Enter valid email ");
        verify(mockDispatcher).forward(mockRequest, mockResponse);
        verify(mockRequest, times(1)).getRequestDispatcher(Constants.REGISTER_PAGE);
    }
    @Test
    public void shouldReturnToRegisterPageWhenInvalidPasswordInput() throws ServletException, IOException {
        when(mockRequest.getParameter("firstName")).thenReturn("Correct");
        when(mockRequest.getParameter("lastName")).thenReturn("Correct");
        when(mockRequest.getParameter("email")).thenReturn("gmail@gmail.com");
        when(mockRequest.getParameter("password")).thenReturn("password    ");
        registerCommand.execute(mockRequest, mockResponse);
        verify(mockRequest).setAttribute(Constants.ERROR_VALIDATION, "Enter valid password ");
        verify(mockDispatcher).forward(mockRequest, mockResponse);
        verify(mockRequest, times(1)).getRequestDispatcher(Constants.REGISTER_PAGE);
    }
}

