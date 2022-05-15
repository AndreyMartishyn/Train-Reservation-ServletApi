package ua.martishyn.app.command;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.martishyn.app.controller.commands.RegisterCommand;
import ua.martishyn.app.data.dao.impl.UserDaoImpl;
import ua.martishyn.app.data.entities.User;
import ua.martishyn.app.data.utils.Constants;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

public class RegisterPostTest {
    @Mock
    HttpServletResponse mockResponse;

    @Mock
    HttpServletRequest mockRequest;

    @Mock
    RequestDispatcher mockDispatcher;


    RegisterCommand registerCommand;
    User user;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        registerCommand = new RegisterCommand();
        user = new User();
    }
    @Test
    public void shouldReturnToRegisterPageWhenInvalidInput() throws ServletException, IOException {
        when(mockRequest.getRequestDispatcher(Constants.REGISTER_PAGE)).thenReturn(mockDispatcher);
        when(mockRequest.getParameter("firstName")).thenReturn("Test");
        when(mockRequest.getParameter("lastName")).thenReturn("Test");
        when(mockRequest.getParameter("email")).thenReturn("1@gmail.com");
        when(mockRequest.getParameter("password")).thenReturn("pass");
        registerCommand.execute(mockRequest, mockResponse);

        verify(mockRequest, times(1)).getRequestDispatcher(Constants.REGISTER_PAGE);
        verify(mockDispatcher).forward(mockRequest, mockResponse);
    }



}
