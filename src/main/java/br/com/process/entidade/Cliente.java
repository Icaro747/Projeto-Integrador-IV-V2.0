package br.com.process.entidade;

import br.com.process.uteis.CPF;

import java.sql.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author JoaoDantas
 * @author Icaro
 */
@ToString
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {
    
    private int id_cliente;
    @NotEmpty(message = "Nome vazio")
    @Size(min = 3, max = 50, message = "Tamanho do Nome inválido")
    private String nome;
    @NotEmpty(message = "Sobrenome vazio")
    @Size(min = 3, max = 100, message = "Tamanho do Sobrenome inválido")
    private String sobrenome;
    @NotEmpty(message = "Email vazio")
    @Size(min = 3, max = 100, message = "Tamanho do Email inválido")
    private String email;
    @NotEmpty(message = "Senha vazio")
    @Size(min = 6, max = 200, message = "Tamanho minimo para a senha é de 6 digitos")
    private String senha;
    @NotEmpty(message = "CPF válido")
    private String cpf;
    private Date nascimento;
    private String sexo;
    
  
    /**
     * Construtor
     * @param id_cliente ID do Cliente
     */
    public Cliente (int id_cliente) {
        this.id_cliente = id_cliente;
    }
    
    public void setCpf(String cpf) {
        try{
            if (CPF.Validar_CPF(cpf.replaceAll("\\.","").replaceAll("-",""))) {
                this.cpf = cpf;
            }else{
                this.cpf = "";
            }
        }
        catch (Exception e ){
            System.out.println("Nao foi possível abrir o arquivo para leitura");
        }
    }
}
