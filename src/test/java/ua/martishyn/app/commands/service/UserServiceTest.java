package ua.martishyn.app.commands.service;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.martishyn.app.data.entities.User;
import ua.martishyn.app.data.service.UserService;
import ua.martishyn.app.data.utils.constants.UserServiceConstants;
import ua.martishyn.app.data.utils.constants.ViewConstants;

import javax.servlet.http.HttpServletRequest;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserServiceTest {
    private static final String HASHED_PASSWORD = "846d29aa0bb20f3665d1b5b558b8b7bdf34537b8d98694881b1c42566ee2a7fd";

    @Mock
    HttpServletRequest mockedRequest;

    @InjectMocks
    private UserService userService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldCreateAndReturnUserWhenParamsPassed() {
        when(mockedRequest.getParameter("id")).thenReturn("1");
        when(mockedRequest.getParameter("firstName")).thenReturn("testName");
        when(mockedRequest.getParameter("lastName")).thenReturn("testName");
        when(mockedRequest.getParameter("email")).thenReturn("test@gmail.com");
        when(mockedRequest.getParameter("password")).thenReturn("password1");
        when(mockedRequest.getParameter("role")).thenReturn("CUSTOMER");
        final User createdUsr = userService.getUserFromRequest(mockedRequest);

        Assert.assertEquals(1, createdUsr.getId());
        Assert.assertEquals("testName", createdUsr.getFirstName());
        Assert.assertEquals("testName", createdUsr.getLastName());
        Assert.assertEquals("test@gmail.com", createdUsr.getEmail());
        Assert.assertEquals(HASHED_PASSWORD, createdUsr.getPassword());
        Assert.assertEquals("CUSTOMER", createdUsr.getRole().name());
    }

    @Test
    public void shouldReturnTrueWithValidInputDataWhenCreateUser(){
        when(mockedRequest.getParameter("firstName")).thenReturn("Andrii");
        when(mockedRequest.getParameter("lastName")).thenReturn("Andrii");
        when(mockedRequest.getParameter("email")).thenReturn("test@gmail.com");
        when(mockedRequest.getParameter("password")).thenReturn("password1");

        userService.isUserInputIsValid(mockedRequest);
    }

    @Test
    public void shouldReturnFalseWithInvalidFirstNameInputWhenCreateUser(){
        when(mockedRequest.getParameter("firstName")).thenReturn("Andrii123");

        boolean userInputIsValid = userService.isUserInputIsValid(mockedRequest);
        verify(mockedRequest).setAttribute(ViewConstants.ERROR_VALIDATION, UserServiceConstants.FIRST_NAME_INVALID_MESS);
        Assert.assertFalse(userInputIsValid);
    }

    @Test
    public void shouldReturnFalseWithInValidLastNameInputWhenCreateUser(){
        when(mockedRequest.getParameter("firstName")).thenReturn("Validname");
        when(mockedRequest.getParameter("lastName")).thenReturn("InvalidName123");

        boolean userInputIsValid = userService.isUserInputIsValid(mockedRequest);
        verify(mockedRequest).setAttribute(ViewConstants.ERROR_VALIDATION, UserServiceConstants.LAST_NAME_INVALID_MESS);
        Assert.assertFalse(userInputIsValid);
    }

    @Test
    public void shouldReturnFalseWithInvalidEmailInputWhenCreateUser(){
        when(mockedRequest.getParameter("firstName")).thenReturn("Имя");
        when(mockedRequest.getParameter("lastName")).thenReturn("Фамилия");
        when(mockedRequest.getParameter("email")).thenReturn("invalid_+.@gmail.com");

        boolean userInputIsValid = userService.isUserInputIsValid(mockedRequest);
        verify(mockedRequest).setAttribute(ViewConstants.ERROR_VALIDATION, UserServiceConstants.EMAIL_INVALID_MESS);
        Assert.assertFalse(userInputIsValid);
    }

    @After
    public void tearDown(){
        userService = null;
    }
}
