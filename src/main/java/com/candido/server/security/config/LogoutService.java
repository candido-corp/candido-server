package com.candido.server.security.config;

import com.candido.server.domain.v1.token.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

    private final TokenRepository tokenRepository;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        // Ensure the logout request is a POST request
        if (!HttpMethod.POST.name().equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
            return;
        }

        // Recupero header di autorizzazione
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        // Variabile per il token
        final String jwt;

        // Se header recuperato è null o non inizia con la parola chiave "Bearer ", chiudo la richiesta
        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        // Recupero il token alla settima posizione che è la lunghezza della parola chiave "Bearer "
        jwt = authHeader.substring(7);

        // Se non esiste il token dentro la chiamata di logout ritorno un errore
        if(jwt.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        // Recupero il token salvato
        var storedToken = tokenRepository.findByAccessToken(jwt).orElse(null);
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);

        // Se il token non è null lo disabilito e pulisco il contesto di spring
        if(storedToken != null) {
            tokenRepository.delete(storedToken);
            SecurityContextHolder.clearContext();
        }

    }

}
