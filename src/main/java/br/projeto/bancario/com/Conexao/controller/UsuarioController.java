package br.projeto.bancario.com.Conexao.controller;

import br.projeto.bancario.com.Conexao.dto.DepRequestDTO;
import br.projeto.bancario.com.Conexao.dto.LoginRequestDTO;
import br.projeto.bancario.com.Conexao.dto.VerSaldoResponseDTO;
import br.projeto.bancario.com.Conexao.model.Usuario;
import br.projeto.bancario.com.Conexao.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
    @Autowired
    private UsuarioService service;


    @PostMapping("/cadastro")
public ResponseEntity<?> cadastro(@RequestBody Usuario usuario){
        try {
            service.cadastrarUsuario(usuario);

            return ResponseEntity.ok("Usuário cadastrado");

        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }

    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO usuario){

      try {
         LoginRequestDTO user = service.login(usuario);
           return ResponseEntity.ok(user);

      }catch (RuntimeException e){

          return ResponseEntity.status(400).body(e.getMessage());
      }

    }
    @PostMapping("/depositar")
    public ResponseEntity<?> depositar(@RequestBody DepRequestDTO usuario){

        try {
            VerSaldoResponseDTO user = service.depositar(usuario.getSaldo());

             return ResponseEntity.ok("Deposito efetuado com sucesso para "+ user.getNome());

        }catch (RuntimeException e){

            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
    @GetMapping("/saldo")
    public ResponseEntity<?> verSaldo(){
        try {
            VerSaldoResponseDTO user = service.verSaldo();

            return ResponseEntity.ok(user);

        }catch (RuntimeException e){
            return ResponseEntity.status(401).body(e.getMessage());
        }


    }

}
