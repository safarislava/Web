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
        FCGIInterface fcgiInterface = new FCGIInterface();
        while (fcgiInterface.FCGIaccept() >= 0) {
            String method = FCGIInterface.request.params.getProperty("REQUEST_METHOD");
            if (method == null) {
                InteractionController.sendProblemJson("Unsupported HTTP method: null");
                continue;
            }

            if (method.equals("POST")) {
                String contentType = FCGIInterface.request.params.getProperty("CONTENT_TYPE");
                if (!contentType.equals("application/json")) {
                    InteractionController.sendProblemJson("Content-Type is null");
                    continue;
                }

                try {
                    FCGIInterface.request.inStream.fill();
                } catch (IOException e) {
                    InteractionController.sendProblemJson("Error: " + e.getMessage());
                }

                try {
                    PointRequest pointRequest = InteractionController.getPointRequest();
                    PointResponse pointResponse = processRequest(pointRequest);
                    InteractionController.sendResponse(pointResponse);
                }
                catch (JsonSyntaxException e) {
                    InteractionController.sendProblemJson("Синтаксическая ошибка, попробуйте отправить заново");
                }
                catch (IllegalArgumentException e) {
                    InteractionController.sendProblemJson(e.getMessage());
                }
            }
        }
    }

    private static PointResponse processRequest(PointRequest pointRequest) {
        long startTime = System.nanoTime();
        Boolean isPointInArea = CacheController.getCache(pointRequest);
        if (isPointInArea == null) {
            isPointInArea = CalculationController.checkPointInArea(pointRequest.x, pointRequest.y, pointRequest.r);
            CacheController.setCache(pointRequest, isPointInArea);
        }
        long endTime = System.nanoTime();
        int deltaTime = (int) (endTime - startTime);
        return new PointResponse(isPointInArea, deltaTime, pointRequest);
    }
}