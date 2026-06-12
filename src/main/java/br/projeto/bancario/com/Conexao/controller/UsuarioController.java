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

          Map<String, Object> response = new LinkedHashMap<>();

          LoginRequestDTO user = service.login(usuario);

          response.put("mensagem", "Bem-Vindo, "+usuario.getNome());
          response.put("status", 200);
          response.put("dados", user);

           return ResponseEntity.ok(response);

    }
    @PostMapping("/depositar")
    public ResponseEntity<?> depositar(@RequestHeader("Authorization") String header, @RequestBody DepRequestDTO usuario){

            VerSaldoResponseDTO user = service.depositar(header,usuario.getSaldo());

             return ResponseEntity.ok(
                     new ResponseDTO("Deposito efetuado com sucesso para "+ user.getNome(),200));

    }
    @GetMapping("/saldo")
    public ResponseEntity<?> verSaldo(@RequestHeader("Authorization") String header){
            Map<String, Object> response = new LinkedHashMap<>();
            VerSaldoResponseDTO user = service.verSaldo(header);

            response.put("dados", user);

            return ResponseEntity.ok(response);
    }
    @PostMapping("/sacar")
   public ResponseEntity<?> sacar(@RequestHeader("Authorization") String header, @RequestBody SacarRequestDTO usuario){
            service.sacar(header, usuario.getValor());

            return ResponseEntity.ok(
                    new ResponseDTO("Saque efetuado com sucesso", 200));

   }
   @PostMapping("/transferir")
    public ResponseEntity<?> transferir(@RequestHeader("Authorization") String header, @RequestBody TransferirRequestDTO usuario){
            TransferirResponseDTO user = service.transferir(header, usuario.getCpf(), usuario.getValor());

            return ResponseEntity.ok(
                    new ResponseDTO("Transferencia efetuada com sucesso para "+user.getNome(),200));

   }
   @GetMapping("/lista")
   public ResponseEntity<?> lista(@RequestHeader("Authorization") String header){
            Map<String, Object> response = new LinkedHashMap<>();
            List<ListaUsuariosDTO> users = service.lista(header);

            response.put("dados", users);

            return ResponseEntity.ok(response);
   }
   @DeleteMapping("/delete")
   public ResponseEntity<?> delete(@RequestHeader("Authorization") String header){
            service.delete(header);

            return ResponseEntity.ok(
                    new ResponseDTO("Conta excluida com sucesso", 200));

   }

   @PutMapping("/update")
   public ResponseEntity<?> update(@RequestHeader("Authorization") String header, @RequestBody SenhaRequestDTO usuario){
            service.update(header, usuario);

            return ResponseEntity.status(200).body(
                    new ResponseDTO("Senha atualizada com sucesso",200));

   }
   @PutMapping("/set")
   public ResponseEntity<?> setCargo(@RequestHeader("Authorization") String header,@RequestBody GetCargoRequestDTO dados){
            service.getCargo(header, dados);

            return ResponseEntity.status(200).body(
                    new ResponseDTO("Cargo setado", 200));

   }
   @GetMapping("/historico")
   public ResponseEntity<?> historico(){
        TransacaoResponseDTO hist = service.historico();

        return ResponseEntity.ok(hist);
   }


}
