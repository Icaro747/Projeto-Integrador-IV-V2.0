package br.com.process.entidade;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Vinicius
 * @author Icaro
 */
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Funcionario extends Use{
    
    private int id_funcionario;
    @NotEmpty(message = "Nome vasio")
    @Size(min = 3, max = 50, message = "Tamanho do campo inválido")
    private String nome;
    @NotEmpty(message = "Sobrenome vasio")
    @Size(min = 3, max = 100, message = "Tamanho do campo inválido")
    private String sobrenome;
    @NotEmpty(message = "Email vasio")
    @Size(min = 3, max = 100, message = "Tamanho do campo inválido")
    private String email;
    @NotEmpty(message = "Senha vasio")
    @Size(min = 3, max = 200, message = "Tamanho do campo inválido")
    private String senha;
    @NotEmpty(message = "CPF vasio")
    @Size(min = 11, max = 11, message = "Tamanho do campo inválido")
    private String cpf;
    @NotEmpty(message = "Atuação vasio")
    @Size(min = 3, max = 50, message = "Tamanho do campo inválido")
    private String atuacao;
    private boolean status;
  
    /**
     * Construtor
     * @param id_funcionario  ID do Funcionario
     */
    public Funcionario(int id_funcionario) {
        this.id_funcionario = id_funcionario;
    }

    /**
     * 
     * @param id_funcionario ID do Funcionario
     * @param atuacao        Atuação do Funcionario
     * @param status         Status do Funcionario
     * @param nome           Nome do Funcionario
     * @param sobrenome      Sobrenome do Funcionario
     * @param CPF            CPF do Funcionario
     * @param email          Email do Funcionario
     * @param senha          Senha do Funcionario
     */
    public Funcionario(int id_funcionario, String atuacao, boolean status, String nome, String sobrenome, String CPF, String email, String senha) {
        super(nome, sobrenome, CPF, email, senha);
        this.id_funcionario = id_funcionario;
        this.atuacao = atuacao;
        this.status = status;
    }
    
}