package ru.ifmo.se.api.pointchecker;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ru.ifmo.se.api.pointchecker.controller.JwtBean;
import ru.ifmo.se.api.pointchecker.controller.ShotBean;
import ru.ifmo.se.api.pointchecker.dto.ShotRequest;
import ru.ifmo.se.api.pointchecker.dto.ShotResponse;
import ru.ifmo.se.api.pointchecker.filter.Secure;

import java.util.List;

@Path("/shots")
@Secure
public class ShotsResource {
    @EJB
    ShotBean shotBean;
    @EJB
    JwtBean jwtBean;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response add(@CookieParam("accessToken") String token, ShotRequest request) {
        request.username = jwtBean.getUsername(token);

        boolean success = shotBean.addShots(List.of(request));
        if (success) {
            return Response.status(Response.Status.CREATED).build();
        }
        else  {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<ShotResponse> getPoints(@CookieParam("accessToken") String token) {
        return shotBean.getShotResponses(jwtBean.getUsername(token));
    }

    @DELETE
    public Response clear(@CookieParam("accessToken") String token) {
        boolean success = shotBean.clearShots(jwtBean.getUsername(token));
        if (success) {
            return Response.ok().build();
        }
        else  {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}