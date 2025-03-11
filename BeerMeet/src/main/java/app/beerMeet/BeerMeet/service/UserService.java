package app.beerMeet.BeerMeet.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import app.beerMeet.BeerMeet.dto.RegisterRequest;
import app.beerMeet.BeerMeet.entity.User;
import app.beerMeet.BeerMeet.repository.UserRepository;
import jakarta.annotation.PostConstruct;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void createDefaultAdmin() {
        if (userRepository.findByEmail("admin@beermeet.com").isEmpty()) {
            User admin = new User();
            admin.setNombre("Administrador");
            admin.setApellidos("Admin");  // 🔥 Nuevo campo: apellidos
            admin.setEmail("admin@beermeet.com");
            admin.setFechaNacimiento(LocalDate.of(1990, 1, 1));  // 🔥 Nuevo campo: fechaNacimiento
            admin.setPassword(passwordEncoder.encode("admin123")); // 🔥 Encripta la contraseña
            admin.setIsAdmin(true); // 🚀 Se asegura de que sea admin

            userRepository.save(admin);
            System.out.println("✅ Administrador creado: admin@beermeet.com / Contraseña: admin123");
        }
    }

    public User registerUser(RegisterRequest request) {
        Optional<User> existingUser = userRepository.findByEmail(request.getEmail());
        if (existingUser.isPresent()) {
            throw new RuntimeException("❌ El email ya está registrado.");
        }

        User user = new User();
        user.setNombre(request.getNombre());
        user.setApellidos(request.getApellidos());  // 🔥 Nuevo campo: apellidos
        user.setEmail(request.getEmail());
        user.setFechaNacimiento(request.getFechaNacimiento());  // 🔥 Nuevo campo: fechaNacimiento
        user.setPassword(passwordEncoder.encode(request.getPassword())); // 🔥 Encripta la contraseña
        user.setIsAdmin(false); 

        return userRepository.save(user);
    }
    
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}