package app.beerMeet.BeerMeet.dto;

import java.time.LocalDate;

public class RegisterRequest {
    private String nombre;
    private String apellidos;  // 🔥 Nuevo campo: apellidos
    private String email;
    private LocalDate fechaNacimiento;  // 🔥 Nuevo campo: fechaNacimiento
    private String password;

    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellidos() { return apellidos; }  // 🔥 Getter para apellidos
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }  // 🔥 Setter para apellidos

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public LocalDate getFechaNacimiento() { return fechaNacimiento; }  // 🔥 Getter para fechaNacimiento
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }  // 🔥 Setter para fechaNacimiento

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}