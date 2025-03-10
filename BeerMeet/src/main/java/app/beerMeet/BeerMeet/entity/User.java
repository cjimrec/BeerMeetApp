package app.beerMeet.BeerMeet.entity;

import jakarta.persistence.*;

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
    private String password;  // 🔥 Agregado campo de contraseña

    @Column(nullable = false)
    private Boolean isAdmin = false;

    public User() {}

    public User(String email, String nombre, String password, Boolean isAdmin) {
        this.email = email;
        this.nombre = nombre;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getPassword() { return password; }  // 🔥 Getter para password
    public void setPassword(String password) { this.password = password; } // 🔥 Setter para password

    public Boolean getIsAdmin() { return isAdmin; }
    public void setIsAdmin(Boolean isAdmin) { this.isAdmin = isAdmin; }
}
