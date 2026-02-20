package com.agrosoft.api.security.filter;

import com.agrosoft.api.security.service.CustomUserDetailsService;
import com.agrosoft.api.security.service.PasetoUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class PasetoAuthFilter extends OncePerRequestFilter {

    private final PasetoUtil pasetoUtil;
    private final CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String token;
        final String userEmail;

        // 1. Verificar si hay un token en el header
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        token = authHeader.substring(7);

        // 2. Si el token es válido y no hay un usuario ya autenticado en el contexto actual
        if (pasetoUtil.isTokenValid(token) && SecurityContextHolder.getContext().getAuthentication() == null) {

            userEmail = pasetoUtil.extractEmail(token);
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

            // 3. Crear el objeto de autenticación oficial de Spring Security
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities()
            );

            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            // 4. Guardarlo en el contexto de seguridad
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }

        // 5. Continuar con la petición hacia el controlador
        filterChain.doFilter(request, response);
    }
}