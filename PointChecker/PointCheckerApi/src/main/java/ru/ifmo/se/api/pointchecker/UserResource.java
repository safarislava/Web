package ru.ifmo.se.api.pointchecker;

import jakarta.ejb.EJB;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.*;
import ru.ifmo.se.api.pointchecker.controller.JwtBean;
import ru.ifmo.se.api.pointchecker.controller.UserBean;
import ru.ifmo.se.api.pointchecker.dto.UserDto;

@Path("/user")
public class UserResource {
    @EJB
    private UserBean userBean;
    @EJB
    private JwtBean jwtBean;

    @GET
    public Response get() {
        return Response.ok().build();
    }

    @Path("/login")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(UserDto userDto) {
        try {
            boolean correct = userBean.login(userDto);
            if (!correct) return Response.status(Response.Status.UNAUTHORIZED).build();

            String token = jwtBean.generate(userDto);
            NewCookie authCookie = new NewCookie.Builder("accessToken")
                    .value(token)
                    .path("/")
                    .domain("185.239.141.48")
                    .maxAge(15 * 60)
                    .secure(false)
                    .httpOnly(false)
                    //.sameSite(NewCookie.SameSite.NONE)
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
            userBean.register(userDto);

            String token = jwtBean.generate(userDto);
            NewCookie authCookie = new NewCookie.Builder("accessToken")
                    .value(token)
                    .path("/")
                    .domain("185.239.141.48")
                    .maxAge(15 * 60)
                    .secure(false)
                    .httpOnly(false)
                    //.sameSite(NewCookie.SameSite.NONE)
                    .build();

            return Response.ok().cookie(authCookie).build();
        }
        catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
