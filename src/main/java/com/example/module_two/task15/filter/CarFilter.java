package com.example.module_two.task15.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;

import java.io.IOException;
import java.util.logging.Logger;

@WebFilter(urlPatterns = "/cars/*")
public class CarFilter implements Filter {

    Logger logger = Logger.getLogger(EngineFilter.class.getName());

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.info("CarFilter is called");
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
