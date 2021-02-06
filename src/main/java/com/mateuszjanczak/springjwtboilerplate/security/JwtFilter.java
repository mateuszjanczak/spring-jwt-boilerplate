package com.mateuszjanczak.springjwtboilerplate.security;

import com.mateuszjanczak.springjwtboilerplate.dto.error.ErrorResponse;
import com.mateuszjanczak.springjwtboilerplate.entity.User;
import com.mateuszjanczak.springjwtboilerplate.service.UserService;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
public class JwtFilter extends GenericFilterBean {

    private final UserService userService;
    private final JwtProvider jwtProvider;

    public JwtFilter(UserService userService, JwtProvider jwtProvider) {
        this.userService = userService;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String header = request.getHeader(JwtProvider.AUTHORIZATION_HEADER);

        if(Objects.isNull(header) || !header.startsWith(JwtProvider.TOKEN_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String token = header.replace(JwtProvider.TOKEN_PREFIX, "");
            Authentication authentication = getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (JwtException ex){
            // DEV JWT DEBUG
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getOutputStream().write(new ErrorResponse(HttpStatus.UNAUTHORIZED, ex.getMessage()).toJson().getBytes());
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return;
            // DEV JWT DEBUG
        } catch (UsernameNotFoundException ex) {
            String errorMessage = "User not exist";
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getOutputStream().write(new ErrorResponse(HttpStatus.UNAUTHORIZED, errorMessage).toJson().getBytes());
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return;
        }

        filterChain.doFilter(request, response);
    }

    private Authentication getAuthentication(String token) {
        String username = jwtProvider.getUsernameFromToken(token);
        User user = userService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
    }
}
