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

/**
 *
 * @author Icaro
 */
public class ProdutoDAO {
    
    /**
     * método para adicionar os dados do produto no banco de dados.
     * @param produto Entidade a ser adicionar.
     * @return <b>true</b> se a adicionar foi bem sucedida <b>false</b> se não for.
     */
    public static boolean Adicionar(Produto produto){
        
        Connection conexao = null;
        PreparedStatement instrucaoSQL = null;
        
        try{
            conexao = Conexao.abrirConexao();
            instrucaoSQL = conexao.prepareStatement("INSERT INTO Produtos (Nome, Marca, Tamanho, Descricao, Tag, Quantidade, V_compra, V_venda, Statu) VALUES (?,?,?,?,?,?,?,?,?)");
            
            instrucaoSQL.setString(1, produto.getNome());
            instrucaoSQL.setString(2, produto.getMarca());
            instrucaoSQL.setString(3, produto.getTamanho());
            instrucaoSQL.setString(4, produto.getDescricao());
            //instrucaoSQL.setString(5, produto.getTag());
            instrucaoSQL.setInt(6, produto.getQuantidade());
            instrucaoSQL.setDouble(7, produto.getV_compra());
            instrucaoSQL.setDouble(8, produto.getV_venda());
            instrucaoSQL.setBoolean(9, produto.isStatus());
            
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
    
    /**
     * Método para excluir um produto do banco de dados.
     * @param produto Entidade identifica o produto a ser excluído.
     * @return <b>true</b> se a exclusão foi bem sucedida <b>false</b> se não for.
     */
    public static boolean Excluir(Produto produto){
        
        Connection conexao = null;
        PreparedStatement instrucaoSQL = null;
        
        try{
            conexao = Conexao.abrirConexao();
            instrucaoSQL = conexao.prepareStatement("DELETE FROM Produtos WHERE ID_Produto = ?");
            
            instrucaoSQL.setInt(1, produto.getId_produto());
            
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
    
    /**
     * método para atualizar os dados do produto.
     * @param produto Entidade a ser atualizar e os demais dados.
     * @return <b>true</b> se a Atualizar foi bem sucedida <b>false</b> se não for.
     */
    public static boolean Atualizar(Produto produto){
        
        Connection conexao = null;
        PreparedStatement instrucaoSQL = null;
        
        try{
            conexao = Conexao.abrirConexao();
            instrucaoSQL = conexao.prepareStatement("UPDATE Produtos SET Nome = ?, Marca = ?, Tamanho = ?, Descricao = ?, Tag = ?, Quantidade = ?, V_compra = ?, V_venda = ?, Statu = ? WHERE ID_Produto = ?");
            
            instrucaoSQL.setString(1, produto.getNome());
            instrucaoSQL.setString(2, produto.getMarca());
            instrucaoSQL.setString(3, produto.getTamanho());
            instrucaoSQL.setString(4, produto.getDescricao());
            //instrucaoSQL.setString(5, produto.getTag());
            instrucaoSQL.setInt(6, produto.getQuantidade());
            instrucaoSQL.setDouble(7, produto.getV_compra());
            instrucaoSQL.setDouble(8, produto.getV_venda());
            instrucaoSQL.setBoolean(9, produto.isStatus());
            instrucaoSQL.setInt(10, produto.getId_produto());
            
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
    
    /**
    * método para pegar todos os dados da tabela Estoque no banco de dados.
    * @return Retorna uma <b>List</b> com todas os Produtos<br> se nenhum Produto foram encontrado, retorna uma <b>List</b> vazia.
    */
    public static List<Produto> getEstoque(){
        
        ResultSet rs = null;
        Connection conexao = null;
        PreparedStatement instrucaoSQL = null;
        
        List<Produto> Estoque = new ArrayList<>();
        
        try {
            conexao = Conexao.abrirConexao();
            instrucaoSQL = conexao.prepareStatement("SELECT * FROM Produtos");
            rs = instrucaoSQL.executeQuery();
            
            while(rs.next()){
                int ID = rs.getInt("ID_Produto");
                String Nome = rs.getString("Nome");
                String Marca = rs.getString("Marca");
                String Tamanho = rs.getString("Tamanho");
                String Descricao = rs.getString("Descricao");
                int QTD = rs.getInt("Quantidade");
                double V_compra = rs.getDouble("V_compra");
                double V_venda = rs.getDouble("V_venda");
                boolean Statu = rs.getBoolean("Statu");
                
                Produto produto = new Produto(ID, Nome, Marca, Tamanho, Descricao, QTD, V_compra, V_venda, Statu);
                Estoque.add(produto);
            }
            return Estoque;
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
    * método para pegar todos os dados da tabela Estoque no banco de dados.
    * @return Retorna uma <b>List</b> com todas os Produtos<br> se nenhum Produto foram encontrado, retorna uma <b>List</b> vazia.
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
                
                Tag tag = new Tag(ID, Nome);
                Tags.add(tag);
            }
            return Tags;
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
