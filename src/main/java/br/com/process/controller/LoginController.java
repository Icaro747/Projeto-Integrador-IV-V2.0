package br.com.process.controller;

import br.com.process.DAO.FuncionarioDAO;
import br.com.process.entidade.Funcionario;
import br.com.process.uteis.Crypto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author Icaro
 */
@Controller @Slf4j
public class LoginController {
    
    @PostMapping("/login")
    public String Login(Model model, @Valid @ModelAttribute(value="funcionario") Funcionario funcionario, BindingResult result, HttpServletRequest request){
        String senha = funcionario.getSenha();
        try {
            String[] splitted= funcionario.getEmail().split("@");
            HttpSession session = request.getSession();
            
            if (splitted[1].equalsIgnoreCase("newman.com")) {
                if (FuncionarioDAO.CheckFuncionario(funcionario)) {
                    
                    Funcionario CheckFuncionario = FuncionarioDAO.getFuncionarioEmail(funcionario);
                    if (CheckFuncionario.isStatus()) {
                        
                        if (Crypto.ValidaSenha(senha, CheckFuncionario.getSenha())) {
                            funcionario.setSenha("");
                            funcionario.setNome(CheckFuncionario.getNome());
                            funcionario.setAtuacao(CheckFuncionario.getAtuacao());
                            session.setAttribute("Use", funcionario);
                            return "adminHome";
                        }
                    }else{
                        model.addAttribute("MSG", "Conta desativada");
                    }
                }
                model.addAttribute("MSG", "Senha ou login inválido");
            }else{
                //login com cliente
            }
        } catch (Exception e) {
            log.error(""+e);
            model.addAttribute("MSG", e.getMessage());
        }
        return "mensagem";
    }
    
    @GetMapping("/login/fun")
    public String TelaLoginFucionario(){
        return "loginFuncionario";
    }
    
    @PostMapping("/login/fun")
    public String LoginFucionario(Model model, @Valid @ModelAttribute(value="funcionario") Funcionario funcionario, BindingResult result, HttpServletRequest request){
        String senha = funcionario.getSenha();
        try {
            HttpSession session = request.getSession();
            
            if (FuncionarioDAO.CheckFuncionario(funcionario)) {

                Funcionario CheckFuncionario = FuncionarioDAO.getFuncionarioEmail(funcionario);
                if (CheckFuncionario.isStatus()) {

                    if (Crypto.ValidaSenha(senha, CheckFuncionario.getSenha())) {
                        funcionario.setSenha("");
                        funcionario.setNome(CheckFuncionario.getNome());
                        funcionario.setAtuacao(CheckFuncionario.getAtuacao());
                        session.setAttribute("Use", funcionario);
                        return "adminHome";
                    }
                }else{
                    model.addAttribute("MSG", "Conta desativada");
                }
            }
            model.addAttribute("MSG", "Senha ou login inválido");
            
        } catch (Exception e) {
            log.error(""+e);
            model.addAttribute("MSG", e.getMessage());
        }
        return "mensagem";
    }
    
//    @GetMapping("/logoff")
//    public String LogOff(HttpServletRequest request){
//        
//    }
    
}
