package br.com.process.controller;

import br.com.process.entidade.Funcionario;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Icaro
 */
@Controller
@RequestMapping("/admin")
public class AdminController {
    
    @RequestMapping("/*")
    public String RestrictedArea(Model model, HttpServletRequest request){
        try {
            if (RestrictedAreaAccess(request.getSession())) {
                return "AdminHome";
            }else{
                model.addAttribute("MSG", "página restrita");
            }
        } catch (Exception e) {
            model.addAttribute("MSG", e);
        }
        return "mensagem";
    }
    
    @GetMapping("")
    public String HomeAdmin(Model model, HttpServletRequest request){
        return RestrictedArea(model, request);
    }
    
    public static boolean RestrictedAreaAccess(HttpSession session){
        Funcionario fun = (Funcionario) session.getAttribute("Use");
        return fun != null;
    }
}
