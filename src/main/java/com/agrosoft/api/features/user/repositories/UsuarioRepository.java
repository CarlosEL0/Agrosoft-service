package com.agrosoft.api.features.user.repositories;

import com.agrosoft.api.features.user.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {

    // Método para buscar al usuario por su correo electrónico
    Optional<Usuario> findByCorreoElectronico(String correoElectronico);

    // Método para validar si un correo ya está registrado en la base de datos
    boolean existsByCorreoElectronico(String correoElectronico);
}