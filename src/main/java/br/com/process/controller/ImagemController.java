package br.com.process.controller;

import br.com.process.uteis.GerenciadorArquivo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Icaro
 */
@Controller
public class ImagemController {

    private final String FOLDER_IMG_TAG = GerenciadorArquivo.CaminhoTag();
    private final String FOLDER_IMG_PRODUTO = GerenciadorArquivo.CaminhoProduto();
    
    @RequestMapping("/IMG/PRODUTO/{name}")
    public String login(Model model, @PathVariable String name){
        model.addAttribute("IMG", FOLDER_IMG_TAG +" \\ " +name);
        return "IMG";
    }
    
}
