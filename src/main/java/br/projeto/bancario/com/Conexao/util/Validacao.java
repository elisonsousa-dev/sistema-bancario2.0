package br.projeto.bancario.com.Conexao.util;

import org.springframework.stereotype.Component;

@Component
public class Validacao {
    public boolean validarCPF(String cpf){
            return cpf.matches("\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}");

    }
    public boolean validarSenha(String senha){
           if (senha.isEmpty()){
               return false;
           }
               return true;
    }
    public boolean validarNome(String nome){
        if(nome.isEmpty()){
            return false;
        }
        return true;
    }
}
