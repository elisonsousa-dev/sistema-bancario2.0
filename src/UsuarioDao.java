import Conexao.Conexao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDao {
    public void cadastrar(Usuario usuario){
        String sql = "insert into dados (nome, cpf, senha, saldo) values (?, ?, ?, ?)";

        try {
            PreparedStatement cad = Conexao.getConexao().prepareStatement(sql);

            cad.setString(1,usuario.getNome());
            cad.setString(2,usuario.getCpf());
            cad.setString(3,usuario.getSenha());
            cad.setDouble(4,usuario.getSaldo());

            cad.execute();
            cad.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public Usuario login(String cpf , String senha){
        String sql = "select *  from  dados where cpf = ? and senha = ?";

        try {
            PreparedStatement bus = Conexao.getConexao().prepareStatement(sql);
            bus.setString(1,cpf);
            bus.setString(2,senha);

            ResultSet log = bus.executeQuery();

            if(log.next()){
                Usuario user = new Usuario();

                  user.setNome(log.getString("nome"));
                  user.setCpf(log.getString("cpf"));
                  user.setSenha(log.getString("senha"));

                  return user;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    public boolean sacar(String cpf, double saldo){
       String sql = "update dados set saldo = saldo - ? where cpf =  ? and  saldo >= ?";

        try {

            PreparedStatement sac = Conexao.getConexao().prepareStatement(sql);
            sac.setDouble(1,saldo);
            sac.setString(2,cpf);
            sac.setDouble(3,saldo);

            int linha = sac.executeUpdate();

            if(linha > 0){
                System.out.println("Saldo atualizado");
                return true;
            }else{
                System.out.println("saldo insuficiente");
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean depositar(String cpf , double saldo){
        String sql = "update dados set saldo = saldo + ? where cpf = ?";

        try {
            PreparedStatement dep = Conexao.getConexao().prepareStatement(sql);

            dep.setDouble(1,saldo);
            dep.setString(2,cpf);

            int linha = dep.executeUpdate();
            if(linha > 0){
                System.out.println("Depositado");
                return true;
            }else{
                System.out.println("Erro");
                return false;
            }

        } catch (SQLException e) {

            throw new RuntimeException(e);
        }
    }
    public Usuario buscarConta(String cpf){
        String sql = "select * from dados where cpf = ?";

        try {
            PreparedStatement busc = Conexao.getConexao().prepareStatement(sql);
            busc.setString(1,cpf);

            ResultSet res = busc.executeQuery();

            if(res.next()){
                Usuario con = new Usuario();

                con.setNome(res.getString("nome"));
                con.setCpf(res.getString("cpf"));
                con.setSenha(res.getString("senha"));
               return con;
            }



        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    public void verSaldo(String cpf){
        String sql = "select nome, saldo from dados where cpf = ?";

        try {
            PreparedStatement ver = Conexao.getConexao().prepareStatement(sql);

            ver.setString(1,cpf);

            ResultSet ex = ver.executeQuery();

            if(ex.next()){
                Double saldo = ex.getDouble("saldo");
                String nome = ex.getString("nome");
                System.out.println("======================");
                System.out.println("Usuário: "+ nome);
                System.out.println("Saldo: R$"+ saldo);
                System.out.println("======================");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void delete(String cpf, String senha){
            String sql = "delete from dados where cpf = ? and senha = ?";

        try {
            PreparedStatement del = Conexao.getConexao().prepareStatement(sql);

            del.setString(1,cpf);
            del.setString(2,senha);

           int linha = del.executeUpdate();

           if(linha > 0){
               System.out.println("Conta excluída! Voltando ao inicio...");
           }else{
               System.out.println("Não foi possivel excluir sua conta!");
           }

            del.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<Usuario> listaUsuario(){
        ArrayList<Usuario> usuarios = new ArrayList<>();
        String sql = "select * from dados";

        try {
            PreparedStatement list = Conexao.getConexao().prepareStatement(sql);
             ResultSet lis = list.executeQuery();

             while (lis.next()){
                 Usuario users = new Usuario();

                 users.setNome(lis.getString("nome"));
                 users.setCpf(lis.getString("cpf"));

                 usuarios.add(users);

             }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return usuarios;
    }
    public void ListaContas(){
        String sql = "select * from dados";

        try {
            PreparedStatement list = Conexao.getConexao().prepareStatement(sql);

            ResultSet lis = list.executeQuery();

            while (lis.next()){
               String nome = lis.getString("nome");
               String cpf = lis.getString("cpf");
               Double saldo =lis.getDouble("saldo");

                System.out.println("Conta: "+ nome);
                System.out.println("CPF: "+ cpf);
                System.out.println("Saldo: R$ "+ saldo);
                System.out.println("==========================");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean transferir(String origem , String destino , double valor){

        Boolean saque = sacar(origem, valor);

        if(!saque){
            return false;
        }

        Boolean dep = depositar(destino, valor);



        if(!dep){
            return false;
        }
            return true;
    }
   public void buscarConts(String cpf){
        String sql = "select * from dados where cpf = ?";

       try {
           PreparedStatement buscc = Conexao.getConexao().prepareStatement(sql);
             buscc.setString(1,cpf);

           ResultSet rs = buscc.executeQuery();

           if(rs.next()){
              String nome = rs.getString("nome");
              String Cpf = rs.getString("cpf");
               System.out.println("========================");
               System.out.println("Nome: "+ nome);
               System.out.println("CPF: "+Cpf);
               System.out.println("========================");

           }
       } catch (SQLException e) {
           throw new RuntimeException(e);
       }
   }

}
