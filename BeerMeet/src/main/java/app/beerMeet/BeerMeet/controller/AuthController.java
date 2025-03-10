package app.beerMeet.BeerMeet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import app.beerMeet.BeerMeet.dto.LoginRequest;
import app.beerMeet.BeerMeet.dto.RegisterRequest;
import app.beerMeet.BeerMeet.entity.User;
import app.beerMeet.BeerMeet.security.CustomUserDetailsService;
import app.beerMeet.BeerMeet.security.JwtUtil;
import app.beerMeet.BeerMeet.service.UserService;

@CrossOrigin(origins = "http://localhost:3001") // Habilitar CORS si es necesario
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtUtil jwtUtil;
    private final UserService userService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, CustomUserDetailsService customUserDetailsService, JwtUtil jwtUtil, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.customUserDetailsService = customUserDetailsService;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            // Intentar autenticar con las credenciales proporcionadas
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );

            // Cargar detalles del usuario
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(loginRequest.getEmail());

            // Generar token JWT
            String token = jwtUtil.generateToken(userDetails.getUsername());

            // Retornar el token y el email del usuario
            return ResponseEntity.ok(Map.of("token", token, "email", userDetails.getUsername()));

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("error", "Credenciales incorrectas"));
        } catch (Exception e) {
            // Manejo general de excepciones
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Ocurrió un error en el proceso de autenticación"));
        }
    }
@PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        try {
            // Intentar registrar al usuario
            User user = userService.registerUser(registerRequest);
            return ResponseEntity.ok(Map.of("message", "Usuario registrado correctamente", "email", user.getEmail()));
        } catch (Exception e) {
            // Manejo de error si no se puede registrar al usuario
            return ResponseEntity.status(400).body(Map.of("error", "No se pudo registrar el usuario"));
        }
    }
}