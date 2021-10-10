package br.com.process.entidade;

import br.com.process.uteis.Formulas;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author Icaro
 */
@ToString
public class Produto {

    @Getter @Setter private int id_produto;
    @Getter @Setter private String nome;
    @Getter @Setter private String marca;
    @Getter private String tamanho;
    @Getter @Setter private String genero;
    @Getter @Setter private String descricao;
    @Getter private int quantidade;
    @Getter private double v_compra;
    @Getter private double v_venda;
    @Getter @Setter private String ImagemPrincipal;
    @Getter @Setter private boolean status;
    @Getter @Setter private int indice;
    @Getter @Setter private int qtdImg;

    private final double ValorDescontoVista = 0.15;
    private final double ValorJuros = 0.05;
    @Getter private final double QuantidadeParcelaMaxima = 3;
    
    @Getter @Setter private ArrayList<Integer> tags = new ArrayList<>();

    public Produto() {
    }
    
    /**
     * Construtor
     * @param id_produto ID do Produto
     */
    public Produto(int id_produto) {
        this.id_produto = id_produto;
    }

    /**
     * Construtor
     * @param id_produto ID do Produto
     * @param nome Nome do Produto
     * @param marca Marca do Produto
     * @param descricao Descrição do Produto
     * @param quantidade Quatidade do Produto
     * @param v_compra Valor de compra do Produto
     * @param v_venda Valor de venda do Produto
     * @param status Status do produto Ativo e desativado
     */
    public Produto(int id_produto, String nome, String marca, String descricao, int quantidade, double v_compra, double v_venda, boolean status) {
        this.id_produto = id_produto;
        this.nome = nome;
        this.marca = marca;
        this.descricao = descricao;
        if (quantidade > 0) {
            this.quantidade = quantidade;
        } else {
            throw new IllegalArgumentException("quantidade de produto inválida");
        }
        if (v_compra > 0) {
            this.v_compra = v_compra;
        } else {
            throw new IllegalArgumentException("valor de compra inválido");
        }
        if (v_venda > v_compra) {
            this.v_venda = v_venda;
        } else {
            throw new IllegalArgumentException("valor de venda inválido");
        }
        this.status = status;
    }

    /**
     * Retorna O valor de venda x quantidade
     * @return <b>double</b> Valor de venda * Quantidade
     */
    public double V_QTD() {
        return v_venda * quantidade;
    }

    /**
     * 
     * @return Valor de venda - (Valor de venda *  porcentagem de desconto à vista)
     */
    public double getV_vista() {
        double V_vista = v_venda - (v_venda * ValorDescontoVista);
        return Formulas.Arredondando(V_vista, 2);
    }

    public double getV_juros3v() {
        double V_juros3v = (v_venda + (v_venda * ValorJuros)) / ValorDescontoVista;
        return Formulas.Arredondando(V_juros3v, 2);
    }

    public double getValorDescontoVista() {
        return ValorDescontoVista * 100;
    }

    public double getValorJuros() {
        return ValorJuros * 100;
    }

    public double getValorTotal() {
        return Formulas.Arredondando(v_venda * quantidade, 2);
    }
    
    public void setTamanho(String tamanho) {
        if (tamanho.length() > 2) {
            throw new IllegalArgumentException("Quantidade de caracteres no campo tamanho inválido");
        } else {
            this.tamanho = tamanho;
        }
    }

    public void setQuantidade(int quantidade) {
        if (quantidade > 0) {
            this.quantidade = quantidade;
        } else {
            throw new IllegalArgumentException("quantidade de produto inválida");
        }
    }

    public void setV_compra(double v_compra) {
        if (v_compra > 0) {
            this.v_compra = v_compra;
        } else {
            throw new IllegalArgumentException("valor de compra inválido");
        }
    }

    public void setV_venda(double v_venda) {
        if (v_venda > v_compra) {
            this.v_venda = v_venda;
        } else {
            throw new IllegalArgumentException("valor de venda inválido");
        }
    }
}
