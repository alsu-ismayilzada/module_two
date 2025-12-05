package com.example.module_two.exersize.model;

public class Car {

    String brand;
    String model;
    Engine engine;
    String manufacturer;

    public Car(String brand, String model, Engine engine, String manufacturer) {
        this.brand = brand;
        this.model = model;
        this.engine = engine;
        this.manufacturer = manufacturer;
    }

    @Override
    public String toString() {
        return "Car{" +
                "brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", engine=" + engine +
                ", manufacturer='" + manufacturer + '\'' +
                '}';
    }
}
