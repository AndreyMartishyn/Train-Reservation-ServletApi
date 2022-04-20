package ua.martishyn.app.data.entities;

import java.io.Serializable;
import java.util.Objects;

public class Station implements Serializable {
    private int id;
    private String name;
    private String code;

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Station newStation;

        public Builder() {
            newStation = new Station();
        }

        public Builder id(int id) {
            newStation.id = id;
            return this;
        }

        public Builder name(String name) {
            newStation.name = name;
            return this;
        }

        public Builder code(String code) {
            newStation.code = code;
            return this;
        }

        public Station build() {
            return newStation;
        }

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Station station = (Station) o;
        return id == station.id && Objects.equals(name, station.name) && Objects.equals(code, station.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, code);
    }

    @Override
    public String toString() {
        return "Station{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
