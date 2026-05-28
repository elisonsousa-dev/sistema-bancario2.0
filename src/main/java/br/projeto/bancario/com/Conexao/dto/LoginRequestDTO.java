package br.projeto.bancario.com.Conexao.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class LoginRequestDTO {
    private String nome;
    private String cpf;
    @JsonIgnore
    private String token;
    private String user;

}
