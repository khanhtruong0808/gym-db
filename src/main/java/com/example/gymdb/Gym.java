package com.example.gymdb;

public class Gym {
    private final int id;
    private final String gym_name;
    private final String location;

    public Gym(int id, String gym_name, String location) {
        this.id = id;
        this.gym_name = gym_name;
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public String getGymName() {
        return gym_name;
    }

    public String getLocation() {
        return location;
    }
}
