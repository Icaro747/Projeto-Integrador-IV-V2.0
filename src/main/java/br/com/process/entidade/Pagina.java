package br.com.process.entidade;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author Vinicius
 */
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pagina {

    private int paginas;
    private int pageAtual;
    private int quantidadeItems;
}
