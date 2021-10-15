package br.com.process.controller;

import br.com.process.DAO.FuncionarioDAO;
import br.com.process.DAO.ProdutoDAO;
import br.com.process.entidade.Funcionario;
import br.com.process.entidade.Pagina;
import br.com.process.entidade.Produto;
import br.com.process.uteis.PropriedadeStatus;
import br.com.process.uteis.Crypto;

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
            model.addAttribute("MSG", e);
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
            model.addAttribute("MSG", e);
        }
        return "mensagem";
    }
    
    
    @RequestMapping("/admin/listaFuncionario")
    public String listaFuncionario (Model model){
        model.addAttribute("listaFuncionario", FuncionarioDAO.getUsuario(PropriedadeStatus.Desativa));
        return "listaFuncionario";
    }
}