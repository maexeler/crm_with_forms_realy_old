package ch.zli.m223.CRM.security.configuration;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {

	@Override
	public void handle(
			HttpServletRequest httpServletRequest, 
			HttpServletResponse httpServletResponse, 
			AccessDeniedException accessDeniedException) throws IOException, ServletException 
	{
		httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/403");	
	}

}
