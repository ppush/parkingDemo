package com.example.demo.dao.builder;

import java.util.Objects;

import com.example.demo.dao.Users;

public final class UsersBuilder {
    private String username;
    private String firstName;
    private String lastName;

    private UsersBuilder() {
    }

    public static UsersBuilder builder() {
        return new UsersBuilder();
    }

    public UsersBuilder withUsername(String username) {
        this.username = username;
        return this;
    }

    public UsersBuilder withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public UsersBuilder withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public Users build() {
        Users users = new Users();
        users.setUsername(Objects.requireNonNullElseGet(this.username,
                                                        () -> "%s.%s".formatted(this.firstName, this.lastName)
                                                            .toLowerCase()));

        users.setFirstName(firstName);
        users.setLastName(lastName);
        return users;
    }
}
