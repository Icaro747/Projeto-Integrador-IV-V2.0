package br.com.process.controller;

import br.com.process.entidade.Carrinho;
import br.com.process.entidade.Produto;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Dell
 * @author Icaro
 */
@Controller @Slf4j
public class CarrinhoController {
    
    @RequestMapping("/carrinho")
    public String carrinho (Model model, HttpServletRequest request, Produto produto){
        try {
            HttpSession session = request.getSession();
            
            if(session.getAttribute("carrinho")!=null){
                Carrinho carrinho = (Carrinho) session.getAttribute("carrinho");
                carrinho.AddNewProduto(produto);
                session.setAttribute("carrinho", carrinho);
            } else {
                Carrinho carrinho = new Carrinho ();
                carrinho.AddNewProduto(produto);
                session.setAttribute("carrinho", carrinho);
            }
            return "redirect";
        } catch (Exception e) {
            log.error(""+e);
            model.addAttribute("MSG", e.getMessage());
            return "mensagem";
        }
    }
    
    @RequestMapping("/listaCarrinho")
    public String listaCarrinho (Model model, HttpServletRequest request){
        try {
            HttpSession session = request.getSession();
            
            if(session.getAttribute("carrinho")!=null){            
                Carrinho carrinho = (Carrinho) session.getAttribute("carrinho");
                List<Produto> itensProduto = carrinho.getProdutos();
                model.addAttribute("ListaCarrinho", itensProduto);
                return "carrinho";
            } else{
                model.addAttribute("MSG", "Ops!!\nSeu Carrinho está Vazio :(");
            }    
        } catch (Exception e) {
            log.error(""+e);
            model.addAttribute("MSG", e.getMessage());
        }
        return "mensagem";
    }
    
    @RequestMapping(value = "/RemoveItem/{indice}")
    public String RemoveItem(HttpServletRequest request, @PathVariable int indice, Model model){
        try {
            HttpSession session = request.getSession();
            
            Carrinho carrinho = (Carrinho) session.getAttribute("carrinho");
            carrinho.RemoveItem(indice);
            carrinho.AlignLista();
            
            session.setAttribute("carrinho", carrinho);
            
            List<Produto> itensProduto = carrinho.getProdutos();
            model.addAttribute("ListaCarrinho", itensProduto);
            
            return "redirect";
        } catch (Exception e) {
            log.error(""+e);
            model.addAttribute("MSG", e.getMessage());
        }
        return "mensagem";
    }
    
    @RequestMapping(value = "/listaCarrinho/{indice}/{acao}")
    @ResponseBody
    public boolean AcaoItem(HttpServletRequest request, @PathVariable int indice, @PathVariable String acao, Model model){
        try {
            HttpSession session = request.getSession();
            
            Carrinho carrinho = (Carrinho) session.getAttribute("carrinho");
            
            Produto produto = carrinho.getProduto(indice);
            carrinho.RemoveItem(indice);
            
            if (acao.equals("+")) {
                produto.setQuantidade(produto.getQuantidade()+1);
            }
            if (acao.equals("-")) {
                produto.setQuantidade(produto.getQuantidade()-1);
            }
            
            carrinho.AddProduto(produto);
            session.setAttribute("carrinho", carrinho);
            
            return true;
        } catch (Exception e) {
            log.error(""+e);
            model.addAttribute("MSG", e.getMessage());
        }
        return false;
    }
    
}
