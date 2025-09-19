package ru.ifmo.se.server;

import com.fastcgi.*;
import com.google.gson.JsonSyntaxException;
import ru.ifmo.se.server.controller.CacheController;
import ru.ifmo.se.server.controller.CalculationController;
import ru.ifmo.se.server.controller.InteractionController;
import ru.ifmo.se.server.dto.PointRequest;
import ru.ifmo.se.server.dto.PointResponse;

import java.io.IOException;

public class App {
    public static void main(String[] args) {
        while (true) {
            try {
                runServer();
            }
            catch (Exception ignored) {}
        }
    }

    private static void runServer() {
        InteractionController interactionController = new InteractionController();

        FCGIInterface fcgiInterface = new FCGIInterface();
        while (fcgiInterface.FCGIaccept() >= 0) {
            String method = FCGIInterface.request.params.getProperty("REQUEST_METHOD");

            switch (method) {
                case "POST" -> {
                    String contentType = FCGIInterface.request.params.getProperty("CONTENT_TYPE");
                    if (!contentType.equals("application/json")) {
                        interactionController.sendProblemJson("Content-Type is null");
                        continue;
                    }

                    try {
                        FCGIInterface.request.inStream.fill();
                    } catch (IOException e) {
                        interactionController.sendProblemJson("Error: " + e.getMessage());
                    }

                    try {
                        PointRequest pointRequest = interactionController.getPointRequest();
                        PointResponse pointResponse = processRequest(pointRequest);
                        interactionController.sendResponse(pointResponse);
                    } catch (JsonSyntaxException e) {
                        interactionController.sendProblemJson("Синтаксическая ошибка, попробуйте отправить заново");
                    } catch (IllegalArgumentException e) {
                        interactionController.sendProblemJson(e.getMessage());
                    }
                }
                default -> interactionController.sendProblemJson(String.format("Unsupported HTTP method: %s", method));
            }
        }
    }

    private static PointResponse processRequest(PointRequest pointRequest) {
        CalculationController calculationController = new CalculationController();
        CacheController cacheController = new CacheController();

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
}