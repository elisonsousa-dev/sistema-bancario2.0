package br.projeto.bancario.com.Conexao.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthUtil {
    @Autowired
    private TokenUtil tokenUtil;
    public String getToken(String header){
        if(header == null || !header.startsWith("Bearer ")){
            return null;
        }

        return header.replace("Bearer ", "").trim();
    }
    public String getCpf(String header) {
        String token = getToken(header);

        if (token == null) {
            return null;
        }

        return tokenUtil.validarToken(token);
    }
    public String getRoles(String header){
        String token = getToken(header);

        if(token == null){
            return null;
        }

        return tokenUtil.validarRoles(token);
    }
}
