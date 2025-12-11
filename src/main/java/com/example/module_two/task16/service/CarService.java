package com.example.module_two.task16.service;

import com.example.module_two.task16.model.Car;
import com.example.module_two.task16.peyload.request.CarRequest;
import com.example.module_two.task16.peyload.response.CarResponse;

import java.util.List;

public interface CarService {

    CarResponse findById(Long id);
    List<CarResponse> findAll();
    void addCar(CarRequest request);
    void updateCar(Long id, CarRequest request);
    void delete(Long id);
}
