package br.com.process.controller;

import br.com.process.DAO.ClienteDAO;
import br.com.process.DAO.EnderecoDAO;
import br.com.process.entidade.Cliente;
import br.com.process.entidade.Endereco;
import br.com.process.uteis.Crypto;
import br.com.process.uteis.PropriedadeStatus;
import br.com.process.uteis.RestrictedAreaAccess;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author Icaro
 */
@Slf4j
@Controller
public class ClienteController {

    @GetMapping("/home/AtualizarCliente")
    public String TeleAtualizarCliente(Model model, HttpServletRequest request) {
        try {
            HttpSession session = request.getSession();

            Cliente cliente = (Cliente) session.getAttribute("Use");
            cliente = ClienteDAO.getClienteId(cliente);;

            model.addAttribute("cliente", cliente);
            return "UpdataCliente";

        } catch (Exception e) {
            log.error("" + e);
            model.addAttribute("MSG", e.getMessage());
        }
        return "mensagem";
    }

    @PostMapping("/home/AtualizarCliente")
    public String Atualizar(Model model, Cliente cliente) {
        try {
            if (ClienteDAO.Atualizar(cliente)) {
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

    @GetMapping("/home/AtualizarSenha")
    public String TelaAtualizarSenha(Model model, HttpServletRequest request) {
        try {
            model.addAttribute("cliente", new Cliente());
            return "UpdateSenha";
        } catch (Exception e) {
            log.error("" + e);
            model.addAttribute("MSG", e.getMessage());
        }
        return "mensagem";
    }

    @PostMapping("/home/AtualizarSenha")
    public String AtualizarSenha(Model model, Cliente cliente, HttpServletRequest request) {
        try {
            HttpSession session = request.getSession();
            Cliente cliental = (Cliente) session.getAttribute("Use");
            cliente.setId_cliente(cliental.getId_cliente());

            cliente.setSenha(Crypto.HashSenha(cliente.getSenha()));

            if (ClienteDAO.AtualizarSenha(cliente)) {
                model.addAttribute("MSG", "Senha atualizada com Sucesso");
            } else {
                model.addAttribute("MSG", "Erro ao Atualizar a Senha");
            }
        } catch (Exception e) {
            log.error("" + e);
            model.addAttribute("MSG", e.getMessage());
        }
        return "mensagem";
    }

    @GetMapping("/CadastroCliente")
    public String TeleCadastro(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "cadastroCliente";
    }

    @PostMapping("/CadastroCliente")
    public String Cadatro(Model model, @Valid @ModelAttribute(value = "cliente") Cliente cliente, BindingResult result, HttpServletRequest request) {
        try {
            if (!result.hasErrors()) {
                if (!ClienteDAO.CheckCPF(cliente)) {
                    if (!ClienteDAO.CheckEmail(cliente)) {
                        cliente.setSenha(Crypto.HashSenha(cliente.getSenha()));
                        cliente.setId_cliente(ClienteDAO.Adicionar(cliente));
                        if (cliente.getId_cliente() >= 1) {
                            HttpSession session = request.getSession();
                            session.setAttribute("Use", cliente);
                            return TelaCadastroEndereco(model);
                        } else {
                            model.addAttribute("MSG", "Falhia ao cadatra");
                        }
                    } else {
                        model.addAttribute("MSG", "Email j?? cadatrado");
                    }
                } else {
                    model.addAttribute("MSG", "CPF j?? cadatrado");
                }
            } else {
                log.info("validar cliente erro");
                return "cadastroCliente";
            }
        } catch (Exception e) {
            log.error("" + e);
            model.addAttribute("MSG", e.getMessage());
        }
        return "mensagem";
    }

    @GetMapping("/CadastroEndereco")
    public String TelaCadastroEndereco(Model model) {
        try {
            model.addAttribute("Endereco", new Endereco());
            return "cadastroEndereco";
        } catch (Exception e) {
            model.addAttribute("MSG", e);
            return "mensagem";
        }
    }

    @PostMapping("/CadastroEndereco")
    public String CadastroEndereco(Model model, @Valid @ModelAttribute(value = "Endereco") Endereco endereco, BindingResult result, HttpServletRequest request) {
        try {
            HttpSession session = request.getSession();
            if (session.getAttribute("Use") != null) {
                Cliente cliente1 = (Cliente) session.getAttribute("Use");
                if (!result.hasErrors()) {

                    if (EnderecoDAO.CheckEnderecos(cliente1) == 0) {
                        endereco.setPrincipalStatus(true);
                    } else {
                        endereco.setPrincipalStatus(false);
                    }
                    if (EnderecoDAO.Adicionar(endereco, cliente1)) {
                        if (EnderecoDAO.CheckEnderecoFatura(cliente1)) {

                            return Home(model, request);
                        } else {
                            
                            return TelaFatura(model, endereco);
                        }

                    } else {
                        model.addAttribute("MSG", "Falhia ao cadatra");
                    }

                } else {
                    log.info("validar Endereco erro");
                    return "cadastroEndereco";
                }
            } else {
                model.addAttribute("MSG", "usu??rio n??o encontrado");
            }
        } catch (Exception e) {
            log.error("" + e);
            model.addAttribute("MSG", e.getMessage());
        }
        return "mensagem";
    }

    @GetMapping({"/home", "/home/*"})
    public String Home(Model model, HttpServletRequest request) {
        try {
            if (RestrictedAreaAccess.Cliente(request.getSession())) {
                return "homeCliente";
            } else {
                return "login";
            }
        } catch (Exception e) {
            log.error("" + e);
            model.addAttribute("MSG", e.getMessage());
            return "mensagem";
        }
    }

    @GetMapping("/home/listaEndereco")
    public String listaEndereco(Model model, HttpServletRequest request) {
        try {
            Cliente cliente = (Cliente) request.getSession().getAttribute("Use");
            model.addAttribute("lista", EnderecoDAO.ClienteEnderecos(cliente, PropriedadeStatus.Desativa));
            model.addAttribute("Principal", EnderecoDAO.EnderecoPrincipal(cliente));
            model.addAttribute("listaFatura", EnderecoDAO.ClienteEnderecoFatura(cliente));
            return "listaEndereco";
        } catch (Exception e) {
            log.error("" + e);
            model.addAttribute("MSG", e.getMessage());
            return "mensagem";
        }
    }

    @GetMapping("/home/Endereco/Ativar/{id}")
    public String Ativar(Model model, @PathVariable int id, HttpServletRequest request) {
        Endereco endereco = new Endereco(id);
        try {
            if (EnderecoDAO.MudancaStatus(endereco, PropriedadeStatus.Ativo)) {
                return listaEndereco(model, request);
            } else {
                model.addAttribute("MSG", "Erro ao Ativar");
            }
        } catch (Exception e) {
            log.error("" + e);
            model.addAttribute("MSG", e.getMessage());
        }
        return "mensagem";
    }

    @GetMapping("/home/Endereco/Desativar/{id}")
    public String Desativar(Model model, @PathVariable int id, HttpServletRequest request) {
        Endereco endereco = new Endereco(id);
        try {
            if (EnderecoDAO.MudancaStatus(endereco, PropriedadeStatus.Desativa)) {
                return listaEndereco(model, request);
            } else {
                model.addAttribute("MSG", "Erro ao Desativar");
            }
        } catch (Exception e) {
            log.error("" + e);
            model.addAttribute("MSG", e.getMessage());
        }
        return "mensagem";
    }

    @GetMapping("/home/Endereco/Principal/{id1}/{id2}")
    public String Principal(Model model, @PathVariable int id1, @PathVariable int id2, HttpServletRequest request) {
        Endereco endereco1 = new Endereco(id1);
        Endereco endereco2 = new Endereco(id2);

        try {
            if (EnderecoDAO.MudancaPrincipal(endereco1, PropriedadeStatus.Ativo) && EnderecoDAO.MudancaPrincipal(endereco2, PropriedadeStatus.Desativa)) {
                return listaEndereco(model, request);
            } else {
                model.addAttribute("MSG", "Erro ao Desativar");
            }
        } catch (Exception e) {
            log.error("" + e);
            model.addAttribute("MSG", e.getMessage());
        }
        return "mensagem";
    }

    @PostMapping("/CadastroEnderecoFatura")
    public String CadastroEnderecoFatura(Model model, @Valid @ModelAttribute(value = "Endereco") Endereco endereco, BindingResult result, HttpServletRequest request) {
        try {
            HttpSession session = request.getSession();
            if (session.getAttribute("Use") != null) {
                Cliente cliente1 = (Cliente) session.getAttribute("Use");
                if (!result.hasErrors()) {
                    
                    if (EnderecoDAO.AdicionarFatura(endereco, cliente1)) {
                        return Home(model, request);

                    } else {
                        model.addAttribute("MSG", "Falhia ao cadatra");
                    }
                } else {
                    log.info("validar Endereco erro");
                    return "cadastroEndereco";
                }
            } else {
                model.addAttribute("MSG", "usu??rio n??o encontrado");
            }
        } catch (Exception e) {
            log.error("" + e);
            model.addAttribute("MSG", e.getMessage());
        }
        return "mensagem";
    }

    @GetMapping("/CadastroEnderecoFatura")
    public String TelaFatura(Model model, Endereco endereco) {

        try {
            model.addAttribute("endereco", endereco);
            return "cadastroEnderecoFatura";

        } catch (Exception e) {

            log.error("" + e);
            model.addAttribute("MSG", e.getMessage());
        }
        return "mensagem";

    }
    
    

}
