package br.com.process.entidade;

import java.sql.Date;
import java.util.ArrayList;


/**
 *
 * @author Eduardo
 */
public class Carrinho {

    private int id_carrinho;
    private ArrayList<Produto> produtos = new ArrayList<>();
    private double totalVenda;
    private Date dataVenda;
    
    public Carrinho() {        
    }

    public int getId_carrinho() {
        return id_carrinho;
    }

    public void setId_carrinho(int id_carrinho) {
        this.id_carrinho = id_carrinho;
    }

    public ArrayList<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(ArrayList<Produto> produtos) {
        this.produtos = produtos;
    }

    public void addProdutos(ArrayList<Produto> produtos){
        produtos.forEach(item->{
            totalVenda += item.getV_venda();
            produtos.add(item);
        });
    }
    
    public void addProduto(Produto produto){
            totalVenda += produto.getV_venda();
            produtos.add(produto);       
    }
    
    public double getTotalVenda() {
        return totalVenda;
    }

    public void setTotalVenda(double totalVenda) {
        this.totalVenda = totalVenda;
    }

    public Date getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(Date dataVenda) {
        this.dataVenda = dataVenda;
    }
    
}
