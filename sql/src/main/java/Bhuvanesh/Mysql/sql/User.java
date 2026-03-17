package Bhuvanesh.Mysql.sql;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nonnull;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;


@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;




    @Pattern(regexp = "^[0-9]{10}$")
    private String description;

    @Schema(name = "name",example = "name is from spring boot")
    @NotBlank
    @Column(nullable = false)
    private String name;
    @Email

    @Email(message = "Invalid email format")
    @NotBlank
    @Column(nullable = false, unique = true)
    private String email;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public void setDescription(String description) { this.description = description; }
}