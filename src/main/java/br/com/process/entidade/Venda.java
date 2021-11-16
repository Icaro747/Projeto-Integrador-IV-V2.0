/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.process.entidade;

import java.sql.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Vinicius
 */

@Getter
@Setter
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
        
}