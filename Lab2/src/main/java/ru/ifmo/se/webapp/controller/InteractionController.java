package ru.ifmo.se.webapp.controller;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import ru.ifmo.se.webapp.dto.PointRequest;
import ru.ifmo.se.webapp.dto.PointResponse;
import ru.ifmo.se.webapp.dto.ProblemJson;

import java.util.Scanner;

public class InteractionController {
    private final Gson gson = new Gson();
    private final ValidationController validationController = new ValidationController();

    public PointRequest getPointRequest() throws JsonSyntaxException {
        Scanner scanner = new Scanner(System.in);
        String data = scanner.next();

        PointRequest pointRequest = gson.fromJson(data, PointRequest.class);
        validationController.validate(pointRequest);

        return pointRequest;
    }

    public void sendResponse(PointResponse pointResponse) {
        String json = gson.toJson(pointResponse);
        System.out.println(json);
    }

    public void sendProblemJson(String message) {
        System.out.printf(gson.toJson(new ProblemJson(message)));
    }
}
