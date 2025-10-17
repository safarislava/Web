package ru.ifmo.se.weblab.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import ru.ifmo.se.weblab.dto.CirclePointResponse;
import ru.ifmo.se.weblab.dto.PointResponse;
import ru.ifmo.se.weblab.dto.SquarePointResponse;
import ru.ifmo.se.weblab.dto.TrianglePointResponse;

import java.lang.reflect.Type;

public class PointResponseSerializer implements JsonSerializer<PointResponse> {

    @Override
    public JsonElement serialize(PointResponse pointResponse, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", pointResponse.getId());
        jsonObject.addProperty("status", pointResponse.getStatus());
        jsonObject.addProperty("x", pointResponse.getX());
        jsonObject.addProperty("y", pointResponse.getY());
        jsonObject.addProperty("r", pointResponse.getR());
        jsonObject.addProperty("isPointInArea", pointResponse.getIsPointInArea());
        jsonObject.addProperty("deltaTime", pointResponse.getDeltaTime());
        jsonObject.addProperty("time", pointResponse.getTime().toString());

        if (pointResponse instanceof CirclePointResponse) {
            jsonObject.addProperty("shape",  ((CirclePointResponse) pointResponse).getShape());
        }
        else if (pointResponse instanceof SquarePointResponse) {
            jsonObject.addProperty("shape",  ((SquarePointResponse) pointResponse).getShape());
        }
        else if (pointResponse instanceof TrianglePointResponse) {
            jsonObject.addProperty("shape",  ((TrianglePointResponse) pointResponse).getShape());
        }

        return jsonObject;
    }
}
