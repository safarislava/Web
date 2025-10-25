package ru.ifmo.se.api.pointchecker;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ru.ifmo.se.api.pointchecker.controller.ShotBean;
import ru.ifmo.se.api.pointchecker.dto.ShotRequest;
import ru.ifmo.se.api.pointchecker.dto.ShotResponse;
import ru.ifmo.se.api.pointchecker.filter.Secure;

import java.util.List;

@Path("point-area")
@Secure
public class PointAreaResource {
    @EJB
    ShotBean shotBean;

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response add(List<ShotRequest> request) {
        shotBean.addShots(request);
        return Response.ok().build();
    }

    @GET
    @Path("/get-points")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ShotResponse> getPoints() {
        return shotBean.getShotResponses();
    }
}