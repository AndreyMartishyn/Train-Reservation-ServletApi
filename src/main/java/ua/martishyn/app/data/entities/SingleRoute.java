package ua.martishyn.app.data.entities;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class SingleRoute implements Entity {
    private int id;
    private int trainId;
    private int stationId;
    private LocalDateTime arrival;
    private LocalDateTime departure;

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final SingleRoute intermediateStationRoute;

        public Builder() {
            intermediateStationRoute = new SingleRoute();
        }

        public Builder id(int id) {
            intermediateStationRoute.id = id;
            return this;
        }

        public Builder trainId(int id) {
            intermediateStationRoute.trainId = id;
            return this;
        }

        public Builder stationId(int id) {
            intermediateStationRoute.stationId = id;
            return this;
        }

        public Builder arrivalDate(LocalDateTime arrivalDate) {
            intermediateStationRoute.arrival = arrivalDate;
            return this;
        }

        public Builder departureDate(LocalDateTime departureDate) {
            intermediateStationRoute.departure = departureDate;
            return this;
        }

        public SingleRoute build() {
            return intermediateStationRoute;
        }
    }

    public int getId() {
        return id;
    }

    public int getTrainId() {
        return trainId;
    }

    public int getStationId() {
        return stationId;
    }

    public LocalDateTime getArrival() {
        return arrival;
    }

    public LocalDateTime getDeparture() {
        return departure;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SingleRoute that = (SingleRoute) o;
        return id == that.id && trainId == that.trainId && stationId == that.stationId && Objects.equals(arrival, that.arrival) && Objects.equals(departure, that.departure);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, trainId, stationId, arrival, departure);
    }

    @Override
    public String toString() {
        return "SingleRoute{" +
                "id=" + id +
                ", trainId=" + trainId +
                ", stationId=" + stationId +
                ", arrival=" + arrival +
                ", departure=" + departure +
                '}';
    }
}
