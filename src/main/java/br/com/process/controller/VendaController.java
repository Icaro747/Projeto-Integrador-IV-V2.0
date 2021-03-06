package br.com.process.controller;

import br.com.process.DAO.VendaDAO;
import br.com.process.DAO.EnderecoDAO;
import br.com.process.DAO.FuncionarioDAO;
import br.com.process.DAO.TagDAO;
import br.com.process.entidade.Cliente;
import br.com.process.entidade.Carrinho;
import br.com.process.entidade.Endereco;
import br.com.process.entidade.FormaPagamento;
import br.com.process.entidade.Frete;
import br.com.process.entidade.Venda;
import br.com.process.entidade.VendaDetalhada;
import br.com.process.uteis.Datas;
import br.com.process.uteis.PropriedadeStatus;
import br.com.process.uteis.RestrictedAreaAccess;
import br.com.process.uteis.Parcelamento;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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

    private List<Frete> listaFrete = new ArrayList<>();

    @RequestMapping("/*")
    public String RestrictedVenda(Model model, HttpServletRequest request, String URL) {
        try {
            HttpSession session = request.getSession();
            if (session.getAttribute("Use") == null) {
                return "login";
            } else {
                return URL;
            }
        } catch (Exception e) {
            log.error("" + e);
            model.addAttribute("MSG", e.getMessage());
        }
        return "mensagem";
    }

    @GetMapping("/entrega")
    public String listaEndereco(Model model, HttpServletRequest request) {
        try {
            if (RestrictedAreaAccess.Cliente(request.getSession())) {
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
    
    

    public void setFretes(int km) {
        try {
            if (!listaFrete.isEmpty()) {
                listaFrete.clear();
            }
            listaFrete.add(new Frete(1, "Loggi", "Em at?? 3 dias uteis", (6.50 * km)));
            listaFrete.add(new Frete(2, "Sedex", "Em at?? 7 dias uteis", (5 * km)));
            listaFrete.add(new Frete(3, "PAC", "Em at?? 14 dias uteis", km));
        } catch (Exception e) {
            log.error("" + e);
        }
    }

    @ResponseBody
    @RequestMapping("/Calcular/{id}")
    public boolean fretes(Model model, HttpServletRequest request, @PathVariable int id) {
        try {
            HttpSession session = request.getSession();
            session.setAttribute("enderecoEntrega", EnderecoDAO.getEndereco(id));
            Random randomNum = new Random();
            int km = randomNum.nextInt(10) + 1;
            setFretes(km);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @ResponseBody
    @RequestMapping("/setCEP/{indice}")
    public boolean setPrecoEntrega(Model model, @PathVariable int indice, HttpServletRequest request) {
        try {
            HttpSession session = request.getSession();
            if (session.getAttribute("carrinho") != null) {
                Frete frete = listaFrete.get(indice - 1);
                Carrinho carrinho = (Carrinho) session.getAttribute("carrinho");
                carrinho.setPresoEntrega(frete.getValor());
                session.setAttribute("carrinho", carrinho);
                session.setAttribute("dadosFrete", frete);
                return true;
            }
        } catch (Exception e) {
            log.error("Erro ao setar o preco de entrega");
            log.error("" + e);
        }
        return false;
    }

    @GetMapping({"/pagamento", "/pagamento#none"})
    public String TelaPagamento(Model model, HttpServletRequest request) {
        try {
            HttpSession session = request.getSession();
            Carrinho carrinho = (Carrinho) session.getAttribute("carrinho");
            Parcelamento parcelamento = new Parcelamento(3, 0.15F);
            parcelamento.CriarListaParcelamento(carrinho.getTotal(), 0, 20F, 12);
            model.addAttribute("parcelas", parcelamento.getParcelas());
            return RestrictedVenda(model, request, "formaPagamento");
        } catch (Exception e) {
            log.error("" + e);
            model.addAttribute("MSG", e.getMessage());
            return "mensagem";
        }
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
            log.info("iniciando o busca de venda:"+id);
            VendaDetalhada VD = VendaDAO.Detalhes(new Venda(id));
            model.addAttribute("VD", VD);
            return "detalhesPedido";
        } catch (Exception e) {
            log.error("" + e);
            model.addAttribute("MSG", e.getMessage());
            return "mensagem";
        }
    }

    @PostMapping("/resumoPedido")
    public String resumoPedido(Model model, HttpServletRequest request, FormaPagamento pagamento) {
        try {
            HttpSession session = request.getSession();
            session.setAttribute("pagamento", pagamento);
            model.addAttribute("tipoPg", pagamento);
            return "resumoPedido";
        } catch (Exception e) {
            log.error("" + e);
            model.addAttribute("MSG", e.getMessage());
            return "mensagem";
        }
    }

    @GetMapping("/finalizar")
    public String finalizar(Model model, HttpServletRequest request) {
        try {
            log.info("inicializando o finaliza????o do pedido");
            HttpSession session = request.getSession();

            log.info("armazenando informa????es essenciais");
            Carrinho carrinho = (Carrinho) session.getAttribute("carrinho");
            log.info("" + carrinho.toString());
            Cliente cliente = (Cliente) session.getAttribute("Use");
            log.info("" + cliente.toString());
            FormaPagamento FP = (FormaPagamento) session.getAttribute("pagamento");
            if (FP.getCartao() == null) {
                FP.setCartao("?? vista");
            }
            log.info("" + FP.toString());
            Frete frete = (Frete) session.getAttribute("dadosFrete");
            log.info("" + frete.toString());
            Venda venda = new Venda();
            venda.setData_venda(Datas.getData());
            log.info("" + venda.toString());
            Endereco endereco = (Endereco) session.getAttribute("enderecoEntrega");

            log.info("inicializando o cadastro no banco");
            int id = VendaDAO.Criar(venda, cliente, carrinho, FP, frete, endereco);
            log.info("cadastro no banco finalizado. ID:" + id);

            if (id > 0) {
                model.addAttribute("MSG", "Pedido finalizado com sucesso");
                model.addAttribute("MSG2", "O n??mero do seu pedido ?? " + id);
                session.removeAttribute("carrinho");
                session.removeAttribute("tipoPg");
                return "finalizado";
            } else {
                model.addAttribute("MSG", "Elgo deu errado ao finalizar seu pedido");
            }
        } catch (Exception e) {
            log.error("" + e);
            model.addAttribute("MSG", e.getMessage());
        }
        return "mensagem";
    }
}
