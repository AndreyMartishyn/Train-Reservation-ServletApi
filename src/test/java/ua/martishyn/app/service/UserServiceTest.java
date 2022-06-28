package ua.martishyn.app.service;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.martishyn.app.data.dao.impl.UserDaoImpl;
import ua.martishyn.app.data.dao.interfaces.UserDao;
import ua.martishyn.app.data.entities.User;
import ua.martishyn.app.data.service.UserService;
import ua.martishyn.app.data.utils.constants.UserServiceConstants;
import ua.martishyn.app.data.utils.constants.ViewConstants;

import javax.servlet.http.HttpServletRequest;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    @Mock
    HttpServletRequest mockedRequest;


    @InjectMocks
    private UserService userService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldReturnTrueWithValidInputDataWhenCreateUser() {
        when(mockedRequest.getParameter("firstName")).thenReturn("Andrii");
        when(mockedRequest.getParameter("lastName")).thenReturn("Andrii");
        when(mockedRequest.getParameter("email")).thenReturn("test@gmail.com");
        when(mockedRequest.getParameter("password")).thenReturn("password1");

        userService.isUserInputIsValid(mockedRequest);
    }

    @Test
    public void shouldReturnFalseWithInvalidFirstNameInputWhenCreateUser() {
        when(mockedRequest.getParameter("firstName")).thenReturn("Andrii123");

        boolean userInputIsValid = userService.isUserInputIsValid(mockedRequest);
        verify(mockedRequest).setAttribute("wrongFirstName",true);
        Assert.assertFalse(userInputIsValid);
    }

    @Test
    public void shouldReturnFalseWithInValidLastNameInputWhenCreateUser() {
        when(mockedRequest.getParameter("firstName")).thenReturn("Validname");
        when(mockedRequest.getParameter("lastName")).thenReturn("InvalidName123");

        boolean userInputIsValid = userService.isUserInputIsValid(mockedRequest);
        verify(mockedRequest).setAttribute("wrongLastName",true);
        Assert.assertFalse(userInputIsValid);
    }

    @Test
    public void shouldReturnFalseWithInvalidEmailInputWhenCreateUser() {
        when(mockedRequest.getParameter("firstName")).thenReturn("Имя");
        when(mockedRequest.getParameter("lastName")).thenReturn("Фамилия");
        when(mockedRequest.getParameter("email")).thenReturn("invalid_+.@gmail.com");

        boolean userInputIsValid = userService.isUserInputIsValid(mockedRequest);
        verify(mockedRequest).setAttribute("wrongEmail",true);
        Assert.assertFalse(userInputIsValid);
    }

    @After
    public void tearDown() {
        userService = null;
    }
}
