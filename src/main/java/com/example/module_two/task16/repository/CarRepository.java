package com.example.module_two.task16.repository;

import com.example.module_two.task16.model.Car;

import java.util.List;
import java.util.Optional;

public interface CarRepository {

    Optional<Car> findById(Long id);
    List<Car> findAll();
    void save(Car car);
    void update(Long id, Car car);
    void delete(Long id);

}
