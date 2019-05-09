// https://www.youtube.com/watch?v=x965RfL6rwc

package com.deshmukhamit.springbootmysql.interceptor;

import com.deshmukhamit.springbootmysql.exception.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

@Component
public class AuthHeaderInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
        throws Exception {

        // array to hold all public routes
        String[] publicRoutes = new String[]{"public", "login"};

        String path = request.getServletPath(); // get current route path

        // checks if the current route is in public routes array or not
        Boolean isPublicRoute = Arrays.stream(publicRoutes).anyMatch(path::contains);

        if(!isPublicRoute) {
            // do all validations necessary to secure the application
            String authToken = request.getHeader("auth-token");

            if(authToken == null || authToken.isEmpty()) {
                throw new AccessDeniedException("Auth token is required");
            }

            // here set the SessionUser which will be available in the service to determine the id of logged in user
        }

        return super.preHandle(request, response, handler);
    }
}
