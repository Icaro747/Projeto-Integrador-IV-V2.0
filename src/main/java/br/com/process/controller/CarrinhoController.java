package br.com.process.controller;

import br.com.process.DAO.EnderecoDAO;
import br.com.process.DAO.ImagensDAO;
import br.com.process.DAO.ProdutoDAO;
import br.com.process.entidade.Carrinho;
import br.com.process.entidade.Cliente;
import br.com.process.entidade.Frete;
import br.com.process.entidade.Produto;
import br.com.process.uteis.PropriedadeStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 *
 * @author Dell
 * @author Icaro
 */
@Slf4j
@Controller
public class CarrinhoController {

    private List<Frete> listaFrete = new ArrayList<>();

    public void setFretes(int km) {
        try {
            if (listaFrete.isEmpty()) {
                listaFrete.add(new Frete("Loggi", "Em até 3 dias uteis", (6.50 * km)));
                listaFrete.add(new Frete("Sedex", "Em até 7 dias uteis", (5 * km)));
                listaFrete.add(new Frete("PAC", "Em até 14 dias uteis", 0));
            }
        } catch (Exception e) {
            log.error("" + e);
        }
    }

    @PostMapping("/Calcular")
    public String fretes(Model model, HttpServletRequest request) {
        Random randomNum = new Random();
        int km = randomNum.nextInt(10) + 1;
        setFretes(km);
        model.addAttribute("Fretes", listaFrete);
        return ListaCarrinho(model, request);
    }

    @ResponseBody
    @RequestMapping("/setCEP/{preso}")
    public boolean setProsoEntrega(Model model, @PathVariable double preso, HttpServletRequest request) {
        try {
            HttpSession session = request.getSession();
            if (session.getAttribute("carrinho") != null) {
                Carrinho carrinho = (Carrinho) session.getAttribute("carrinho");
                carrinho.setPresoEntrega(preso);
                session.setAttribute("carrinho", carrinho);
                return true;
            }
        } catch (Exception e) {
            log.error("Erro ao setar o preso de entrega");
            log.error("" + e);
        }
        return false;
    }

    @RequestMapping("/carrinho")
    public String Carrinho(Model model, HttpServletRequest request, Produto produto) {
        try {
            HttpSession session = request.getSession();
            produto.setImagemPrincipal(ImagensDAO.getImagemPrincipal(produto));
            if (session.getAttribute("carrinho") != null) {
                Carrinho carrinho = (Carrinho) session.getAttribute("carrinho");
                carrinho.AddNewProduto(produto);
                session.setAttribute("carrinho", carrinho);
            } else {
                Carrinho carrinho = new Carrinho();
                carrinho.AddNewProduto(produto);
                session.setAttribute("carrinho", carrinho);
            }
            return "redirect";
        } catch (Exception e) {
            log.error("" + e);
            model.addAttribute("MSG", e.getMessage());
            return "mensagem";
        }
    }

    @RequestMapping("/listaCarrinho")
    public String ListaCarrinho(Model model, HttpServletRequest request) {
        try {
            HttpSession session = request.getSession();

            if (session.getAttribute("carrinho") != null) {
                if (listaFrete.isEmpty()) {
                    model.addAttribute("Fretes", listaFrete);
                }
                Carrinho carrinho = (Carrinho) session.getAttribute("carrinho");
                List<Produto> itensProduto = carrinho.getProdutos();
                model.addAttribute("ListaCarrinho", itensProduto);
                return "carrinho";
            } else {
                model.addAttribute("MSG", "Ops!!\nSeu Carrinho está Vazio :(");
            }
        } catch (Exception e) {
            log.error("" + e);
            model.addAttribute("MSG", e.getMessage());
        }
        return "mensagem";
    }

    @RequestMapping(value = "/RemoveItem/{indice}")
    public String RemoveItem(HttpServletRequest request, @PathVariable int indice, Model model) {
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
            log.error("" + e);
            model.addAttribute("MSG", e.getMessage());
        }
        return "mensagem";
    }

    @RequestMapping(value = "/listaCarrinho/{indice}/{acao}")
    @ResponseBody
    public boolean AcaoItem(HttpServletRequest request, @PathVariable int indice, @PathVariable String acao, Model model) {
        try {
            HttpSession session = request.getSession();
            
            Carrinho carrinho = (Carrinho) session.getAttribute("carrinho");

            Produto produto = carrinho.getProduto(indice);
            int QTD = produto.getQuantidade();
            
            carrinho.RemoveItem(indice);

            if (acao.equals("+")) {
                if (QTD < ProdutoDAO.getProduto(produto).getQuantidade()) {
                    produto.setQuantidade(QTD + 1);
                }
            }
            if (acao.equals("-")) {
                produto.setQuantidade(produto.getQuantidade() - 1);
            }

            carrinho.AddProduto(produto);
            session.setAttribute("carrinho", carrinho);
            
            return true;
        } catch (Exception e) {
            log.error("" + e);
            model.addAttribute("MSG", e.getMessage());
        }
        return false;
    }
  
    @GetMapping("/entrega")
    public String listaEndereco(Model model, HttpServletRequest request) {
        try {
            Cliente cliente = (Cliente) request.getSession().getAttribute("Use");
            model.addAttribute("lista", EnderecoDAO.ClienteEnderecos(cliente, PropriedadeStatus.Desativa));
            model.addAttribute("Principal", EnderecoDAO.EnderecoPrincipal(cliente));
            return "entrega";
        } catch (Exception e) {
            log.error("" + e);
            model.addAttribute("MSG", e.getMessage());
            return "MSG";
        }
    }

}
