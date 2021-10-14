package br.com.process.entidade;

import br.com.process.uteis.Formulas;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author Eduardo
 * @author Icaro
 */
@ToString
@NoArgsConstructor
public class Carrinho {

    @Getter @Setter private int id_carrinho;
    @Getter private final ArrayList<Produto> produtos = new ArrayList<>();
    @Setter private double totalVenda;
    @Getter @Setter private Date dataVenda;

    public void setProdutos(ArrayList<Produto> produtos) {
        int indice = 0;
        for (Produto produto : produtos) {
            produto.setIndice(indice);
            indice++;
            totalVenda += produto.V_QTD();
            produtos.add(produto);
        }
    }
    
    public void AddNewProduto(Produto produto) {
        produto.setIndice(produtos.size());
        totalVenda += produto.V_QTD();
        this.produtos.add(produto);
    }
    
    public void AddProduto(Produto produto){
        totalVenda += produto.V_QTD();
        this.produtos.add(produto);
        OrderLista();
    }
    
    public void RemoveItem(int indice){
        Produto produto = produtos.get(indice);
        totalVenda -= produto.V_QTD();
        this.produtos.remove(produto);
    }
    
    public void OrderLista(){
        Collections.sort (produtos, (Produto p1, Produto p2) -> {
            return p1.getIndice() < p2.getIndice() ? -1 : (p1.getIndice() > p2.getIndice() ? +1 : 0);
        });
    }
    
    public void AlignLista() {
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
    
}
