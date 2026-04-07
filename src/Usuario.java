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

    public void cadastrar(String nome , String cpf , String senha){
            this.setNome(nome);
            this.senha = senha;
            this.cpf = cpf;

    }
    public boolean autenticar(String cpf , String senha){
        return cpf.equalsIgnoreCase(getCpf()) && senha.equalsIgnoreCase(getSenha());
    }

    @Override
    public String toString() {
        return nome;
    }
}
