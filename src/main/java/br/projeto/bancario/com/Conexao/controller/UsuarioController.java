package br.projeto.bancario.com.Conexao.controller;

import br.projeto.bancario.com.Conexao.dto.*;
import br.projeto.bancario.com.Conexao.model.Usuario;
import br.projeto.bancario.com.Conexao.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
    @Autowired
    private UsuarioService service;


    @PostMapping("/cadastro")
public ResponseEntity<?> cadastro(@RequestBody UsuarioRequestDTO usuario){
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
          Map<String, Object> response = new LinkedHashMap<>();

          LoginRequestDTO user = service.login(usuario);

          response.put("mensagem", "Bem-Vindo, "+usuario.getNome());
          response.put("status", 200);
          response.put("dados", user);

           return ResponseEntity.ok(response);

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
    public ResponseEntity<?> verSaldo(@RequestHeader("Authorization") String header){
        try {
            VerSaldoResponseDTO user = service.verSaldo(header);

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
    public ResponseEntity<?> transferir(@RequestHeader("Authorization") String header, @RequestBody TransferirRequestDTO usuario){
        try {
            TransferirResponseDTO user = service.transferir(header, usuario.getCpf(), usuario.getValor());

            return ResponseEntity.ok("Transferencia efetuada com sucesso para "+user.getNome());
        }catch (RuntimeException e){

            return ResponseEntity.status(401).body(e.getMessage());
        }
   }
   @GetMapping("/lista")
   public ResponseEntity<?> lista(@RequestHeader("Authorization") String header){
        try {
            List<ListaUsuariosDTO> users = service.lista(header);

            return ResponseEntity.ok(users);
        }catch (RuntimeException e){
            return ResponseEntity.status(401).body(e.getMessage());
        }
   }
   @DeleteMapping("/delete")
   public ResponseEntity<?> delete(@RequestHeader("Authorization") String header){
        try {

            service.delete(header);

            return ResponseEntity.ok("Conta excluida com sucesso");

        }catch (RuntimeException e){
            return ResponseEntity.status(401).body(e.getMessage());
        }
   }

   @PutMapping("/update")
   public ResponseEntity<?> update(@RequestHeader("Authorization") String header, @RequestBody SenhaRequestDTO usuario){
        try {
            service.update(header, usuario);

            return ResponseEntity.status(200).body("Senha atualizada com sucesso");

        }catch (RuntimeException e){

            return ResponseEntity.status(401).body(e.getMessage());
        }
   }
   @PutMapping("/set")
   public ResponseEntity<?> setCargo(@RequestHeader("Authorization") String header,@RequestBody GetCargoRequestDTO dados){
        try {
            service.getCargo(header, dados);

            return ResponseEntity.status(200).body("Cargo setado");
        }catch (RuntimeException e){
            return ResponseEntity.status(401).body(e.getMessage());
        }
   }


}
