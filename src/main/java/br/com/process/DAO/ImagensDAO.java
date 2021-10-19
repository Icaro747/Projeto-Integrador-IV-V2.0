package br.com.process.DAO;

import br.com.process.conexao.Conexao;
import br.com.process.entidade.Imagen;
import br.com.process.entidade.Produto;

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
public class ImagensDAO {

    /**
     *
     * @param img
     * @return
     */
    public static boolean Adicionar(Imagen img) {

        Connection conexao = null;
        PreparedStatement instrucaoSQL = null;

        try {

            conexao = Conexao.abrirConexao();
            instrucaoSQL = conexao.prepareStatement("INSERT INTO Pruduto_Imagens (name_img , FK_Produto) VALUES (?,?)");

            instrucaoSQL.setString(1, img.getName());
            instrucaoSQL.setInt(2, img.getId_produto());

            int linhaAfetadas = instrucaoSQL.executeUpdate();

            return linhaAfetadas > 0;

        } catch (SQLException e) {
            throw new IllegalArgumentException("Erro no banco de dados\n" + e.getMessage());
        } finally {
            try {
                if (instrucaoSQL != null) {
                    instrucaoSQL.close();
                }
                Conexao.fecharConexao();
            } catch (SQLException e) {
            }
        }
    }
    
    /**
     *
     * @param produto
     * @return
     */
    public static List<Imagen> getImgs(Produto produto) {

        ResultSet rs = null;
        Connection conexao = null;
        PreparedStatement instrucaoSQL = null;

        List<Imagen> imgs = new ArrayList<>();

        try {
            conexao = Conexao.abrirConexao();

            instrucaoSQL = conexao.prepareStatement("SELECT * FROM Pruduto_Imagens WHERE FK_Produto = ?");
            instrucaoSQL.setInt(1, produto.getId_produto());

            rs = instrucaoSQL.executeQuery();

            while (rs.next()) {
                int ID = rs.getInt("ID_Imagen");
                int ID_produto = rs.getInt("FK_Produto");
                String Nome = rs.getString("name_img");

                Imagen img = new Imagen(ID, Nome, ID_produto);
                imgs.add(img);
            }
            return imgs;
        } catch (SQLException e) {
            throw new IllegalArgumentException("Erro no banco de dados\n" + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (instrucaoSQL != null) {
                    instrucaoSQL.close();
                }
                Conexao.fecharConexao();
            } catch (SQLException e) {
            }
        }
    }
    
    /**
     *
     * @param imagen
     * @return
     */
    public static List<Imagen> getImgs(Imagen imagen) {

        ResultSet rs = null;
        Connection conexao = null;
        PreparedStatement instrucaoSQL = null;

        List<Imagen> imgs = new ArrayList<>();

        try {
            conexao = Conexao.abrirConexao();

            instrucaoSQL = conexao.prepareStatement("SELECT * FROM Pruduto_Imagens WHERE FK_Produto = ?");
            instrucaoSQL.setInt(1, imagen.getId_produto());

            rs = instrucaoSQL.executeQuery();

            while (rs.next()) {
                int ID = rs.getInt("ID_Imagen");
                int ID_produto = rs.getInt("FK_Produto ");
                String Nome = rs.getString("name_img");

                Imagen img = new Imagen(ID, Nome, ID_produto);
                imgs.add(img);
            }
            return imgs;
        } catch (SQLException e) {
            throw new IllegalArgumentException("Erro no banco de dados\n" + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (instrucaoSQL != null) {
                    instrucaoSQL.close();
                }
                Conexao.fecharConexao();
            } catch (SQLException e) {
            }
        }
    }
    
    /**
     *
     * @param produto
     * @return
     */
    public static String getImagemPrincipal(Produto produto) {

        ResultSet rs = null;
        Connection conexao = null;
        PreparedStatement instrucaoSQL = null;

        try {
            conexao = Conexao.abrirConexao();

            instrucaoSQL = conexao.prepareStatement("SELECT * FROM Pruduto_Imagens WHERE FK_Produto = ? LIMIT 1");
            
            instrucaoSQL.setInt(1, produto.getId_produto());
            rs = instrucaoSQL.executeQuery();
            
            String Nome = null;
            
            if (rs.next()) {
                Nome = rs.getString("name_img");
            }
            
            return Nome;
            
        } catch (SQLException e) {
            throw new IllegalArgumentException("Erro no banco de dados\n" + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (instrucaoSQL != null) {
                    instrucaoSQL.close();
                }
                Conexao.fecharConexao();
            } catch (SQLException e) {
            }
        }
    }

    public static boolean Excluir(Imagen imagen) {

        Connection conexao = null;
        PreparedStatement instrucaoSQL = null;

        try {
            conexao = Conexao.abrirConexao();

            instrucaoSQL = conexao.prepareStatement("DELETE FROM Pruduto_Imagens WHERE ID_Imagen = ?");
            instrucaoSQL.setInt(1, imagen.getId());

            int linhaAfetadas = instrucaoSQL.executeUpdate();

            return linhaAfetadas > 0;

        } catch (SQLException e) {
            throw new IllegalArgumentException("Erro no banco de dados\n" + e.getMessage());
        } finally {
            try {
                if (instrucaoSQL != null) {
                    instrucaoSQL.close();
                }
                Conexao.fecharConexao();
            } catch (SQLException e) {
            }
        }
    }
}
