package com.example.jwtsessionwithredis.util;

import com.example.jwtsessionwithredis.service.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.session.web.http.HttpSessionIdResolver;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class CustomJWTSessionIdResolver implements HttpSessionIdResolver {

    @Override
    public List<String> resolveSessionIds(HttpServletRequest httpServletRequest) {
        String sessionId = JwtUtil.retrieveSessionId(httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION));
        return sessionId!=null ? Collections.singletonList(sessionId) : Collections.emptyList();
    }

    @Override
    public void setSessionId(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, String s) {
        String jwtToken = JwtUtil.generateJwtToken(s);
        httpServletResponse.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken);

    }

    @Override
    public void expireSession(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        httpServletResponse.setHeader(HttpHeaders.AUTHORIZATION, "");
    }
}
