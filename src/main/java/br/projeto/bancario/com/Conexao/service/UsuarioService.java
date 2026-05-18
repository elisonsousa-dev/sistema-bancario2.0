package br.projeto.bancario.com.Conexao.service;

import br.projeto.bancario.com.Conexao.dto.LoginRequestDTO;
import br.projeto.bancario.com.Conexao.model.Usuario;
import br.projeto.bancario.com.Conexao.repository.RepositoryUser;
import br.projeto.bancario.com.Conexao.util.Validacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    @Autowired
    private Validacao validar;
    @Autowired
    private RepositoryUser repo;


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
         Usuario usuario = new Usuario();

        usuario.setNome(usuarios.getNome());
        usuario.setCpf(usuarios.getCpf());
        usuario.setSenha(usuarios.getSenha());

        repo.save(usuario);
    }

    public LoginRequestDTO login(LoginRequestDTO usuario){

        boolean validarCpf = validar.validarCPF(usuario.getCpf());

        if(!validarCpf){
            throw new RuntimeException("CPF inválido, digite o formato correto");
        }

        boolean validarSenha = validar.validarSenha(usuario.getSenha());

        if(!validarSenha){
            throw new RuntimeException("A senha não pode ser vazia");
        }
        System.out.println(usuario);
        Usuario user = repo.findByCpf(usuario.getCpf());

        if(user == null){
            throw new RuntimeException("O usuário não foi encontrado");
        }

        LoginRequestDTO usuario1 = new LoginRequestDTO();

        usuario1.setNome(user.getNome());
        usuario1.setCpf(user.getCpf());
        usuario1.setSenha(user.getSenha());

        return usuario1;
    }
    public void depositar(String cpf, double valor){
        boolean validarCpf = validar.validarCPF(cpf);

        if(!validarCpf){
            throw new RuntimeException("CPF inválido");
        }

        Usuario user = repo.findByCpf(cpf);

        if(user == null){
            throw new RuntimeException("Usuário não encontrado");
        }

        valor += user.getSaldo();

       user.setSaldo(valor);


        repo.save(user);
    }

}
