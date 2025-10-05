package ru.ifmo.se.weblab;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.ifmo.se.weblab.controller.CacheController;
import ru.ifmo.se.weblab.controller.CalculationController;
import ru.ifmo.se.weblab.controller.ValidationController;
import ru.ifmo.se.weblab.dto.PointRequest;
import ru.ifmo.se.weblab.dto.PointResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "areaCheckServlet", value = "/area-check-servlet")
public class AreaCheckServlet extends HttpServlet {
    private final Gson gson = new Gson();
    private final CacheController cacheController = new CacheController();
    private final ValidationController validationController = new ValidationController();
    private final CalculationController calculationController = new CalculationController();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String xValues = request.getParameter("x");
        String yValues = request.getParameter("y");
        String rValues = request.getParameter("r");

        ArrayList<PointResponse> points = new ArrayList<>();
        try {
            for (String x : xValues.split(",")) {
                for (String y : yValues.split(",")) {
                    for (String r : rValues.split(",")) {
                        PointRequest pointRequest = new PointRequest(x, y, r);
                        validationController.validate(pointRequest);
                        points.add(process(pointRequest));
                    }
                }
            }
        } catch (Exception e) {
            response.sendRedirect(request.getContextPath() + "/error/400.jsp");
            return;
        }

        addSessionPoints(request, points);
        response.sendRedirect(request.getContextPath() + "/calculation/index.jsp");
    }

    private PointResponse process(PointRequest pointRequest) {
        long startTime = System.nanoTime();
        Boolean isPointInArea = cacheController.getCache(pointRequest);
        if (isPointInArea == null) {
            isPointInArea = calculationController.checkPointInArea(pointRequest.x, pointRequest.y, pointRequest.r);
            cacheController.setCache(pointRequest, isPointInArea);
        }
        long endTime = System.nanoTime();
        int deltaTime = (int) (endTime - startTime);
        return new PointResponse(isPointInArea, deltaTime, pointRequest);
    }

    private void addSessionPoints(HttpServletRequest request, List<PointResponse> pointsNew) {
        HttpSession session = request.getSession();
        session.setAttribute("points", gson.toJson(pointsNew));

        List<PointResponse> points = gson.fromJson((String) session.getAttribute("points-history"),
                new TypeToken<List<PointResponse>>(){}.getType());
        if (points == null) points = new ArrayList<>();
        points.addAll(pointsNew);
        session.setAttribute("points-history", gson.toJson(points));
    }
}
