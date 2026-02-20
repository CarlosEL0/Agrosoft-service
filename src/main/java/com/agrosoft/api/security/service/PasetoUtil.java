package com.agrosoft.api.security.service;

import dev.paseto.jpaseto.Paseto;
import dev.paseto.jpaseto.PasetoParser;
import dev.paseto.jpaseto.Pasetos;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component
public class PasetoUtil {

    private final SecretKey secretKey;

    public PasetoUtil(@Value("${paseto.secret-key:12345678901234567890123456789012}") String secret) {
        this.secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "AES");
    }

    // Método para generar el Token cuando el usuario hace Login exitoso
    public String generateToken(String email) {
        Instant now = Instant.now();

        return Pasetos.V2.LOCAL.builder()
                .setSharedSecret(this.secretKey)
                .setSubject(email)
                .setExpiration(now.plus(24, ChronoUnit.HOURS))
                .compact();
    }

    // Método para extraer el email del Token en cada petición
    public String extractEmail(String token) {
        Paseto parsedToken = getParser().parse(token);
        return parsedToken.getClaims().getSubject();
    }

    // Verifica que el token fue firmado por nosotros y no ha expirado
    public boolean isTokenValid(String token) {
        try {
            getParser().parse(token);
            return true; // Si no lanza excepción, el token es válido
        } catch (Exception e) {
            return false;
        }
    }

    private PasetoParser getParser() {
        return Pasetos.parserBuilder()
                .setSharedSecret(this.secretKey)
                .build();
    }
}