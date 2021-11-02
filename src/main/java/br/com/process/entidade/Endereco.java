package br.com.process.entidade;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Icaro
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {

    private int id_Endereco;
    @Size(min = 0, max = 9, message = "Tamanho do CEP inválido")
    @NotEmpty(message = "CEP vazio")
    private String cep;
    @Size(min = 0, max = 100, message = "Tamanho do endereco inválido")
    @NotEmpty(message = "Endereço vazio")
    private String endereco;
    @Min(value = 0, message = "O valor deve ser positivo")
    private int numero;
    @Size(min = 0, max = 50, message = "Tamanho do complemento inválido")
    private String complemento;
    @Size(min = 0, max = 50, message = "Tamanho do bairro inválido")
    @NotEmpty(message = "Bairro vazio")
    private String bairro;
    @Size(min = 0, max = 50, message = "Tamanho do cidade inválido")
    @NotEmpty(message = "Cidada vazio")
    private String cidade;
    @Size(min = 0, max = 2, message = "Tamanho do estado inválido")
    @NotEmpty(message = "Estado vazio")
    private String estado;
    private boolean principalStatus;

    public Endereco(int id_Endereco) {
        this.id_Endereco = id_Endereco;
    }

    public String getComplemento() {
        if (complemento == null) {
            return "";
        } else {
            return complemento;
        }
    }
}
