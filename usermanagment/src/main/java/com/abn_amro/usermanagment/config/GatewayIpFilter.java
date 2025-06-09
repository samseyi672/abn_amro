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
            "/v3/api-docs",
            "/swagger-resources/**",
            "/webjars/**",
            "/actuator/health"
    );
    private final String gatewayIp;

    public GatewayIpFilter(String gatewayIp) {
        this.gatewayIp = gatewayIp;
    }

    private static final Map<Pattern, List<String>> PATH_ROLES = Map.of(
            Pattern.compile("^/api/users/.*$"), List.of("ADMIN", "USER"),
            Pattern.compile("^/api/recipe/.*$"), List.of("ADMIN","USER")
    );
          // Pattern.compile("^/api/admin/.*$"), List.of("ADMIN"),
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String forwardedFor = request.getHeader("X-Forwarded-For");
        String sourceIp = (forwardedFor != null) ? forwardedFor.split(",")[0].trim() : request.getRemoteAddr();
        log.info("sourceIp "+sourceIp);
        if (!gatewayIp.equals(sourceIp)) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.getWriter().write("Access denied: Go through the API Gateway");
            return;
        }
        String path = request.getRequestURI();
        if (isPublicPath(path)) {
            filterChain.doFilter(request,response);
            return;
        }
        List<String> userRoles = getRolesFromHeader(request);
        // Find first matching path rule
        Optional<Map.Entry<Pattern, List<String>>> matchedRule = PATH_ROLES.entrySet()
                .stream()
                .filter(entry -> entry.getKey().matcher(path).matches())
                .findFirst();
        // Check roles if path is protected
        if (matchedRule.isPresent() &&
                Collections.disjoint(userRoles, matchedRule.get().getValue())) {
            ((HttpServletResponse) request).sendError(403, "FORBIDDEN");
            return;
        }
       response.setHeader("Access-Control-Allow-Origin", sourceIp);
        // You can specify specific origins instead of "*"
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
       // response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization, Kycheader");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        filterChain.doFilter(request, response);
    }

    private List<String> getRolesFromHeader(HttpServletRequest req) {
        String rolesHeader = req.getHeader("X-User-Roles");
        return rolesHeader != null ?
                Arrays.asList(rolesHeader.split(",")) :
                Collections.emptyList();
    }

    private boolean isPublicPath(String path) {
        return PUBLIC_PATHS.stream().anyMatch(publicPath ->
                path.startsWith(publicPath.replace("/**", "")) ||
                        path.matches(publicPath.replace("**", ".*"))
        );
    }
}




























