package ua.martishyn.app.data.entities;

import java.io.Serializable;

public class PersonalRoute implements Serializable {
    private int routeId;
    private String trainModel;
    private String departure;
    private String departureStation;
    private String arrival;
    private String arrivalStation;
    private String roadTime;
    private int price;
    private String buyLink;


    public int getRouteId() {
        return routeId;
    }

    public String getTrainModel() {
        return trainModel;
    }

    public String getDeparture() {
        return departure;
    }

    public String getArrival() {
        return arrival;
    }

    public String getRoadTime() {
        return roadTime;
    }

    public String getBuyLink() {
        return buyLink;
    }

    public int getPrice() {
        return price;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    public void setTrainModel(String trainModel) {
        this.trainModel = trainModel;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public void setDepartureStation(String departureStation) {
        this.departureStation = departureStation;
    }

    public void setArrival(String arrival) {
        this.arrival = arrival;
    }

    public void setArrivalStation(String arrivalStation) {
        this.arrivalStation = arrivalStation;
    }

    public void setRoadTime(String roadTime) {
        this.roadTime = roadTime;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setBuyLink(String buyLink) {
        this.buyLink = buyLink;
    }
}
