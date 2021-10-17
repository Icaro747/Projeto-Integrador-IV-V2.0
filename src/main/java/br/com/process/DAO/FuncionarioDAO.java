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
 * @author Eduardo
 */
public class FuncionarioDAO {

    /**
     * método para adicionar os dados do funcionario no banco de dados.
     *
     * @param funcionario Entidade a ser adicionar.
     * @return <b>true</b> se a adicionar foi bem sucedida <b>false</b> se não for.
     */
    public static int Adicionar(Funcionario funcionario) {

        ResultSet rs = null;
        Connection conexao = null;
        PreparedStatement instrucaoSQL = null;

        try {
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

            /**
             * Se o funcionario for adicionado ao banco de dados com sucesso ele prossegue
             */
            if (linhaAfetadas > 0) {
                
                /**
                 * pega o ID do último produto adicionado ao banco de dados
                 */
                instrucaoSQL = conexao.prepareStatement("SELECT ID_Funcionario FROM Funcionario ORDER BY ID_Funcionario DESC LIMIT 1");
                rs = instrucaoSQL.executeQuery();

                if (rs.next()) {

                    funcionario.setId_funcionario(rs.getInt("ID_Funcionario"));
                    
                }

            }

            if (linhaAfetadas > 0) {
                return funcionario.getId_funcionario();
            } else {
                return -1;
            }

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
