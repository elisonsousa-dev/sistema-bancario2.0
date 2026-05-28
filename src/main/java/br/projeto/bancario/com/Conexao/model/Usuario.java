package br.projeto.bancario.com.Conexao.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Entity
@Component
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String cpf;
    private String senha;
    private double saldo;
    @Enumerated(EnumType.STRING)
    private Roles roles;

    public enum Roles{
        CEO,USER,ADMIN
    }
}
