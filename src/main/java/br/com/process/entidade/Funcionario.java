package br.com.process.entidade;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author Vinicius
 */
@ToString
public class Funcionario {
    
    @Getter @Setter private int id_funcionario;
    @NotEmpty(message = "Nome vasio")
    @Size(min = 3, max = 50, message = "Tamanho do campo inválido")
    @Getter @Setter private String nome;
    @NotEmpty(message = "Sobrenome vasio")
    @Size(min = 3, max = 100, message = "Tamanho do campo inválido")
    @Getter @Setter private String sobrenome;
    @NotEmpty(message = "Email vasio")
    @Size(min = 3, max = 100, message = "Tamanho do campo inválido")
    @Getter @Setter private String email;
    @NotEmpty(message = "Senha vasio")
    @Size(min = 3, max = 200, message = "Tamanho do campo inválido")
    @Getter @Setter private String senha;
    @NotEmpty(message = "CPF vasio")
    @Size(min = 11, max = 11, message = "Tamanho do campo inválido")
    @Getter @Setter private String cpf;
    @NotEmpty(message = "Atuação vasio")
    @Size(min = 3, max = 50, message = "Tamanho do campo inválido")
    @Getter @Setter private String atuacao;
    @Getter @Setter private boolean status;

    public Funcionario() {
    }

        
    /**
     * Construtor
     * @param id_funcionario  ID do Funcionario
     */
    public Funcionario(int id_funcionario) {
        this.id_funcionario = id_funcionario;
    }

    /**
     * Construtor
     * @param id_funcionario  ID do Funcionario
     * @param nome            Nome do Funcionario
     * @param sobrenome       Sobrenome do Funcionario
     * @param email           Email do Funcionario
     * @param senha           Senha do Funcionario
     * @param cpf             CPF do Funcionario
     * @param atuacao         Cargo do Funcionario
     * @param status          Status do funcionario Ativo e desativado
     */
    public Funcionario(int id_funcionario, String nome, String sobrenome, String email, String senha, String cpf, String atuacao, boolean status) {
        this.id_funcionario = id_funcionario;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.email = email;
        this.senha = senha;
        this.cpf = cpf;
        this.atuacao = atuacao;
        this.status = status;
    }
        
}
