package br.projeto.bancario.com.Conexao.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class ResponseDTO {
    private String mensagem;
    private int status;

    public ResponseDTO(String mensagem, int status){
        this.mensagem = mensagem;
        this.status = status;
    }
}
