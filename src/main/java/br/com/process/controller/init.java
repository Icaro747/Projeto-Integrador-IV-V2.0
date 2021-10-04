package br.com.process.controller;

import br.com.process.DAO.ProdutoDAO;
import br.com.process.DAO.TagDAO;
import br.com.process.entidade.Produto;
import br.com.process.uteis.PropriedadeStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Icaro
 */
@Controller
public class init {
   
    /**
     * 
     * @return 
     */
    @RequestMapping("login")
    public String login(){
        return "login";
    }
    
    /**
     * 
     * @param model
     * @return 
     */
    @RequestMapping("/")
    public String index(Model model){
        try {
            model.addAttribute("lista", ProdutoDAO.getEstoque(PropriedadeStatus.Ativo));
            model.addAttribute("tags", TagDAO.getTags());
        } catch (Exception e) {
            model.addAttribute("MSG", e.getMessage());
            System.err.println("------------------>>ERRO<<------------------");
            System.err.println(e);
            return "mensagem";
        }
        return "index";
    }
    
    /**
     * 
     * @param model
     * @param produto
     * @return 
     */
    @RequestMapping("/BuscarProduto")
    public String Buscar(Model model, Produto produto){
        try {
            System.out.println("Nome:"+produto.getNome());
            model.addAttribute("lista", ProdutoDAO.BuscarProdutos(produto, PropriedadeStatus.Ativo));
            return "BuscarProduto";
        } catch (Exception e) {
            model.addAttribute("MSG", e.getMessage());
            System.err.println("------------------>>ERRO<<------------------");
            System.err.println(e);
            return "mensagem";
        }
    }
}
