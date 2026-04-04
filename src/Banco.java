import java.util.HashMap;

public class Banco {
    private HashMap<String, Conta> contas = new HashMap<>();

   public void addBanco(Usuario conta, Conta user){
       contas.put(conta.getCpf() , user );
   }

   public void listaDeContas(){
       for(Conta c : contas.values()){
           System.out.printf("Conta: "+c.getUsuario());
           System.out.println("| Saldo: "+c.getSaldo());
           System.out.println("------------------");
       }
   }
   public void buscarConta(String cpf){
       Conta u = contas.get(cpf);

       if(u != null){
           System.out.println("Usuario:"+u.getUsuario());
           System.out.println("Saldo: "+u.getSaldo());
           System.out.println("---------------------------");
       }else {
           System.out.println("Conta não encontrada");
       }
   }
   public Conta pegarConta(String cpf){
       return contas.get(cpf);
   }
   public void transfer(String de , String para , double valor){
       Conta origem = pegarConta(de);
       Conta destino = pegarConta(para);

       if(origem == null || destino == null){
           System.out.println("Erro: Uma das contas não foi encontrada");
           return;
       }
           origem.transferir(destino, valor);

   }


}