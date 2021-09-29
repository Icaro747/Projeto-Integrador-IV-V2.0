package br.com.process.controller;

import br.com.process.DAO.ProdutoDAO;
import br.com.process.entidade.Produto;
import br.com.process.uteis.PropriedadeStatus;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Icaro
 */
@Controller
public class ProdutoController {
    
    /**
     * 
     */
    private final static String FOLDER_IMG_UPLOADED = "C://Users//Icaro//Documents//NetBeansProjects//PI//Projeto-Integrador-IV-V2.0//src//main//resources//static//img//uploads//";

    /**
     * 
     * @param model
     * @return 
     */
    @RequestMapping("/admin/cadastro")
    public String cadastro(Model model){
        model.addAttribute("listTags", ProdutoDAO.getTags());
        return "cadastro";
    }
    
    @RequestMapping("/produto")
    public String produto(Model model , Produto produto){
        try{
        produto = ProdutoDAO.getProduto(produto);
        model.addAttribute("produto",produto);
        return "produtos" ;
        }
        catch(Exception e){
             model.addAttribute("MSG", e);
        }
        return "mensagem";
    }
    
    @PostMapping("/add")
    public String add(Model model, Produto produto, @RequestParam("img") MultipartFile file, RedirectAttributes redirectAttributes){
        try {
            produto.newName_IMG(file.getOriginalFilename());
            if(ProdutoDAO.Adicionar(produto)){
                if (!file.isEmpty()) {
                    byte[] bytes = file.getBytes();
                    Path path = Paths.get(FOLDER_IMG_UPLOADED + produto.getName_IMG());
                    Files.write(path, bytes);
                }
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
    @RequestMapping("/admin/listaProduto")
    public String listaProduto (Model model){
        model.addAttribute("listaProduto", ProdutoDAO.getEstoque(PropriedadeStatus.Desativa));
        return "listaProduto";
    }
    
    /**
     * 
     * @param model
     * @param produto
     * @return 
     */
    @RequestMapping("Desativar")
    public String Desativar(Model model, Produto produto){
        try {
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
     * @param produto
     * @return 
     */
    @RequestMapping("Ativar")
    public String Ativar(Model model, Produto produto){
        try {
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
     * @param produto
     * @return 
     */
    @RequestMapping("/admin/buscar")
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
}
