package br.com.process.DAO;

import br.com.process.conexao.Conexao;
import br.com.process.entidade.Produto;
import br.com.process.entidade.Tag;

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
public class TagDAO {
    
    public static boolean Adicionar(Tag tag){
        
        Connection conexao = null;
        PreparedStatement instrucaoSQL = null;
        
        try{
            conexao = Conexao.abrirConexao();
            instrucaoSQL = conexao.prepareStatement("INSERT INTO Tags (Nome_tag, IMG) VALUES (?,?)");
            
            instrucaoSQL.setString(1, tag.getNome_tag());
            instrucaoSQL.setString(2, tag.getName_img());
            
            int linhaAfetadas = instrucaoSQL.executeUpdate();
            
            return linhaAfetadas > 0;
            
        } catch (SQLException e){
            log.error(""+e);
            throw new IllegalArgumentException("Erro no banco de dados");
        } finally {
            try {
                if (instrucaoSQL!=null) {
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
    * método para pegar todos os dados da tabela Tasg no banco de dados.
    * @return Retorna uma <b>List</b> com todas os Tags<br> se nenhum Tag foram encontrado, retorna uma <b>List</b> vazia.
    */
    public static List<Tag> getTags(){
        
        ResultSet rs = null;
        Connection conexao = null;
        PreparedStatement instrucaoSQL = null;
        
        List<Tag> Tags = new ArrayList<>();
        
        try {
            conexao = Conexao.abrirConexao();
            instrucaoSQL = conexao.prepareStatement("SELECT * FROM Tags");
            rs = instrucaoSQL.executeQuery();
            
            while(rs.next()){
                int ID = rs.getInt("ID_Tag");
                String Nome = rs.getString("Nome_tag");
                String IMG = rs.getString("IMG");
                
                Tag tag = new Tag(ID, Nome, IMG);
                Tags.add(tag);
            }
            return Tags;
        } catch (SQLException e) {
            log.error(""+e);
            throw new IllegalArgumentException("Erro no banco de dados");
        } finally {
            try {
                if (rs!=null) {
                    rs.close();
                }
                if (instrucaoSQL!=null) {
                    instrucaoSQL.close();
                }
                if (conexao != null) {
                    Conexao.fecharConexao();
                }
            } catch (SQLException e) {
            }
        }
    }
    
    public static int AdicionarRelacao(Produto produto){
        
        Connection conexao = null;
        PreparedStatement instrucaoSQL = null;
        
        try {
            conexao = Conexao.abrirConexao();
            
            int linhaAfetadas = 0;
            
            for (Integer tag : produto.getTags()) {
                instrucaoSQL = conexao.prepareStatement("INSERT INTO Relacao_Produtos_Tags (FK_Produto, FK_Tag) VALUES (?,?)");

                instrucaoSQL.setInt(1, produto.getId_produto());
                instrucaoSQL.setInt(2, tag);

                linhaAfetadas += instrucaoSQL.executeUpdate();
            }
            
            return linhaAfetadas;
            
        } catch (SQLException e) {
            log.error(""+e);
            throw new IllegalArgumentException("Erro no banco de dados");
        } finally {
            try {
                if (instrucaoSQL!=null) {
                    instrucaoSQL.close();
                }
                if (conexao != null) {
                    Conexao.fecharConexao();
                }
            } catch (SQLException e) {
            }
        }
    }
    
    public static boolean RemoverRelacao(Produto produto) {
        
        Connection conexao = null;
        PreparedStatement instrucaoSQL = null;
        
        try {
            conexao = Conexao.abrirConexao();
            
            instrucaoSQL = conexao.prepareStatement("DELETE FROM Relacao_Produtos_Tags WHERE FK_Produto = ?");
            instrucaoSQL.setInt(1, produto.getId_produto());

            int linhaAfetadas = instrucaoSQL.executeUpdate();
            
            return linhaAfetadas > 0;
            
        } catch (SQLException e) {
            log.error(""+e);
            throw new IllegalArgumentException("Erro no banco de dados");
        } finally {
            try {
                if (instrucaoSQL!=null) {
                    instrucaoSQL.close();
                }
                if (conexao != null) {
                    Conexao.fecharConexao();
                }
            } catch (SQLException e) {
            }
        }
    }
    
    public static boolean Excluir(Tag tag){
        
        Connection conexao = null;
        PreparedStatement instrucaoSQL = null;
        
        try {
            conexao = Conexao.abrirConexao();
            
            instrucaoSQL = conexao.prepareStatement("DELETE FROM Tags WHERE ID_Tag = ?");
            instrucaoSQL.setInt(1, tag.getId_tag());
            
            int linhaAfetadas = instrucaoSQL.executeUpdate();
            
            if (linhaAfetadas > 0) {
                instrucaoSQL = conexao.prepareStatement("DELETE FROM Relacao_Produtos_Tags WHERE FK_Tag = ?");
                instrucaoSQL.setInt(1, tag.getId_tag());
                linhaAfetadas += instrucaoSQL.executeUpdate();
            }
            
            return linhaAfetadas > 0;
            
        } catch (SQLException e) {
            log.error(""+e);
            throw new IllegalArgumentException("Erro no banco de dados");
        } finally {
            try {
                if (instrucaoSQL!=null) {
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
