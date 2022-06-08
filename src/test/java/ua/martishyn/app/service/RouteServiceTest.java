package ua.martishyn.app.service;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.martishyn.app.data.service.RouteService;
import ua.martishyn.app.data.utils.constants.RouteServiceConstants;
import ua.martishyn.app.data.utils.constants.ViewConstants;

import javax.servlet.http.HttpServletRequest;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RouteServiceTest {
    @Mock
    HttpServletRequest mockedRequest;

    @InjectMocks
    RouteService routeService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldReturnFalseWithInvalidRouteIdInputWhenCreateRoute() {
        when(mockedRequest.getParameter("id")).thenReturn("XXX");

        routeService.isInputForRouteValid(mockedRequest);
        verify(mockedRequest).setAttribute(ViewConstants.ERROR_VALIDATION, RouteServiceConstants.ROUTE_ID_INVALID_MESS);
    }

    @Test
    public void shouldReturnFalseWithInvalidTrainIdInputWhenCreateRoute() {
        when(mockedRequest.getParameter("id")).thenReturn("1");
        when(mockedRequest.getParameter("trainId")).thenReturn("XXX");

        routeService.isInputForRouteValid(mockedRequest);
        verify(mockedRequest).setAttribute(ViewConstants.ERROR_VALIDATION, RouteServiceConstants.ROUTE_TRAIN_ID_INVALID_MESS);
    }

    @Test
    public void shouldReturnFalseWithInvalidStationIdInputWhenCreateRoute() {
        when(mockedRequest.getParameter("id")).thenReturn("146");
        when(mockedRequest.getParameter("trainId")).thenReturn("222");
        when(mockedRequest.getParameter("stationId")).thenReturn("XXX");

        routeService.isInputForRouteValid(mockedRequest);
        verify(mockedRequest).setAttribute(ViewConstants.ERROR_VALIDATION, RouteServiceConstants.ROUTE_STATION_INVALID_MESS);
    }

    @Test
    public void shouldReturnFalseWithInvalidDepartureAndArrivalDatesInputWhenCreateRoute() {
        when(mockedRequest.getParameter("id")).thenReturn("146");
        when(mockedRequest.getParameter("trainId")).thenReturn("222");
        when(mockedRequest.getParameter("stationId")).thenReturn("4");
        when(mockedRequest.getParameter("departure")).thenReturn("2222-12-12T04:44");
        when(mockedRequest.getParameter("arrival")).thenReturn("2222-12-12T04:55");

        routeService.isInputForRouteValid(mockedRequest);
        verify(mockedRequest).setAttribute(ViewConstants.ERROR_VALIDATION, RouteServiceConstants.ROUTE_DATES_INVALID_MESS);
    }

    @Test
    public void shouldReturnTrueUponInputtingCorrectInfoWhenCreateRoute() {
        when(mockedRequest.getParameter("id")).thenReturn("146");
        when(mockedRequest.getParameter("trainId")).thenReturn("222");
        when(mockedRequest.getParameter("stationId")).thenReturn("4");
        when(mockedRequest.getParameter("departure")).thenReturn("2222-12-12T05:55");
        when(mockedRequest.getParameter("arrival")).thenReturn("2222-12-12T05:55");

        Assert.assertTrue(routeService.isInputForRouteValid(mockedRequest));
    }

    @After
    public void tearDown(){
        routeService = null;
    }
}
