package com.project.pickItUp.security;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

@Component
public class JWTFilter extends GenericFilterBean {

    @Autowired
    private JWTUtility jwtUtility;

    private static final String HEADER = "Authorization";

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse)res;
        String authorization = request.getHeader(HEADER);
        String token;
        if(authorization != null && authorization.startsWith("Bearer ")) {
            token = authorization.substring(7);
            SecurityContextHolder.getContext().setAuthentication(jwtUtility.getAuthentication(token));
        }
        chain.doFilter(request, response);
    }

}
