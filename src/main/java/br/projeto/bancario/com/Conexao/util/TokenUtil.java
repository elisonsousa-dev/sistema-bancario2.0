package br.projeto.bancario.com.Conexao.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
@Component
public class TokenUtil {
    @Value("${jwt.secret}")
    private String secret;

    public String gerarToken(String cpf, String roles){
      return  JWT.create()
                .withSubject(cpf)
                .withClaim("roles", roles)
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .sign(Algorithm.HMAC256(secret));

    }

    public String validarToken(String token){
        try {
            DecodedJWT jwt = JWT.require(Algorithm.HMAC256(secret))
                    .build()
                    .verify(token);
            return jwt.getSubject();

        }catch (JWTVerificationException e){
            return null;
        }
    }
    public String validarRoles(String roles){
       try {
           DecodedJWT jwt = JWT.require(Algorithm.HMAC256(secret))
                   .build()
                   .verify(roles);

           return jwt.getClaim("roles").asString();

       }catch (JWTVerificationException e){
           return null;
       }
    }

}
