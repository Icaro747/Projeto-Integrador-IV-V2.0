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
    
    private String nome;
    private String dataEntraga;
    private double valor;

    public Frete(String nome, String dataEntraga, double valor) {
        this.nome = nome;
        this.dataEntraga = dataEntraga;
        this.valor = valor;
    }
}
