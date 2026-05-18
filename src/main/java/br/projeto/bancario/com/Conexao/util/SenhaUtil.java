package br.projeto.bancario.com.Conexao.util;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class SenhaUtil {
    public static String gerarHash(String senha){
        return BCrypt.hashpw(senha, BCrypt.gensalt());
    }

    public static boolean verificarHash(String senha, String hash){
        return BCrypt.checkpw(senha, hash);
    }
}
