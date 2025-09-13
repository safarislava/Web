package ru.ifmo.se.server.controller;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import ru.ifmo.se.server.dto.PointRequest;
import ru.ifmo.se.server.dto.PointResponse;
import ru.ifmo.se.server.dto.ProblemJson;

import java.util.Scanner;

import static ru.ifmo.se.server.controller.ValidationController.validate;

public class InteractionController {
    private static final Gson gson = new Gson();

    public static PointRequest getPointRequest() throws JsonSyntaxException {
        Scanner scanner = new Scanner(System.in);
        String data = scanner.next();

        PointRequest pointRequest = gson.fromJson(data, PointRequest.class);
        validate(pointRequest);

        return pointRequest;
    }

    public static void sendResponse(PointResponse pointResponse) {
        String json = gson.toJson(pointResponse);
        System.out.println(json);
    }

    public static void sendProblemJson(String message) {
        System.out.printf(gson.toJson(new ProblemJson(message)));
    }
}
