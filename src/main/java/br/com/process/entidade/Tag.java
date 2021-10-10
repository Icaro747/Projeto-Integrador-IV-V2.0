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
public class Tag {
    
    @Getter @Setter private int id_tag;
    @Getter @Setter private String nome_tag;
    @Getter @Setter private String name_img;

    public Tag(int id_tag, String nome_tag, String img) {
        this.id_tag = id_tag;
        this.nome_tag = nome_tag;
        this.name_img = img;
    }

    public void newName_IMG(String img) {
        setName_img("Tags_IMG_"+img);
    }
    
}
