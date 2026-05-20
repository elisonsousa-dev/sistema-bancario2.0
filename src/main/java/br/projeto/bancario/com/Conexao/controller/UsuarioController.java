package br.projeto.bancario.com.Conexao.controller;

import br.projeto.bancario.com.Conexao.dto.*;
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
    public ResponseEntity<?> login(@RequestBody Usuario usuario){

      try {
         LoginRequestDTO user = service.login(usuario);
           return ResponseEntity.ok(user);

      }catch (RuntimeException e){

          return ResponseEntity.status(400).body(e.getMessage());
      }

    }
    @PostMapping("/depositar")
    public ResponseEntity<?> depositar(@RequestHeader("Authorization") String header, @RequestBody DepRequestDTO usuario){

        try {
            VerSaldoResponseDTO user = service.depositar(header,usuario.getSaldo());

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
    @PostMapping("/sacar")
   public ResponseEntity<?> sacar(@RequestHeader("Authorization") String header, @RequestBody SacarRequestDTO usuario){
        try {
            service.sacar(header, usuario.getValor());

            return ResponseEntity.ok("Saque efetuado com sucesso");
        }catch (RuntimeException e){
            return ResponseEntity.status(401).body(e.getMessage());
        }
   }
   @PostMapping("/transferir")
    public ResponseEntity<?> transferir(@RequestBody TransferirRequestDTO usuario){
        try {
            service.transferir(usuario.getCpf(), usuario.getValor());

            return ResponseEntity.ok("Transferencia efetuada com sucesso");
        }catch (RuntimeException e){

            return ResponseEntity.status(401).body(e.getMessage());
        }
   }

}
