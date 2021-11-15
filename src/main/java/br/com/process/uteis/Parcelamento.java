package br.com.process.uteis;

import br.com.process.entidade.Parcela;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Icaro
 */
@Getter
@Setter
public class Parcelamento {

    private List<Parcela> parcelas = new ArrayList<>();
    private int initJuros;
    private double vJuros;

    /**
     * Construtor
     *
     * @param initJuros início de incidência de juros
     * @param vJuros valor de juros
     */
    public Parcelamento(int initJuros, double vJuros) {
        this.initJuros = initJuros;
        this.vJuros = vJuros;
    }

    public void limpar() {
        parcelas = null;
    }

    /**
     *
     * @param valor valor a ser parcelado
     * @param indise indise da parcela
     * @param valorMin valor mínimo da parcela
     * @param quatidadeMax quantidade máxima de parcela
     * @return
     */
    public double CriarListaParcelamento(double valor, int indise, double valorMin, int quatidadeMax) {
        if ((valor / valorMin) > (indise + 1) && indise < quatidadeMax) {
            String text;
            if (indise < initJuros) {
                text = indise + 1 + "x de " + Formulas.PardaoValorBR(valor / (indise + 1)) + " sem juros";
            } else {
                text = indise + 1 + "x de " + Formulas.PardaoValorBR((valor + (valor * vJuros)) / (indise + 1));
            }
            this.parcelas.add(new Parcela(indise, this.initJuros, valor / (indise + 1), this.vJuros, text));
            return CriarListaParcelamento(valor, indise + 1, valorMin, quatidadeMax);
        } else {
            return valor / indise;
        }
    }
}
