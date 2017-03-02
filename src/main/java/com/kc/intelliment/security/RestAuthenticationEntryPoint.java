package com.kc.intelliment.security;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {
	private final static Logger logger = Logger.getLogger(RestAuthenticationEntryPoint.class);
	
    @Override
    public void commence(HttpServletRequest request
    					, HttpServletResponse response
    					, AuthenticationException authException) throws IOException {
    	
    	logger.debug("KC003" + " - " + authException.getMessage());
    	response.setContentType("application/json");
    	response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    	response.setHeader("WWW-Authenticate", "BASIC realm=\"Employees via Http Basic\"");
    	response.getWriter().print("{\"error\":" + HttpServletResponse.SC_UNAUTHORIZED + ", \"message\":\"" + authException.getMessage() + "\"}");
    }
}
