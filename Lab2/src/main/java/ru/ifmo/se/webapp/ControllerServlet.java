package ru.ifmo.se.webapp;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "controllerServlet", value = "/controller-servlet")
public class ControllerServlet extends  HttpServlet{
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String verifiableToken = request.getHeader("x-csrf-token");
        String token = request.getSession().getAttribute("csrf-token").toString();

        if (verifiableToken == null || !verifiableToken.equals(token)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        String x = request.getParameter("x");
        String y = request.getParameter("y");
        String r = request.getParameter("r");

        if (x != null && y != null && r != null) {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("area-check-servlet");
            requestDispatcher.forward(request, response);
        }
    }
}

