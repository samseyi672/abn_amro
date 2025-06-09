package com.abn_amro.recipemanagement.config;


import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.util.Base64;


@Slf4j
public class GatewayTrustFilter implements Filter {

    private final String gatewaySecret;

    public GatewayTrustFilter(String gatewaySecret) {
        this.gatewaySecret = gatewaySecret;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpReq = (HttpServletRequest) req;
        String signature = httpReq.getHeader("X-Gateway-Signature");
        if (!isValidSignature(httpReq, signature)) {
            ((HttpServletResponse) res).sendError(401, "Untrusted request source");
            return;
        }
        chain.doFilter(req, res);
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

}



































































