package br.com.process.controller;

import br.com.process.DAO.FuncionarioDAO;
import br.com.process.DAO.TagDAO;
import br.com.process.entidade.Funcionario;
import br.com.process.uteis.PropriedadeStatus;
import br.com.process.uteis.Crypto;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Vinicius
 */
@Controller @Slf4j
public class FuncionarioController {

    @RequestMapping(value = "/admin/AtualizarFuncionario/{id}")
    public String telaAtualizar(Model model, @PathVariable int id) {
        try {
            Funcionario funcionario = new Funcionario(id);
            funcionario = FuncionarioDAO.getFuncionarioId(funcionario);
            log.info(funcionario.toString());
            
            if (funcionario.getNome() != null) {
                funcionario.setSenha("");
                model.addAttribute("funcionario", funcionario);
                
                log.info("redirecionando pra tela de update funcionario");
                return "FuncionarioUpdate";
            }else{
                model.addAttribute("MSG", "Fsuncionário não encontrado");
            }
        } catch (Exception e) {
            log.error(""+e);
            model.addAttribute("MSG", e.getMessage());
        }
        return "mensagem";
    }

    @PostMapping("/admin/AtualizarFuncionario")
    public String Atualizar(Model model, @Valid @ModelAttribute(value = "funcionario") Funcionario funcionario, BindingResult result) {
        try {
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
                return "FuncionarioUpdate";
            }
        } catch (Exception e) {
            log.error(""+e);
            model.addAttribute("MSG", e.getMessage());
        }
        return "mensagem";
    }
    
    @RequestMapping("/admin/listaFuncionario")
    public String listaFuncionario (Model model){
        model.addAttribute("listaFuncionario", FuncionarioDAO.getUsuarios(PropriedadeStatus.Desativa));
        return "listaFuncionario";
    }
    
    @RequestMapping(value = "/admin/listaFuncionario/{id}")
    public String listaUse (Model model, @PathVariable int id){
        try {
            Funcionario funcionario = new Funcionario(id);
            List<Funcionario> Use = new ArrayList<>();
            Use.add(FuncionarioDAO.getFuncionarioId(funcionario));
            model.addAttribute("listaFuncionario", Use);
            model.addAttribute("eu", true);
            return "listaFuncionario";
        } catch (Exception e) {
            log.error(""+e);
            model.addAttribute("MSG", e.getMessage());
        }
        return "mensagem";
    }
    
    @RequestMapping(value = "/admin/Desativar/{id}")
    public String Desativar(Model model, @PathVariable int id){
        Funcionario funcionario = new Funcionario(id);
        try {
            if (FuncionarioDAO.MudancaStatus(funcionario, PropriedadeStatus.Desativa)) {
                return listaFuncionario(model);
            }else{
                model.addAttribute("MSG", "Erro ao Desativado");
            }
        } catch (Exception e) {
            log.error(""+e);
            model.addAttribute("MSG", e.getMessage());
        }
        return "mensagem";
    }
    
    @RequestMapping(value = "/admin/Ativar/{id}")
    public String Ativar(Model model, @PathVariable int id){
        Funcionario funcionario = new Funcionario(id);
        try {
            if (FuncionarioDAO.MudancaStatus(funcionario, PropriedadeStatus.Ativo)) {
                return listaFuncionario(model);
            }else{
                model.addAttribute("MSG", "Erro ao Ativar");
            }
        } catch (Exception e) {
            log.error(""+e);
            model.addAttribute("MSG", e.getMessage());
        }
        return "mensagem";
    }
    
    @RequestMapping("/admin/CadastroFuncionario")
    public String cadastro(Model model) {
        return "CadastroFuncionario";
    }
    
    @PostMapping("/admin/CadastroFuncionario/add")
    public String add(Model model, Funcionario funcionario) {
        try {
            funcionario.setSenha(Crypto.HashSenha(funcionario.getSenha()));
            if (FuncionarioDAO.Adicionar(funcionario)) {
                model.addAttribute("MSG", "Funcionario ao Adicionado com Sucesso!");
            }else {
                model.addAttribute("MSG", "Erro ao Adicionar");
            }
        } catch (Exception e){
            log.error(""+e);
            model.addAttribute("MSG", e.getMessage());
        }
        return "mensagem";
    }    
}
