package br.com.process.controller;

import br.com.process.DAO.VendaDAO;
import br.com.process.entidade.Cliente;
import br.com.process.entidade.Venda;
import br.com.process.uteis.Parcelamento;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
