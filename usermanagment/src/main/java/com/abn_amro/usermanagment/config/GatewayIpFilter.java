package com.abn_amro.usermanagment.config;


import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;

@Slf4j
public class GatewayIpFilter extends OncePerRequestFilter {

    private static final List<String> PUBLIC_PATHS = List.of(
            "/swagger-ui.html",
            "/swagger-ui/index.html",
            "/v3/api-docs",
            "/swagger-resources",
            "/webjars",
            "/actuator/health",
            "/api/v1/user/testserver",
            "/swagger-resources/configuration/ui",
            "/swagger-resources/configuration/security",
            "/configuration/ui",
            "/configuration/security",
            "/favicon.ico",
            "/swagger-ui",
            "/v2/api-docs"
    );

    private final String gatewayIp;

    public GatewayIpFilter(String gatewayIp) {
        this.gatewayIp = gatewayIp;
    }

    private static final Map<Pattern, List<String>> PATH_ROLES = Map.of(
            Pattern.compile("^/api/users/.*$"), List.of("ADMIN", "USER"),
            Pattern.compile("^/api/recipe/.*$"), List.of("ADMIN", "USER")
    );

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String forwardedFor = request.getHeader("X-Forwarded-For");
        String sourceIp = (forwardedFor != null) ? forwardedFor.split(",")[0].trim() : request.getRemoteAddr();
        String path = request.getRequestURI();

        log.info("sourceIp {}", sourceIp);
        log.info("request.getRemoteAddr() {}", request.getRemoteAddr());
        log.info("request properties {} {} {}", request.getRemoteHost(), request.getRemotePort(), request.getProtocol());
        log.info("checking path {} {}", isPublicPath(path), path);

        // Allow public paths
        if (isPublicPath(path) || path.contains("/api/v1/user/register")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Uncomment and adjust gateway
        // if (!gatewayIp.equals(sourceIp)) {
        //    response.setStatus(HttpStatus.FORBIDDEN.value());
        //    response.getWriter().write("Access denied: Go through the API Gateway");
        //    return;
        // }

        // Check role authorization
        if (!validateUserRoles(request, path)) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.getWriter().write("Forbidden: You do not have permission to access this resource.");
            return;
        }
        // Set CORS headers (adjust as needed)
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Credentials", "true");

        filterChain.doFilter(request, response);
    }

    private boolean validateUserRoles(HttpServletRequest request, String path) {
        List<String> userRoles = getRolesFromHeader(request);
        Optional<Map.Entry<Pattern, List<String>>> matchedRule = PATH_ROLES.entrySet()
                .stream()
                .filter(entry -> entry.getKey().matcher(path).matches())
                .findFirst();
        // Deny if path is protected and user lacks required roles
        if (matchedRule.isPresent() &&
                Collections.disjoint(userRoles, matchedRule.get().getValue())) {
            return false;
        }
        return true;
    }

    private List<String> getRolesFromHeader(HttpServletRequest req) {
        String rolesHeader = req.getHeader("X-User-Roles");
        return rolesHeader != null ?
                Arrays.asList(rolesHeader.split(",")) :
                Collections.emptyList();
    }

    private boolean isPublicPath(String path) {
        return PUBLIC_PATHS.stream().anyMatch(publicPath ->
                path.startsWith(publicPath)
        );
    }
}


























