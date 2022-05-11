package ua.martishyn.app.data.entities;

import java.io.Serializable;
import java.util.Objects;

public class TrainModel implements Serializable {
    private int id;
    private String name;

    public static Builder builder() {
        return new Builder();
    }

    private static class Builder {
        private TrainModel newModel;

        public Builder() {
            newModel = new TrainModel();

        }

        public Builder id(int id) {
            newModel.id = id;
            return this;
        }

        public Builder name(String name) {
            newModel.name = name;
            return this;
        }

        public TrainModel build() {
            return newModel;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrainModel model = (TrainModel) o;
        return id == model.id && Objects.equals(name, model.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Model{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
