package com.agrosoft.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Aplica a todos nuestros endpoints (ej. /api/v1/...)
                .allowedOrigins(
                        "http://localhost:3000", // Puerto por defecto de Next.js/React
                        "http://localhost:5173",  // Puerto por defecto de Vite (por si acaso)
                        "http://localhost:8081"   // Puerto para exposicion web
                )
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH") // Métodos permitidos
                .allowedHeaders("*") // Permitimos cualquier cabecera (incluyendo Authorization para PASETO)
                .allowCredentials(true)
                .maxAge(3600); // Cachea la respuesta de validación CORS por 1 hora para optimizar
    }
}