package ua.martishyn.app.utils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ua.martishyn.app.data.entities.*;
import ua.martishyn.app.data.service.TrainHelper;

import java.util.List;

public class TrainSearcherTest {
    private TrainHelper trainSearcher;
    private List<ComplexRoute> routeList;
    private List<Wagon> wagons;


    @Before
    public void setUp() {
        TrainSearcherContainer trainSearcherContainer = new TrainSearcherContainer();
        routeList = trainSearcherContainer.getComplexRoutes();
        wagons = trainSearcherContainer.getWagons();
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
        trainSearcher = new TrainHelper(routeList, notExistingDeparture, notExistingArrival, wagons);
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
        trainSearcher = new TrainHelper(routeList, notExistingDeparture, notExistingArrival, wagons);
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
        trainSearcher = new TrainHelper(routeList, departure, arrival, wagons);
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
        trainSearcher = new TrainHelper(routeList, departure, arrival, wagons);
        int expected = 1;
        int actual = trainSearcher.getSuitableRoutes().size();
        Assert.assertEquals(expected, actual);
    }

}
