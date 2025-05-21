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
    
    /**
     * @para filterConfig FilterConfig object contains configuration info for this filter
     * @throws ServletException If an error occurs during filter initialization
     * @return void
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Default init implementation, currently no initialization logic
        Filter.super.init(filterConfig);
    }

    /**
     * Core method that intercepts incoming requests and enforces authentication rules.
     * 
     * @para request ServletRequest object representing the client request
     * @para response ServletResponse object representing the response to the client
     * @para chain FilterChain to pass request/response to the next filter or resource
     * @throws IOException If an input or output error occurs while processing the request
     * @throws ServletException If the processing fails for other reasons
     * @return void
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        // Cast the request and response to HttpServletRequest and HttpServletResponse
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        // Get the requested URI
        String uri = req.getRequestURI();

        // Allow static resources and publicly accessible URLs without authentication
        if (uri.endsWith(".css") || uri.contains("/resources/") || ALLOWED_URLS.stream().anyMatch(uri::endsWith)) {
            chain.doFilter(request, response);
            return;
        }

        // Check if the user is logged in by checking the "username" session attribute
        boolean isLoggedIn = SessionUtil.getAttribute(req, "username") != null;

        if (!isLoggedIn) {
            // If user not logged in, allow access only to CSS, login and signup pages
            if (uri.endsWith(".css") || uri.endsWith(LOGIN) || uri.endsWith(REGISTER)) {
                chain.doFilter(request, response);
            } else {
                // Redirect unauthenticated users to login page
                res.sendRedirect(req.getContextPath() + LOGIN);
            }
        } else {
            // If user is logged in, redirect away from login/signup pages to home page
            if  (uri.endsWith(".css") || uri.endsWith(LOGIN) || uri.endsWith(REGISTER)) {
                res.sendRedirect(req.getContextPath() + HOME);
            } else {
                // Allow logged in users to proceed to requested resource
                chain.doFilter(request, response);
            }
        }
    }

    /**
     * Called when the filter is taken out of service.
     * 
     * @return void
     */
    @Override
    public void destroy() {
        // No cleanup necessary currently
    }
}
