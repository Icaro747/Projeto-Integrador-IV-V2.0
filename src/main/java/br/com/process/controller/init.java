package br.com.process.controller;

import br.com.process.DAO.ImagensDAO;
import br.com.process.DAO.ProdutoDAO;
import br.com.process.DAO.TagDAO;
import br.com.process.entidade.Produto;
import br.com.process.entidade.Pagina;
import br.com.process.entidade.Tag;
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
   
    @RequestMapping("login")
    public String login(){
        log.info("EEEEEEEEEEEEEEEEEEEEEEEEEEEEE");
        return "login";
    }
    
    @RequestMapping("/")
    public String index(Model model){
        try {
            Pagina pagina = new Pagina();
            pagina.setPageAtual(0);
            pagina.setQuantidadeItems(16);
            
            List<Tag> Tags = TagDAO.getTags();
            if (!Tags.isEmpty()) {
                List<Produto> Estoque = ProdutoDAO.getEstoque(PropriedadeStatus.Ativo, pagina);
                Estoque.forEach(produto -> {
                    produto.setImagemPrincipal(ImagensDAO.getImagemPrincipal(produto));
                });

                model.addAttribute("lista", Estoque);
                model.addAttribute("tags", Tags);
                return "index";
            }else{
                model.addAttribute("MSG", "Não foi encontrada nenhuma tag cadastrada. favor cadastrar uma tag");
            }
        } catch (Exception e) {
            model.addAttribute("MSG", e.getMessage());
            log.error(""+e);
        }
        return "mensagem";
    }
    
    @RequestMapping("/BuscarProduto")
    public String Buscar(Model model, Produto produto){
        try {
            log.info("iniciando busca por produtos");
            List<Produto> Estoque = ProdutoDAO.BuscarProdutos(produto, PropriedadeStatus.Ativo);
            log.info(Estoque.size() + " produtos encontrados");
            
            if (Estoque.size()>0) {
                Estoque.forEach(pro -> {
                    pro.setImagemPrincipal(ImagensDAO.getImagemPrincipal(pro));
                });
                model.addAttribute("lista", Estoque);
                return "BuscarProduto";
            }else{
                model.addAttribute("MSG", "Nenhum produto foi encontrado");
            }
        } catch (Exception e) {
            model.addAttribute("MSG", e.getMessage());
            log.error(""+e);
        }
        return "mensagem";
    }
}
