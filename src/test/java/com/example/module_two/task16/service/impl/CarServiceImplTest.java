package com.example.module_two.task16.service.impl;

import com.example.module_two.task16.exception.NotFoundException;
import com.example.module_two.task16.model.Car;
import com.example.module_two.task16.peyload.request.CarRequest;
import com.example.module_two.task16.peyload.response.CarResponse;
import com.example.module_two.task16.repository.CarRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class CarServiceImplTest {

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private CarServiceImpl carService;

    @Test
    void findById_success() {
        when(carRepository.findById(anyLong()))
                .thenReturn(Optional.of(new Car(1L, "Porsche", "911", 2022, "green")));

        CarResponse actual = carService.findById(1L);
        CarResponse expected = new CarResponse(1L, "Porsche", "911", 2022, "green");

        Assertions.assertEquals(expected.brand(), actual.brand());
        Assertions.assertEquals(expected.model(), actual.model());
        Assertions.assertEquals(expected.year(), actual.year());
        Assertions.assertEquals(expected.color(), actual.color());
    }

    @Test
    void findById_fail() {
        when(carRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class,
                () -> carService.findById(10L));
    }

    @Test
    void findAll() {
        when(carRepository.findAll())
                .thenReturn(List.of(new Car(1L, "Porsche", "911", 2022, "green")));

        List<CarResponse> actual = carService.findAll();
        List<CarResponse> expected = List.of(new CarResponse(1L, "Porsche", "911", 2022, "green"));

        Assertions.assertEquals(expected.get(0).brand(), actual.get(0).brand());
        Assertions.assertEquals(expected.get(0).model(), actual.get(0).model());
        Assertions.assertEquals(expected.get(0).year(), actual.get(0).year());
        Assertions.assertEquals(expected.get(0).color(), actual.get(0).color());
    }

    @Test
    void addCar() {
//        var car = new Car(1L, "Porsche", "911", 2022, "green");
        CarRequest request = new CarRequest("Porsche", "911", 2023, "green");
        Assertions.assertDoesNotThrow(()-> carService.addCar(request));
        verify(carRepository, times(1)).save(any(Car.class));

    }

    @Test
    void updateCar_success() {
        var car = new Car(1L, "Porsche", "911", 2022, "green");
        when(carRepository.findById(anyLong()))
                .thenReturn(Optional.of(car));
        CarRequest request = new CarRequest("Porsche", "911", 2023, "green");
        Assertions.assertDoesNotThrow(()-> carService.updateCar(1L, request));

        Assertions.assertEquals(request.brand(), car.getBrand());
        Assertions.assertEquals(request.model(), car.getModel());
        Assertions.assertEquals(request.year(), car.getYear());
        Assertions.assertEquals(request.color(), car.getColor());
        verify(carRepository, times(1)).update(anyLong(),any(Car.class));
    }

    @Test
    void updateCar_fail() {
        when(carRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class,
                () -> carService.findById(10L));

        verify(carRepository,times(0)).update(anyLong(), any());
    }

    @Test
    void delete_success(){
        var car = new Car(1L, "Porsche", "911", 2022, "green");
        when(carRepository.findById(anyLong()))
                .thenReturn(Optional.of(car));

        carService.delete(1L);
        Assertions.assertDoesNotThrow(()-> carRepository.findById(1L));
    }

    @Test
    void delete_fail(){
        when(carRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class,
                () -> carService.findById(10L));
        verify(carRepository,times(0)).delete(anyLong());
    }
}