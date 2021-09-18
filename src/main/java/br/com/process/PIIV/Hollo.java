package br.com.process.PIIV;

import br.com.process.entidade.Produto;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 *
 * @author Icaro
 */
@Controller
public class Hollo {
    
    
    
//    @RequestMapping("/lista")
//    public String sayListe(Model model){
//        
//        Produto produto = new Produto();
//        produto.setNome("Roupa 01");
//        
//        List<Produto> Estoque = new ArrayList<>();
//        Estoque.add(produto);
//        Estoque.add(produto);
//        Estoque.add(produto);
//        Estoque.add(produto);
//        
//        model.addAttribute("Estoque", Estoque);
//        return "lista";
//    }
}
