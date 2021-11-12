package br.com.process.DAO;

import br.com.process.conexao.Conexao;
import br.com.process.entidade.Endereco;
import br.com.process.entidade.Cliente;
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
 */
@Slf4j
public class EnderecoDAO {
    
    public static boolean Adicionar(Endereco endereco, Cliente cliente){
        
        Connection conexao = null;
        PreparedStatement instrucaoSQL = null;
        
        try{
            conexao = Conexao.abrirConexao();
            instrucaoSQL = conexao.prepareStatement("INSERT INTO Enderecos (CEP, Endereco, Numero, Complemento, Bairro, Cidade, Estado, Status, Desativado, FK_Cliente) VALUES (?,?,?,?,?,?,?,?,?,?)");
            
            instrucaoSQL.setString(1, endereco.getCep());
            instrucaoSQL.setString(2, endereco.getEndereco());
            instrucaoSQL.setInt(3, endereco.getNumero());
            instrucaoSQL.setString(4, endereco.getComplemento());
            instrucaoSQL.setString(5, endereco.getBairro());
            instrucaoSQL.setString(6, endereco.getCidade());
            instrucaoSQL.setString(7, endereco.getEstado());
            instrucaoSQL.setBoolean(8, endereco.isPrincipalStatus());
            instrucaoSQL.setBoolean(9, false);
            instrucaoSQL.setInt(10, cliente.getId_cliente());
            
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
    
    public static List<Endereco> ClienteEnderecos(Cliente cliente){
        
        ResultSet rs = null;
        Connection conexao = null;
        PreparedStatement instrucaoSQL = null;
        
        List<Endereco> enderecos = new ArrayList<>();

        try {
            conexao = Conexao.abrirConexao();
            instrucaoSQL = conexao.prepareStatement("SELECT * FROM Enderecos WHERE FK_Cliente = ?");
          
            instrucaoSQL.setInt(1, cliente.getId_cliente());

            rs = instrucaoSQL.executeQuery();
            while (rs.next()) {                
                int ID_Endereco = rs.getInt("ID_Endereco");
                String CEP = rs.getString("CEP");
                String Endereco = rs.getString("Endereco");
                int Numero = rs.getInt("Numero");
                String Complemento = rs.getString("Complemento");
                String Bairro = rs.getString("Bairro");
                String Cidade = rs.getString("Cidade");
                String Estado = rs.getString("Estado");
                boolean Status = rs.getBoolean("Status");
                boolean Desativado = rs.getBoolean("Desativado");
                
                Endereco end = new Endereco(ID_Endereco, CEP, Endereco, Numero, Complemento, Bairro, Cidade, Estado, Status, Desativado);
                enderecos.add(end);
            }
            
            return enderecos;
        } catch (SQLException e) {
            log.error(""+e);
            throw new IllegalArgumentException("Erro no banco de dados");
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
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
    
    public static int CheckEnderecos(Cliente cliente){
        
        ResultSet rs = null;
        Connection conexao = null;
        PreparedStatement instrucaoSQL = null;

        try {
            conexao = Conexao.abrirConexao();
            instrucaoSQL = conexao.prepareStatement("SELECT COUNT(*) AS QTD FROM Enderecos WHERE FK_Cliente = ?");
          
            instrucaoSQL.setInt(1, cliente.getId_cliente());

            rs = instrucaoSQL.executeQuery();
            if (rs.next()) {
                if (rs.getInt("QTD") > 0){
                    
                } 
                return rs.getInt("QTD");
            }else{
                return -1;
            }
        } catch (SQLException e) {
            log.error(""+e);
            throw new IllegalArgumentException("Erro no banco de dados");
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
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
    
    /**
     * Método para desativar um Endereço no banco de dados.
     * @param endereco entidade que identifica o Endereço que mudará o status.
     * @param status
     * @return <b>true</b> se a desativar foi bem sucedida <b>false</b> se não for.
     */
    public static boolean MudancaStatus(Endereco endereco, PropriedadeStatus status) {

        Connection conexao = null;
        PreparedStatement instrucaoSQL = null;

        try {
            conexao = Conexao.abrirConexao();

            if (status == PropriedadeStatus.Desativa) {
                instrucaoSQL = conexao.prepareStatement("UPDATE Enderecos SET Desativado = 1 WHERE ID_Endereco = ?");
            } else {
                instrucaoSQL = conexao.prepareStatement("UPDATE Enderecos SET Desativado = 0 WHERE ID_Endereco = ?");
            }

            instrucaoSQL.setInt(1, endereco.getId_Endereco());

            int linhaAfetadas = instrucaoSQL.executeUpdate();
            return linhaAfetadas > 0;
        } catch (SQLException e) {
            log.error(""+e);
            throw new IllegalArgumentException("Erro no banco de dados");
        } finally {
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
    
    
    public static int EnderecoPrincipal (Cliente cliente) {

        Connection conexao = null;
        PreparedStatement instrucaoSQL = null;
        ResultSet rs = null;

        try {
            conexao = Conexao.abrirConexao();

            instrucaoSQL = conexao.prepareStatement("SELECT * FROM Enderecos WHERE FK_Cliente = ? AND Status = 1");
 
            instrucaoSQL.setInt(1, cliente.getId_cliente());
            
            rs = instrucaoSQL.executeQuery();
            
            if(rs.next()){
                
                return rs.getInt("ID_Endereco");
            } else {
                
                return -1;
            }
            
            
        } catch (SQLException e) {
            log.error(""+e);
            throw new IllegalArgumentException("Erro no banco de dados");
        } finally {
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
    
    public static boolean MudancaPrincipal(Endereco endereco, PropriedadeStatus status) {

        Connection conexao = null;
        PreparedStatement instrucaoSQL = null;

        try {
            conexao = Conexao.abrirConexao();

            if (status == PropriedadeStatus.Ativo) {
                instrucaoSQL = conexao.prepareStatement("UPDATE Enderecos SET Status= 1 WHERE ID_Endereco = ?");
            } else {
                instrucaoSQL = conexao.prepareStatement("UPDATE Enderecos SET Status = 0 WHERE ID_Endereco = ?");
            }

            instrucaoSQL.setInt(1, endereco.getId_Endereco());

            int linhaAfetadas = instrucaoSQL.executeUpdate();
            return linhaAfetadas > 0;
        } catch (SQLException e) {
            log.error(""+e);
            throw new IllegalArgumentException("Erro no banco de dados");
        } finally {
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
