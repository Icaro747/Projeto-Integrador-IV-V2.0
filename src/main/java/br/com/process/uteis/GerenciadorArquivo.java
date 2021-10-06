package br.com.process.uteis;

import java.io.File;

/**
 *
 * @author Icaro
 */
public class GerenciadorArquivo {
    
    public static boolean Pasta(){
        
        File directory = new File("");
        
        String tags = directory.getAbsolutePath()+"\\img\\tags\\";
        String uploads = directory.getAbsolutePath()+"\\img\\uploads\\";
        String img = directory.getAbsolutePath()+"\\img\\";
        
        if (!new File(img).exists()) {
            new File(img).mkdir(); 
        }
        
        if (!new File(uploads).exists()) {
            new File(uploads).mkdir();
        }
        
        if (!new File(tags).exists()) {
            new File(tags).mkdir();
        }
        
        return new File(img).exists() && new File(uploads).exists() && new File(tags).exists();
    }
    
    public static String Caminho(){
        File directory = new File("");
        return directory.getAbsolutePath();
    }
    
    public static String CaminhoProduto(){
        return Caminho() + "\\img\\uploads\\";
    }
    
    public static String CaminhoTag(){
        return Caminho() + "\\img\\tags\\";
    }
}
