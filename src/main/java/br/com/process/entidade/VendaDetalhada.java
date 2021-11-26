package br.com.process.entidade;

import java.sql.Date;
import java.util.List;

import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class VendaDetalhada extends Venda {

    private String formPagamento;
    private String parcela;
    private String endereco;
    private Frete frete;
    private List<Produto> Items;

    public VendaDetalhada(String formPagamento, String parcela, String endereco, int id_venda, Date data_venda, double v_total, double v_frete, String statusPedido) {
        super(id_venda, data_venda, v_total, v_frete, statusPedido);
        this.formPagamento = formPagamento;
        this.parcela = parcela;
        this.endereco = endereco;
    }
    
}
