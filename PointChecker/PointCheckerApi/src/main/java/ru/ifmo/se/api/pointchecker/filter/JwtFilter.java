package ru.ifmo.se.api.pointchecker.filter;

import jakarta.ejb.EJB;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Cookie;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import ru.ifmo.se.api.pointchecker.controller.JwtController;

@Provider
@Secure
public class JwtFilter implements ContainerRequestFilter {
    @EJB
    private JwtController jwtController;

    @Override
    public void filter(ContainerRequestContext requestContext) {
        Cookie authCookie = requestContext.getCookies().get("accessToken");

        if (authCookie == null) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            return;
        }

        String token = authCookie.getValue();
        if (!jwtController.verify(token))
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
    }
}