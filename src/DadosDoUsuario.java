
import java.util.HashMap;

public class DadosDoUsuario {
   private HashMap<String, Usuario> usuarios = new HashMap<>();
    public void addUsuarios(Usuario nome){
        usuarios.put(nome.getCpf() , nome);
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

}
