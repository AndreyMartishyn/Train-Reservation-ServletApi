package ua.martishyn.app.data.utils;

import ua.martishyn.app.data.dao.impl.TrainModelDaoImpl;
import ua.martishyn.app.data.dao.interfaces.TrainAndModelDao;
import ua.martishyn.app.data.entities.ComplexRoute;
import ua.martishyn.app.data.entities.PersonalRoute;
import ua.martishyn.app.data.entities.Station;
import ua.martishyn.app.data.entities.Wagon;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class TrainSearcher {
    private final List<PersonalRoute> suitableRoutes;
    private final List<ComplexRoute> routeList;
    private final Station fromStation;
    private final Station toStation;
    private final TrainAndModelDao trainAndModelDao = new TrainModelDaoImpl();

    public TrainSearcher(List<ComplexRoute> routeList, Station fromStation,
                         Station toStation) {
        this.suitableRoutes = new ArrayList<>();
        this.routeList = routeList;
        this.fromStation = fromStation;
        this.toStation = toStation;
        findSuitableRoots();
    }

    private void findSuitableRoots() {
        DateFormat formatPattern = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        DateFormat routePattern = new SimpleDateFormat("HH:mm");

        for (ComplexRoute complexRoute : routeList) {
            List<ComplexRoute.IntermediateStation> stations = complexRoute.getIntermediateStations();
            //checks if arrival and departure station are in route
            if (stationsExistInRoute(stations)) {
                PersonalRoute personalRoute = new PersonalRoute();
                personalRoute.setRouteId(complexRoute.getId());
                personalRoute.setTrain(complexRoute.getTrain());
                StringBuilder redirectLink = new StringBuilder();
                redirectLink.append("?train=")
                        .append(complexRoute.getTrain().getId());
                int numOfStationsPassed = 0;
                int fromId = 0;
                int toId = 0;
                Date depDate = null;
                Date arrDate = null;

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
                    long routeDuration = Objects.requireNonNull(arrDate).getTime() - Objects.requireNonNull(depDate).getTime();
                    personalRoute.setRoadTime(routePattern.format(new Date(routeDuration)));
                    Optional<List<Wagon>> trainCoaches = trainAndModelDao.getWagonsForTrain(complexRoute.getId());
                    if (trainCoaches.isPresent()) {
                        int firstClassPlaces = getClassPlaces(trainCoaches.get(), "FIRST");
                        personalRoute.setFirstClassSeats(firstClassPlaces);
                        int secondClassPlaces = getClassPlaces(trainCoaches.get(), "SECOND");
                        personalRoute.setSecondClassSeats(secondClassPlaces);
                    }
                    personalRoute.setRedirectLink(redirectLink);
                    addRoute(personalRoute);

                }
            }
        }
    }

    public boolean stationsExistInRoute(List<ComplexRoute.IntermediateStation> stations) {
        return (stations.stream().anyMatch(st -> st.getStation().equals(fromStation))
                &&
                stations.stream().anyMatch(st1 -> st1.getStation().equals(toStation)));
    }

    //gets train_coaches objects and makes stream with check if class is appropriate and sums seats for same class
    private int getClassPlaces(List<Wagon> wagons, String type) {
        return wagons.stream()
                .filter(trainCoach -> trainCoach.getComfortClass().name().equals(type))
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
