package ua.martishyn.app.commands.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.martishyn.app.controller.commands.common.SearchTicketsCommand;
import ua.martishyn.app.data.entities.*;
import ua.martishyn.app.data.service.BookingSearcher;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

public class TrainSearcherTest {
    private BookingSearcher trainSearcher;
    private List<Route> routeList;
    private List<Wagon> wagons;
    private SearchTicketsCommand customerSearchTicketsCommand;

    @Mock
    HttpServletRequest mockRequest;
    @Mock
    HttpServletResponse mockResponse;
    @Mock
    RequestDispatcher mockDispatcher;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        TrainSearcherContainer trainSearcherContainer = new TrainSearcherContainer();
        routeList = trainSearcherContainer.getComplexRoutes();
        wagons = trainSearcherContainer.getWagons();
        customerSearchTicketsCommand = new SearchTicketsCommand();
    }

    @Test
    public void shouldRedirectToMainPageIfSameStations() throws ServletException, IOException {
        when(mockRequest.getRequestDispatcher("index.command")).thenReturn(mockDispatcher);
        when(mockRequest.getParameter("stationFrom")).thenReturn(String.valueOf(1));
        when(mockRequest.getParameter("stationTo")).thenReturn(String.valueOf(1));
        customerSearchTicketsCommand.execute(mockRequest, mockResponse);
        verify(mockRequest).setAttribute("sameStations", "Departure and arrival stations are same");
        verify(mockRequest, times(1)).getRequestDispatcher("index.command");
        verify(mockDispatcher).forward(mockRequest, mockResponse);
    }

    @Test
    public void shouldNotPerformSearchWhenNotExistingStations() {
        Station notExistingDeparture = Station.builder().id(888)
                .name("DEP")
                .code("DEP")
                .build();
        Station notExistingArrival = Station.builder().id(777)
                .name("ARR")
                .code("ARR")
                .build();
        trainSearcher = new BookingSearcher(routeList, notExistingDeparture, notExistingArrival, wagons);
        int expected = 0;
        int actual = trainSearcher.getSuitableRoutes().size();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void shouldNotAddRouteIfOppositeDirectionsAndStationsExistInRoutes() {
        Station notExistingDeparture = Station.builder().id(3)
                .name("THIRD")
                .code("THRD")
                .build();
        Station notExistingArrival = Station.builder().id(1)
                .name("FIRST")
                .code("FRST")
                .build();
        trainSearcher = new BookingSearcher(routeList, notExistingDeparture, notExistingArrival, wagons);
        int expected = 0;
        int actual = trainSearcher.getSuitableRoutes().size();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void shouldReturnIntermediateStationsRoutesWhenNormalSearch() {
        Station departure = Station.builder().id(2)
                .name("Second")
                .code("SCND")
                .build();
        Station arrival = Station.builder().id(3)
                .name("Third")
                .code("THRD")
                .build();
        trainSearcher = new BookingSearcher(routeList, departure, arrival, wagons);
        int expected = 2;
        int actual = trainSearcher.getSuitableRoutes().size();
        Assert.assertEquals(expected, actual);
    }


    @Test
    public void shouldReturnRoutesThatContainDepartureAndArrivalStationsWhenNormalSearch() {
        Station departure = Station.builder().id(1)
                .name("First")
                .code("FRST")
                .build();
        Station arrival = Station.builder().id(3)
                .name("Third")
                .code("THRD")
                .build();
        trainSearcher = new BookingSearcher(routeList, departure, arrival, wagons);
        int expected = 1;
        int actual = trainSearcher.getSuitableRoutes().size();
        Assert.assertEquals(expected, actual);
    }

}
