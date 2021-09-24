package br.com.process.controller;

import br.com.process.DAO.ProdutoDAO;
import br.com.process.entidade.Produto;
import br.com.process.uteis.PropriedadeStatus;
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
            } else{
                model.addAttribute("MSG", "Erro ao Adicionar");
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
    @RequestMapping("/adim/listaProduto")
    public String listaProduto (Model model){
        model.addAttribute("listaProduto", ProdutoDAO.getEstoque(PropriedadeStatus.Desativa));
        return "listaProduto";
    }
    
    /**
     * 
     * @param model
     * @param request
     * @return 
     */
    @RequestMapping("Desativar")
    public String Desativar(Model model, HttpServletRequest request){
        try {
            Produto produto = new Produto();
            produto.setId_produto(Integer.parseInt(request.getParameter("ID")));
            
            if(ProdutoDAO.MudancaStatus(produto, PropriedadeStatus.Desativa)){
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
     * @param request
     * @return 
     */
    @RequestMapping("Ativar")
    public String Ativar(Model model, HttpServletRequest request){
        try {
            Produto produto = new Produto();
            produto.setId_produto(Integer.parseInt(request.getParameter("ID")));
            
            if(ProdutoDAO.MudancaStatus(produto, PropriedadeStatus.Ativo)){
                model.addAttribute("MSG", "Ativar com Sucesso");
            } else{
                model.addAttribute("MSG", "Erro ao Ativar");
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
            model.addAttribute("lista", ProdutoDAO.getEstoque(PropriedadeStatus.Desativa));
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
    @RequestMapping("/adim/buscar")
    public String AdimBuscar(Model model, Produto produto){
        try {
            model.addAttribute("listaProduto", ProdutoDAO.BuscarProdutos(produto, PropriedadeStatus.Desativa));
            return "listaProduto";
        } catch (Exception e) {
            model.addAttribute("MSG", e.getMessage());
            System.err.println("------------------>>ERRO<<------------------");
            System.err.println(e);
            return "mensagem";
        }
    }
    
    /**
     * 
     * @param model
     * @param produto
     * @return 
     */
    @RequestMapping("/buscar")
    public String Buscar(Model model, Produto produto){
        try {
            System.out.println("Nome:"+produto.getNome());
            model.addAttribute("lista", ProdutoDAO.BuscarProdutos(produto, PropriedadeStatus.Ativo));
            return "buscar";
        } catch (Exception e) {
            model.addAttribute("MSG", e.getMessage());
            System.err.println("------------------>>ERRO<<------------------");
            System.err.println(e);
            return "mensagem";
        }
    }
}
