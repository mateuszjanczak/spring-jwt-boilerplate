package com.mateuszjanczak.springjwtboilerplate.security;

import com.mateuszjanczak.springjwtboilerplate.entity.User;
import com.mateuszjanczak.springjwtboilerplate.exception.Error;
import com.mateuszjanczak.springjwtboilerplate.service.UserService;
import com.mateuszjanczak.springjwtboilerplate.web.ErrorController;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final UserService userService;
    private final JwtProvider jwtProvider;
    private final ErrorController errorController;

    @Autowired
    public JwtFilter(UserService userService, JwtProvider jwtProvider, ErrorController errorController) {
        this.userService = userService;
        this.jwtProvider = jwtProvider;
        this.errorController = errorController;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        if(checkHeader(header)){
           try {
                String username = jwtProvider.parseHeader(header);
                User user = userService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                response.addHeader(JwtProvider.AUTHORIZATION_HEADER, jwtProvider.createToken(user.getUsername()));
            } catch (SignatureException | MalformedJwtException | ExpiredJwtException | UnsupportedJwtException ex){
                Error error = errorController.handleJwtException(ex);
                response.setStatus(error.getErrorCode());
                response.setContentType("application/json");
                response.getOutputStream().write(error.toJson().getBytes());
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    private boolean checkHeader(String header) {
        return !Objects.isNull(header) && header.startsWith(JwtProvider.TOKEN_PREFIX);
    }

}
