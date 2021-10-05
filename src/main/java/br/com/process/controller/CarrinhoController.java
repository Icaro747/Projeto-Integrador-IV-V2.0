package br.com.process.controller;

import br.com.process.entidade.Carrinho;
import br.com.process.entidade.Produto;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Dell
 * @author Icaro
 */
@Controller
public class CarrinhoController {
    
    @RequestMapping("/carrinho")
    public String carrinho (Model model, HttpServletRequest request, Produto produto){
        try {
            if(request.getSession().getAttribute("carrinho")!=null){
                Carrinho carrinho = (Carrinho) request.getSession().getAttribute("carrinho");
                carrinho.addProduto(produto);
                request.getSession().setAttribute("carrinho", carrinho);
            } else {
                Carrinho carrinho = new Carrinho ();
                carrinho.addProduto(produto);
                request.getSession().setAttribute("carrinho", carrinho);
            }
            model.addAttribute("MSG", "Adicionado ao Carrinho");
        } catch (Exception e) {
        }
        return "mensagem";
    }
    
    @RequestMapping("/listaCarrinho")
    public String listaCarrinho (Model model, HttpServletRequest request){
        try {
            if(request.getSession().getAttribute("carrinho")!=null){            
                HttpSession session = request.getSession();
                Carrinho carrinho = (Carrinho) session.getAttribute("carrinho");
                List<Produto> itensProduto = carrinho.getProdutos();
                model.addAttribute("ListaCarrinho", itensProduto);
                return "carrinho";
            } else{
                model.addAttribute("MSG", "Ops!!\nSeu Carrinho está Vazio :(");
                return "mensagem";
            }    
        } catch (Exception e) {
            model.addAttribute("MSG", e);        
            return "mensagem";
        }
    }
    
}
