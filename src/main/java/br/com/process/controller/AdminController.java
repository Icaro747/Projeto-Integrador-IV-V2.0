package br.com.process.controller;

import br.com.process.uteis.RestrictedAreaAccess;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
            if (RestrictedAreaAccess.Funcionario(request.getSession())) {
                return "adminHome";
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
}
