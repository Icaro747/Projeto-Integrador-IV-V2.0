package br.com.process.uteis;

import br.com.process.entidade.Cliente;
import br.com.process.entidade.Funcionario;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Uffa
 */
public class RestrictedAreaAccess {
    
    public static boolean Funcionario(HttpSession session){
        Funcionario fun = (Funcionario) session.getAttribute("Use");
        return fun != null;
    }
    
    public static boolean Cliente(HttpSession session){
        Cliente cli = (Cliente) session.getAttribute("Use");
        return cli != null;
    }
}
