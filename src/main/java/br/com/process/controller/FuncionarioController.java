package br.com.process.controller;

import br.com.process.DAO.FuncionarioDAO;
import br.com.process.DAO.TagDAO;
import br.com.process.entidade.Funcionario;
import br.com.process.uteis.PropriedadeStatus;

import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Vinicius
 * @author Icaro
 */
@Controller @Slf4j
public class FuncionarioController {

    /**
     *
     * @param model
     * @return
     */
    @RequestMapping("/admin/CadastroFuncionario")
    public String cadastro(Model model) {
        model.addAttribute("tags", TagDAO.getTags());
        return "CadastroFuncionario";
    }

    @PostMapping("/admin/CadastroFuncionario/add")
    public String add(Model model, Funcionario funcionario) {
        try {
            int resotadoDAO = FuncionarioDAO.Adicionar(funcionario);
            if (resotadoDAO != -1) {
                model.addAttribute("MSG", "Funcionario ao Adicionado com Sucesso!");
            }else {
                model.addAttribute("MSG", "Erro ao Adicionar");
            }
        } catch (Exception e) {
            model.addAttribute("MSG", e);
        }
        return "mensagem";
    }
           
}
