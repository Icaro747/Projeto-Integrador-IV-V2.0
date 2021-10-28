package br.com.process.DAO;

import br.com.process.conexao.Conexao;
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
public class ClienteDAO {
    
    public static boolean CheckCliente(Cliente cliente){
        
        ResultSet rs = null;
        Connection conexao = null;
        PreparedStatement instrucaoSQL = null;

        try {
            conexao = Conexao.abrirConexao();
            instrucaoSQL = conexao.prepareStatement("SELECT * FROM Cliente WHERE Email = ?");
          
            instrucaoSQL.setString(1, cliente.getEmail());

            rs = instrucaoSQL.executeQuery();

            return rs.next();
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
    
    public static Cliente getClienteEmail(Cliente cliente){
        
        ResultSet rs = null;
        Connection conexao = null;
        PreparedStatement instrucaoSQL = null;

        try {
            conexao = Conexao.abrirConexao();
            instrucaoSQL = conexao.prepareStatement("SELECT * FROM Cliente WHERE Email = ?");

            instrucaoSQL.setString(1, cliente.getEmail());
            rs = instrucaoSQL.executeQuery();

            if (rs.next()) {
                cliente.setId_cliente(rs.getInt("ID_Cliente"));
                cliente.setNome(rs.getString("Nome"));
                cliente.setSenha(rs.getString("Senha"));
            }

            return cliente;

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
    
    public static int Adicionar(Cliente cliente){
        
        ResultSet rs = null;
        Connection conexao = null;
        PreparedStatement instrucaoSQL = null;
        
        try{
            conexao = Conexao.abrirConexao();
            instrucaoSQL = conexao.prepareStatement("INSERT INTO Cliente (Nome, Sobrenome, Email, Senha, CPF, nasimeto, sexo) VALUES (?,?,?,?,?,?,?)");
            
            instrucaoSQL.setString(1, cliente.getNome());
            instrucaoSQL.setString(2, cliente.getSobrenome());
            instrucaoSQL.setString(3, cliente.getEmail());
            instrucaoSQL.setString(4, cliente.getSenha());
            instrucaoSQL.setString(5, cliente.getCpf());
            instrucaoSQL.setDate(6, cliente.getNascimento());
            instrucaoSQL.setString(7, cliente.getSexo());
            
            int linhaAfetadas = instrucaoSQL.executeUpdate();
            if (linhaAfetadas > 0){
                
                instrucaoSQL = conexao.prepareStatement("SELECT ID_Cliente FROM Cliente ORDER BY ID_Cliente DESC LIMIT 1");
                rs = instrucaoSQL.executeQuery();
                
                if (rs.next()) {
                    return rs.getInt("ID_Cliente");
                }
            }
            return -1;
        }catch (SQLException e){
            log.error(""+e);
            throw new IllegalArgumentException("Erro no banco de dados");
        }finally{
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
    
    public static boolean CheckEmail(Cliente cliente){
        
        ResultSet rs = null;
        Connection conexao = null;
        PreparedStatement instrucaoSQL = null;
        
        try {
            conexao = Conexao.abrirConexao();
            instrucaoSQL = conexao.prepareStatement("SELECT * FROM Cliente WHERE Email = ?");
            
            instrucaoSQL.setString(1, cliente.getEmail());
            rs = instrucaoSQL.executeQuery();
            
            return rs.next();
            
        } catch (SQLException e) {
            log.error(""+e);
            throw new IllegalArgumentException("Erro no banco de dados");
        } finally{
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
    
    public static boolean CheckCPF(Cliente cliente){
        
        ResultSet rs = null;
        Connection conexao = null;
        PreparedStatement instrucaoSQL = null;
        
        try {
            conexao = Conexao.abrirConexao();
            instrucaoSQL = conexao.prepareStatement("SELECT * FROM Cliente WHERE CPF = ?");
            
            instrucaoSQL.setString(1, cliente.getCpf());
            rs = instrucaoSQL.executeQuery();
            
            return rs.next();
            
        } catch (SQLException e) {
            log.error(""+e);
            throw new IllegalArgumentException("Erro no banco de dados");
        } finally{
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
    
    public static boolean Atualizar(Cliente cliente) {
        Connection conexao = null;
        PreparedStatement instrucaoSQL = null;
        
        
        try{
            conexao = Conexao.abrirConexao();
            instrucaoSQL = conexao.prepareStatement("UPDATE Cliente SET Nome = ?,Sobrenome = ?,Email = ? ,CPF = ?,Nascimento = ?,Sexo = ? WHERE ID_Cliente = ?");
            
            instrucaoSQL.setString(1,cliente.getNome());
            instrucaoSQL.setString(2,cliente.getSobrenome());
            instrucaoSQL.setString(3,cliente.getEmail());
            instrucaoSQL.setString(4,cliente.getCpf());
            instrucaoSQL.setDate(5,cliente.getNascimento());
            instrucaoSQL.setString(6,cliente.getSexo());
            instrucaoSQL.setInt(6,cliente.getId_cliente());
        
        
            int linhaAfetadas = instrucaoSQL.executeUpdate();
            
            return  linhaAfetadas > 0;   
       }catch(SQLException e ){
           
           log.error(""+e);
           throw  new IllegalArgumentException("Erro no banco de dados");
       }finally{
            try{
                if(instrucaoSQL != null){
                    instrucaoSQL.close();
                }
                if(conexao != null){
                    Conexao.fecharConexao();
                }
            } catch(SQLException e){
                
            }
        }
    }

    /*public static Cliente getClienteId(Cliente cliente) {
        
    }*/
}
