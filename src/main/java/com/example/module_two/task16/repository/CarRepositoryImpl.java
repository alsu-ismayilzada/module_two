package com.example.module_two.task16.repository;

import com.example.module_two.task16.config.DBConfig;
import com.example.module_two.task16.model.Car;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CarRepositoryImpl implements CarRepository {


    @Override
    public Optional<Car> findById(Long id) {

        String query = "SELECT * FROM car WHERE id = ?";
        Connection connection = DBConfig.getConnection();
        Car car = null;

        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                String brand = resultSet.getString("brand");
                String model = resultSet.getString("model");
                String color = resultSet.getString("color");
                int year = resultSet.getInt("year");
                Long carId = resultSet.getLong("id");
                car = new Car(carId, brand, model, year, color);
            }


        }catch (SQLException sqlException) {
            throw new RuntimeException(sqlException);
        }
        return Optional.ofNullable(car);
    }

    @Override
    public List<Car> findAll() {
        List<Car> cars = new ArrayList<>();

        String query = "SELECT id, brand, model, year, color FROM car";
        Connection connection = DBConfig.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Car car = new Car();
                car.setId(resultSet.getLong("id"));
                car.setBrand(resultSet.getString("brand"));
                car.setModel(resultSet.getString("model"));
                car.setYear(resultSet.getInt("year"));
                car.setColor(resultSet.getString("color"));

                cars.add(car);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return cars;
    }

    @Override
    public void save(Car car) {

        String query = "INSERT INTO car(brand,model,year,color) VALUES(?,?,?,?)";
        Connection connection = DBConfig.getConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1, car.getBrand());
            preparedStatement.setString(2, car.getModel());
            preparedStatement.setInt(3, car.getYear());
            preparedStatement.setString(4, car.getColor());
            preparedStatement.execute();
        }catch (SQLException sqlException) {
            throw new RuntimeException(sqlException);
        }
    }

    @Override
    public void update(Long id, Car car) {
        String query = "UPDATE car SET brand = ?, model = ?, year = ?, color = ? WHERE id = ?";
        Connection connection = DBConfig.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, car.getBrand());
            preparedStatement.setString(2, car.getModel());
            preparedStatement.setInt(3, car.getYear());
            preparedStatement.setString(4, car.getColor());
            preparedStatement.setLong(5, id);
            preparedStatement.executeUpdate();
        } catch (SQLException sqlException) {
            throw new RuntimeException(sqlException);
        }
    }

    @Override
    public void delete(Long id) {
        String query = "DELETE car WHERE id = ?";
        Connection connection = DBConfig.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException sqlException) {
            throw new RuntimeException(sqlException);
        }

    }
}
