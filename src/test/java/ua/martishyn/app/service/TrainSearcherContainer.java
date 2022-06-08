package ua.martishyn.app.service;

import ua.martishyn.app.data.entities.*;
import ua.martishyn.app.data.entities.enums.ComfortClass;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TrainSearcherContainer {
    private static List<Route> routes = new ArrayList<>();
    private static List<Wagon> wagons = new ArrayList<>();
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    static {
        try {
            fillRoutes();
            fillWagons();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    static void fillWagons() {
        Wagon firstWagon = new Wagon();
        firstWagon.setId(3);
        firstWagon.setRouteId(111);
        firstWagon.setType(ComfortClass.FIRST);
        firstWagon.setNumOfSeats(10);
        firstWagon.setPrice(100);
        Wagon secondWagon = new Wagon();
        secondWagon.setId(5);
        secondWagon.setRouteId(222);
        secondWagon.setType(ComfortClass.SECOND);
        secondWagon.setNumOfSeats(10);
        secondWagon.setPrice(200);
        wagons.add(firstWagon);
        wagons.add(secondWagon);
    }

    static void fillRoutes() throws ParseException {
        Station stationFirst = Station.builder().id(1)
                .name("First")
                .code("FRST")
                .build();
        Station stationSecond = Station.builder().id(2)
                .name("Second")
                .code("SCND")
                .build();
        Station stationThird = Station.builder().id(3)
                .name("Third")
                .code("THRD")
                .build();
        DateTimeFormatter formatPattern = DateTimeFormatter.ofPattern(DATE_FORMAT);
        Route firstRoute = new Route();
        firstRoute.setId(111);
        Train train = new Train();
        TrainModel trainModel = new TrainModel();
        trainModel.setId(1);
        trainModel.setName("SKODA");
        train.setId(666);
        train.setModel(trainModel);
        firstRoute.setTrain(train);
        firstRoute.addIntermediateStation(stationFirst, LocalDateTime.parse("2022-10-24 15:30:00", formatPattern),
                LocalDateTime.parse("2022-10-24 16:30:00", formatPattern));
        firstRoute.addIntermediateStation(stationSecond, LocalDateTime.parse("2022-10-24 16:40:00", formatPattern),
                LocalDateTime.parse("2022-10-24 17:40:00", formatPattern));
        firstRoute.addIntermediateStation(stationThird, LocalDateTime.parse("2022-10-24 17:50:00", formatPattern),
                LocalDateTime.parse("2022-10-24 18:50:00", formatPattern));
        Route secondRoute = new Route();
        secondRoute.setId(222);
        Train train1 = new Train();
        TrainModel trainModel1 = new TrainModel();
        trainModel1.setId(2);
        trainModel1.setName("HYUNDAI");
        train1.setId(999);
        train1.setModel(trainModel1);
        secondRoute.setTrain(train1);
        secondRoute.addIntermediateStation(stationSecond, LocalDateTime.parse("2022-10-25 16:40:00", formatPattern),
                LocalDateTime.parse("2022-10-25 17:40:00", formatPattern));
        secondRoute.addIntermediateStation(stationThird, LocalDateTime.parse("2022-10-25 17:50:00", formatPattern),
                LocalDateTime.parse("2022-10-25 18:50:00", formatPattern));
        routes.add(firstRoute);
        routes.add(secondRoute);
    }

    public List<Wagon> getWagons() {
        return wagons;
    }

    public List<Route> getComplexRoutes() {
        return routes;
    }


}
