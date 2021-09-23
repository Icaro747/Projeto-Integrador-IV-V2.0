package br.com.process.controller;

import br.com.process.DAO.ProdutoDAO;
import br.com.process.entidade.Produto;
import javax.servlet.http.HttpServletRequest;
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
    @RequestMapping("/adim/cadastro")
    public String cadastro(Model model){
        model.addAttribute("listTags", ProdutoDAO.getTags());
        return "cadastro";
    }
    
    /**
     * 
     * @param model
     * @param produto
     * @return 
     */
    @RequestMapping("add")
    public String add(Model model, Produto produto){
        try {
            if(ProdutoDAO.Adicionar(produto)){
                model.addAttribute("MSG", "Adicionado com Sucesso");
                return "mensagem";
            } else{
                model.addAttribute("MSG", "Erro ao Adicionar");
                return "mensagem";
            }
        } catch (Exception e) {
            model.addAttribute("MSG", e);
            return "mensagem";
        }        
    }
    
    /**
     * 
     * @param model
     * @return 
     */
    @RequestMapping("/adim/listaProduto")
    public String listaProduto (Model model){
        model.addAttribute("listaProduto", ProdutoDAO.getEstoque());
        return "listaProduto";
    }
    
    /**
     * 
     * @param model
     * @param request
     * @return 
     */
    @RequestMapping("Excluir")
    public String Desativar(Model model, HttpServletRequest request){
        try {
            Produto produto = new Produto(Integer.parseInt(request.getParameter("ID")));
            if(ProdutoDAO.Desativar(produto)){
                model.addAttribute("MSG", "Desativado com Sucesso");
            } else{
                model.addAttribute("MSG", "Erro ao Desativado");
            }
        } catch (Exception e) {
            model.addAttribute("MSG", e);
        }
        return "mensagem";
    }
    
    /**
     * 
     * @param model
     * @return 
     */
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
