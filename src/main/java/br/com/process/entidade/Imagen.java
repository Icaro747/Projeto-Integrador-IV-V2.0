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
    @Getter private String name;
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
     * @param id ID da Imagen
     * @param name Nome da Imagen
     * @param id_Produto ID do Produto
     */
    public Imagen(int id, String name, int id_Produto) {
        this.id = id;
        if (id_Produto > 0) {
            this.id_produto = id_Produto;
        } else {
            throw new IllegalArgumentException("ID de Produto inválido");
        }
        if (name != null) {
            this.name = name;
        } else {
            throw new IllegalArgumentException("Imagen inválido");
        }
    }

    /**
     *
     * @param name Nome da Imagen
     * @param id_Produto ID do Produto
     */
    public Imagen(String name, int id_Produto) {
        if (id_Produto > 0) {
            this.id_produto = id_Produto;
        } else {
            throw new IllegalArgumentException("ID de Produto inválido");
        }
        if (name != null) {
            this.name = name;
        } else {
            throw new IllegalArgumentException("Imagen inválido");
        }
    }

    public void setName(String name) {
        this.name = "Produto_IMG_" + name;
    }
    
    
}
