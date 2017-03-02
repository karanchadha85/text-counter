package com.kc.intelliment.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

@Component
public class RestAccessDeniedHandler implements AccessDeniedHandler {
	private final static Logger logger = Logger.getLogger(RestAccessDeniedHandler.class);
	
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
    		throws IOException, ServletException {
    	logger.debug("KC004");
    	
    	response.setContentType("application/json");
    	response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    	response.getWriter().print("{\"error\":" + HttpServletResponse.SC_FORBIDDEN + ", \"message\":\"" + accessDeniedException.getMessage() + "\"}");
    }
}
