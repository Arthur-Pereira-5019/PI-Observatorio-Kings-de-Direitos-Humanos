package com.kings.okdhvi.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.kings.okdhvi.exception.TokenGenerationException;
import com.kings.okdhvi.model.Usuario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    Logger logger = LoggerFactory.getLogger(SecurityFilter.class);


    @Value("$api.security.token.secret")
    private String segredo;

    public String gerarToken(Usuario u) {
        try {
            Algorithm algoritmo = Algorithm.HMAC256(segredo);
            String token = JWT.create().withIssuer("Kings").withSubject(u.getEmail()).withExpiresAt(gerarPrazo()).sign(algoritmo);
            return token;
        } catch (Exception e) {
            e.printStackTrace();
            throw new TokenGenerationException("Erro ao gerar o token de Usuário");
        }
    }

    public String validateToken(String token) {
        try {
            Algorithm algoritmo = Algorithm.HMAC256(segredo);
            return JWT.require(algoritmo).withIssuer("Kings").build().verify(token).getSubject();
        } catch (JWTVerificationException e) {
            e.printStackTrace();
            return "";
        }
    }

    private Instant gerarPrazo() {
        return LocalDateTime.now().plusDays(14).toInstant(ZoneOffset.of("-03:00"));
    }

}
