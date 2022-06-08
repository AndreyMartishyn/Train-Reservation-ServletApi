package ua.martishyn.app.data.service;

import ua.martishyn.app.data.entities.Route;
import ua.martishyn.app.data.entities.PersonalRoute;
import ua.martishyn.app.data.entities.Station;
import ua.martishyn.app.data.entities.Wagon;
import ua.martishyn.app.data.entities.enums.ComfortClass;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class BookingSearcher {
    private final List<PersonalRoute> suitableRoutes;
    private final List<Route> routeList;
    private final Station fromStation;
    private final Station toStation;
    private final List<Wagon> wagons;

    public BookingSearcher(List<Route> routeList, Station fromStation,
                           Station toStation, List<Wagon> wagons) {
        this.suitableRoutes = new ArrayList<>();
        this.routeList = routeList;
        this.fromStation = fromStation;
        this.toStation = toStation;
        this.wagons = wagons;
        findSuitableRoots();
    }

    private void findSuitableRoots() {
        DateTimeFormatter formatPattern = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        DateFormat routePattern = new SimpleDateFormat("HH:mm");

        for (Route route : routeList) {
            List<Route.IntermediateStation> stations = route.getIntermediateStations();
            //checks if arrival and departure stations are in route
            if (stationsExistInRoute(stations)) {
                PersonalRoute personalRoute = new PersonalRoute();
                personalRoute.setRouteId(route.getId());
                personalRoute.setTrain(route.getTrain());
                StringBuilder redirectLink = new StringBuilder();
                redirectLink.append("?train=")
                        .append(personalRoute.getTrain().getId());
                int overallStationCounter = 0;
                int fromId = 0;
                int toId = 0;
                LocalDateTime depDate = null;
                LocalDateTime arrDate = null;

                for (Route.IntermediateStation stationObject : stations) {
                    Station station = stationObject.getStation();
                    if (station.getId() == fromStation.getId()) {
                        depDate = stationObject.getDepartureDate();
                        fromId = overallStationCounter;
                        personalRoute.setDeparture(formatPattern.format(depDate));
                        personalRoute.setDepartureStation(fromStation.getName());
                        redirectLink.append("&fromStation=")
                                .append(station.getName())
                                .append("&departure=")
                                .append(formatPattern.format(depDate));
                    }
                    overallStationCounter++;
                    if (station.getId() == toStation.getId()) {
                        arrDate = stationObject.getArrivalDate();
                        toId = overallStationCounter;
                        personalRoute.setArrival(formatPattern.format(arrDate));
                        personalRoute.setArrivalStation(toStation.getName());
                        redirectLink.append("&toStation=")
                                .append(station.getName())
                                .append("&arrival=")
                                .append(formatPattern.format(arrDate));
                    }
                }
                if (fromId < toId) {
                    int totalStationInRoute = toId - fromId;
                    long duration = Duration.between((depDate), arrDate).toMillis();
                    String durationFormatted = routePattern.format(new Date(duration));
                    redirectLink.append("&duration=")
                                .append(durationFormatted);
                    personalRoute.setRoadTime(durationFormatted);
                    List<Wagon> trainWagons = wagons.stream()
                            .filter(wagon -> wagon.getRouteId() == route.getId())
                            .collect(Collectors.toList());

                    int firstClassPlaces = getClassPlaces(trainWagons, ComfortClass.FIRST);
                    int firstClassPrice = getPriceForClassSeat(trainWagons, ComfortClass.FIRST, totalStationInRoute);
                    personalRoute.setFirstClassSeats(firstClassPlaces);
                    personalRoute.setFirstClassTotalPrice(firstClassPrice);

                    int secondClassPlaces = getClassPlaces(trainWagons, ComfortClass.SECOND);
                    int secondClassPrice = getPriceForClassSeat(trainWagons, ComfortClass.SECOND, totalStationInRoute);
                    personalRoute.setSecondClassSeats(secondClassPlaces);
                    personalRoute.setSecondClassTotalPrice(secondClassPrice);

                    personalRoute.setRedirectLink(redirectLink);
                    suitableRoutes.add(personalRoute);
                }
            }
        }
    }

    //find any wagon with requested class to get appropriate price and calculates acc to number of stations
    private int getPriceForClassSeat(List<Wagon> trainWagons, ComfortClass type, int numOfStations) {
        Optional<Wagon> desirableClassWagon = trainWagons.stream()
                .filter(wagon -> wagon.getType().equals(type))
                .findFirst();
        return desirableClassWagon.map(wagon -> wagon.getPrice() * numOfStations).orElse(0);
    }

    private boolean stationsExistInRoute(List<Route.IntermediateStation> stations) {
        return (stations.stream().anyMatch(st -> st.getStation().equals(fromStation))
                &&
                stations.stream().anyMatch(st1 -> st1.getStation().equals(toStation)));
    }

    //gets train_coaches objects and makes stream with check if class is appropriate and sums seats for same class
    private int getClassPlaces(List<Wagon> wagons, ComfortClass type) {
        return wagons.stream()
                .filter(wagon -> wagon.getType().name().equals(type.name()))
                .mapToInt(Wagon::getNumOfSeats)
                .reduce(0, Integer::sum);
    }

    public List<PersonalRoute> getSuitableRoutes() {
        return suitableRoutes;
    }
}
