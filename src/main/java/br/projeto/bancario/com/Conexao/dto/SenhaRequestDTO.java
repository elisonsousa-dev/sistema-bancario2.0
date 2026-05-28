package br.projeto.bancario.com.Conexao.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class SenhaRequestDTO {
    private String senhaAtual;
    private String novaSenha;
}
