package ru.ifmo.se.webapp;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import ru.ifmo.se.webapp.controller.ValidationController;
import ru.ifmo.se.webapp.dto.PointRequest;

@WebServlet(name = "controllerServlet", value = "/controller-servlet")
public class ControllerServlet extends HttpServlet {
    private final ValidationController validationController = new ValidationController();

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) {
        String x = req.getParameter("x");
        String y = req.getParameter("y");
        String r = req.getParameter("r");
        PointRequest pointRequest = new PointRequest(x, y, r);
        validationController.validate(pointRequest);
        System.out.println(pointRequest);
    }
}