package ru.ifmo.se.webapp;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import java.io.IOException;

@WebServlet(name = "controllerServlet", value = "/controller-servlet")
public class ControllerServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        String x = request.getParameter("x");
        String y = request.getParameter("y");
        String r = request.getParameter("r");

        if (x != null && y != null && r != null) {
            try {
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("area-check-servlet");
                requestDispatcher.forward(request, response);
            }
             catch (ServletException | IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}