package br.com.process.controller;

import br.com.process.DAO.VendaDAO;
import br.com.process.entidade.Cliente;
import br.com.process.entidade.Venda;
import br.com.process.uteis.Parcelamento;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import br.com.process.entidade.Cliente;
import br.com.process.DAO.EnderecoDAO;
import br.com.process.entidade.Carrinho;
import br.com.process.entidade.Frete;
import br.com.process.uteis.PropriedadeStatus;
import br.com.process.uteis.RestrictedAreaAccess;
import java.net.http.HttpRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Icaro
 */
@Slf4j
@Controller
@RequestMapping("/venda")
public class VendaController {
    
    @RequestMapping("/*")
    public String RestrictedVenda(Model model, HttpServletRequest request, String URL) {
        try {
            HttpSession session = request.getSession();
            if (session.getAttribute("Use") == null) {
                log.info("Não esta logado");
                return "login";
            } else {
                log.info("insio de proseso de venda");
                return URL;
            }
        } catch (Exception e) {
            log.error("" + e);
            model.addAttribute("MSG", e.getMessage());
        }
        return "mensagem";
    }
    
    @GetMapping("/finalizar")
    public String Finalizar(Model model, HttpServletRequest request) {
        return RestrictedVenda(model, request, "index");
    }
        
    @GetMapping("/entrega")
    public String listaEndereco(Model model, HttpServletRequest request) {
        try {
            if(RestrictedAreaAccess.Cliente(request.getSession())){
                Cliente cliente = (Cliente) request.getSession().getAttribute("Use");
                model.addAttribute("lista", EnderecoDAO.ClienteEnderecos(cliente, PropriedadeStatus.Ativo));
                model.addAttribute("Fretes", listaFrete);
                return RestrictedVenda(model, request, "entrega");
            } 
            return RestrictedVenda(model, request, "venda");
        } catch (Exception e) {
            log.error("" + e);
            model.addAttribute("MSG", e.getMessage());            
        }
        return "mensagem";
    }
        
    
    private List<Frete> listaFrete = new ArrayList<>();

    public void setFretes(int km) {
        try {
            if (!listaFrete.isEmpty()) {
                listaFrete.clear();
            }
            listaFrete.add(new Frete(1,"Loggi", "Em até 3 dias uteis", (6.50 * km)));
            listaFrete.add(new Frete(2,"Sedex", "Em até 7 dias uteis", (5 * km)));
            listaFrete.add(new Frete(3,"PAC", "Em até 14 dias uteis", 0));            
        } catch (Exception e) {
            log.error("" + e);
        }
    }
    
    @ResponseBody
    @RequestMapping("/Calcular/{id}")
    public boolean fretes(Model model, HttpServletRequest request, @PathVariable int id) {
        try{
            HttpSession session = request.getSession();
            session.setAttribute("enderecoEntrega", EnderecoDAO.getEndereco(id));
            Random randomNum = new Random();
            int km = randomNum.nextInt(10) + 1;
            setFretes(km);
            return true;
        }
        catch(Exception e){
            return false;
        }
        
         
        
    }
    
    @ResponseBody
    @RequestMapping("/setCEP/{indice}")
    public boolean setPrecoEntrega(Model model, @PathVariable int indice, HttpServletRequest request) {
        try {
            HttpSession session = request.getSession();
            if (session.getAttribute("carrinho") != null) {
                Frete frete = listaFrete.get(indice -1);                
                Carrinho carrinho = (Carrinho) session.getAttribute("carrinho");
                carrinho.setPresoEntrega(frete.getValor());
                session.setAttribute("carrinho", carrinho);
                session.setAttribute("dadosFrete", frete);
                return true;
            }
        } catch (Exception e) {
            log.error("Erro ao setar o preco de entrega");
            log.error(""+e);
        }
        return false;
    }
    
    @GetMapping("/pagamento")
    public String TelaPagamento(Model model, HttpServletRequest request) {
        Parcelamento parcelamento = new Parcelamento(3, 0.15F);
        parcelamento.CriarListaParcelamento(159.00F, 0, 20F, 12);
        model.addAttribute("parcelas", parcelamento.getParcelas());
        return "formaPagamento";
    }
    
    @GetMapping("/listaPedidos")
    public String listaPedidos(Model model, HttpServletRequest request) {
        try {
            Cliente cliente = (Cliente) request.getSession().getAttribute("Use");
            model.addAttribute("lista", VendaDAO.ClientePedidos(cliente));
            
            return "listaPedidos";
        } catch (Exception e) {
            log.error("" + e);
            model.addAttribute("MSG", e.getMessage());
            return "mensagem";
        }
        
    }
    
    @GetMapping("/detalhesPedido/{id}")
    public String detalhePedidos(Model model, HttpServletRequest request, @PathVariable int id) {
        try {
            model.addAttribute("lista", VendaDAO.Detalhes(new Venda(id)));
            
            return "detalhesPedido";
        } catch (Exception e) {
            log.error("" + e);
            model.addAttribute("MSG", e.getMessage());
            return "mensagem";
        }
        
    }
}
