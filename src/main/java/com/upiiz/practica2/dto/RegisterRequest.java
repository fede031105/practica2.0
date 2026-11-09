package com.upiiz.practica2.dto;

import com.upiiz.practica2.models.Rol;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RegisterRequest {
    @NotBlank(message = "El nombre de usuario es obligatorio")
    private String username;

    @NotBlank(message = "La contraseña es obligatoria")
    private String password;

    @NotNull(message = "El rol es obligatorio (ADMIN o CONSULTA)")
    private Rol rol;
}