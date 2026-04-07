void main() {
    Scanner scanner = new Scanner(System.in);
    DadosDoUsuario dados = new DadosDoUsuario();
    Banco banco = new Banco();
     int opcao;

    do {
        System.out.println();
        System.out.println("=|===========================|=");
        System.out.println("=| [1] Cadastrar conta       |=");
        System.out.println("=| [2] Ver saldo             |=");
        System.out.println("=| [3] Depositar             |=");
        System.out.println("=| [4] Sacar                 |=");
        System.out.println("=| [5] Transferir            |=");
        System.out.println("=| [6] Lista de contas       |=");
        System.out.println("=| [7] Lista de Usuarios     |=");
        System.out.println("=| [8] Buscar Usuario        |=");
        System.out.println("=| [9] Login                 |=");
        System.out.println("=| [10] Sair                 |=");
        System.out.println("=|===========================|=");
        System.out.print("Opcao: ");
        try {
            opcao = scanner.nextInt();
        }catch (InputMismatchException erro){
            System.out.println("Entrada inválida. Por favor, informe uma opção numérica válida do menu.");
            scanner.nextLine();
            opcao = 0;
        }

     switch (opcao){
      case 1:
          String cpf;
          String senha;
          scanner.nextLine();
          System.out.print("Digite seu nome: ");
            String nome = scanner.nextLine();
             do {
                 System.out.println("Formato: 000.000.000-00 ");

                 System.out.print("Digite seu CPF: ");
                  cpf = scanner.nextLine();

                 if(!cpf.matches("\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}")){
                     System.out.println("CPF inválido! User o formato correto");
                 }

             }while (!cpf.matches("\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}"));
             do {
                 System.out.print("Digite uma senha: ");
                 senha = scanner.nextLine();

                 if (senha.isEmpty()) {
                     System.out.println("Senha não pode ser vazia!");
                 }
             }while (senha.isEmpty());

            Usuario novo = new Usuario();
            novo.cadastrar(nome , cpf , senha);
            dados.adicionarUsuarios(novo);
            Conta conta = new Conta(novo);
            banco.adicionarConta(novo,conta);
         break;
      case 2:
          String busCpf;
           scanner.nextLine();
           do {
               System.out.println("Digite seu [CPF]: ");
               busCpf = scanner.nextLine();

               if(!busCpf.matches("\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}")){
                   System.out.println("CPF invalido! User o formato correto.");
               }

           }while (!busCpf.matches("\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}"));
           banco.buscarConta(busCpf);

         break;
      case 3:
          String depCpf;
          double valor = -1;
          scanner.nextLine();
          do {
              System.out.print("Digite seu [CPF]: ");
              depCpf = scanner.nextLine();
              if (!depCpf.matches("\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}")) {
                  System.out.println("CPF invalido! digite o formato correto");
              }
          }while (!depCpf.matches("\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}"));
          do {

              try {
                  System.out.print("Valor: ");
                  valor = scanner.nextDouble();

                  if (valor <= 0) {
                      System.out.println("Valor inválido.");
                  }
              } catch (InputMismatchException erro) {
                  System.out.println("Erro na digitação! Somente digitos númericos.");
                  scanner.nextLine();
              }
          }while (valor <= 0);
           //
           Conta depositar = banco.getConta(depCpf);

            if(depositar != null){
                depositar.depositar(valor);
            }else{
                System.out.println("Conta não encontrada");
            }

         break;
      case 4:
          String sacCpf;
          double sacValor = -1;
          scanner.nextLine();
          do {
              System.out.print("Digite seu [CPF]: ");
              sacCpf = scanner.nextLine();

              if (!sacCpf.matches("\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}")) {
                  System.out.println("CPF inválido! Digite o formato correto.");
              }
          }while (!sacCpf.matches("\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}"));
          do {
              //
              try {
                  System.out.println("Valor:");
                  sacValor = scanner.nextDouble();

                  if (sacValor <= 0) {
                      System.out.println("Valor inválido!");
                  }

              } catch (InputMismatchException erro){
                  System.out.println("Erro na digitação! Somente digitos númericos");
                  scanner.nextLine();
              }
          }while (sacValor <= 0);
          Conta contaUsuario = banco.getConta(sacCpf);

          if(contaUsuario == null){
              System.out.println("Conta não encontrada");

          }else if(sacValor > contaUsuario.getSaldo()){
              System.out.println("Erro: Saldo insuficiente");

          }else {
              System.out.println("Usuário: "+contaUsuario.getUsuario());
              contaUsuario.sacar(sacValor);
              System.out.println("Saque efetuado com sucesso");
          }

         break;
      case 5:
          String origem;
          String destino;
          double trsValor = -1;
           scanner.nextLine();
           //
            do {
                System.out.print("Origem: ");
                origem = scanner.nextLine();

                System.out.print("Destino: ");
                destino = scanner.nextLine();
                if (!origem.matches("\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}") || !destino.matches("\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}")) {
                    System.out.println("CPF inválido! Digite o formato correto.");
                }
            }while (!origem.matches("\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}") || !destino.matches("\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}"));
            do {

                try {
                    System.out.print("Valor: ");
                    trsValor = scanner.nextDouble();

                    if (trsValor <= 0) {
                        System.out.println("Valor inválido!");
                    }

                } catch (InputMismatchException erro) {
                    System.out.println("Erro na digitação! Somente digitos númericos.");
                    scanner.nextLine();
                }
            }while (trsValor <= 0);
          banco.transferir(origem , destino , trsValor);

       break;
         case 6:
             banco.listaDeContas();
             break;
         case 7:
             dados.listaDeusuario();
             break;
         case 8:
             String logCpf;
             scanner.nextLine();
             do {

                 System.out.println("Digite seu [CPF]: ");
                 logCpf = scanner.nextLine();

                 if (!logCpf.matches("\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}")) {
                     System.out.println("CPF inválido! Digite o formato correto.");
                 }
             }while (!logCpf.matches("\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}"));
             dados.buscarUsuario(logCpf);
             break;

         case 9:
             String loginCpf;
             String loginSenha;
             scanner.nextLine();
             do {
                 System.out.print("CPF: ");
                 loginCpf = scanner.nextLine();

                 if (!loginCpf.matches("\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}")) {
                     System.out.println("CPF inválido! Digite o formato correto.");
                 }
             }while (!loginCpf.matches("\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}"));
            do {
                System.out.print("Senha: ");
                loginSenha = scanner.nextLine();
                if (loginSenha.isEmpty()) {
                    System.out.println("Senha não pode ser vazia!");
                }
            }while (loginSenha.isEmpty());

            Usuario loginUsuario = dados.login(loginCpf, loginSenha);

                if (loginUsuario != null) {
                    System.out.println("Bem-vindo, " + loginUsuario.getNome());
                    System.out.println("Usuario: " + loginUsuario.getNome() + " | CPF: " + loginUsuario.getCpf());
                } else {
                    System.out.println("Sua conta não foi encontrada");
                    System.out.println("Verifique se sua senha ou CPF estão corretos");
                }
             break;
         case 10:
             System.out.println("Saindo...");
             break;
     }

    }while (opcao != 10);

}