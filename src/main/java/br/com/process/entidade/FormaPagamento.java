package br.com.process.entidade;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author Eduardo
 * @author Icaro
 */
@ToString
@NoArgsConstructor
public class FormaPagamento {

    @Getter @Setter private String formaPg;
    @Getter @Setter private String cartao;

    public FormaPagamento(String formaPg, String cartao) {
        this.formaPg = formaPg;
        this.cartao = cartao;
    }
        
}
