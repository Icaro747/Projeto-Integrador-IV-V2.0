package br.com.process.controller;

import br.com.process.DAO.ProdutoDAO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Icaro
 */
@Controller
public class init {
    
    @RequestMapping("/url")
    public String page(Model model) {
        model.addAttribute("attribute", "value");
        return "view.name";
    }
    
    @RequestMapping("/hello")
    public String sayHello(Model model){
        model.addAttribute("nome", "Batata");
        return "hello";
    }
    
    @RequestMapping("login")
    public String login(){
        return "login";
    }
    
    @RequestMapping("/")
    public String index(Model model){
        try {
            model.addAttribute("lista", ProdutoDAO.getEstoque());
        } catch (Exception e) {
            model.addAttribute("MSG", e.getMessage());
            System.err.println("------------------>>ERRO<<------------------");
            System.err.println(e);
            return "mensagem";
        }
        return "index";
    }
    
}
