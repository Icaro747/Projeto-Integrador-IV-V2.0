package br.com.process.DAO;

import br.com.process.conexao.Conexao;
import br.com.process.entidade.Carrinho;
import br.com.process.entidade.Cliente;
import br.com.process.entidade.Endereco;
import br.com.process.entidade.FormaPagamento;
import br.com.process.entidade.Frete;
import br.com.process.entidade.Produto;
import br.com.process.entidade.Venda;
import br.com.process.entidade.VendaDetalhada;

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

    public static VendaDetalhada Detalhes(Venda venda) {

        ResultSet rs = null;
        Connection conexao = null;
        PreparedStatement instrucaoSQL = null;

        try {
            conexao = Conexao.abrirConexao();
            instrucaoSQL = conexao.prepareStatement("SELECT * FROM Vendas WHERE ID_Venda = ?");

            instrucaoSQL.setInt(1, venda.getId_venda());

            rs = instrucaoSQL.executeQuery();
            if (rs.next()) {
                VendaDetalhada vd = new VendaDetalhada(
                    rs.getString("F_pagameto"),
                    rs.getString("Parcela"),
                    rs.getString("Endereco"),
                    venda.getId_venda(),
                    rs.getDate("data_Venda"),
                    rs.getDouble("V_total"),
                    rs.getDouble("V_frete"),
                    rs.getString("StatusPedido")
                );
                vd.setFrete(getEntrega(venda.getId_venda()));
                vd.setItems(getItem(venda.getId_venda()));
                return vd;
            } else {
                throw new IllegalArgumentException("não foi possível encontrar essa venda");
            }
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

    public static int Criar(Venda venda, Cliente cliente, Carrinho carrinho, FormaPagamento pagamento, Frete frete, Endereco endereco) {

        ResultSet rs = null;
        Connection conexao = null;
        PreparedStatement instrucaoSQL = null;

        try {
            conexao = Conexao.abrirConexao();
            instrucaoSQL = conexao.prepareStatement("INSERT INTO Vendas (data_Venda, V_total, V_frete, StatusPedido, F_pagameto, Parcela, FK_Cliente, endereco) VALUES (?,?,?,?,?,?,?,?)");

            instrucaoSQL.setDate(1, venda.getData_venda());
            instrucaoSQL.setDouble(2, carrinho.getPresoEntrega());
            instrucaoSQL.setDouble(3, carrinho.getPresoEntrega());
            instrucaoSQL.setString(4, "Aguardando pagamento");
            instrucaoSQL.setString(5, pagamento.getFormaPg());
            instrucaoSQL.setString(6, pagamento.getCartao());
            instrucaoSQL.setInt(7, cliente.getId_cliente());
            instrucaoSQL.setString(8, endereco.getEndereco()+", "+endereco.getNumero());

            int linhaAfetadas = instrucaoSQL.executeUpdate();
            log.info("venda cadastrada");

            if (linhaAfetadas > 0) {

                instrucaoSQL = conexao.prepareStatement("SELECT ID_Venda FROM Vendas ORDER BY ID_Venda DESC LIMIT 1");
                rs = instrucaoSQL.executeQuery();
                linhaAfetadas = 0;

                if (rs.next()) {
                    venda.setId_venda(rs.getInt("ID_Venda"));

                    if (venda.getId_venda() > 0) {
                        for (Produto produto : carrinho.getProdutos()) {
                            if (setItem(produto, venda)) {
                                linhaAfetadas++;
                            } else {
                                log.error("ERRO 1");
                                throw new IllegalArgumentException("Erro ao finalizar seu pedido por favor tente mais tarde novamente. Erro 1");
                            }
                        }
                        log.info("Items cadastrada");
                        if (!setEntrega(frete, venda)) {
                            log.error("ERRO 2");
                            throw new IllegalArgumentException("Erro ao finalizar seu pedido por favor tente mais tarde novamente. Erro 2");
                        }
                        log.info("Entrega cadastrada");
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

    public static boolean setItem(Produto produto, Venda venda) {

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

    public static List<Produto> getItem(int ID_Vebda) {

        ResultSet rs = null;
        Connection conexao = null;
        PreparedStatement instrucaoSQL = null;

        List<Produto> Items = new ArrayList<>();

        String QUERY = "SELECT Produtos.Nome, Produtos.Marca, Item.Quantidade, Produtos.V_venda, Item.Desconto FROM Item "
                + "INNER JOIN Produtos ON ID_Produto = FK_Produto "
                + "WHERE FK_Venda = ?";

        try {
            conexao = Conexao.abrirConexao();
            instrucaoSQL = conexao.prepareStatement(QUERY);

            instrucaoSQL.setInt(1, ID_Vebda);

            rs = instrucaoSQL.executeQuery();
            while (rs.next()) {
                Produto item = new Produto(
                        rs.getString("Nome"),
                        rs.getString("Marca"),
                        rs.getInt("Quantidade"),
                        rs.getDouble("V_venda"),
                        rs.getInt("Desconto")
                );
                Items.add(item);
            }
            return Items;
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

    public static boolean setEntrega(Frete frete, Venda venda) {

        Connection conexao = null;
        PreparedStatement instrucaoSQL = null;

        try {
            conexao = Conexao.abrirConexao();
            instrucaoSQL = conexao.prepareStatement("INSERT INTO Entrega (Distribuidora, DataEntraga, FK_Venda) VALUES (?,?,?)");

            instrucaoSQL.setString(1, frete.getNome());
            instrucaoSQL.setString(2, frete.getDataEntraga());
            instrucaoSQL.setInt(3, venda.getId_venda());

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

    public static Frete getEntrega(int ID_venda) {

        ResultSet rs = null;
        Connection conexao = null;
        PreparedStatement instrucaoSQL = null;

        try {
            conexao = Conexao.abrirConexao();
            instrucaoSQL = conexao.prepareStatement("SELECT * FROM Entrega WHERE FK_Venda = ?");

            instrucaoSQL.setInt(1, ID_venda);
            rs = instrucaoSQL.executeQuery();
            if (rs.next()) {
                String Distribuidora = rs.getString("Distribuidora");
                String DataEntraga = rs.getString("DataEntraga");
                return new Frete(Distribuidora, DataEntraga);
            } else {
                throw new IllegalArgumentException("erro ao buscar entrega");
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
}
