package com.mateuszjanczak.springjwtboilerplate.configuration;

import com.mateuszjanczak.springjwtboilerplate.dto.error.ErrorResponse;
import com.mateuszjanczak.springjwtboilerplate.security.JwtFilter;
import com.mateuszjanczak.springjwtboilerplate.web.rest.AuthController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtFilter jwtFilter;

    public WebSecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().disable();
        http.csrf().disable(); // DEV
        http.headers().frameOptions().disable(); // DEV
        http.addFilterAfter(jwtFilter, BasicAuthenticationFilter.class);
        http.authorizeRequests()
                .antMatchers(AuthController.PATH_POST_SIGN_UP).permitAll()
                .antMatchers(AuthController.PATH_POST_REFRESH_TOKEN).permitAll()
                .antMatchers(AuthController.PATH_POST_LOGIN).permitAll()
                .antMatchers("/h2-console/**").permitAll() // DEV
                .anyRequest().authenticated();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.exceptionHandling().authenticationEntryPoint((request, response, authException) -> {
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setHeader("WWW-Authenticate", "Bearer");
            response.getOutputStream().write(new ErrorResponse(HttpStatus.UNAUTHORIZED, authException.getMessage()).toJson().getBytes());
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
        });
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
