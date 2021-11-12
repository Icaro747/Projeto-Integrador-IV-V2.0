package br.com.process.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Icaro
 */
@Slf4j
@Controller
@RequestMapping("/venda")
public class VendaController {
    
    @RequestMapping("/*")
    public String RestrictedVenda(Model model, HttpServletRequest request, String URL){
        try {
            HttpSession session = request.getSession();
            if (session.getAttribute("Use") == null) {
                log.info("Não esta logado");
                return "login";
            }else{
                log.info("insio de proseso de venda");
                return URL;
            }
        } catch (Exception e) {
            log.error("" + e);
            model.addAttribute("MSG", e.getMessage());
        }
        return "mensagem";
    }
    
    @GetMapping("/finalizar")
    public String Finalizar(Model model, HttpServletRequest request){
        return RestrictedVenda(model, request, "index");
    }
    
}
