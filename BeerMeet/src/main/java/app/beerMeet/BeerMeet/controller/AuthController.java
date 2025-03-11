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

@CrossOrigin(origins = "http://localhost:3001") // Habilitar CORS para el frontend
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtUtil jwtUtil;
    private final UserService userService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager,
                          CustomUserDetailsService customUserDetailsService, JwtUtil jwtUtil, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.customUserDetailsService = customUserDetailsService;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            // Autenticar al usuario con las credenciales proporcionadas
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

            // Cargar detalles del usuario
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(loginRequest.getEmail());

            // Generar token JWT
            String token = jwtUtil.generateToken(userDetails);

            // Retornar el token y el email del usuario
            return ResponseEntity.ok(Map.of("token", token, "email", userDetails.getUsername()));

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Credenciales incorrectas"));
        } catch (Exception e) {
            // Manejo general de excepciones
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Ocurrió un error en el proceso de autenticación"));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader(value = "Authorization", required = false) String token) {
        if (token == null || !token.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Token inválido"));
        }

        // Invalidar el token (esto es un ejemplo, en producción deberías usar una lista negra de tokens)
        String jwtToken = token.substring(7);
        System.out.println("🔴 Logout exitoso, token eliminado: " + jwtToken);

        return ResponseEntity.ok(Map.of("message", "Logout exitoso"));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        try {
            // Registrar al usuario
            User user = userService.registerUser(registerRequest);

            // Retornar mensaje de éxito
            return ResponseEntity.ok(Map.of("message", "Usuario registrado correctamente", "email", user.getEmail()));
        } catch (IllegalArgumentException e) {
            // Manejo de error si el usuario ya existe o los datos son inválidos
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            // Manejo de error general
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "No se pudo registrar el usuario"));
        }
    }
}