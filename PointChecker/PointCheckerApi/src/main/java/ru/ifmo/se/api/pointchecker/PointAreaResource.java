package ru.ifmo.se.api.pointchecker;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Request;
import jakarta.ws.rs.core.Response;
import ru.ifmo.se.api.pointchecker.controller.JwtBean;
import ru.ifmo.se.api.pointchecker.controller.ShotBean;
import ru.ifmo.se.api.pointchecker.dto.ShotRequest;
import ru.ifmo.se.api.pointchecker.dto.ShotResponse;
import ru.ifmo.se.api.pointchecker.filter.Secure;

import java.util.List;

@Path("/point-area")
@Secure
public class PointAreaResource {
    @EJB
    ShotBean shotBean;
    @EJB
    JwtBean jwtBean;

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response add(@CookieParam("accessToken") String token, ShotRequest request) {
        request.username = jwtBean.getUsername(token);

        boolean success = shotBean.addShots(List.of(request));
        if (success) {
            return Response.ok().build();
        }
        else  {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @GET
    @Path("/get-all")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ShotResponse> getPoints(@CookieParam("accessToken") String token) {
        return shotBean.getShotResponses(jwtBean.getUsername(token));
    }
}