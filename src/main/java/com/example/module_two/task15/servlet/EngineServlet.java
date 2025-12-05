package com.example.module_two.task15.servlet;

import com.example.module_two.task15.model.Engine;
import jakarta.servlet.ServletContext;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.logging.Logger;

@WebServlet(urlPatterns = "/engines")
public class EngineServlet extends HttpServlet {
    Logger logger = Logger.getLogger(EngineServlet.class.getName());

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String engineName = req.getParameter("name");
        String power = req.getParameter("power");

        Engine engine = new Engine(engineName, power);

        ServletContext context = getServletContext();
        context.setAttribute("engine", engine);

        resp.getWriter().println("<h1>Yeni Mühərrik əlavə olundu: " + engineName + "</h1>");
        logger.info("New engine is created: " + engineName);
    }
}
