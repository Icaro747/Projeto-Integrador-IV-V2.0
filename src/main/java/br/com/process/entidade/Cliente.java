/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.process.entidade;

import br.com.process.uteis.CPF;
import ch.qos.logback.core.net.server.Client;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 *
 * @author JoaoDantas
 */
public class Cliente {
    private int id_cliente;
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

  
    /**
     * Construtor
     * @param id_cliente ID do Cliente
     */
    public Cliente (int id_cliente) {
        this.id_cliente = id_cliente;;
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
