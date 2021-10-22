package br.com.process.entidade;

import br.com.process.uteis.CPF;
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
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Funcionario{
    
    private int id_funcionario;
    @NotEmpty(message = "Nome vazio")
    @Size(min = 3, max = 50, message = "Tamanho do campo inválido")
    private String nome;
    @NotEmpty(message = "Sobrenome vazio")
    @Size(min = 3, max = 100, message = "Tamanho do campo inválido")
    private String sobrenome;
    @NotEmpty(message = "Email vazio")
    @Size(min = 3, max = 100, message = "Tamanho do campo inválido")
    private String email;
    @NotEmpty(message = "Senha vazio")
    @Size(min = 3, max = 200, message = "Tamanho do campo inválido")
    private String senha;
    @NotEmpty(message = "CPF válido")
    private String cpf;
    @NotEmpty(message = "Atuação vazio")
    @Size(min = 3, max = 50, message = "Tamanho do campo inválido")
    private String atuacao;
    private boolean status;
  
    /**
     * Construtor
     * @param id_funcionario ID do Funcionario
     */
    public Funcionario(int id_funcionario) {
        this.id_funcionario = id_funcionario;
    }
    
    public void setCpf(String cpf) {
        try {
            if (CPF.Validar_CPF(cpf.replaceAll("\\.","").replaceAll("-",""))) {
                this.cpf = cpf;
            }else{
                this.cpf = "";
            }
        } catch (Exception e) {
        }
    }
    
}