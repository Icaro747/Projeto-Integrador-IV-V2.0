package br.com.process.controller;

import br.com.process.DAO.ProdutoDAO;
import br.com.process.entidade.Produto;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Icaro
 */
@Controller
public class ImageUpload {
    
    @ResponseBody
    @RequestMapping(value = "getimage/{phote}", method = RequestMethod.GET)
    public ResponseEntity<ByteArrayResource> getImage(@PathVariable("photo") String photo){
        
        try {
            if (!photo.equals("") || photo != null) {
                Path FileName = Paths.get("uploads", photo);
                byte[] Buffer = Files.readAllBytes(FileName);
                ByteArrayResource BAR = new ByteArrayResource(Buffer);
                
                return ResponseEntity.ok().contentLength(Buffer.length).contentType(MediaType.parseMediaType("image/png")).body(BAR);
            }
        } catch (Exception e) {
            System.out.println("Erro de IMG" + e);
        }
        return ResponseEntity.badRequest().build();
    }
    
    /**
     * 
     */
    private static String UPLOADED_FOLDER = "C://Users//Icaro//Documents//NetBeansProjects//PI//Projeto-Integrador-IV-V2.0//src//main//resources//static//img//uploads//";

    
    @RequestMapping("/uploadIMG")
    public String index(Model model, Produto produto) {
        model.addAttribute("produto", ProdutoDAO.getProduto(produto));
        return "upload_IMG";
    }
    
    @PostMapping("/upload") // //new annotation since 4.3
    public String singleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes, Model model) {

        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            return "redirect:uploadStatus";
        }

        try {

            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);

            redirectAttributes.addFlashAttribute("message", "You successfully uploaded '" + file.getOriginalFilename() + "'");

        } catch (IOException e) {
            model.addAttribute("MSG", e.getMessage());
            System.err.println("------------------>>ERRO<<------------------");
            System.err.println(e);
            return "mensagem";
        }

        return "redirect:/uploadStatus";
    }

    @GetMapping("/uploadStatus")
    public String uploadStatus() {
        return "uploadStatus";
    }
}
