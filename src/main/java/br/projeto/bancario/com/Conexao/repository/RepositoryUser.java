package br.projeto.bancario.com.Conexao.repository;

import br.projeto.bancario.com.Conexao.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface RepositoryUser extends JpaRepository<Usuario, Long> {
  Usuario findByCpf(String cpf);
}
