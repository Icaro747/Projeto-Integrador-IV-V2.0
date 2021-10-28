package br.com.process.response;

/**
 *
 * @author Icaro
 */
public class ClienteResponse extends Response{

    public ClienteResponse() {
    }

    public ClienteResponse(boolean sucesso, int statusCode, String mensagem) {
        super(sucesso, statusCode, mensagem);
    }
}
