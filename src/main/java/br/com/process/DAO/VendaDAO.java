package br.com.process.DAO;

import br.com.process.conexao.Conexao;
import br.com.process.entidade.Carrinho;
import br.com.process.entidade.Cliente;
import br.com.process.entidade.Produto;
import br.com.process.entidade.Venda;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author Vinicius
 */
@Slf4j
public class VendaDAO {

    public static List<Venda> ClientePedidos(Cliente cliente) {

        ResultSet rs = null;
        Connection conexao = null;
        PreparedStatement instrucaoSQL = null;

        List<Venda> vendas = new ArrayList<>();

        try {
            conexao = Conexao.abrirConexao();
            instrucaoSQL = conexao.prepareStatement("SELECT * FROM Vendas WHERE FK_Cliente = ?");

            instrucaoSQL.setInt(1, cliente.getId_cliente());

            rs = instrucaoSQL.executeQuery();
            while (rs.next()) {
                int ID_Venda = rs.getInt("ID_Venda");
                Date data_Venda = rs.getDate("data_Venda");
                double V_total = rs.getDouble("V_total");
                double V_frete = rs.getDouble("V_frete");
                String StatusPedido = rs.getString("StatusPedido");

                Venda vend = new Venda(ID_Venda, data_Venda, V_total, V_frete, StatusPedido);
                vendas.add(vend);
            }

            return vendas;
        } catch (SQLException e) {

            log.error("" + e);
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

    public static List<Produto> Detalhes(Venda venda) {

        ResultSet rs = null;
        Connection conexao = null;
        PreparedStatement instrucaoSQL = null;

        List<Produto> produtos = new ArrayList<>();

        try {
            conexao = Conexao.abrirConexao();
            instrucaoSQL = conexao.prepareStatement("SELECT Produtos.Nome AS Nome, Item.Quantidade AS Quantidade,Item.Desconto AS Desconto, Produtos.Marca AS Marca, Item.V_item AS Valor FROM  Item inner join Produtos on Produtos.Id_Produto = Item.FK_Produto WHERE FK_Venda = ?");

            instrucaoSQL.setInt(1, venda.getId_venda());

            rs = instrucaoSQL.executeQuery();
            while (rs.next()) {
                String Nome = rs.getString("Nome");
                int Quantidade = rs.getInt("Quantidade");
                int Desconto = rs.getInt("Desconto");
                String Marca = rs.getString("Marca");
                double Valor = rs.getDouble("Valor");

                Produto prod = new Produto(Nome, Marca, Quantidade, Valor, Desconto);
                produtos.add(prod);
            }

            return produtos;
        } catch (SQLException e) {
            log.error("" + e);
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

    public static int Criar(Venda venda, Cliente cliente, Carrinho carrinho) {

        ResultSet rs = null;
        Connection conexao = null;
        PreparedStatement instrucaoSQL = null;

        try {
            conexao = Conexao.abrirConexao();
            instrucaoSQL = conexao.prepareStatement("INSERT INTO Vendas (data_Venda, V_total, V_frete, StatusPedido, FK_Cliente) VALUES (?,?,?,?,?)");

            instrucaoSQL.setDate(1, venda.getData_venda());
            instrucaoSQL.setDouble(2, 0.0F);
            instrucaoSQL.setDouble(3, carrinho.getPresoEntrega());
            instrucaoSQL.setString(4, "Aguardando pagamento");
            instrucaoSQL.setInt(5, cliente.getId_cliente());

            int linhaAfetadas = instrucaoSQL.executeUpdate();

            if (linhaAfetadas > 0) {

                instrucaoSQL = conexao.prepareStatement("SELECT ID_Venda FROM Vendas ORDER BY ID_Venda DESC LIMIT 1");
                rs = instrucaoSQL.executeQuery();
                linhaAfetadas = 0;

                if (rs.next()) {
                    venda.setId_venda(rs.getInt("ID_Venda"));

                    if (venda.getId_venda() > 0) {
                        for (Produto produto : carrinho.getProdutos()) {
                            if (Item(produto, venda)) {
                                linhaAfetadas++;
                            } else {
                                throw new IllegalArgumentException("Erro ao finalizar seu pedido por favor tente mais tarde novamente");
                            }
                        }
                    }
                }

                return venda.getId_venda();
            } else {
                return -1;
            }
        } catch (SQLException e) {
            log.error("" + e);
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

    public static boolean Item(Produto produto, Venda venda) {

        Connection conexao = null;
        PreparedStatement instrucaoSQL = null;

        try {
            conexao = Conexao.abrirConexao();
            instrucaoSQL = conexao.prepareStatement("INSERT INTO Item (Quantidade, Desconto, V_item, FK_Venda, FK_Produto) VALUES (?,?,?,?,?)");

            instrucaoSQL.setInt(1, produto.getQuantidade());
            instrucaoSQL.setInt(2, produto.getDesconto());
            instrucaoSQL.setDouble(3, produto.getV_venda());
            instrucaoSQL.setInt(4, venda.getId_venda());
            instrucaoSQL.setInt(5, produto.getId_produto());

            int linhaAfetadas = instrucaoSQL.executeUpdate();
            return linhaAfetadas > 0;
        } catch (SQLException e) {
            log.error("" + e);
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
