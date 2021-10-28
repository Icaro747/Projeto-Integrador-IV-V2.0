package br.com.process.uteis;

import br.com.process.entidade.Cliente;
import br.com.process.entidade.Funcionario;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Icaro
 */
public class RestrictedAreaAccess {

    public static boolean Funcionario(HttpSession session) {
        try {
            Funcionario fun = (Funcionario) session.getAttribute("Use");
            return fun != null;
        } catch (Exception e) {
            return false;
        }

    }

    public static boolean Cliente(HttpSession session) {
        try {
            Cliente cli = (Cliente) session.getAttribute("Use");
            return cli != null;
        } catch (Exception e) {
            return false;
        }
    }
}
