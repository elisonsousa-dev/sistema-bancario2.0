void main() {
    Scanner scanner = new Scanner(System.in);
    DadosDoUsuario dados = new DadosDoUsuario();
    Banco banco = new Banco();
     int opcao;

    do {
        System.out.println();
        System.out.println("==========================");
        System.out.println("[1] Cadastrar conta");
        System.out.println("[2] Ver saldo");
        System.out.println("[3] Depositar");
        System.out.println("[4] Sacar");
        System.out.println("[5] Transferir ");
        System.out.println("[6] Lista de contas");
        System.out.println("[7] Lista de Usuarios");
        System.out.println("[8] Buscar Usuario");
        System.out.println("[9] Sair");
        System.out.println("============================");
        System.out.print("Opcao: ");
     opcao = scanner.nextInt();

     switch (opcao){
      case 1:
          scanner.nextLine();
          System.out.print("Digite seu nome: ");
            String nome = scanner.nextLine();

             System.out.print("Digite seu [CPF]: ");
              String cpf = scanner.nextLine();

            System.out.print("Digite uma senha: ");
          String senha = scanner.nextLine();
        Usuario novo = new Usuario();
        novo.cadastrar(nome , cpf , senha);
           dados.adicionarUsuarios(novo);
           Conta conta = new Conta(novo);
           banco.adicionarConta(novo,conta);
         break;
      case 2:
           scanner.nextLine();
            System.out.println("Digite seu [CPF]: ");
            String busCpf = scanner.nextLine();
           banco.buscarConta(busCpf);
         break;
      case 3:
          scanner.nextLine();
           System.out.print("Digite seu [CPF]: ");
            String pgCpf = scanner.nextLine();

            System.out.print("Valor: ");
            double valor = scanner.nextDouble();
           Conta depositar = banco.getConta(pgCpf);
         if(valor <=0 ){
            System.out.println("Erro: valor invalido");
        }else{
            if(depositar != null){
                depositar.depositar(valor);
            }else{
                System.out.println("Conta não encontrada");
            }
        }
         break;
      case 4:
          scanner.nextLine();
           System.out.print("Digite seu [CPF]: ");
            String sacCpf = scanner.nextLine();

            System.out.println("Valor:");
            double sacValor = scanner.nextDouble();
          Conta contaUsuario = banco.getConta(sacCpf);

          if(contaUsuario == null){
              System.out.println("Conta não encontrada");
          }else  if(sacValor <= 0){
              System.out.println("Erro; Valor invalido");
          }else if(sacValor > contaUsuario.getSaldo()){
              System.out.println("Erro: Saldo insuficiente");
          }else {
              System.out.println("Usuário: "+contaUsuario.getUsuario());
              contaUsuario.sacar(sacValor);
              System.out.println("Saque efetuado com sucesso");
          }





         break;
      case 5:
           scanner.nextLine();
            System.out.print("Origem: ");
             String de = scanner.nextLine();

               System.out.print("Destino: ");
               String para = scanner.nextLine();

             System.out.print("Valor: ");
            double trsValor = scanner.nextDouble();
          banco.transferir(de , para , trsValor);

       break;
         case 6:
             banco.listaDeContas();
             break;
         case 7:
             dados.listaDeusuario();
             break;
         case 8:
             scanner.nextLine();
             System.out.println("Digite seu [CPF]: ");
             String logCpf = scanner.nextLine();
             dados.buscarUsuario(logCpf);
             break;

         case 9:
             System.out.println("Saindo...");
             break;
     }

    }while (opcao != 9);

}