package br.com.process.controller;

import br.com.process.DAO.ImagensDAO;
import br.com.process.DAO.ProdutoDAO;
import br.com.process.DAO.TagDAO;
import br.com.process.entidade.Produto;
import br.com.process.uteis.PropriedadeStatus;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Icaro
 */
@Controller @Slf4j
public class init {
   
    
    /**
     * 
     * @return 
     */
    @RequestMapping("login")
    public String login(){
        log.info("EEEEEEEEEEEEEEEEEEEEEEEEEEEEE");
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
            List<Produto> Estoque = ProdutoDAO.getEstoque(PropriedadeStatus.Ativo);
            Estoque.forEach(produto -> {
                produto.setImagemPrincipal(ImagensDAO.getImagemPrincipal(produto));
            });
            model.addAttribute("lista", Estoque);
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
