void main() {
    Scanner scanner = new Scanner(System.in);
    UsuarioDao dao = new UsuarioDao();
    List<Usuario> usarios = dao.listaUsuario();
    Validacao Validador = new Validacao();
    Usuario loginUsuario = null;
    boolean logado = false;
    boolean sistemaAtivo = true;
    boolean senhaValida;
    boolean cpfValido;
    boolean validar;
    int opcao;
    int opcao1;
    int proq = 0;

    while (sistemaAtivo) {

        while (loginUsuario == null) {
            System.out.println("=|===========================|=");
            System.out.println("=| [1] Cadastrar conta       |=");
            System.out.println("=| [2] Login                 |=");
            System.out.println("=|===========================|=");

            do {
                try {
                    System.out.print("Opção: ");
                    opcao1 = scanner.nextInt();
                } catch (InputMismatchException erro) {
                    System.out.println("Digite de acordo com o menu");
                    scanner.nextLine();
                    opcao1 = 0;
                }

                switch (opcao1) {
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
                            cpfValido = Validador.validarCPF(cpf);
                            if (!cpfValido) {
                                System.out.println("CPF inválido! Digite o formato correto");
                            } else if (dao.cpfExist(cpf)) {
                                System.out.println("CPF ja cadastrado!");
                            }
                        } while (!cpfValido || dao.cpfExist(cpf));
                        do {
                            System.out.print("Digite uma senha: ");
                            senha = scanner.nextLine();
                            senhaValida = Validador.validarSenha(senha);
                            if (!senhaValida) {
                                System.out.println("Senha não pode ser vazia!");
                            } else {
                                System.out.println("CPF cadastrado!");
                            }
                        } while (!senhaValida);

                        Usuario novo = new Usuario();
                        novo.cadastrar(nome, cpf, senha);
                        dao.cadastrar(novo);
                        break;
                    case 2:
                        String loginCpf;
                        String loginSenha;
                        scanner.nextLine();
                        do {
                            System.out.print("CPF: ");
                            loginCpf = scanner.nextLine();
                            cpfValido = Validador.validarCPF(loginCpf);

                            if (!cpfValido) {
                                System.out.println("CPF inválido! Digite o formato correto.");
                            }
                        } while (!cpfValido);

                        do {
                            System.out.print("Senha: ");
                            loginSenha = scanner.nextLine();
                            senhaValida = Validador.validarSenha(loginSenha);

                            if (!senhaValida) {
                                System.out.println("Senha não pode ser vazia!");
                                proq++;
                            }
                        } while (!senhaValida);

                        loginUsuario = dao.login(loginCpf, loginSenha);

                        if(proq == 3){
                            System.out.println("--------------------------------------------------------------------");
                            System.out.println("Limite de tentativas atingidas! Tente novamente após alguns minutos.");
                            System.out.println("--------------------------------------------------------------------");
                            proq = 0;
                        }else{

                            if (loginUsuario != null) {
                                System.out.println("Bem-vindo, " + loginUsuario.getNome());
                                System.out.println("Usuario: " + loginUsuario.getNome() + " | CPF: " + loginUsuario.getCpf());
                                logado = true;
                            } else {
                                System.out.println("Sua conta não foi encontrada");
                                System.out.println("Verifique se sua senha ou CPF estão corretos");
                                proq++;
                            }

                        }

                        break;
                }
            } while (!logado);
        }

        do {
            System.out.println();
            System.out.println("=|===========================|=");
            System.out.println("=| [1] Ver saldo             |=");
            System.out.println("=| [2] Depositar             |=");
            System.out.println("=| [3] Sacar                 |=");
            System.out.println("=| [4] Transferir            |=");
            System.out.println("=| [5] Lista de contas       |=");
            System.out.println("=| [6] Lista de Usuarios     |=");
            System.out.println("=| [7] Buscar Usuario        |=");
            System.out.println("=| [8] Excluir Conta         |=");
            System.out.println("=| [9] Sair                  |=");
            System.out.println("=|===========================|=");
            System.out.print("Opcao: ");
            try {
                opcao = scanner.nextInt();
            } catch (InputMismatchException erro) {
                System.out.println("Entrada inválida. Por favor, informe uma opção numérica válida do menu.");
                scanner.nextLine();
                opcao = 0;
            }

            switch (opcao) {
                case 1:

                    dao.verSaldo(loginUsuario.getCpf());

                    break;
                case 2:
                    double valor = -1;
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

                    } while (valor <= 0);

                    Usuario depositar = dao.buscarConta(loginUsuario.getCpf());


                    if (depositar != null) {
                        validar = dao.depositar(loginUsuario.getCpf(), valor);
                        if (!validar) {
                            System.out.println("Valor inválido");
                        } else {
                            System.out.println("Deposito Realizado com sucesso");
                            System.out.println("Depositado: " + depositar.getNome());
                        }
                    } else {
                        System.out.println("Conta não encontrada");
                    }

                    break;
                case 3:
                    double sacValor = -1;

                    do {
                        //
                        try {
                            System.out.println("Valor:");
                            sacValor = scanner.nextDouble();

                            if (sacValor <= 0) {
                                System.out.println("Valor inválido!");
                            }

                        } catch (InputMismatchException erro) {
                            System.out.println("Erro na digitação! Somente digitos númericos");
                            scanner.nextLine();
                        }
                    } while (sacValor <= 0);
                    Usuario contaUsuario = dao.buscarConta(loginUsuario.getCpf());


                    if (contaUsuario == null) {
                        System.out.println("Conta não encontrada");

                    } else {
                        dao.sacar(loginUsuario.getCpf(), sacValor);
                        System.out.println("Conta: " + contaUsuario.getNome());
                    }

                    break;
                case 4:
                    String destino;
                    double trsValor = -1;
                    boolean cpfValidoDe;
                    scanner.nextLine();
                    //
                    do {
                        System.out.print("Destino: ");
                        destino = scanner.nextLine();
                        cpfValidoDe = Validador.validarCPF(destino);
                        if (!cpfValidoDe) {
                            System.out.println("CPF inválido! Digite o formato correto.");
                        }
                    } while (!cpfValidoDe);
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
                    } while (trsValor <= 0);

                    dao.transferir(loginUsuario.getCpf(), destino, trsValor);


                    break;
                case 5:
                    dao.ListaContas();
                    break;
                case 6:
                    for (Usuario us : usarios) {
                        System.out.println("Usuario: " + us.getNome());
                        System.out.println("CPF: " + us.getCpf());
                        System.out.println("----------------------");
                    }
                    break;
                case 7:
                    String logCpf;
                    scanner.nextLine();
                    do {

                        System.out.println("Digite seu [CPF]: ");
                        logCpf = scanner.nextLine();
                        cpfValido = Validador.validarCPF(logCpf);

                        if (!cpfValido) {
                            System.out.println("CPF inválido! Digite o formato correto.");
                        }
                    } while (!cpfValido);
                    dao.buscarConts(logCpf);
                    break;

                case 8:
                    scanner.nextLine();

                    System.out.println("Senha: ");
                    String delSenha = scanner.nextLine();

                    System.out.print("Tem certeza que quer excluir essa conta? [S/N]: ");
                    String valid = scanner.nextLine();

                    if (valid.equalsIgnoreCase("S")) {
                        dao.delete(loginUsuario.getCpf(), delSenha);
                        loginUsuario = null;
                        sistemaAtivo = true;
                        opcao = 9;

                    }

                    break;
                case 9:
                    System.out.println("Saindo...");
                    sistemaAtivo = false;
                    break;
            }
        } while (opcao != 9);
    }
}
