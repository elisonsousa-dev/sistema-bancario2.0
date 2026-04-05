public class Conta {
    private double saldo;
    private Usuario usuario;


    public double getSaldo(){
        return saldo;
    }

     public Conta(Usuario usuario){
         this.usuario = usuario;
         this.saldo = 0;

     }
     public Usuario getUsuario(){
        return usuario;
     }
     public boolean depositar(double dep){
        if(dep <= 0){
            System.out.println("Valor inválido");
            return false;
        }else{
            this.saldo += dep;
            System.out.println("Deposito efetuado com sucesso");
            System.out.println("Destino: "+getUsuario());
            return true;
        }

     }

     public boolean sacar(double saque){
        if(saque <=0 ){
            System.out.println("Valor inválido.");
            return false;
        }else if(saque > getSaldo()){

            System.out.println("Saldo insuficiente");
            return false;
        }else{
            this.saldo -= saque;
            return true;
        }

     }
     public boolean transferir( Conta destino, double valor){
        if(valor <=0 ){
            System.out.println("Erro: Valor invalido");
            return false;
        }else if(valor > this.saldo){
            System.out.println("Erro: Saldo insuficiente");
            return false;
        }else{
            if(this.saldo > valor){
                this.saldo -= valor;
                destino.saldo += valor;
                System.out.println("Transferencia efetuada com sucesso para: "+destino.getUsuario());
                return true;
            }
        }
        return false;
     }

}
