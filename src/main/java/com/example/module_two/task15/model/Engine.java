package com.example.module_two.task15.model;

public class Engine {

    private String name;
    private String power;

    public Engine(String name, String power) {
        this.name = name;
        this.power = power;
    }

    @Override
    public String toString() {
        return "Engine{" +
                "name='" + name + '\'' +
                ", power='" + power + '\'' +
                '}';
    }
}
