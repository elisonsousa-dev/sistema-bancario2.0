package br.projeto.bancario.com.Conexao.service;

import br.projeto.bancario.com.Conexao.dto.LoginRequestDTO;
import br.projeto.bancario.com.Conexao.dto.TransferirResponseDTO;
import br.projeto.bancario.com.Conexao.dto.VerSaldoRequestDTO;
import br.projeto.bancario.com.Conexao.dto.VerSaldoResponseDTO;
import br.projeto.bancario.com.Conexao.model.Usuario;
import br.projeto.bancario.com.Conexao.repository.RepositoryUser;
import br.projeto.bancario.com.Conexao.util.AuthUtil;
import br.projeto.bancario.com.Conexao.util.SenhaUtil;
import br.projeto.bancario.com.Conexao.util.TokenUtil;
import br.projeto.bancario.com.Conexao.util.Validacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    @Autowired
    private Validacao validar;
    @Autowired
    private RepositoryUser repo;
    @Autowired
    private VerSaldoRequestDTO validarUser;
    @Autowired
    private TokenUtil tokenUtil;
    @Autowired
    private AuthUtil authUtil;


    public void cadastrarUsuario(Usuario usuarios){

        boolean validarNome = validar.validarNome(usuarios.getNome());

        if(!validarNome){
            throw new RuntimeException("O nome não pode ser vazio");
        }

        boolean validarCpf = validar.validarCPF(usuarios.getCpf());

        if(!validarCpf){
            throw new RuntimeException("CPF inválido, digite o formato correto ");
        }

        boolean validarSenha = validar.validarSenha(usuarios.getSenha());

        if(!validarSenha){
            throw new RuntimeException("A senha não pode ser vazia");
        }

        Usuario user = repo.findByCpf(usuarios.getCpf());

        if(user != null){
            throw new RuntimeException("Este usuário ja existe");
        }
       String hash = SenhaUtil.gerarHash(usuarios.getSenha());
         Usuario usuario = new Usuario();

        usuario.setNome(usuarios.getNome());
        usuario.setCpf(usuarios.getCpf());
        usuario.setSenha(hash);

        repo.save(usuario);
    }

    public LoginRequestDTO login(Usuario usuario){

        boolean validarCpf = validar.validarCPF(usuario.getCpf());

        if(!validarCpf){
            throw new RuntimeException("CPF inválido, digite o formato correto");
        }

        boolean validarSenha = validar.validarSenha(usuario.getSenha());

        if(!validarSenha){
            throw new RuntimeException("A senha não pode ser vazia");
        }

        Usuario user = repo.findByCpf(usuario.getCpf());

        if(user == null){
            throw new RuntimeException("O usuário não foi encontrado");
        }

        boolean validarHash = SenhaUtil.verificarHash(usuario.getSenha(),user.getSenha());

        if(!validarHash){
            throw new RuntimeException("senha incorreta");
        }

        String token = tokenUtil.gerarToken(user.getCpf());

        LoginRequestDTO usuario1 = new LoginRequestDTO();

        usuario1.setNome(user.getNome());
        usuario1.setCpf(user.getCpf());
        usuario1.setToken(token);
        validarUser.setCpf(user.getCpf());

        return usuario1;

    }
    public VerSaldoResponseDTO depositar(String header, double valor){

         String token = authUtil.getCpf(header);

         if(header.isEmpty()){
             throw new RuntimeException("header não pode ser vazio!");
         }

         if(token == null){
             throw new RuntimeException("Token inválido");
         }

        Usuario user = repo.findByCpf(token);

        if(user == null){
            throw new RuntimeException("O usuário não foi encontrado");
        }

        if(valor <= 0){
            throw new RuntimeException("Valor inválido");
        }

          VerSaldoResponseDTO usuario = new VerSaldoResponseDTO();

          usuario.setNome(user.getNome());
          valor += user.getSaldo();
          user.setSaldo(valor);

           repo.save(user);

       return usuario;
    }
    public VerSaldoResponseDTO verSaldo(String header){
         String token = authUtil.getCpf(header);

         if(token == null){
             throw new RuntimeException("Token inválido");
         }

        Usuario user = repo.findByCpf(token);

        if(user == null){
            throw new RuntimeException("Faça login com sua conta ou cadastre-se");
        }

        VerSaldoResponseDTO usuario = new VerSaldoResponseDTO();

        usuario.setNome(user.getNome());
        usuario.setSaldo(user.getSaldo());

        return usuario;
    }
    public void sacar(String header, double valor){
        String token = authUtil.getCpf(header);

        if(header.isEmpty()){
            throw new RuntimeException("Header não pode ser vazio!");
        }

        if(token == null){
            throw new RuntimeException("Token inválido!");
        }

        Usuario user = repo.findByCpf(token);

        if(user == null){
            throw new RuntimeException("Faça login com sua conta ou cadastre-se");
        }

        if(valor > user.getSaldo()){
            throw new RuntimeException("Saldo insuficiente");
        }

        if(valor <= 0){
            throw new RuntimeException("Valor inválido");
        }

       valor = user.getSaldo() - valor;
         user.setSaldo(valor);

        repo.save(user);

    }
    public TransferirResponseDTO transferir(String header, String destino, double valor){
       String token = authUtil.getCpf(header);

       if(token == null){
           throw new RuntimeException("Token inválido");
        }

        Usuario origem = repo.findByCpf(token);

        if(origem == null){
            throw new RuntimeException("Faça login com sua conta ou cadastre-se");
        }
          if(valor > origem.getSaldo()){
              throw new RuntimeException("Saldo insuficiente");
          }

          if(valor <= 0){
              throw new RuntimeException("Valor inválido");
          }

        Usuario des = repo.findByCpf(destino);

        if(des == null){
            throw new RuntimeException("O usuário não foi encontrado");
        }

        if(destino.equals(origem.getCpf())){
            throw new RuntimeException("Transferencia inválida");
        }

        double retirada = origem.getSaldo() - valor;
        origem.setSaldo(retirada);

        repo.save(origem);

       double dp = des.getSaldo() + valor;
        des.setSaldo(dp);

        repo.save(des);
        TransferirResponseDTO usuario = new TransferirResponseDTO();

        usuario.setNome(des.getNome());

        return usuario;



    }


}
