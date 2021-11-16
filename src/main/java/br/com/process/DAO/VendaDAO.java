/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.process.DAO;

import br.com.process.conexao.Conexao;
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
}
