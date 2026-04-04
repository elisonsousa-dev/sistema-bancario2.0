public class Usuario {
    private String nome;
    private String cpf;
    private String senha;

    public String getNome(){
        return nome;
    }
    public void setNome(String nome){
        this.nome = nome;
    }
    public String getCpf(){
        return cpf;
    }
    public String getSenha(){
        return senha;
    }
    public void setSenha(String senha){
        this.senha = senha;
    }
    public void cadastrar(String nome , String cpf , String senha){
        this.nome = nome;
        this.senha = senha;
        this.cpf = cpf;
    }

    @Override
    public String toString() {
        return nome;
    }
}
