package br.com.process.DAO;

import br.com.process.conexao.Conexao;
import br.com.process.entidade.Funcionario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Dell
 */
public class FuncionarioDAO {

    /**
     * método para atualizar os dados do funcionario.
     *
     * @param funcionario Entidade a ser atualizar e os demais dados.
     * @return <b>true</b> se a Atualizar foi bem sucedida <b>false</b> se não
     * for.
     */
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
    
    public static Funcionario getFuncionario(Funcionario funcionario) {

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

}
