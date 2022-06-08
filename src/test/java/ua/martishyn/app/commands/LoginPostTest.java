package ua.martishyn.app.commands;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.martishyn.app.controller.commands.LoginCommand;
import ua.martishyn.app.data.dao.impl.UserDaoImpl;
import ua.martishyn.app.data.utils.constants.UserServiceConstants;
import ua.martishyn.app.data.utils.constants.ViewConstants;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.mockito.Mockito.*;

public class LoginPostTest {
    @Mock
    HttpServletRequest mockRequest;
    @Mock
    HttpServletResponse mockResponse;
    @Mock
    RequestDispatcher mockDispatcher;
    @Mock
    HttpSession mockSession;
    @Mock
    UserDaoImpl mockDao;
    @InjectMocks
    LoginCommand loginCommand;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(mockRequest.getRequestDispatcher(ViewConstants.LOGIN_PAGE)).thenReturn(mockDispatcher);
        loginCommand = new LoginCommand();
    }

    @Test
    public void shouldRedirectToLoginWhenNotValidInput() throws ServletException, IOException {
        when(mockRequest.getParameter("email")).thenReturn("1@gmail.com");
        when(mockRequest.getParameter("password")).thenReturn("pass");
        loginCommand.execute(mockRequest, mockResponse);
        verify(mockRequest).setAttribute(ViewConstants.ERROR_VALIDATION, UserServiceConstants.INVALID_INPUT);
        verify(mockRequest, times(1)).getRequestDispatcher(ViewConstants.LOGIN_PAGE);
        verify(mockDispatcher).forward(mockRequest, mockResponse);
    }


//    @Test
//    public void shouldRedirectToLoginWhenUserNotFound() throws Exception {
//        User user = new User();
//        user.setEmail("contact@gmail.com");
//        whenNew(UserDaoImpl.class).withNoArguments().thenReturn(mockDao);
//        whenNew(UserService.class).withArguments(mockDao).thenReturn(mockService);
//        when(mockRequest.getParameter("email")).thenReturn("contact1@gmail.com");
//        when(mockRequest.getParameter("password")).thenReturn("password1");
//
//        when(mockService.authenticate(user.getEmail())).thenReturn(null);
//        loginCommand.execute(mockRequest, mockResponse);
//
//        verify(mockRequest).setAttribute("noSuchUser", "No user found");
//        verify(mockRequest, times(1)).getRequestDispatcher(Constants.LOGIN_PAGE);
//        verify(mockDispatcher).forward(mockRequest, mockResponse);
//    }

//    @Test
//    public void shouldRedirectToMainWhenValidUserCredentials() throws Exception {
//        String passEncoded = PasswordEncodingService.makeHash("password1");
//        user.setPassword(passEncoded);
//        when(mockRequest.getParameter("email")).thenReturn("contact@gmail.com");
//        when(mockRequest.getParameter("password")).thenReturn("password1");
//        when(mockRequest.getSession()).thenReturn(mockSession);
//        when(mockDao.getByEmail(anyString())).thenReturn(java.util.Optional.of(user));
//
//        loginCommand.execute(mockRequest, mockResponse);
//
//        verify(mockRequest.getSession()).setAttribute("user", user);
//        verify(mockRequest.getSession()).setAttribute("role", user.getRole());
//        verify(mockResponse).sendRedirect("index.command");
//    }

//    @Test
//    public void shouldRedirectTLoginWhenWrongPassCredentials() throws Exception {
//        String passEncoded = PasswordEncodingService.makeHash("password1");
//        user.setPassword(passEncoded);
//        when(mockRequest.getParameter("email")).thenReturn("contact@gmail.com");
//        when(mockRequest.getParameter("password")).thenReturn("password2");
//
//        loginCommand.execute(mockRequest, mockResponse);
//
//        verify(mockRequest).setAttribute("notCorrectPass", "Enter correct password");
//        verify(mockRequest, times(1)).getRequestDispatcher(Constants.LOGIN_PAGE);
//        verify(mockDispatcher).forward(mockRequest, mockResponse);
//    }

    @After
    public void tearDown() {
        mockRequest = null;
        mockResponse = null;
        mockDispatcher = null;
        mockDao = null;
        mockSession = null;
    }
}
