package com.facturas.cangrivic.web.controller;

import com.facturas.cangrivic.dto.ApiError;
import com.facturas.cangrivic.dto.auth.LoginDTO;
import com.facturas.cangrivic.exception.EmpresaNotFoundException;
import com.facturas.cangrivic.exception.UsuarioNotFoundException;
import com.facturas.cangrivic.persistence.entity.UsuarioEntity;
import com.facturas.cangrivic.service.UsuarioService;
import com.facturas.cangrivic.web.config.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.PublicKey;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;



    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;

    }
    //private final JwtUtil jwtUtil;


    @PostMapping("/login")
    public ResponseEntity<?> login12(@RequestBody LoginDTO loginDTO) {
        System.out.println("Intento de inicio de sesión para: " + loginDTO.getUsername());



        try {
        UsernamePasswordAuthenticationToken login =
                new UsernamePasswordAuthenticationToken(loginDTO.getUsername(),loginDTO.getPassword());


            Authentication authentication = this.authenticationManager.authenticate(login);

            //System.out.println(authentication.isAuthenticated());
            //System.out.println(authentication.getPrincipal());

            String jwt = this.jwtUtil.create(loginDTO.getUsername());
            //System.out.println("token: "+ jwt);
            if (authentication.isAuthenticated()) {
                return ResponseEntity.ok().
                        header(HttpHeaders.AUTHORIZATION,
                                jwt).build();
            }

            return ResponseEntity.badRequest().build();

        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiError(HttpStatus.UNAUTHORIZED, e.getMessage()));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiError(HttpStatus.UNAUTHORIZED, "Error de Credenciales"));
        } catch (LockedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ApiError(HttpStatus.FORBIDDEN, "La cuenta está bloqueada"));
        } catch (DisabledException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ApiError(HttpStatus.FORBIDDEN, "La cuenta está deshabilitada"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "Error inesperado"));
        }
    }


/*
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginDTO loginDTO) {
        System.out.println("Intento de inicio de sesión para: " + loginDTO.getUsername());

        try {
            UsernamePasswordAuthenticationToken login = new UsernamePasswordAuthenticationToken(
                    loginDTO.getUsername(), loginDTO.getPassword()
            );

            Authentication authentication = this.authenticationManager.authenticate(login);

            if (!authentication.isAuthenticated()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            String jwt = this.jwtUtil.create(loginDTO.getUsername());

            Map<String, String> response = new HashMap<>();
            response.put("token", jwt);

            return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION, jwt)
                    .body(response);

        } catch (BadCredentialsException e) {
            System.out.println("Error: Credenciales incorrectas.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Credenciales incorrectas"));
        } catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Error interno del servidor"));
        }
    }*/
}
