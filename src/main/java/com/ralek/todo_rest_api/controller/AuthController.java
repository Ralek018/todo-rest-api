package com.ralek.todo_rest_api.controller;

import com.ralek.todo_rest_api.dto.request.LoginRequest;
import com.ralek.todo_rest_api.dto.request.RefreshRequest;
import com.ralek.todo_rest_api.dto.request.RegisterRequest;
import com.ralek.todo_rest_api.dto.response.JwtResponse;
import com.ralek.todo_rest_api.dto.response.TodoUserResponse;
import com.ralek.todo_rest_api.security.JwtService;
import com.ralek.todo_rest_api.service.TodoUserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authenticate")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final JwtDecoder jwtDecoder;
    private final TodoUserService todoUserService;

    public AuthController(AuthenticationManager authenticationManager,
                          JwtService jwtService, TodoUserService todoUserService,
                          JwtDecoder jwtDecoder) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.jwtDecoder = jwtDecoder;
        this.todoUserService = todoUserService;
    }

    @PostMapping("/login")
    public JwtResponse login(@Valid @RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password()));

        return new JwtResponse(
                jwtService.generateToken(authentication),
                jwtService.generateRefreshToken(authentication),
                "Bearer",
                request.username(),
                authentication.getAuthorities().toString());
    }

    @PostMapping("/register")
    public ResponseEntity<TodoUserResponse> register(@Valid @RequestBody RegisterRequest request) {
        var user = todoUserService.register(request.username(), request.email(), request.password());
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PostMapping("/refresh")
    public JwtResponse refresh(@Valid @RequestBody RefreshRequest request) {
        Jwt decoded;
        try {
            decoded = jwtDecoder.decode(request.refreshToken());
        } catch (JwtException e) {
            throw new BadCredentialsException("Invalid or expired refresh token");
        }

        if (!"refresh".equals(decoded.getClaimAsString("type"))) {
            throw new BadCredentialsException("Provided token is not a refresh token");
        }

        UserDetails user = todoUserService.loadUserByUsername(decoded.getSubject());

        var authentication = new UsernamePasswordAuthenticationToken(
                user.getUsername(), null, user.getAuthorities());

        return new JwtResponse(
                jwtService.generateToken(authentication),
                jwtService.generateRefreshToken(authentication),
                "Bearer",
                user.getUsername(),
                user.getAuthorities().toString());
    }
}
