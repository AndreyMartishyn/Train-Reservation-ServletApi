package ua.martishyn.app.controller.commands.customer;

import ua.martishyn.app.data.entities.ComplexRoute;
import ua.martishyn.app.data.entities.PersonalRoute;
import ua.martishyn.app.data.entities.Station;
import ua.martishyn.app.data.entities.Wagon;
import ua.martishyn.app.data.entities.enums.ComfortClass;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class TrainHelper {
    private final List<PersonalRoute> suitableRoutes;
    private final List<ComplexRoute> routeList;
    private final Station fromStation;
    private final Station toStation;
    private final List<Wagon> wagons;

    public TrainHelper(List<ComplexRoute> routeList, Station fromStation,
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

        for (ComplexRoute complexRoute : routeList) {
            List<ComplexRoute.IntermediateStation> stations = complexRoute.getIntermediateStations();
            //checks if arrival and departure stations are in route
            if (stationsExistInRoute(stations)) {
                PersonalRoute personalRoute = new PersonalRoute();
                personalRoute.setRouteId(complexRoute.getId());
                personalRoute.setTrain(complexRoute.getTrain());
                StringBuilder redirectLink = new StringBuilder();
                redirectLink.append("?train=")
                        .append(personalRoute.getTrain().getId());
                int numOfStationsPassed = 0;
                int fromId = 0;
                int toId = 0;
                LocalDateTime depDate = null;
                LocalDateTime arrDate = null;

                for (ComplexRoute.IntermediateStation stationObject : stations) {
                    Station station = stationObject.getStation();
                    if (station.getId() == fromStation.getId()) {
                        depDate = stationObject.getDepartureDate();
                        fromId = numOfStationsPassed;
                        personalRoute.setDeparture(formatPattern.format(depDate));
                        personalRoute.setDepartureStation(fromStation.getName());
                        redirectLink.append("&fromStation=")
                                .append(station.getName())
                                .append("&departure=")
                                .append(formatPattern.format(depDate));
                    }
                    if (station.getId() == toStation.getId()) {
                        arrDate = stationObject.getArrivalDate();
                        toId = numOfStationsPassed;
                        personalRoute.setArrival(formatPattern.format(arrDate));
                        personalRoute.setArrivalStation(toStation.getName());
                        redirectLink.append("&toStation=")
                                .append(station.getName())
                                .append("&arrival=")
                                .append(formatPattern.format(arrDate));
                    }
                    numOfStationsPassed++;
                }
                if (fromId < toId) {
                    long duration = Duration.between(Objects.requireNonNull(depDate), arrDate).toMillis();
                    personalRoute.setRoadTime(routePattern.format(new Date(duration)));
                    List<Wagon> trainWagons = wagons.stream()
                            .filter(wagon -> wagon.getRouteId() == complexRoute.getId())
                            .collect(Collectors.toList());
                    int firstClassPlaces = getClassPlaces(trainWagons, ComfortClass.FIRST);
                    personalRoute.setFirstClassSeats(firstClassPlaces);
                    int secondClassPlaces = getClassPlaces(trainWagons, ComfortClass.SECOND);
                    personalRoute.setSecondClassSeats(secondClassPlaces);
                    personalRoute.setRedirectLink(redirectLink);
                    addRoute(personalRoute);
                }
            }
        }
    }

    private boolean stationsExistInRoute(List<ComplexRoute.IntermediateStation> stations) {
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

    public void addRoute(PersonalRoute personalRoute) {
        suitableRoutes.add(personalRoute);
    }

    public List<PersonalRoute> getSuitableRoutes() {
        return suitableRoutes;
    }
}
