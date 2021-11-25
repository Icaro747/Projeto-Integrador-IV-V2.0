package br.com.process.entidade;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author Icaro
 */
@ToString
@Getter @Setter
public class Frete {
    
    private int indice;
    private String nome;
    private String dataEntraga;
    private double valor;

    public Frete(String nome, String dataEntraga, double valor) {
        this.nome = nome;
        this.dataEntraga = dataEntraga;
        this.valor = valor;
    }
    
    public Frete(int indice, String nome, String dataEntraga, double valor) {
        this.indice = indice;
        this.nome = nome;
        this.dataEntraga = dataEntraga;
        this.valor = valor;
    }

    public Frete(String nome, String dataEntraga) {
        this.nome = nome;
        this.dataEntraga = dataEntraga;
    }
    
}
