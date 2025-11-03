package ru.ifmo.se.api.pointchecker.filter;

import jakarta.ejb.EJB;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.container.PreMatching;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;
import java.util.Set;
import java.util.StringJoiner;

@Provider
@PreMatching
public class CorsFilter implements ContainerRequestFilter, ContainerResponseFilter {
    @EJB
    private CorsConfig corsConfig;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        if (isPreflightRequest(requestContext)) {
            String origin = requestContext.getHeaderString("Origin");

            if (isOriginAllowed(origin)) {
                Response.ResponseBuilder builder = Response.ok();
                addCorsHeaders(builder, origin);
                requestContext.abortWith(builder.build());
            }
            else {
                requestContext.abortWith(Response.status(Response.Status.FORBIDDEN).build());
            }
        }
    }

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        String origin = requestContext.getHeaderString("Origin");

        if (origin != null && isOriginAllowed(origin)) {
            addCorsHeaders(responseContext, origin);
        }
    }

    private boolean isPreflightRequest(ContainerRequestContext requestContext) {
        return requestContext.getMethod().equalsIgnoreCase("OPTIONS") &&
                requestContext.getHeaderString("Origin") != null &&
                requestContext.getHeaderString("Access-Control-Request-Method") != null &&
                requestContext.getHeaderString("Access-Control-Request-Headers") != null;
    }

    private boolean isOriginAllowed(String origin) {
        return corsConfig.getAllowedOrigins().contains(origin) ||
                corsConfig.getAllowedOrigins().contains("*");
    }

    private void addCorsHeaders(ContainerResponseContext responseContext, String origin) {
        responseContext.getHeaders().putSingle("Access-Control-Allow-Origin",
                corsConfig.isAllowCredentials() ? origin : "*");

        if (corsConfig.isAllowCredentials()) {
            responseContext.getHeaders().putSingle("Access-Control-Allow-Credentials", "true");
        }

        if (!corsConfig.getAllowedMethods().isEmpty()) {
            responseContext.getHeaders().putSingle("Access-Control-Allow-Methods",
                    joinSet(corsConfig.getAllowedMethods()));
        }

        if (!corsConfig.getAllowedHeaders().isEmpty()) {
            responseContext.getHeaders().putSingle("Access-Control-Allow-Headers",
                    joinSet(corsConfig.getAllowedHeaders()));
        }
        if (!corsConfig.getExposedHeaders().isEmpty()) {
            responseContext.getHeaders().putSingle("Access-Control-Expose-Headers",
                    joinSet(corsConfig.getExposedHeaders()));
        }

        responseContext.getHeaders().putSingle("Access-Control-Max-Age",
                String.valueOf(corsConfig.getMaxAge()));
    }

    private void addCorsHeaders(Response.ResponseBuilder builder, String origin) {
        builder.header("Access-Control-Allow-Origin",
                corsConfig.isAllowCredentials() ? origin : "*");

        if (corsConfig.isAllowCredentials()) {
            builder.header("Access-Control-Allow-Credentials", "true");
        }

        if (!corsConfig.getAllowedMethods().isEmpty()) {
            builder.header("Access-Control-Allow-Methods", joinSet(corsConfig.getAllowedMethods()));
        }

        if (!corsConfig.getAllowedHeaders().isEmpty()) {
            builder.header("Access-Control-Allow-Headers", joinSet(corsConfig.getAllowedHeaders()));
        }

        if (!corsConfig.getExposedHeaders().isEmpty()) {
            builder.header("Access-Control-Expose-Headers", joinSet(corsConfig.getExposedHeaders()));
        }

        builder.header("Access-Control-Max-Age", String.valueOf(corsConfig.getMaxAge()));
    }

    private String joinSet(Set<String> set) {
        StringJoiner joiner = new StringJoiner(", ");
        set.forEach(joiner::add);
        return joiner.toString();
    }
}