package com.mateuszjanczak.springjwtboilerplate.service;

import com.mateuszjanczak.springjwtboilerplate.dto.LoginRequest;
import com.mateuszjanczak.springjwtboilerplate.dto.RegisterRequest;
import com.mateuszjanczak.springjwtboilerplate.security.JwtProvider;
import com.mateuszjanczak.springjwtboilerplate.security.JwtToken;
import com.mateuszjanczak.springjwtboilerplate.repository.RoleRepository;
import com.mateuszjanczak.springjwtboilerplate.repository.UserRepository;
import com.mateuszjanczak.springjwtboilerplate.entity.Role;
import com.mateuszjanczak.springjwtboilerplate.entity.RoleName;
import com.mateuszjanczak.springjwtboilerplate.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public AuthServiceImpl(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, JwtProvider jwtProvider, UserRepository userRepository, RoleRepository roleRepository) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public JwtToken login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        User user = (User) authentication.getPrincipal();
        String token = jwtProvider.createToken(user.getUsername());
        return new JwtToken(token);
    }

    public User register(RegisterRequest registerRequest) {
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setEmail(registerRequest.getEmail());
        Optional<Role> roleUser = roleRepository.findByName(RoleName.ROLE_USER);
        user.setRoles(Collections.singletonList(roleUser.orElseGet(() -> roleRepository.save(new Role(RoleName.ROLE_USER)))));
        return userRepository.save(user);
    }
}
