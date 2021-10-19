package br.com.process.controller;

import br.com.process.entidade.Funcionario;

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
@Controller @Slf4j
@RequestMapping("/admin")
public class AdminController {
    
    @RequestMapping({"/*",""})
    public String RestrictedArea(Model model, HttpServletRequest request){
        try {
            if (RestrictedAreaAccess(request.getSession())) {
                return "AdminHome";
            }else{
                log.info("restrita. funcionário não logado");
                model.addAttribute("MSG", "página restrita");
            }
        } catch (Exception e) {
            log.error(""+e);
            model.addAttribute("MSG", e.getMessage());
        }
        return "mensagem";
    }
    
    public static boolean RestrictedAreaAccess(HttpSession session){
        Funcionario fun = (Funcionario) session.getAttribute("Use");
        return fun != null;
    }
}
