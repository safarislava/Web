package ru.ifmo.se.api.pointchecker;

import jakarta.ejb.EJB;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.*;
import ru.ifmo.se.api.pointchecker.controller.JwtController;
import ru.ifmo.se.api.pointchecker.controller.UserController;
import ru.ifmo.se.api.pointchecker.dto.UserDto;

@Path("/user")
public class UserResource {
    @EJB
    private UserController userController;
    @EJB
    private JwtController jwtController;

    @GET
    public Response get() {
        return Response.ok().build();
    }

    @Path("/login")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(UserDto userDto) {
        try {
            boolean correct = userController.login(userDto);
            if (!correct) return Response.status(Response.Status.UNAUTHORIZED).build();

            String token = jwtController.generate(userDto);
            NewCookie authCookie = new NewCookie.Builder("accessToken")
                    .value(token)
                    .path("/")
                    .maxAge(15 * 60)
                    .secure(false)
                    .httpOnly(false)
                    .sameSite(NewCookie.SameSite.NONE)
                    .build();

            return Response.ok().cookie(authCookie).build();
        }
        catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @Path("/register")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response register(UserDto userDto) {
        try {
            userController.register(userDto);

            String token = jwtController.generate(userDto);
            NewCookie authCookie = new NewCookie.Builder("accessToken")
                    .value(token)
                    .path("/")
                    .maxAge(15 * 60)
                    .secure(true)
                    .httpOnly(true)
                    .sameSite(NewCookie.SameSite.NONE)
                    .build();

            return Response.ok().cookie(authCookie).build();
        }
        catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
