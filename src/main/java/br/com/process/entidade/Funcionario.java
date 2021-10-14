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
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Funcionario extends Use{
    
    private int id_funcionario;
    private String atuacao;
    private boolean status;

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