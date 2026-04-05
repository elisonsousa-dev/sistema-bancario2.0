
import java.util.HashMap;

public class DadosDoUsuario {
   private HashMap<String, Usuario> usuarios = new HashMap<>();

    public void adicionarUsuarios(Usuario usuario){
        if(usuarios.containsKey(usuario.getCpf())){
            System.out.println("Erro: Usuário ja existente");
        }else{
            usuarios.put(usuario.getCpf() , usuario);
            System.out.println("Usuário cadastrado com sucesso");
        }
    }
    public void listaDeusuario(){
        for(Usuario c: usuarios.values()){
            System.out.println("Usuario: "+c.getNome());
            System.out.println("CPF: "+c.getCpf());
            System.out.println("--------------------------");
        }
    }
    public void buscarUsuario(String cpf){
      Usuario c = usuarios.get(cpf);

      if(c != null){
          System.out.println("Usuario: "+c.getNome());
          System.out.println("CPF: "+c.getCpf());

      }else{
          System.out.println("Usuario não encontrado");

      }
    }
    public Usuario login(String cpf, String senha){
          for(Usuario c : usuarios.values()){
              if(c.autenticar(cpf, senha)){
                  return c;
              }
          }
          return null;
    }

}
