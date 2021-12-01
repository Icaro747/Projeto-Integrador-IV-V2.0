package br.com.process.entidade;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author Vinicius
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Venda {

    private int id_venda;
    private Date data_venda;
    private double v_total;
    private double v_frete;
    private String statusPedido;

    public Venda(int id_venda) {
        this.id_venda = id_venda;
    }
    
    public Venda(int id_venda, String statusPedido) {
        this.id_venda = id_venda;
        this.statusPedido = statusPedido;
    }
    
}
