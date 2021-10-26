package br.com.process.DAO;

import br.com.process.conexao.Conexao;
import br.com.process.entidade.Endereco;
import br.com.process.entidade.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author Icaro
 */
@Slf4j
public class EnderecoDAO {
    
    public static boolean Adicionar(Endereco endereco, Cliente cliente){
        
        Connection conexao = null;
        PreparedStatement instrucaoSQL = null;
        
        try{
            conexao = Conexao.abrirConexao();
            instrucaoSQL = conexao.prepareStatement("INSERT INTO Enderecos (CEP, Endereco, Numero, Complemento, Bairro, Cidade, Estado, FK_Cliente) VALUES (?,?,?,?,?,?,?,?)");
            
            instrucaoSQL.setString(1, endereco.getCep());
            instrucaoSQL.setString(2, endereco.getEndereco());
            instrucaoSQL.setInt(3, endereco.getNumero());
            instrucaoSQL.setString(4, endereco.getComplemento());
            instrucaoSQL.setString(5, endereco.getBairro());
            instrucaoSQL.setString(6, endereco.getCidade());
            instrucaoSQL.setString(7, endereco.getEstado());
            instrucaoSQL.setInt(8, cliente.getId_cliente());
            
            int linhaAfetadas = instrucaoSQL.executeUpdate();
            return linhaAfetadas > 0;
        }catch (SQLException e){
            log.error(""+e);
            throw new IllegalArgumentException("Erro no banco de dados");
        }finally{
             try {
                if (instrucaoSQL != null) {
                    instrucaoSQL.close();
                }
                if (conexao != null) {
                    Conexao.fecharConexao();
                }
            } catch (SQLException e) {
            }
        }
    }
    
}
