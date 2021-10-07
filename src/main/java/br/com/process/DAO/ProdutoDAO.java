package br.com.process.DAO;

import br.com.process.conexao.Conexao;
import br.com.process.entidade.Produto;
import br.com.process.uteis.PropriedadeStatus;
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
     *
     * @param produto Entidade a ser adicionar.
     * @return <b>true</b> se a adicionar foi bem sucedida <b>false</b> se não
     * for.
     */
    public static boolean Adicionar(Produto produto) {

        ResultSet rs = null;
        Connection conexao = null;
        PreparedStatement instrucaoSQL = null;

        try {
            conexao = Conexao.abrirConexao();
            instrucaoSQL = conexao.prepareStatement("INSERT INTO Produtos (Nome, Marca, Descricao, Quantidade, V_compra, V_venda, name_img, Statu) VALUES (?,?,?,?,?,?,?,?)");

            instrucaoSQL.setString(1, produto.getNome());
            instrucaoSQL.setString(2, produto.getMarca());
            instrucaoSQL.setString(3, produto.getDescricao());
            instrucaoSQL.setInt(4, produto.getQuantidade());
            instrucaoSQL.setDouble(5, produto.getV_compra());
            instrucaoSQL.setDouble(6, produto.getV_venda());
            instrucaoSQL.setString(7, produto.getName_IMG());
            instrucaoSQL.setBoolean(8, produto.isStatus());

            int linhaAfetadas = instrucaoSQL.executeUpdate();

            /**
             * Seu produto foi adicionar ao banco de dados com sucesso ele
             * prossegue
             */
            if (linhaAfetadas > 0) {

                /**
                 * Produto tem 1 ou mais Tag?
                 */
                if (produto.getTags().size() >= 1) {
                    /**
                     * pega o ID do último produto adicionado ao banco de dados
                     */
                    instrucaoSQL = conexao.prepareStatement("SELECT ID_Produto FROM Produtos ORDER BY ID_Produto DESC LIMIT 1");
                    rs = instrucaoSQL.executeQuery();

                    if (rs.next()) {

                        produto.setId_produto(rs.getInt("ID_Produto"));

                        linhaAfetadas = TagDAO.AdicionarRelacao(produto);
                    }
                }
            }

            return linhaAfetadas > 0;

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

    /**
     * Método para desativar um produto no banco de dados.
     *
     * @param produto Entidade identifica o produto a ser desativar.
     * @param status
     * @return <b>true</b> se a desativar foi bem sucedida <b>false</b> se não
     * for.
     */
    public static boolean MudancaStatus(Produto produto, PropriedadeStatus status) {

        Connection conexao = null;
        PreparedStatement instrucaoSQL = null;

        try {
            conexao = Conexao.abrirConexao();

            if (status == PropriedadeStatus.Ativo) {
                instrucaoSQL = conexao.prepareStatement("UPDATE Produtos SET Statu = 1 WHERE ID_Produto = ?");
            } else {
                instrucaoSQL = conexao.prepareStatement("UPDATE Produtos SET Statu = 0 WHERE ID_Produto = ?");
            }

            instrucaoSQL.setInt(1, produto.getId_produto());

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

    /**
     * método para atualizar os dados do produto.
     *
     * @param produto Entidade a ser atualizar e os demais dados.
     * @param img
     * @return <b>true</b> se a Atualizar foi bem sucedida <b>false</b> se não
     * for.
     */
    public static boolean Atualizar(Produto produto, boolean img) {

        Connection conexao = null;
        PreparedStatement instrucaoSQL = null;

        try {
            conexao = Conexao.abrirConexao();

            if (img) {
                instrucaoSQL = conexao.prepareStatement("UPDATE Produtos SET Nome = ?, Marca = ?, Descricao = ?, Quantidade = ?, V_compra = ?, V_venda = ?, name_img = ? WHERE ID_Produto = ?");

                instrucaoSQL.setString(7, produto.getName_IMG());
                instrucaoSQL.setInt(8, produto.getId_produto());
            } else {
                instrucaoSQL = conexao.prepareStatement("UPDATE Produtos SET Nome = ?, Marca = ?, Descricao = ?, Quantidade = ?, V_compra = ?, V_venda = ? WHERE ID_Produto = ?");

                instrucaoSQL.setInt(7, produto.getId_produto());
            }

            instrucaoSQL.setString(1, produto.getNome());
            instrucaoSQL.setString(2, produto.getMarca());
            instrucaoSQL.setString(3, produto.getDescricao());
            instrucaoSQL.setInt(4, produto.getQuantidade());
            instrucaoSQL.setDouble(5, produto.getV_compra());
            instrucaoSQL.setDouble(6, produto.getV_venda());

            int linhaAfetadas = instrucaoSQL.executeUpdate();

            if (linhaAfetadas > 0) {

                TagDAO.RemoverRelacao(produto);

                if (produto.getTags().size() >= 1) {

                    linhaAfetadas = TagDAO.AdicionarRelacao(produto);
                }
            }

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

    /**
     * método para pegar todos os dados da tabela Estoque no banco de dados.
     *
     * @param status
     * @return Retorna uma <b>List</b> com todas os Produtos<br> se nenhum
     * Produto foram encontrado, retorna uma <b>List</b> vazia.
     */
    public static List<Produto> getEstoque(PropriedadeStatus status) {

        ResultSet rs = null;
        Connection conexao = null;
        PreparedStatement instrucaoSQL = null;

        List<Produto> Estoque = new ArrayList<>();

        try {
            conexao = Conexao.abrirConexao();

            if (status == PropriedadeStatus.Ativo) {
                instrucaoSQL = conexao.prepareStatement("SELECT * FROM Produtos WHERE Statu = 1");
            } else {
                instrucaoSQL = conexao.prepareStatement("SELECT * FROM Produtos");
            }

            rs = instrucaoSQL.executeQuery();

            while (rs.next()) {
                int ID = rs.getInt("ID_Produto");
                String Nome = rs.getString("Nome");
                String Marca = rs.getString("Marca");
                String Descricao = rs.getString("Descricao");
                int QTD = rs.getInt("Quantidade");
                double V_compra = rs.getDouble("V_compra");
                double V_venda = rs.getDouble("V_venda");
                String IMG = rs.getString("name_img");
                boolean Statu = rs.getBoolean("Statu");

                Produto produto = new Produto(ID, Nome, Marca, Descricao, QTD, V_compra, V_venda, IMG, Statu);
                Estoque.add(produto);
            }
            return Estoque;
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

    /**
     * método usado para buscar um Produto de acordo com o nome.
     *
     * @param Produto Entidade que referencia o Produto a ser buscado.
     * @param status
     * @return Retorna uma <b>List</b> com todas os Produtos<br> se nenhum
     * Produto for encontrado, retorna uma <b>List</b> vazia.
     */
    public static List<Produto> BuscarProdutos(Produto Produto, PropriedadeStatus status) {

        ResultSet rs = null;
        Connection conexao = null;
        PreparedStatement instrucaoSQL = null;

        List<Produto> estoque = new ArrayList<>();

        try {
            conexao = Conexao.abrirConexao();

            if (status == PropriedadeStatus.Ativo) {
                instrucaoSQL = conexao.prepareStatement("SELECT * FROM Produtos WHERE Nome LIKE ? AND Statu = 1");
            } else {
                instrucaoSQL = conexao.prepareStatement("SELECT * FROM Produtos WHERE Nome LIKE ?");
            }

            instrucaoSQL.setString(1, "%" + Produto.getNome() + "%");
            rs = instrucaoSQL.executeQuery();

            while (rs.next()) {
                int ID = rs.getInt("ID_Produto");
                String Nome = rs.getString("Nome");
                String Marca = rs.getString("Marca");
                String Descricao = rs.getString("Descricao");
                int QTD = rs.getInt("Quantidade");
                double V_compra = rs.getDouble("V_compra");
                double V_venda = rs.getDouble("V_venda");
                String IMG = rs.getString("name_img");
                boolean Status = rs.getBoolean("Statu");

                Produto produto = new Produto(ID, Nome, Marca, Descricao, QTD, V_compra, V_venda, IMG, Status);
                estoque.add(produto);
            }
            return estoque;
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

    public static Produto getProduto(Produto produto) {

        ResultSet rs = null;
        Connection conexao = null;
        PreparedStatement instrucaoSQL = null;

        try {
            conexao = Conexao.abrirConexao();

            instrucaoSQL = conexao.prepareStatement("SELECT * FROM Produtos WHERE ID_Produto = ?");

            instrucaoSQL.setInt(1, produto.getId_produto());
            rs = instrucaoSQL.executeQuery();

            if (rs.next()) {
                produto.setNome(rs.getString("Nome"));
                produto.setV_venda(rs.getDouble("V_venda"));
                produto.setName_IMG(rs.getString("name_img"));
                produto.setDescricao(rs.getString("Descricao"));
                produto.setV_compra(rs.getDouble("V_compra"));
                produto.setQuantidade(rs.getInt("Quantidade"));
                produto.setMarca(rs.getString("Marca"));
            }

            return produto;

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

    public static List<Produto> ProdutoPagn(PropriedadeStatus status, int numPage) {

        ResultSet rs = null;
        Connection conexao = null;
        PreparedStatement instrucaoSQL = null;

        List<Produto> estoque = new ArrayList<>();

        try {
            conexao = Conexao.abrirConexao();

            if (status == PropriedadeStatus.Ativo) {
                instrucaoSQL = conexao.prepareStatement("SELECT * FROM Produtos WHERE Statu = 1 limit ?,12");
            } else {
                instrucaoSQL = conexao.prepareStatement("SELECT * FROM Produtos WHERE Nome LIKE ?");
            }
            
            instrucaoSQL.setInt(1, numPage * 10);
            
            rs = instrucaoSQL.executeQuery();

            while (rs.next()) {
                int ID = rs.getInt("ID_Produto");
                String Nome = rs.getString("Nome");
                String Marca = rs.getString("Marca");
                String Descricao = rs.getString("Descricao");
                int QTD = rs.getInt("Quantidade");
                double V_compra = rs.getDouble("V_compra");
                double V_venda = rs.getDouble("V_venda");
                String IMG = rs.getString("name_img");
                boolean Status = rs.getBoolean("Statu");

                Produto produto = new Produto(ID, Nome, Marca, Descricao, QTD, V_compra, V_venda, IMG, Status);
                estoque.add(produto);
            }
            return estoque;
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

    public static int QuantProd() {

        ResultSet rs = null;
        Connection conexao = null;
        PreparedStatement instrucaoSQL = null;

        try {
            conexao = Conexao.abrirConexao();
            instrucaoSQL = conexao.prepareStatement("select count(*) as Produtos from Produtos");

            rs = instrucaoSQL.executeQuery();

            if (rs.next()) {

                int quantidade = rs.getInt("Produtos");
                return quantidade;

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
