package br.com.process.DAO;

import br.com.process.conexao.Conexao;
import br.com.process.entidade.Funcionario;
import br.com.process.entidade.Pagina;
import br.com.process.uteis.PropriedadeStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Icaro
 * @author Vinicius
 */
public class FuncionarioDAO {

    public static boolean CheckFuncionario(Funcionario funcionario){
        
        ResultSet rs = null;
        Connection conexao = null;
        PreparedStatement instrucaoSQL = null;

        try {
            conexao = Conexao.abrirConexao();
            instrucaoSQL = conexao.prepareStatement("SELECT * FROM Funcionario WHERE Email = ?");
          
            instrucaoSQL.setString(1, funcionario.getEmail());

            rs = instrucaoSQL.executeQuery();

            return rs.next();
        } catch (SQLException e) {
            throw new IllegalArgumentException(e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (instrucaoSQL != null) {
                    instrucaoSQL.close();
                }
                if (conexao != null) {
                    conexao.close();
                    Conexao.fecharConexao();
                }
            } catch (SQLException e) {
            }
        }
    }
    
    public static Funcionario getFuncionarioEmail(Funcionario funcionario){
        
        ResultSet rs = null;
        Connection conexao = null;
        PreparedStatement instrucaoSQL = null;

        try {
            conexao = Conexao.abrirConexao();

            instrucaoSQL = conexao.prepareStatement("SELECT * FROM Funcionario WHERE Email = ?");

            instrucaoSQL.setString(1, funcionario.getEmail());
            rs = instrucaoSQL.executeQuery();

            if (rs.next()) {
                funcionario.setNome(rs.getString("Nome"));
                funcionario.setSobrenome(rs.getString("Sobrenome"));
                funcionario.setSenha(rs.getString("Senha"));
                funcionario.setCPF(rs.getString("CPF"));
                funcionario.setAtuacao(rs.getString("Atuacao"));
                funcionario.setStatus(rs.getBoolean("Status"));
            }

            return funcionario;

        } catch (SQLException e) {
            throw new IllegalArgumentException(e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (instrucaoSQL != null) {
                    instrucaoSQL.close();
                }
                if (conexao != null) {
                    conexao.close();
                    Conexao.fecharConexao();
                }
            } catch (SQLException e) {
            }
        }
    }
    
    public static Funcionario getFuncionarioId(Funcionario funcionario){
        
        ResultSet rs = null;
        Connection conexao = null;
        PreparedStatement instrucaoSQL = null;

        try {
            conexao = Conexao.abrirConexao();

            instrucaoSQL = conexao.prepareStatement("SELECT * FROM Funcionario WHERE ID_Funcionario = ?");

            instrucaoSQL.setString(1, funcionario.getEmail());
            rs = instrucaoSQL.executeQuery();

            if (rs.next()) {
                funcionario.setNome(rs.getString("Nome"));
                funcionario.setSobrenome(rs.getString("Sobrenome"));
                funcionario.setSenha(rs.getString("Senha"));
                funcionario.setCPF(rs.getString("CPF"));
                funcionario.setAtuacao(rs.getString("Atuacao"));
                funcionario.setStatus(rs.getBoolean("Status"));
            }

            return funcionario;

        } catch (SQLException e) {
            throw new IllegalArgumentException(e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (instrucaoSQL != null) {
                    instrucaoSQL.close();
                }
                if (conexao != null) {
                    conexao.close();
                    Conexao.fecharConexao();
                }
            } catch (SQLException e) {
            }
        }
    }
    
    public static boolean Adicionar(Funcionario funcionario){
        
        Connection conexao = null;
        PreparedStatement instrucaoSQL = null;
        
        try{
            conexao = Conexao.abrirConexao();
            instrucaoSQL = conexao.prepareStatement("INSERT INTO Funcionario (Nome, Sobrenome, Email, Senha, CPF, Atuacao, Status) VALUES (?,?,?,?,?,?,?)");

            instrucaoSQL.setString(1, funcionario.getNome());
            instrucaoSQL.setString(2, funcionario.getSobrenome());
            instrucaoSQL.setString(3, funcionario.getEmail());
            instrucaoSQL.setString(4, funcionario.getSenha());
            instrucaoSQL.setString(5, funcionario.getCPF());
            instrucaoSQL.setString(6, funcionario.getAtuacao());
            instrucaoSQL.setBoolean(7, funcionario.isStatus());

            int linhaAfetadas = instrucaoSQL.executeUpdate();
            return linhaAfetadas > 0;
        } catch (SQLException e){
            throw new IllegalArgumentException(e.getMessage());
        }finally{
            try {
                if (instrucaoSQL!=null) {
                    instrucaoSQL.close();
                }
                if (conexao!=null) {
                    conexao.close();
                    Conexao.fecharConexao();  
                }
            } catch (SQLException e) {
            }
        }
    }
  
    public static boolean Atualizar(Funcionario funcionario) {

  
        Connection conexao = null;
        PreparedStatement instrucaoSQL = null;

        try {
            conexao = Conexao.abrirConexao();

            instrucaoSQL = conexao.prepareStatement(
                    "UPDATE Funcionario SET Nome = ?, Sobrenome = ?, Senha = ?, CPF = ?, Atuacao = ? WHERE ID_Funcionario = ?");

            instrucaoSQL.setString(1, funcionario.getNome());
            instrucaoSQL.setString(2, funcionario.getSobrenome());
            instrucaoSQL.setString(3, funcionario.getSenha());
            instrucaoSQL.setString(4, funcionario.getCpf());
            instrucaoSQL.setString(5, funcionario.getAtuacao());
            instrucaoSQL.setInt(6, funcionario.getId_funcionario());
            
            int linhaAfetadas = instrucaoSQL.executeUpdate();
            
            return linhaAfetadas > 0;

        } catch (SQLException e) {
            throw new IllegalArgumentException(e.getMessage());            
        } finally {
            try {
                if (instrucaoSQL != null) {
                    instrucaoSQL.close();
                }
                if (conexao != null) {
                    conexao.close();
                    Conexao.fecharConexao();
                }
            } catch (SQLException e) {
            }
        }
                
    }
    
    
    public static List<Funcionario> getUsuario(PropriedadeStatus status){
        
        ResultSet rs = null;
        Connection conexao = null;
        PreparedStatement instrucaoSQL = null;

        List<Funcionario> Usuario = new ArrayList<>();

        try {
            conexao = Conexao.abrirConexao();

            if (status == PropriedadeStatus.Ativo) {
                instrucaoSQL = conexao.prepareStatement("SELECT * FROM Funcionario WHERE Statu = 1");
            }else{
                instrucaoSQL = conexao.prepareStatement("SELECT * FROM Funcionario");
            }

            rs = instrucaoSQL.executeQuery();

            while(rs.next()){
                int ID = rs.getInt("ID_Funcionario");
                String Nome = rs.getString("Nome");
                String Sobrenome = rs.getString("Sobrenome");
                String Email = rs.getString("Email");
                String Senha = rs.getString("Senha");
                String CPF = rs.getString("CPF");
                String Atuacao = rs.getString("Atuacao");
                boolean Status = rs.getBoolean("Status");

                Funcionario funcionario = new Funcionario(ID, Nome, Sobrenome, Email, Senha, CPF, Atuacao, Status);
                Usuario.add(funcionario);
            }
            return Usuario;
        } catch (SQLException e) {
            throw new IllegalArgumentException(e.getMessage());
        }finally{
            try {
                if (rs!=null) {
                    rs.close();
                }
                if (instrucaoSQL!=null) {
                    instrucaoSQL.close();
                }
                if (conexao!=null) {
                    conexao.close();
                    Conexao.fecharConexao();
                }
            } catch (SQLException e) {
            }
        }
    }
        
}
  

