package br.com.process.uteis;

import at.favre.lib.crypto.bcrypt.BCrypt;

/**
 * classe de criptografia
 * @author Icaro
 */
public class Crypto {
    
    /**
     * método de criptografia de senha
     * @param password senha acer criptografada
     * @return retorna a senha criptografada
     */
    public static String HashSenha(String password){
        return BCrypt.withDefaults().hashToString(12, password.toCharArray());
    }
    
    /**
     * método de verificação de senha criptografada.<br> se a senha criptografada é igual a senha não criptografada.
     * @param SenhaAberta senha não criptografada.
     * @param SenhaFechada senha criptografada.
     * @return <b>true</b> se a senhas forem iguais <b>false</b> se não for.
     */
    public static boolean ValidaSenha(String SenhaAberta, String SenhaFechada){
        BCrypt.Result res = BCrypt.verifyer().verify(SenhaAberta.toCharArray(), SenhaFechada.toCharArray());
        return res.verified;
    }
}