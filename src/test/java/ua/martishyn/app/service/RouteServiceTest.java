package ua.martishyn.app.service;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.martishyn.app.data.entities.Route;
import ua.martishyn.app.data.entities.RoutePoint;
import ua.martishyn.app.data.service.RouteService;

import javax.servlet.http.HttpServletRequest;

import java.util.List;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RouteServiceTest extends RouteService {
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
        verify(mockedRequest).setAttribute("wrongId", true);
    }

    @Test
    public void shouldReturnFalseWithInvalidDepartureAndArrivalDatesInputWhenCreateRoute() {
        when(mockedRequest.getParameter("id")).thenReturn("146");
        when(mockedRequest.getParameter("trainId")).thenReturn("222");
        when(mockedRequest.getParameter("stationId")).thenReturn("4");
        when(mockedRequest.getParameter("departure")).thenReturn("2222-12-12T04:44");
        when(mockedRequest.getParameter("arrival")).thenReturn("2222-12-12T04:55");

        routeService.isInputForRouteValid(mockedRequest);
        verify(mockedRequest).setAttribute("wrongDates", true);
    }

    @Test
    public void shouldReturnTrueUponInputtingCorrectInfoWhenCreateRoute() {
        when(mockedRequest.getParameter("id")).thenReturn("146");
        when(mockedRequest.getParameter("trainId")).thenReturn("222");
        when(mockedRequest.getParameter("stationId")).thenReturn("4");
        when(mockedRequest.getParameter("departure")).thenReturn("2222-12-12T05:56");
        when(mockedRequest.getParameter("arrival")).thenReturn("2222-12-12T05:55");

        Assert.assertTrue(routeService.isInputForRouteValid(mockedRequest));
    }

    @Test
    public void shouldReturnNewRoutePointWhenNewRequestMade() {
        when(mockedRequest.getParameter("id")).thenReturn("146");
        when(mockedRequest.getParameter("trainId")).thenReturn("222");
        when(mockedRequest.getParameter("stationId")).thenReturn("4");
        when(mockedRequest.getParameter("departure")).thenReturn("2222-12-12T05:56");
        when(mockedRequest.getParameter("arrival")).thenReturn("2222-12-12T05:55");

        RoutePoint testRoutePoint = routeService.getRoutePointFromRequest(mockedRequest);
        Assert.assertNotNull(testRoutePoint);
        Assert.assertEquals(146, testRoutePoint.getId());
    }

    @Test
    public void shouldReturnIntermediateStationForParticularRouteWhenIdFromRequestIsPassed() {
        TrainSearcherContainer trainSearcherContainer = new TrainSearcherContainer();
        List<Route> testComplexRoutes = trainSearcherContainer.getComplexRoutes();
        when(mockedRequest.getParameter("route")).thenReturn("111");
         List<Route.IntermediateStation> stationsForTestRoute = routeService.getStationsForRoute(mockedRequest, testComplexRoutes);
        verify(mockedRequest).setAttribute("routeId", 111);
        Assert.assertNotNull(stationsForTestRoute);
        Assert.assertEquals(3, stationsForTestRoute.size());
        Assert.assertEquals(1, stationsForTestRoute.get(0).getStation().getId());
        Assert.assertEquals(2, stationsForTestRoute.get(1).getStation().getId());
        Assert.assertEquals(3, stationsForTestRoute.get(2).getStation().getId());
    }

    @After
    public void tearDown() {
        routeService = null;
    }
}
