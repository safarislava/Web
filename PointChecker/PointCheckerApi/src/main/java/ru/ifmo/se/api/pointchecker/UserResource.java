package ru.ifmo.se.api.pointchecker;

import jakarta.ejb.EJB;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.*;
import ru.ifmo.se.api.pointchecker.controller.JwtBean;
import ru.ifmo.se.api.pointchecker.controller.UserBean;
import ru.ifmo.se.api.pointchecker.dto.UserDto;

@Path("/users")
public class UserResource {
    @EJB
    private UserBean userBean;
    @EJB
    private JwtBean jwtBean;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response register(UserDto userDto) {
        try {
            userBean.register(userDto);

            String token = jwtBean.generate(userDto);
            NewCookie authCookie = new NewCookie.Builder("accessToken")
                    .value(token)
                    .path("/")
                    .maxAge(15 * 60)
                    .secure(false)
                    .httpOnly(true)
                    .sameSite(NewCookie.SameSite.STRICT)
                    .build();

            return Response.ok().cookie(authCookie).build();
        }
        catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
