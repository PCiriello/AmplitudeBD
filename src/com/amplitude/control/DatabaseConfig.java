package com.amplitude.control;

public class DatabaseConfig {
    private String username;
    private String password;

    public DatabaseConfig(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
