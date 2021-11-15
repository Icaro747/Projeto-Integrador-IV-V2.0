package br.com.process.entidade;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author Icaro
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Parcela {

    private int indiceVezes;
    private int initJuros;
    private double valor;
    private double juros;
    private String text;

    public Parcela(int indiceVezes, int initJuros, double valor, double juros, String text) {
        this.indiceVezes = indiceVezes;
        this.initJuros = initJuros;
        this.valor = valor;
        this.juros = juros;
        this.text = text;
    }

    public double getValor() {
        if (indiceVezes >= initJuros) {
            return valor + (valor * juros);
        } else {
            return valor;
        }
    }

}
