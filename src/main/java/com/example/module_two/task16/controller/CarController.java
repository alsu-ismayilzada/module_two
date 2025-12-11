package com.example.module_two.task16.controller;

import com.example.module_two.task16.exception.NotFoundException;
import com.example.module_two.task16.peyload.request.CarRequest;
import com.example.module_two.task16.peyload.response.CarResponse;
import com.example.module_two.task16.repository.CarRepository;
import com.example.module_two.task16.repository.CarRepositoryImpl;
import com.example.module_two.task16.service.CarService;
import com.example.module_two.task16.service.impl.CarServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;

@WebServlet(urlPatterns = "/cars/*")
public class CarController extends HttpServlet {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private CarService carService;

    @Override
    public void init() throws ServletException {
        this.carService = new CarServiceImpl(new CarRepositoryImpl());
    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        try {
            if (id != null) {
                CarResponse car = carService.findById(Long.parseLong(id));
                resp.getWriter().println(OBJECT_MAPPER.writeValueAsString(car));
            } else {
                var cars = carService.findAll();
                resp.getWriter().println(OBJECT_MAPPER.writeValueAsString(cars));
            }
            resp.setStatus(200);
            resp.setContentType("application/json");
        } catch (Exception exception) {
            if (exception instanceof NotFoundException) {
                resp.setStatus(404);
                resp.getWriter().println("Car not found");
            } else {
                resp.setStatus(500);
                resp.getWriter().println("Internal Server Error");
            }

        }
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StringBuffer sb = new StringBuffer();

        try (BufferedReader bufferedReader = req.getReader()) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
        }
        CarRequest request = OBJECT_MAPPER.readValue(sb.toString(), CarRequest.class);
        carService.addCar(request);
        resp.setStatus(201);
    }

    public void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.parseLong(req.getParameter("id"));
        StringBuffer sb = new StringBuffer();

        try (BufferedReader bufferedReader = req.getReader()) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
        }
        CarRequest request = OBJECT_MAPPER.readValue(sb.toString(), CarRequest.class);
        carService.updateCar(id, request);
        resp.setStatus(200);
    }
    public void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.parseLong(req.getParameter("id"));

        carService.delete(id);
        resp.setStatus(204);
    }
}
