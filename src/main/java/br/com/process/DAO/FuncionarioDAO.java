package br.com.process.DAO;

import br.com.process.conexao.Conexao;
import br.com.process.entidade.Funcionario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author Icaro
 * @author Vinicius
 */
@Slf4j
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
                funcionario.setCpf(rs.getString("CPF"));
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
            
            instrucaoSQL.setInt(1, funcionario.getId_funcionario());
            rs = instrucaoSQL.executeQuery();

            if (rs.next()) {
                funcionario.setNome(rs.getString("Nome"));
                funcionario.setSobrenome(rs.getString("Sobrenome"));
                funcionario.setEmail(rs.getString("Email"));
                funcionario.setSenha(rs.getString("Senha"));
                funcionario.setCpf(rs.getString("CPF"));
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
            instrucaoSQL.setString(5, funcionario.getCpf());
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
            instrucaoSQL = conexao.prepareStatement("UPDATE Funcionario SET Nome = ?, Sobrenome = ?, Senha = ?, CPF = ?, Atuacao = ? WHERE ID_Funcionario = ?");

            instrucaoSQL.setString(1, funcionario.getNome());
            instrucaoSQL.setString(2, funcionario.getSobrenome());
            instrucaoSQL.setString(3, funcionario.getSenha());
            instrucaoSQL.setString(4, funcionario.getCpf());
            instrucaoSQL.setString(5, funcionario.getAtuacao());
            instrucaoSQL.setInt(6, funcionario.getId_funcionario());
            
            int linhaAfetadas = instrucaoSQL.executeUpdate();
            
            return linhaAfetadas > 0;

        } catch (SQLException e) {
            log.error("SQL ERROR "+e);
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
  
}
