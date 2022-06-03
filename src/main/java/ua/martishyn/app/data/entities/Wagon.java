package ua.martishyn.app.data.entities;

import ua.martishyn.app.data.entities.enums.ComfortClass;

public class Wagon implements Entity {
    private int id;
    private int routeId;
    private ComfortClass type;
    private int numOfSeats;
    private int price;

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final Wagon newWagon;

        public Builder() {
            newWagon = new Wagon();
        }

        public Builder id(int id) {
            newWagon.id = id;
            return this;
        }

        public Builder routeId(int id) {
            newWagon.routeId = id;
            return this;
        }

        public Builder type(ComfortClass type) {
            newWagon.type = type;
            return this;
        }

        public Builder numOfSeats(int seats) {
            newWagon.numOfSeats = seats;
            return this;
        }

        public Builder price(int price) {
            newWagon.price = price;
            return this;
        }

        public Wagon build() {
            return newWagon;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    public ComfortClass getType() {
        return type;
    }

    public void setType(ComfortClass type) {
        this.type = type;
    }

    public int getNumOfSeats() {
        return numOfSeats;
    }

    public void setNumOfSeats(int numOfSeats) {
        this.numOfSeats = numOfSeats;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
