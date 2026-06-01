package br.projeto.bancario.com.Conexao.service;

import br.projeto.bancario.com.Conexao.dto.*;
import br.projeto.bancario.com.Conexao.model.Usuario;
import br.projeto.bancario.com.Conexao.repository.RepositoryUser;
import br.projeto.bancario.com.Conexao.util.AuthUtil;
import br.projeto.bancario.com.Conexao.util.SenhaUtil;
import br.projeto.bancario.com.Conexao.util.TokenUtil;
import br.projeto.bancario.com.Conexao.util.Validacao;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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


    public void cadastrarUsuario(UsuarioRequestDTO usuarios){

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
        Usuario usuario = new Usuario();

       String hash = SenhaUtil.gerarHash(usuarios.getSenha());

        usuario.setNome(usuarios.getNome());
        usuario.setCpf(usuarios.getCpf());
        usuario.setRoles(Usuario.Roles.ADMIN);
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

        String token = tokenUtil.gerarToken(user.getCpf(),String.valueOf(user.getRoles()));

        LoginRequestDTO usuario1 = new LoginRequestDTO();

        usuario1.setNome(user.getNome());
        usuario1.setCpf(user.getCpf());
        usuario.setNome(user.getNome());
        usuario1.setToken(token);
        usuario1.setUser(String.valueOf(user.getRoles()));
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

    public List<ListaUsuariosDTO> lista(String header){
        String token = authUtil.getCpf(header);

        if(token == null){
            throw new RuntimeException("Token inválido");
        }

        Usuario user = repo.findByCpf(token);

        if(user == null){
            throw new RuntimeException("O usuário não foi encontrado");
        }
         String roles = authUtil.getRoles(header);

        if(roles == null){
            throw  new RuntimeException("token inválido 'roles' ");
        }

        if(!Usuario.Roles.ADMIN.name().equals(roles) && !Usuario.Roles.CEO.name().equals(roles)){
            throw new RuntimeException("Acesso negado");
        }

        return repo.findAll().stream().map(usuario -> {
            ListaUsuariosDTO usuarios = new ListaUsuariosDTO();
            usuarios.setId(usuario.getId());
            usuarios.setNome(usuario.getNome());
            usuarios.setCpf(usuario.getCpf());
            usuarios.setSaldo(usuario.getSaldo());
            usuarios.setUser(String.valueOf(usuario.getRoles()));
            return usuarios;
        }).toList();

    }
    public void delete(String header){
        String token = authUtil.getCpf(header);

        if(token == null){
            throw new RuntimeException("O usuário não foi encontrado");
        }
        Usuario user = repo.findByCpf(token);

        if(user == null){
            throw new RuntimeException("O usuário não foi encontrado no banco de dados");
        }
        String roles = authUtil.getRoles(header);

        if(!Usuario.Roles.USER.name().equals(roles)){
            throw new RuntimeException("Esta conta não pode ser deletada");
        }

        repo.delete(user);
    }

    public void update(String header, SenhaRequestDTO dados){
        String token = authUtil.getCpf(header);

        if(token == null){
            throw new RuntimeException("Token inválido");
        }

        Usuario user = repo.findByCpf(token);

        if(user == null){
            throw new RuntimeException("O Usuário não foi encontrado");
        }

        if(!BCrypt.checkpw(dados.getSenhaAtual(),user.getSenha())){
            throw new RuntimeException("Senha atual incorreta");
        }

        boolean validarNovaSenha = validar.validarSenha(dados.getNovaSenha());

        if(!validarNovaSenha){
            throw new RuntimeException("Sua nova senha não pode ser vazia");
        }

        if(BCrypt.checkpw(dados.getNovaSenha(), user.getSenha())){
            throw new RuntimeException("Sua nova senha não pode ser igual a sua atual");
        }

        String hash = SenhaUtil.gerarHash(dados.getNovaSenha());

        user.setSenha(hash);

        repo.save(user);

    }
    public void getCargo(String header,GetCargoRequestDTO dados){
        String token = authUtil.getCpf(header);

        if(token == null){
            throw new RuntimeException("Token inválido");

        }
        Usuario user = repo.findByCpf(token);

        if(user == null){
            throw new RuntimeException("O usuário não foi encontrado");
        }

        String role = authUtil.getRoles(header);

        if(role == null){
            throw new RuntimeException("Token inválido 'role'");
        }

        if(!Usuario.Roles.CEO.name().equals(role)){
            throw new RuntimeException("Você não tem autorização para acessa essa rota!");
        }

        boolean ok = SenhaUtil.verificarHash(dados.getSenha(), user.getSenha());

        if(!ok){
            throw new RuntimeException("Senha incorreta");
        }

        Usuario usuarioAl = repo.findByCpf(dados.getCpf());

        if(usuarioAl == null){
            throw new RuntimeException("O user não foi encontrado");
        }
        String roles = dados.getCargo();


        if(usuarioAl.getRoles() == Usuario.Roles.valueOf(roles)){
            throw new RuntimeException("O usuário já possui esse cargo");
        }

        roles = roles.toUpperCase();

        usuarioAl.setRoles(Usuario.Roles.valueOf(roles));

        repo.save(usuarioAl);

    }

}
