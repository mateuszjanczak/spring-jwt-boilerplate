package com.mateuszjanczak.springjwtboilerplate.security;

import com.mateuszjanczak.springjwtboilerplate.exception.ErrorMessage;
import com.mateuszjanczak.springjwtboilerplate.web.ErrorController;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
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

    private final UserDetailsService userDetailsService;
    private final JwtProvider jwtProvider;
    private final ErrorController errorController;

    @Autowired
    public JwtFilter(UserDetailsService userDetailsService, JwtProvider jwtProvider, ErrorController errorController) {
        this.userDetailsService = userDetailsService;
        this.jwtProvider = jwtProvider;
        this.errorController = errorController;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        if(checkHeader(header)){
           try {
                String username = jwtProvider.parseHeader(header);
                UserDetails user = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                response.addHeader(JwtProvider.AUTHORIZATION_HEADER, jwtProvider.createToken(user.getUsername()));
            } catch (SignatureException | MalformedJwtException | ExpiredJwtException | UnsupportedJwtException ex){
                ErrorMessage errorMessage = errorController.handleJwtException(ex);
                response.setStatus(errorMessage.getErrorCode());
                response.setContentType("application/json");
                response.getOutputStream().write(errorMessage.toJson().getBytes());
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    private boolean checkHeader(String header) {
        return !Objects.isNull(header) && header.startsWith(JwtProvider.TOKEN_PREFIX);
    }

}
