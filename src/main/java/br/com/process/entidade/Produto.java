package br.com.process.entidade;

import br.com.process.uteis.Formulas;

import java.util.ArrayList;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

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
    @NotEmpty(message = "Nome vazio")
    @Size(min = 3, max = 150, message = "Tamanho do campo inválido")
    @Getter @Setter private String nome;
    @Size(min = 3, max = 50, message = "Tamanho do campo inválido")
    @Getter @Setter private String marca;
    @Size(min = 1, max = 2, message = "Tamanho do campo inválido")
    @Getter @Setter private String tamanho;
    @Getter @Setter private String genero;
    @Size(min = 3, max = 500, message = "Tamanho do campo inválido")
    @Getter @Setter private String descricao;
    @Min(value = 1, message = "Quantidade inválida")
    @Getter @Setter private int quantidade;
    @Min(value = 1, message = "Valor inválida")
    @Getter @Setter private double v_compra;
    @Min(value = 1, message = "Valor inválida")
    @Getter private double v_venda;
    @Getter @Setter private String ImagemPrincipal;
    @Getter @Setter private boolean status;
    @Getter @Setter private int indice;
    @Getter @Setter private int qtdImg;
    @Getter @Setter private int desconto;

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
     * @param nome       Nome do Produto
     * @param marca      Marca do Produto
     * @param descricao  Descrição do Produto
     * @param quantidade Quatidade do Produto
     * @param v_compra   Valor de compra do Produto
     * @param v_venda    Valor de venda do Produto
     * @param status     Status do produto Ativo e desativado
     */
    public Produto(int id_produto, String nome, String marca, String descricao, int quantidade, double v_compra, double v_venda, boolean status) {
        this.id_produto = id_produto;
        this.nome = nome;
        this.marca = marca;
        this.descricao = descricao;
        this.quantidade = quantidade;
        this.v_compra = v_compra;
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
        double V_juros3v = (v_venda +(v_venda * ValorJuros)) / QuantidadeParcelaMaxima;
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

    public void setV_venda(double v_venda) {
        if (v_venda > v_compra) {
            this.v_venda = v_venda;
        } else {
            this.v_venda = 0;
        }
    }

    @ToString(callSuper=true, includeFieldNames=true)
    public static class Square{
        private final int width, height;

        public Square(int width, int height) {
          this.width = width;
          this.height = height;
        }
    }

    public Produto(String nome, String marca, int quantidade, double v_venda, int desconto) {
        this.nome = nome;
        this.marca = marca;
        this.quantidade = quantidade;
        this.v_venda = v_venda;
        this.desconto = desconto;
    }
    
    
}
