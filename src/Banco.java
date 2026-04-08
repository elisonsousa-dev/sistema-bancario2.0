import java.util.HashMap;

public class Banco {
    private final HashMap<String, Conta> contas = new HashMap<>();

   public boolean adicionarConta(Usuario usuario, Conta conta){

       if(contas.containsKey(usuario.getCpf())){
          return false;
       }else{
           contas.put(usuario.getCpf() , conta );
           return true;
       }
   }

   public void listaDeContas(){
       for(Conta c : contas.values()){
           System.out.printf("Conta: "+c.getUsuario());
           System.out.println(" | Saldo: "+c.getSaldo());
           System.out.println("------------------");
       }
   }
   public void buscarConta(String cpf){
       Conta u = contas.get(cpf);

       if(u != null){
           System.out.println("Usuario: "+u.getUsuario());
           System.out.println("Saldo: "+u.getSaldo());
           System.out.println("---------------------------");
       }else {
           System.out.println("Conta não encontrada");
       }
   }
   public Conta getConta(String cpf ){
       return contas.get(cpf);
   }

   public void transferir(String de , String para , double valor){
       Conta origem = getConta(de);
       Conta destino = getConta(para);

       if(origem == null || destino == null){
           System.out.println("Erro: Uma das contas não foi encontrada");
           return;
       }
           origem.transferir(destino, valor);
   }



}