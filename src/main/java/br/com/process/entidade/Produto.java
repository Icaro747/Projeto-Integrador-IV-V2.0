package br.com.process.entidade;

import java.util.ArrayList;

/**
 *
 * @author Icaro
 */
public class Produto {

    private int id_produto;
    private String nome;
    private String marca;
    private String tamanho;
    private String descricao;
    private int quantidade;
    private double v_compra;
    private double v_venda;
    private String name_IMG;
    private boolean status;
    
    private static double v_vista;
    private static double v_juros3v;
    private final double ValorDescontoVista = 0.15;
    private final double ValorJuros = 0.05;
    private final double QuantidadeParcelaMaxima = 3;
    
    private ArrayList<Integer> tags = new ArrayList<>();

    public Produto() {
    }

    public Produto(int id_produto, String nome, String marca, String descricao, int quantidade, double v_compra, double v_venda, String name_IMG, boolean status) {
        this.id_produto = id_produto;
        this.nome = nome;
        this.marca = marca;
        this.descricao = descricao;
        this.quantidade = quantidade;
        this.v_compra = v_compra;
        this.v_venda = v_venda;
        this.name_IMG = name_IMG;
        this.status = status;
    }
    
    public Produto(int id_produto, String nome, String marca, String tamanho, String descricao, int quantidade, double v_compra, double v_venda, String name_IMG, boolean status) {
        this.id_produto = id_produto;
        this.nome = nome;
        this.marca = marca;
        if (tamanho.length()>2) {
            throw new IllegalArgumentException("Quantidade de caracteres no campo tamanho inválido");
        } else{
            this.tamanho = tamanho;
        }
        this.descricao = descricao;
        this.quantidade = quantidade;
        if (v_compra > 0) {
            this.v_compra = v_compra;
        }else{
            throw new IllegalArgumentException("valor de compra inválido");
        }
        if (v_venda > v_compra) {
            this.v_venda = v_venda;
        }else{
            throw new IllegalArgumentException("valor de venda inválido");
        }
        this.name_IMG = name_IMG;
        this.status = status;
    }
    
    public double getV_vista() {
        return getV_venda() - (getV_venda() * getValorDescontoVista());
    }

    public double getV_juros3v() {
        return (getV_venda() + (getV_venda() * getValorJuros())) / getQuantidadeParcelaMaxima();
    }
    
    public double getValorDescontoVista() {
        return ValorDescontoVista;
    }

    public double getValorJuros() {
        return ValorJuros;
    }
    
    public double getQuantidadeParcelaMaxima() {
        return QuantidadeParcelaMaxima;
    }
    
    public String getName_IMG() {
        return name_IMG;
    }

    public void newName_IMG(String name_IMG) {
        this.name_IMG = "Produto_IMG_" + name_IMG;
    }

    public void setName_IMG(String name_IMG) {
        this.name_IMG = name_IMG;
    }
    
    public ArrayList<Integer> getTags() {
        return tags;
    }

    public void setTags(ArrayList<Integer> tags) {
        this.tags = tags;
    }
    
    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getId_produto() {
        return id_produto;
    }

    public void setId_produto(int id_produto) {
        this.id_produto = id_produto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getTamanho() {
        return tamanho;
    }

    public void setTamanho(String tamanho) {
        if (tamanho.length()>2) {
            throw new IllegalArgumentException("Quantidade de caracteres no campo tamanho inválido");
        } else{
            this.tamanho = tamanho;
        }
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getV_compra() {
        return v_compra;
    }

    public void setV_compra(double v_compra) {
        if (v_compra > 0) {
            this.v_compra = v_compra;
        }else{
            throw new IllegalArgumentException("valor de compra inválido");
        }
    }

    public double getV_venda() {
        return v_venda;
    }

    public void setV_venda(double v_venda) {
        if (v_venda > getV_compra()) {
            this.v_venda = v_venda;
        }else{
            throw new IllegalArgumentException("valor de venda inválido");
        }
    }

    public boolean validarPrecoUnit() {
        return true;
    }

    @Override
    public String toString() {
        return String.format("id: %d <br/> Nome: %s <br/> Marca: %s <br/> Tamanho: %s <br/>"
                + " Descricao: %s <br/>Qtd: %d <br/> Valor de Compra: %f <br/>"
                + "Valor de Venda: %f <br/> Status: %s",
                id_produto, nome, marca, tamanho, descricao, quantidade, v_compra, v_venda, status);
    }
}
