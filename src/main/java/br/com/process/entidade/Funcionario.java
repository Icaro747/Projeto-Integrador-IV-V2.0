package br.com.process.entidade;

import br.com.process.uteis.Formulas;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author Eduardo
 */
@ToString
public class Funcionario {

    @Getter @Setter private int id_funcionario;
    @Getter @Setter private String nome;
    @Getter @Setter private String sobrenome;
    @Getter @Setter private String email;
    @Getter @Setter private String senha;
    @Getter @Setter private String cpf;    
    @Getter @Setter private String atuacao;    
    @Getter @Setter private boolean status;
    @Getter @Setter private int indice;
    
    
    public Funcionario() {
    }
    
    /**
     * Construtor
     * @param id_funcionario ID do Funcionario
     */
    public Funcionario(int id_funcionario) {
        this.id_funcionario = id_funcionario;
    }

    /**
     * Construtor
     * @param id_funcionario ID do Funcionario
     * @param nome Nome do Funcioanrio
     * @param sobrenome Sobrenome do Funcionario
     * @param email E-mail do Funcionario
     * @param senha Senha do Funcionario
     * @param cpf CPF do Funcionario
     * @param atuacao Atuacao do Funcionario
     * @param status Status do funcionario Ativo e desativado
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

    
    @ToString(callSuper=true, includeFieldNames=true)
    public static class Square{
        private final int width, height;

        public Square(int width, int height) {
          this.width = width;
          this.height = height;
        }
    }
}
