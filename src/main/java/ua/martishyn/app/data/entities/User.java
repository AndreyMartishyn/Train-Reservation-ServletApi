package ua.martishyn.app.data.entities;

import ua.martishyn.app.data.entities.enums.Role;

import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private Role role;

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private User newUser;

        public Builder() {
            newUser = new User();
        }
        public Builder id(int id){
            newUser.id = id;
            return this;
        }

        public Builder firstName(String firstName) {
            newUser.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName) {
            newUser.lastName = lastName;
            return this;
        }

        public Builder password(String password) {
            newUser.password = password;
            return this;
        }

        public Builder email(String email) {
            newUser.email = email;
            return this;
        }

        public Builder role(Role role) {
            newUser.role = role;
            return this;
        }

        public User build() {
            return newUser;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(password, user.password) && Objects.equals(email, user.email) && role == user.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, password, email, role);
    }

    @Override
    public String toString() {
        return "UserDaoImpl{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                '}';
    }
}
