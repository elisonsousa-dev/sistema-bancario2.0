package br.projeto.bancario.com.Conexao.exeption;

import br.projeto.bancario.com.Conexao.dto.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExeption {
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ResponseDTO> exeption(RuntimeException e){

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO(e.getMessage(), 400));
    }
}
