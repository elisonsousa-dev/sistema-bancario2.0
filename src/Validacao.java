public class Validacao {
    public boolean validarCPF(String cpf){
            if(!cpf.matches("\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}")){
                return false;
            }
                return true;
    }
    public boolean validarSenha(String senha){
           if (senha.isEmpty()){
               return false;
           }
               return true;
    }
}
