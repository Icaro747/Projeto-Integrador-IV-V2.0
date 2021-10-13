package br.com.process.entidade;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author Icaro
 */
@ToString
@NoArgsConstructor
public class Imagen {

    @Getter @Setter private int id;
    @Getter @Setter private String name;
    @Getter @Setter private int id_produto;

    /**
     * 
     * @param id ID da Imagen
     */
    public Imagen(int id) {
        this.id = id;
    }

    /**
     *
     * @param id         ID da Imagen
     * @param name       Nome da Imagen
     * @param id_Produto ID do Produto
     */
    public Imagen(int id, String name, int id_Produto) {
        this.id = id;
        this.id_produto = id_Produto;
        this.name = name;
    }

    /**
     *
     * @param name       Nome da Imagen
     * @param id_Produto ID do Produto
     */
    public Imagen(String name, int id_Produto) {
        this.id_produto = id_Produto;
        this.name = name;
    }

    public void NewName(String name) {
        this.name = "Produto_IMG_" + name;
    }
    
    
}
