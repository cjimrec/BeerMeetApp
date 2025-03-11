package app.beerMeet.BeerMeet.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellidos;  // 🔥 Nuevo campo: apellidos

    @Column(nullable = false)
    private LocalDate fechaNacimiento;  // 🔥 Nuevo campo: fecha de nacimiento

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Boolean isAdmin = false;

    public User() {}

    public User(String email, String nombre, String apellidos, LocalDate fechaNacimiento, String password, Boolean isAdmin) {
        this.email = email;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.fechaNacimiento = fechaNacimiento;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellidos() { return apellidos; }  // 🔥 Getter para apellidos
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }  // 🔥 Setter para apellidos

    public LocalDate getFechaNacimiento() { return fechaNacimiento; }  // 🔥 Getter para fechaNacimiento
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }  // 🔥 Setter para fechaNacimiento

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Boolean getIsAdmin() { return isAdmin; }
    public void setIsAdmin(Boolean isAdmin) { this.isAdmin = isAdmin; }
}