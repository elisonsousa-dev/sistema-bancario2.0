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
     public void depositar(double dep){
        this.saldo += dep;
     }

     public void sacar(double saque){
            this.saldo -= saque;
     }
     public boolean transferir( Conta destino, double valor){
        if(valor <=0 ){
            System.out.println("Erro: Valor invalido");
        }else if(valor > this.saldo){
            System.out.println("Erro: Saldo insuficiente");
        }else{
            if(this.saldo >= valor){
                this.saldo -= valor;
                destino.saldo += valor;
                System.out.println("Transferencia efetuada com sucesso para: "+destino.getUsuario());
                return true;
            }
        }
        return false;
     }

}
