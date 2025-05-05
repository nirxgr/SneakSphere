package com.sneaksphere.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.sneaksphere.util.SessionUtil;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebFilter(asyncSupported = true, urlPatterns = "/*")
public class AuthenticationFilter implements Filter {

	private static final String LOGIN = "/login";
	private static final String REGISTER = "/signup";
	private static final String HOME = "/home";
	private static final String ROOT = "/";
	private static final String ABOUTUS = "/aboutus";
	private static final String CONTACTUS = "/contactUs";
	private static final String MENS = "/product";
	private static final String INDPRO1 = "/individualProductPage";
	private static final String WOMENS = "/womensProduct";
	private static final String NEW = "/newProduct";
	private static final String FUTURE = "/futureWorks";
	private static final String SEARCH = "/searchProduct";

	
	
	
	
	// List of URLs that should not require authentication
    private static final List<String> ALLOWED_URLS = Arrays.asList(LOGIN, REGISTER, ROOT, HOME, ABOUTUS, 
    		CONTACTUS, MENS,INDPRO1,WOMENS,NEW,FUTURE,SEARCH
    );
    
    
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		Filter.super.init(filterConfig);
	}
	
	

	
	@Override
	//core method
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		// Cast the request and response to HttpServletRequest and HttpServletResponse
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		// Get the requested URI
		String uri = req.getRequestURI();

		if (uri.endsWith(".css") || uri.contains("/resources/") || ALLOWED_URLS.stream().anyMatch(uri::endsWith)) {
			chain.doFilter(request, response);
			return;
		}

		// Get the session and check if user is logged in
		boolean isLoggedIn = SessionUtil.getAttribute(req, "username") != null;

		if (!isLoggedIn) {
			if (uri.endsWith(".css") || uri.endsWith(LOGIN) || uri.endsWith(REGISTER)) {
				chain.doFilter(request, response);
			} else {
				res.sendRedirect(req.getContextPath() + LOGIN);
			}
		} else {
			if  (uri.endsWith(".css") || uri.endsWith(LOGIN) || uri.endsWith(REGISTER)) {
				res.sendRedirect(req.getContextPath() + HOME);
			} else {
				chain.doFilter(request, response);
			}
		}
	}

	@Override
	public void destroy() {
		
	}
}
