package br.com.process.controller;

import br.com.process.DAO.TagDAO;
import br.com.process.entidade.Tag;
import br.com.process.uteis.GerenciadorArquivo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Icaro
 */
@Controller
public class TagController {
    
    private final static String FOLDER_IMG_UPLOADED = "C://Users//Icaro//Documents//NetBeansProjects//PI//Projeto-Integrador-IV-V2.0//src//main//resources//static//img//tags//";
    
    @RequestMapping("/admin/CadastroTag")
    public String login(){
        return "CadastroTag";
    }
    
    @PostMapping("/NewTag")
    public String NewTag(Model model, Tag tag, @RequestParam("img") MultipartFile file, RedirectAttributes redirectAttributes){
        try {
            tag.newName_IMG(file.getOriginalFilename());
            if (TagDAO.Adicionar(tag)) {
                if (!file.isEmpty() && GerenciadorArquivo.Pasta()) {
                    byte[] bytes = file.getBytes();
                    Path path = Paths.get(FOLDER_IMG_UPLOADED + tag.getName_img());
                    Files.write(path, bytes);
                }
                model.addAttribute("MSG", "Nova Tag Adicionado com Sucesso");
            }else{
                model.addAttribute("MSG", "Erro ao Adicionar");
            }
        } catch (IOException e) {
            model.addAttribute("MSG", e);
        }
        return "mensagem";
    }
    
    @RequestMapping("/admin/listaTag")
    public String Tags(Model model){
        try {
            model.addAttribute("listaTags", TagDAO.getTags());
        } catch (Exception e) {
            model.addAttribute("MSG", e);
            return "mensagem";
        }
        return "listaTag";
    }
    
    @RequestMapping("/admin/ExcluirTag")
    public String Excluir(Model model, Tag tag){
        try {
            if (TagDAO.Excluir(tag)) {
                model.addAttribute("MSG", "Tag excluída com sucesso");
            } else {
                model.addAttribute("MSG", "Erro ao excluir a Tag");
            }
        } catch (Exception e) {
            model.addAttribute("MSG", e);
        }
        return "mensagem";
    }
        
}
