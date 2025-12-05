package com.example.module_two.exersize.servlet;

import com.example.module_two.exersize.model.Car;
import com.example.module_two.exersize.model.Engine;
import jakarta.servlet.ServletContext;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.logging.Logger;

@WebServlet(urlPatterns = "/cars/*",
        initParams = {
                @WebInitParam(name = "manufacturer", value = "Toyota")
        })
public class CarServlet extends HttpServlet {

    Logger logger = Logger.getLogger(CarServlet.class.getName());

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String brand = req.getParameter("brand");
        String model = req.getParameter("model");
        String manufacturer = getInitParameter("manufacturer");

        ServletContext context = getServletContext();
        Engine engine =(Engine) context.getAttribute("engine");
        context.removeAttribute("engine");
        Car car = new Car(brand, model, engine, manufacturer);
        resp.getWriter().print(car);
        logger.info("New car is created: " + car);
    }
}