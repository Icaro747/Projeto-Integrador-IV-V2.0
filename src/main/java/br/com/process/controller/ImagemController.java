package br.com.process.controller;

import br.com.process.DAO.ImagensDAO;
import br.com.process.entidade.Imagen;
import br.com.process.entidade.Produto;
import br.com.process.uteis.GerenciadorArquivo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Icaro
 */
@Controller @Slf4j
public class ImagemController {

    private final String FOLDER_IMG_TAG = GerenciadorArquivo.CaminhoTag();
    private final String FOLDER_IMG_PRODUTO = "C://Users//Icaro//Documents//NetBeansProjects//PI//Projeto-Integrador-IV-V2.0//src//main//resources//static//img//uploads//";

    @PostMapping("/admin/CadastroProduto/NewImg")
    public String newImgProduto(Model model, @ModelAttribute(value="imagen")Imagen imagen, @RequestParam("img") MultipartFile file, RedirectAttributes redirectAttributes) {

        try {
            if (!file.isEmpty()) {
                log.info("ID Produto:"+imagen.getId());
                imagen.NewName(file.getOriginalFilename());

                if (ImagensDAO.Adicionar(imagen)) {
                    byte[] bytes = file.getBytes();
                    Path path = Paths.get(FOLDER_IMG_PRODUTO + imagen.getName());
                    Files.write(path, bytes);

                    model.addAttribute("img", imagen);
                    model.addAttribute("MSG", "Nova imagen para produto foi adisinonada com suseso");
                    return "MsgImg";
                }
            } else {
                model.addAttribute("MSG", "imagen não foi encontrado");
            }
        } catch (IOException e) {
            log.error(""+e);
            model.addAttribute("MSG", e.getMessage());
        }
        return "mensagem";

    }
    
    @RequestMapping("/admin/CadastroProduto/MaisIMG")
    public String MaisIMG(Model model, Produto produto){
        model.addAttribute("NewProduto", produto);
        return "NewImagemProduto";
    }
        

    @RequestMapping("/IMG/PRODUTO/{name}")
    public String login(Model model, @PathVariable String name) {
        model.addAttribute("IMG", FOLDER_IMG_TAG + " \\ " + name);
        return "IMG";
    }

    public String ListaImagenProduto(Model model) {
        return "";
    }

}
