package br.com.process.controller;

import br.com.process.DAO.FuncionarioDAO;
import br.com.process.entidade.Funcionario;
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
 * @author Dell
 */
@Controller
@Slf4j
public class FuncionarioController {

    @RequestMapping(value = "/admin/AtualizarFuncionario/{id}")
    public String telaAtualizar(Model model, @PathVariable int id) {
        try {
            Funcionario funcionario = new Funcionario(id);
            model.addAttribute("funcionario", FuncionarioDAO.getFuncionario(funcionario));
            log.info("redirecionando pra tela de update funcionario");
            return "FuncionarioUpdate";
        } catch (Exception e) {
            model.addAttribute("MSG", e);
        }
        return "mensagem";
    }

    @PostMapping("/admin/AtualizarFunc")
    public String Atualizar(Model model, @Valid @ModelAttribute(value = "funcionario") Funcionario funcionario, BindingResult result) {
        try {
            if (true) {
                if (FuncionarioDAO.Atualizar(funcionario)) {
                    model.addAttribute("MSG", "Atualizar com Sucesso");
                } else {
                    model.addAttribute("MSG", "Erro ao Atualizar");
                }
            } else {
                log.info("validar funcionario erro");
                return "FuncionarioUpdate:All";
            }
        } catch (Exception e) {
            model.addAttribute("MSG", e);
        }
        return "mensagem";
    }
}
