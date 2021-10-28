package br.com.process.controller;

import br.com.process.DAO.ClienteDAO;
import br.com.process.DAO.FuncionarioDAO;
import br.com.process.entidade.Cliente;
import br.com.process.entidade.Funcionario;
import br.com.process.uteis.Crypto;
import br.com.process.uteis.RestrictedAreaAccess;

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
@Slf4j
@Controller
public class LoginController {
    
    @GetMapping("/login")
    public String Login(Model model, HttpServletRequest request){
        try {
            if (RestrictedAreaAccess.Cliente(request.getSession())) {
                return "homeCliente";
            } else if (RestrictedAreaAccess.Funcionario(request.getSession())) {
                return "homeAdmin";
            }else{
                return "login";
            }
        } catch (Exception e) {
            log.error("" + e);
            model.addAttribute("MSG", e.getMessage());
            return "mensagem";
        }
    }
    
    @PostMapping("/login")
    public String Login(Model model, @Valid @ModelAttribute(value = "cliente") Cliente cliente, BindingResult result, HttpServletRequest request) {
        try {

            String senha = cliente.getSenha();
            HttpSession session = request.getSession();

            if (ClienteDAO.CheckCliente(cliente)) {

                cliente = ClienteDAO.getClienteEmail(cliente);
                if (Crypto.ValidaSenha(senha, cliente.getSenha())) {
                    cliente.setSenha("");
                    session.setAttribute("Use", cliente);
                    return "homeCliente";
                }
            }
            model.addAttribute("MSG", "Senha ou login inválido");

        } catch (Exception e) {
            log.error("" + e);
            model.addAttribute("MSG", e.getMessage());
        }
        return "mensagem";
    }

    @GetMapping("/login/fun")
    public String TelaLoginFucionario(Model model, HttpServletRequest request) {
        try {
            if (RestrictedAreaAccess.Cliente(request.getSession())) {
                return "homeCliente";
            } else if (RestrictedAreaAccess.Funcionario(request.getSession())) {
                return "homeAdmin";
            }else{
                return "loginFuncionario";
            }
        } catch (Exception e) {
            log.error("" + e);
            model.addAttribute("MSG", e.getMessage());
            return "mensagem";
        }
    }

    @PostMapping("/login/fun")
    public String LoginFucionario(Model model, @Valid @ModelAttribute(value = "funcionario") Funcionario funcionario, BindingResult result, HttpServletRequest request) {
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
                        return "homeAdmin";
                    }
                } else {
                    model.addAttribute("MSG", "Conta desativada");
                }
            }
            model.addAttribute("MSG", "Senha ou login inválido");

        } catch (Exception e) {
            log.error("" + e);
            model.addAttribute("MSG", e.getMessage());
        }
        return "mensagem";
    }

//    @GetMapping("/logoff")
//    public String LogOff(HttpServletRequest request){
//        
//    }
}
