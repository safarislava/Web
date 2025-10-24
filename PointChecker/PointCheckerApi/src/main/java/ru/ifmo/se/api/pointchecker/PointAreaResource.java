package ru.ifmo.se.api.pointchecker;

import jakarta.ejb.EJB;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import ru.ifmo.se.api.pointchecker.controller.ShotController;
import ru.ifmo.se.api.pointchecker.dto.ShotRequest;
import ru.ifmo.se.api.pointchecker.dto.ShotResponse;

import java.util.List;

@Path("point-area")
public class PointAreaResource {
    @EJB
    ShotController shotController;

    @POST
    public String authorize() {
        return "";
    }

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    public void add(ShotRequest request) {
        shotController.addShots(List.of(request));
    }
}