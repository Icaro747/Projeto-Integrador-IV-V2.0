package br.com.process.entidade;

import br.com.process.uteis.Formulas;
import java.sql.Date;
import java.util.ArrayList;


/**
 *
 * @author Eduardo
 */
public class Carrinho {

    private int id_carrinho;
    private final ArrayList<Produto> produtos = new ArrayList<>();
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
        int indice = 0;
        for (Produto produto : produtos) {
            produto.setIndice(indice);
            indice++;
            totalVenda += produto.V_QTD();
            produtos.add(produto);
        }
    }
    
    public void addProduto(Produto produto){
        produto.setIndice(produtos.size());
        totalVenda += produto.V_QTD();
        this.produtos.add(produto);
    }
    
    public void RemoveItem(int indice){
        Produto produto = produtos.get(indice);
        totalVenda -= produto.V_QTD();
        this.produtos.remove(produto);
    }
    
    public void AlinaLista(){
        int indic = 0;
        for (Produto produto : produtos) {
            produto.setIndice(indic);
            indic++;
        }
    }
    
    public Produto getProduto(int indice) {
        return produtos.get(indice);
    }
    
    public int getQTD_Produtos(){
        return produtos.size();
    }
    
    public double getTotalVenda() {
        return Formulas.Arredondando(totalVenda, 2);
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
