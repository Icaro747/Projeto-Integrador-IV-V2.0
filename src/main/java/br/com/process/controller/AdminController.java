package br.com.process.controller;

import br.com.process.entidade.Funcionario;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Icaro
 */
@Controller
public class AdminController {
    
    @RequestMapping("admin")
    public String admin(Model model, HttpServletRequest request){
        try {
            HttpSession session = request.getSession();
            Funcionario fun = (Funcionario) session.getAttribute("Use");
            if (fun != null) {
                return "AdminHome";
            }else{
                model.addAttribute("MSG", "página restrita");
            }
        } catch (Exception e) {
            model.addAttribute("MSG", e);
        }
        return "mensagem";
    }
}
