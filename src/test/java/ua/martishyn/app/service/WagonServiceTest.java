package ua.martishyn.app.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.martishyn.app.data.entities.Wagon;
import ua.martishyn.app.data.entities.enums.ComfortClass;
import ua.martishyn.app.data.service.WagonService;

import javax.servlet.http.HttpServletRequest;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class WagonServiceTest {
    @Mock
    HttpServletRequest mockedRequest;


    @InjectMocks
    private WagonService wagonService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldReturnNewWagonFromRequestDataPassed() {
        when(mockedRequest.getParameter("routeId")).thenReturn("1");
        when(mockedRequest.getParameter("type")).thenReturn("FIRST");
        when(mockedRequest.getParameter("seats")).thenReturn("1");
        when(mockedRequest.getParameter("price")).thenReturn("1");
        final Wagon testWagon = wagonService.getWagonFromRequest(mockedRequest);
        Assert.assertNotNull(testWagon);
        Assert.assertEquals(1, testWagon.getRouteId());
        Assert.assertEquals("FIRST", testWagon.getType().name());
        Assert.assertEquals(1, testWagon.getNumOfSeats());
        Assert.assertEquals(1, testWagon.getPrice());
    }

    @Test
    public void shouldReturnTrueWhenInputIsValid() {
        when(mockedRequest.getParameter("seats")).thenReturn("1");
        when(mockedRequest.getParameter("price")).thenReturn("1");

        boolean userInputIsValid = wagonService.isWagonInputValid(mockedRequest);
        Assert.assertTrue(userInputIsValid);
    }

    @Test
    public void shouldReturnFalseWhenSeatsInputIsInValid() {
        when(mockedRequest.getParameter("seats")).thenReturn("1 ");

        boolean userInputIsValid = wagonService.isWagonInputValid(mockedRequest);
        verify(mockedRequest).setAttribute("wrongSeats", true);
        Assert.assertFalse(userInputIsValid);
    }

    @Test
    public void shouldReturnFalseWhenPriceInputIsInValid() {
        when(mockedRequest.getParameter("seats")).thenReturn("1");
        when(mockedRequest.getParameter("price")).thenReturn("bbb");

        boolean userInputIsValid = wagonService.isWagonInputValid(mockedRequest);
        verify(mockedRequest).setAttribute("wrongPrice", true);
        Assert.assertFalse(userInputIsValid);
    }
}
