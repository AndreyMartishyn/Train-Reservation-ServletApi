package ua.martishyn.app.data.entities;

import java.util.Objects;

public class Model {
    private int id;
    private String name;

    public static Builder builder() {
        return new Builder();
    }

    private static class Builder {
        private Model newModel;

        public Builder() {
            newModel = new Model();

        }

        public Builder id(int id) {
            newModel.id = id;
            return this;
        }

        public Builder name(String name) {
            newModel.name = name;
            return this;
        }

        public Model build() {
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
        Model model = (Model) o;
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
