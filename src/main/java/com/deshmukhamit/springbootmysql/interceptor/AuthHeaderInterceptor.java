// https://www.youtube.com/watch?v=x965RfL6rwc

package com.deshmukhamit.springbootmysql.interceptor;

import com.deshmukhamit.springbootmysql.exception.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthHeaderInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
        throws Exception {

        String authToken = request.getHeader("auth-token");

        if(authToken == null || authToken.isEmpty()) {
            throw new AccessDeniedException("Auth token is required");
        }

        // here set the SessionUser which will be available in the service to determine the id of logged in user

        return super.preHandle(request, response, handler);
    }
}
