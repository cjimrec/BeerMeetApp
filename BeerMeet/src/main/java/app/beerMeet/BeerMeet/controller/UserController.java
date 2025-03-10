package app.beerMeet.BeerMeet.controller;

import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.beerMeet.BeerMeet.entity.User;
import app.beerMeet.BeerMeet.service.UserService;
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<Object> getAuthenticatedUser(Authentication authentication) {
        System.out.println("🔍 Authentication: " + authentication);

        if (authentication == null || !authentication.isAuthenticated()) {
            System.out.println("❌ Usuario no autenticado");
            return ResponseEntity.status(401).body(Map.of("error", "Usuario no autenticado"));
        }

        String email = authentication.getName();
        Optional<User> userOpt = userService.findByEmail(email);

        if (userOpt.isEmpty()) {
            System.out.println("❌ Usuario no encontrado en BD: " + email);
            return ResponseEntity.status(404).body(Map.of("error", "Usuario no encontrado"));
        }

        User user = userOpt.get();
        System.out.println("✅ Usuario encontrado: " + user.getEmail());

        return ResponseEntity.ok(Map.of(
            "id", user.getId(),
            "email", user.getEmail(),
            "nombre", user.getNombre(),
            "isAdmin", user.getIsAdmin()
        ));
    }

}
