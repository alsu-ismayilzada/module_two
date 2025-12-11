package com.example.module_two.task16.service.impl;

import com.example.module_two.task16.exception.NotFoundException;
import com.example.module_two.task16.mapper.CarMapper;
import com.example.module_two.task16.model.Car;
import com.example.module_two.task16.peyload.request.CarRequest;
import com.example.module_two.task16.peyload.response.CarResponse;
import com.example.module_two.task16.repository.CarRepository;
import com.example.module_two.task16.service.CarService;

import java.util.List;
import java.util.stream.Collectors;

public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;

    public CarServiceImpl(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    private Car findCarById(Long id) {
        return carRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Car not found"));
    }

    @Override
    public CarResponse findById(Long id) {
        var car = findCarById(id);
        return CarMapper.toCarResponse(car);
    }

    @Override
    public List<CarResponse> findAll() {
        return carRepository.findAll()
                .stream()
                .map(CarMapper::toCarResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void addCar(CarRequest request) {
        var car = CarMapper.toCarEntity(request);
        carRepository.save(car);
    }

    @Override
    public void updateCar(Long id, CarRequest request) {
        var car = findCarById(id);
        CarMapper.updateCar(car, request);
        carRepository.update(id, car);
    }

    @Override
    public void delete(Long id) {
        carRepository.delete(id);
    }
}
