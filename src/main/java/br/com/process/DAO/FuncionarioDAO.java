package br.com.process.DAO;

import br.com.process.conexao.Conexao;
import br.com.process.entidade.Funcionario;
import br.com.process.uteis.PropriedadeStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
                funcionario.setId_funcionario(rs.getInt("ID_Funcionario"));
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
                funcionario.setId_funcionario(rs.getInt("ID_Funcionario"));
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
            instrucaoSQL.setBoolean(7, true);

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
    
    
    public static List<Funcionario> getUsuarios(PropriedadeStatus status){
        
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
                String CPF = rs.getString("CPF");
                String Atuacao = rs.getString("Atuacao");
                boolean Status = rs.getBoolean("Status");

                Funcionario funcionario = new Funcionario(ID, Nome, Sobrenome, Email, "", CPF, Atuacao, Status);
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
    
    /**
     * Método para desativar um produto no banco de dados.
     * @param funcionario entidade que identifica o funcionário que mudará o status.
     * @param status
     * @return <b>true</b> se a desativar foi bem sucedida <b>false</b> se não for.
     */
    public static boolean MudancaStatus(Funcionario funcionario, PropriedadeStatus status) {

        Connection conexao = null;
        PreparedStatement instrucaoSQL = null;

        try {
            conexao = Conexao.abrirConexao();

            if (status == PropriedadeStatus.Ativo) {
                instrucaoSQL = conexao.prepareStatement("UPDATE Funcionario SET Status = 1 WHERE ID_Funcionario = ?");
            } else {
                instrucaoSQL = conexao.prepareStatement("UPDATE Funcionario SET Status = 0 WHERE ID_Funcionario = ?");
            }

            instrucaoSQL.setInt(1, funcionario.getId_funcionario());

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
    
}