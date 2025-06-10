package com.abn_amro.usermanagment.config;


import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import javax.crypto.*;
import java.util.Base64;
import java.util.List;


@Slf4j
public class GatewayTrustFilter implements Filter {

    private final String gatewaySecret;

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

    public GatewayTrustFilter(String gatewaySecret) {
        this.gatewaySecret = gatewaySecret;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        String forwardedFor = request.getHeader("X-Forwarded-For");
        String sourceIp = (forwardedFor != null) ? forwardedFor.split(",")[0].trim() : request.getRemoteAddr();
        log.info("GatewayTrustFilter sourceIp {}", sourceIp);
        String path = request.getRequestURI();
        log.info("GatewayTrustFilter path {}", path);
        // Allow public paths
        if (isPublicPath(path) || path.contains("/api/v1/user/register")
                || path.contains("/api/v1/user/login")
                || path.contains("/api/v1/user/activate")) {
            chain.doFilter(request, response);
            return;
        }
        String signature = request.getHeader("X-Gateway-Signature");
        if (!isValidSignature(request, signature)) {
            log.info("received signature ....."+signature);
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Untrusted request source");
            return;
        }
        chain.doFilter(request, response);
    }

    private boolean isValidSignature(HttpServletRequest req, String receivedSig) {
        try {
            String data = req.getRequestURI() + "|" + req.getHeader("X-User-Id");
            Mac hmac = Mac.getInstance("HmacSHA256");
            hmac.init(new SecretKeySpec(gatewaySecret.getBytes(), "HmacSHA256"));
            String expectedSig = Base64.getEncoder()
                    .encodeToString(hmac.doFinal(data.getBytes()));
            return expectedSig.equals(receivedSig);
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isPublicPath(String path) {
        return PUBLIC_PATHS.stream().anyMatch(publicPath ->
                path.startsWith(publicPath)
        );
    }
}

































































