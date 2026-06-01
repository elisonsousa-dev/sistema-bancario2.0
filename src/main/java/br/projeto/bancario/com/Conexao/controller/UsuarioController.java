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
          Map<String, Object> response = new LinkedHashMap<>();

             response.put("mensagem", e.getMessage());

          return ResponseEntity.status(400).body(response);
      }

    }
    @PostMapping("/depositar")
    public ResponseEntity<?> depositar(@RequestHeader("Authorization") String header, @RequestBody DepRequestDTO usuario){

        try {
            Map<String, Object> response = new LinkedHashMap<>();
            VerSaldoResponseDTO user = service.depositar(header,usuario.getSaldo());

                response.put("mensagem", "Deposito efetuado com sucesso para "+ user.getNome());

             return ResponseEntity.ok(response);

        }catch (RuntimeException e){
            Map<String, Object> response = new LinkedHashMap<>();

            response.put("mensagem", e.getMessage());

            return ResponseEntity.status(400).body(response);
        }
    }
    @GetMapping("/saldo")
    public ResponseEntity<?> verSaldo(@RequestHeader("Authorization") String header){
        try {
            Map<String, Object> response = new LinkedHashMap<>();
            VerSaldoResponseDTO user = service.verSaldo(header);

            response.put("dados", user);

            return ResponseEntity.ok(response);

        }catch (RuntimeException e){
            Map<String, Object> response = new LinkedHashMap<>();

            response.put("mensagem", e.getMessage());

            return ResponseEntity.status(401).body(response);
        }

    }
    @PostMapping("/sacar")
   public ResponseEntity<?> sacar(@RequestHeader("Authorization") String header, @RequestBody SacarRequestDTO usuario){
        try {
            Map<String, Object> response = new LinkedHashMap<>();
            service.sacar(header, usuario.getValor());

            response.put("mensagem", "Saque efetuado com sucesso");
            return ResponseEntity.ok(response);

        }catch (RuntimeException e){
            Map<String, Object> response = new LinkedHashMap<>();

            response.put("mensagem", e.getMessage());

            return ResponseEntity.status(401).body(response);
        }
   }
   @PostMapping("/transferir")
    public ResponseEntity<?> transferir(@RequestHeader("Authorization") String header, @RequestBody TransferirRequestDTO usuario){
        try {
            Map<String, Object> response = new LinkedHashMap<>();
            TransferirResponseDTO user = service.transferir(header, usuario.getCpf(), usuario.getValor());

            response.put("mensagem","Transferencia efetuada com sucesso para "+user.getNome());
            return ResponseEntity.ok(response);
        }catch (RuntimeException e){
            Map<String, Object> response = new LinkedHashMap<>();

            response.put("mensagem", e.getMessage());

            return ResponseEntity.status(401).body(response);
        }
   }
   @GetMapping("/lista")
   public ResponseEntity<?> lista(@RequestHeader("Authorization") String header){
        try {
            Map<String, Object> response = new LinkedHashMap<>();
            List<ListaUsuariosDTO> users = service.lista(header);

            response.put("dados", users);

            return ResponseEntity.ok(response);
        }catch (RuntimeException e){
            Map<String, Object> response = new LinkedHashMap<>();

            response.put("mensagem", e.getMessage());

            return ResponseEntity.status(401).body(response);
        }
   }
   @DeleteMapping("/delete")
   public ResponseEntity<?> delete(@RequestHeader("Authorization") String header){
        try {
            Map<String, Object> response = new LinkedHashMap<>();
            service.delete(header);

            response.put("mensagem","Conta excluida com sucesso");

            return ResponseEntity.ok(response);

        }catch (RuntimeException e){
            Map<String, Object> response = new LinkedHashMap<>();

            response.put("mensagem", e.getMessage());

            return ResponseEntity.status(401).body(response);
        }
   }

   @PutMapping("/update")
   public ResponseEntity<?> update(@RequestHeader("Authorization") String header, @RequestBody SenhaRequestDTO usuario){
        try {
            Map<String, Object> response = new LinkedHashMap<>();
            service.update(header, usuario);

            response.put("mensagem", "Senha atualizada com sucesso");

            return ResponseEntity.status(200).body(response);

        }catch (RuntimeException e){

            Map<String, Object> response = new LinkedHashMap<>();

            response.put("mensagem", e.getMessage());

            return ResponseEntity.status(401).body(response);
        }
   }
   @PutMapping("/set")
   public ResponseEntity<?> setCargo(@RequestHeader("Authorization") String header,@RequestBody GetCargoRequestDTO dados){
        try {
            Map<String, Object> response = new LinkedHashMap<>();
            service.getCargo(header, dados);

            response.put("mensagem", "Cargo setado");

            return ResponseEntity.status(200).body(response);
        }catch (RuntimeException e){
            Map<String, Object> response = new LinkedHashMap<>();

            response.put("mensagem", e.getMessage());

            return ResponseEntity.status(401).body(response);
        }
   }


}
