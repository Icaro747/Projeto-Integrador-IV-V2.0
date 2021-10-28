package br.com.process.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author icaro
 */
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class Response {
    
    private boolean sucesso;
    private int statusCode;
    private String mensagem;
}
