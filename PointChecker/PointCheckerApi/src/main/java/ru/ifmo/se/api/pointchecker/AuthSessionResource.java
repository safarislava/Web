package ru.ifmo.se.api.pointchecker;

import jakarta.ejb.EJB;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.NewCookie;
import jakarta.ws.rs.core.Response;
import ru.ifmo.se.api.pointchecker.controller.JwtBean;
import ru.ifmo.se.api.pointchecker.controller.UserBean;
import ru.ifmo.se.api.pointchecker.dto.UserDto;

@Path("/auth-sessions")
public class AuthSessionResource {
    @EJB
    private UserBean userBean;
    @EJB
    private JwtBean jwtBean;

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
                    .maxAge(15 * 60)
                    .secure(false)
                    .httpOnly(true)
                    .sameSite(NewCookie.SameSite.STRICT)
                    .build();

            return Response.ok().cookie(authCookie).build();
        }
        catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }


}
