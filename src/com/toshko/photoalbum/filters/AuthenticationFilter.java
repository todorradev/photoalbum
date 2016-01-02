package com.toshko.photoalbum.filters;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.toshko.photoalbum.data.UserUtils;

public class AuthenticationFilter implements Filter {
	private static final String LOGIN_PAGE = "Login.jsp";
	private static final String REGISTER_PAGE = "Register.jsp";
	
	public void init(FilterConfig aFilterConfig) throws ServletException {
	}
	
	public void doFilter(ServletRequest aRequest, ServletResponse aResponse, FilterChain aFilterChain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) aRequest;
		HttpServletResponse httpResponse = (HttpServletResponse) aResponse;
		String requestedPage = getRequestedPage(httpRequest);
		
		if (requestedPage.contains("fancybox")) {
			aFilterChain.doFilter(aRequest, aResponse);
			return;
		}
		
		if (requestedPage.endsWith("css")) {
			aFilterChain.doFilter(aRequest, aResponse);
			return;
		}
		
		if (requestedPage.equals("")) {
			// Accessing root directory redirects to login form
			httpResponse.sendRedirect(LOGIN_PAGE);
			return;
		}
		
		if (LOGIN_PAGE.equals(requestedPage) ||	"login".equals(requestedPage) || 
				REGISTER_PAGE.equals(requestedPage) || "register".equals(requestedPage) || 
				"Logout".equals(requestedPage)) {
			// Accessing login/logout is always permitted
			aFilterChain.doFilter(aRequest, aResponse);
			return;
		}
		HttpSession session = httpRequest.getSession();
 		boolean authenticated =	(UserUtils.getCurrentUser(session) != null);
		if (authenticated) {
			// Authenticated user. Permit the request
			aFilterChain.doFilter(aRequest, aResponse);
		}
		else {
			// Not authenticated user. Redirect to login form
			httpResponse.sendRedirect(LOGIN_PAGE);
		}
	}
	
	private String getRequestedPage(HttpServletRequest aHttpRequest) {
		String url = aHttpRequest.getRequestURI();
		int firstSlash = url.indexOf("/",1);
		String requestedPage = null;
		if (firstSlash != -1) 
		requestedPage = url.substring(firstSlash + 1, url.length());
		return requestedPage;
	}
	
	public void destroy() {
	}
}