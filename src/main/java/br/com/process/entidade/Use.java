package br.com.process.entidade;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author Icaro
 */
@ToString
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Use {

    private String nome;
    private String sobrenome;
    private String CPF;
    private String email;
    private String senha;
    
}


