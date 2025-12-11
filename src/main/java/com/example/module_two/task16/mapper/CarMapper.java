package com.example.module_two.task16.mapper;

import com.example.module_two.task16.model.Car;
import com.example.module_two.task16.peyload.request.CarRequest;
import com.example.module_two.task16.peyload.response.CarResponse;

public interface CarMapper {

    static Car toCarEntity(CarRequest carRequest) {
        return new Car(carRequest.brand(), carRequest.model(), carRequest.year(), carRequest.color());
    }

    static CarResponse toCarResponse(Car car) {
        return new CarResponse(car.getId(), car.getBrand(), car.getModel(), car.getYear(), car.getColor());
    }

    static void updateCar(Car car, CarRequest request) {
        car.setBrand(request.brand());
        car.setModel(request.model());
        car.setYear(request.year());
        car.setColor(request.color());
    }
}
