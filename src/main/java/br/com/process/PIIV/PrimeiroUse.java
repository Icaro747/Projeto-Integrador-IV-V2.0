package br.com.process.PIIV;

import br.com.process.DAO.FuncionarioDAO;
import br.com.process.entidade.Funcionario;
import br.com.process.uteis.Crypto;

/**
 *
 * @author Icaro
 */
public class PrimeiroUse {
    public static void main(String[] args) {
        Funcionario fun = new Funcionario(5, "Eduardo", "Silva", "eduardo1@newman.com", "eduardo123", "41156286859", "Admin", true);
        fun.setSenha(Crypto.HashSenha(fun.getSenha()));
        cadatro(fun);
//        System.out.println("Test de Senha");
//        System.out.println("senha:"+fun.getSenha());
//        String n1 = fun.getSenha();
//        fun.setSenha(Crypto.HashSenha(fun.getSenha()));
//        System.out.println(fun.getSenha());
//        System.out.println("--------------------------------------------------");
//        cadatro(fun);
//        Funcionario fun2 = FuncionarioDAO.getFuncionarioEmail(fun);
//        System.out.println(fun2.getSenha());
//        System.out.println("Validação:"+valida(fun,n1));

//        String a = "337.366-111";
//        a = a.replaceAll("\\.","").replaceAll("-","");
//        System.out.println(a);
    }
    
    public static boolean valida(Funcionario fun, String senha){
        Funcionario CheckFuncionario = FuncionarioDAO.getFuncionarioEmail(fun);
        return Crypto.ValidaSenha(senha, CheckFuncionario.getSenha());
    }
    
    public static void cadatro(Funcionario fun){
        try {
            boolean Rse = FuncionarioDAO.Adicionar(fun);
            System.out.println("Cadatro:"+Rse);
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}
