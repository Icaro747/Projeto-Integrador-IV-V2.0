package br.com.process.entidade;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author Icaro
 */
@ToString @Slf4j
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Use {

    @NotEmpty(message = "Nome vasio")
    @Size(min = 3, max = 50, message = "Tamanho do campo inválido")
    private String nome;
    @NotEmpty(message = "Sobrenome vasio")
    @Size(min = 3, max = 100, message = "Tamanho do campo inválido")
    private String sobrenome;
    @NotEmpty(message = "CPF inválido")
    @Size(min = 11, max = 14, message = "Tamanho do campo inválido")
    private String cpf;
    @NotEmpty(message = "Email vasio")
    @Size(min = 3, max = 100, message = "Tamanho do campo inválido")
    private String email;
    @NotEmpty(message = "Senha vasio")
    @Size(min = 3, max = 200, message = "Tamanho do campo inválido")
    private String senha;
    
}