package br.com.process.controller;

import br.com.process.DAO.ClienteDAO;
import br.com.process.DAO.FuncionarioDAO;
import br.com.process.DAO.VendaDAO;
import br.com.process.entidade.Cliente;
import br.com.process.entidade.Funcionario;
import br.com.process.entidade.Venda;
import br.com.process.entidade.VendaDetalhada;
import br.com.process.uteis.PropriedadeStatus;
import br.com.process.uteis.Crypto;
import br.com.process.uteis.RestrictedAreaAccess;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Vinicius
 */
@Slf4j
@Controller
public class FuncionarioController {

    @RequestMapping(value = "/admin/AtualizarFuncionario/{id}")
    public String TelaAtualizar(Model model, @PathVariable int id, HttpServletRequest request) {
        try {
            if (RestrictedAreaAccess.Funcionario(request.getSession())) {
                Funcionario funcionario = new Funcionario(id);
                funcionario = FuncionarioDAO.getFuncionarioId(funcionario);
                log.info(funcionario.toString());

                if (funcionario.getNome() != null) {
                    funcionario.setSenha("");
                    model.addAttribute("funcionario", funcionario);

                    log.info("redirecionando pra tela de update funcionario");
                    return "updateFuncionario";
                } else {
                    model.addAttribute("MSG", "Fsuncionário não encontrado");
                }
            } else {
                model.addAttribute("MSG", "Área Restrita");
            }
        } catch (Exception e) {
            log.error("" + e);
            model.addAttribute("MSG", e.getMessage());
        }
        return "mensagem";
    }

    @PostMapping("/admin/AtualizarFuncionario")
    public String Atualizar(Model model, @Valid @ModelAttribute(value = "funcionario") Funcionario funcionario, BindingResult result, HttpServletRequest request) {
        try {
            if (RestrictedAreaAccess.Funcionario(request.getSession())) {
                log.info(funcionario.toString());
                if (!result.hasErrors()) {
                    log.info("iniciando encriptação de senha");
                    funcionario.setSenha(Crypto.HashSenha(funcionario.getSenha()));
                    log.info("senha criptografada");
                    if (FuncionarioDAO.Atualizar(funcionario)) {
                        model.addAttribute("MSG", "Atualizar com Sucesso");
                    } else {
                        model.addAttribute("MSG", "Erro ao Atualizar");
                    }
                } else {
                    log.info("validar funcionario erro");
                    return "updateFuncionario";
                }
            } else {
                model.addAttribute("MSG", "Área Restrita");
            }
        } catch (Exception e) {
            log.error("" + e);
            model.addAttribute("MSG", e.getMessage());
        }
        return "mensagem";
    }

    @GetMapping("/admin/listaVenda")
    public String ListaVenda(Model model, HttpServletRequest request) {
        if (RestrictedAreaAccess.Funcionario(request.getSession())) {
            model.addAttribute("listaVenda", VendaDAO.Vendas());
            return "listaVenda";
        } else {
            model.addAttribute("MSG", "Área Restrita");
            return "mensagem";
        }
    }

    @RequestMapping("/admin/listaFuncionario")
    public String ListaFuncionario(Model model, HttpServletRequest request) {
        if (RestrictedAreaAccess.Funcionario(request.getSession())) {
            model.addAttribute("listaFuncionario", FuncionarioDAO.getUsuarios(PropriedadeStatus.Desativa));
            return "listaFuncionario";
        } else {
            model.addAttribute("MSG", "Área Restrita");
            return "mensagem";
        }
    }

    @RequestMapping(value = "/admin/listaFuncionario/{id}")
    public String ListaUse(Model model, @PathVariable int id) {
        try {
            Funcionario funcionario = new Funcionario(id);
            List<Funcionario> Use = new ArrayList<>();
            Use.add(FuncionarioDAO.getFuncionarioId(funcionario));
            model.addAttribute("listaFuncionario", Use);
            model.addAttribute("eu", true);
            return "listaFuncionario";
        } catch (Exception e) {
            log.error("" + e);
            model.addAttribute("MSG", e.getMessage());
        }
        return "mensagem";
    }

    @RequestMapping(value = "/admin/Desativar/{id}")
    public String Desativar(Model model, @PathVariable int id, HttpServletRequest request) {
        Funcionario funcionario = new Funcionario(id);
        try {
            if (RestrictedAreaAccess.FuncionarioADM(request.getSession())) {
                if (FuncionarioDAO.MudancaStatus(funcionario, PropriedadeStatus.Desativa)) {
                    return ListaFuncionario(model, request);
                } else {
                    model.addAttribute("MSG", "Erro ao Desativado");
                }
            } else {
                model.addAttribute("MSG", "Área Restrita");
            }
        } catch (Exception e) {
            log.error("" + e);
            model.addAttribute("MSG", e.getMessage());
        }
        return "mensagem";
    }

    @RequestMapping(value = "/admin/Ativar/{id}")
    public String Ativar(Model model, @PathVariable int id, HttpServletRequest request) {
        Funcionario funcionario = new Funcionario(id);
        try {
            if (RestrictedAreaAccess.FuncionarioADM(request.getSession())) {
                if (FuncionarioDAO.MudancaStatus(funcionario, PropriedadeStatus.Ativo)) {
                    return ListaFuncionario(model, request);
                } else {
                    model.addAttribute("MSG", "Erro ao Ativar");
                }
            } else {
                model.addAttribute("MSG", "Área Restrita");
            }
        } catch (Exception e) {
            log.error("" + e);
            model.addAttribute("MSG", e.getMessage());
        }
        return "mensagem";
    }

    @RequestMapping("/admin/CadastroFuncionario")
    public String Cadastro(Model model, HttpServletRequest request) {
        if (RestrictedAreaAccess.FuncionarioADM(request.getSession())) {
            return "cadastroFuncionario";
        } else {
            model.addAttribute("MSG", "Área Restrita");
            return "mensagem";
        }
    }

    @PostMapping("/admin/CadastroFuncionario/add")
    public String Add(Model model, Funcionario funcionario, HttpServletRequest request) {
        try {
            funcionario.setSenha(Crypto.HashSenha(funcionario.getSenha()));
            if (FuncionarioDAO.Adicionar(funcionario)) {
                model.addAttribute("MSG", "Funcionario ao Adicionado com Sucesso!");
            } else {
                model.addAttribute("MSG", "Erro ao Adicionar");
            }
        } catch (Exception e) {
            log.error("" + e);
            model.addAttribute("MSG", e.getMessage());
        }
        return "mensagem";
    }

    @GetMapping("/admin/AlterarPedido/{id}")
    public String AlterarPedido(Model model, HttpServletRequest request, @PathVariable int id) {
        try {
            VendaDetalhada venda = new VendaDetalhada(id);
            venda = VendaDAO.getVendaId(venda);
            Cliente cliente = new Cliente(venda.getIdCliente());
            cliente = ClienteDAO.getClienteId(cliente);
            model.addAttribute("venda", venda);
            model.addAttribute("cliente", cliente);
            return "alterarPedido";
        } catch (Exception e) {
            log.error("" + e);
            model.addAttribute("MSG", e.getMessage());
            return "mensagem";
        }
    }

    @PostMapping("/admin/Alterar")
    public String Alterar(Model model, Venda venda) {
        try {
            if (VendaDAO.setStatusVenda(venda)) {
                model.addAttribute("MSG", "Atualizado com Sucesso");
            } else {
                model.addAttribute("MSG", "Erro ao Atualizar");
            }
        } catch (Exception e) {
            log.error("" + e);
            model.addAttribute("MSG", e.getMessage());
        }
        return "mensagem";
    }
}
