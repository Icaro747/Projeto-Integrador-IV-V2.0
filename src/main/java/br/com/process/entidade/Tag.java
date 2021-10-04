package br.com.process.entidade;

/**
 *
 * @author Icaro
 */
public class Tag {
    
    private int id_tag;
    private String nome_tag;
    private String name_img;

    public Tag() {
    }

    public Tag(int id_tag, String nome_tag, String img) {
        this.id_tag = id_tag;
        this.nome_tag = nome_tag;
        this.name_img = img;
    }
    
    public String getName_img() {
        return name_img;
    }

    public void setName_img(String name_img) {
        this.name_img = name_img;
    }

    public int getId_tag() {
        return id_tag;
    }

    public void setId_tag(int id_tag) {
        this.id_tag = id_tag;
    }

    public String getNome_tag() {
        return nome_tag;
    }

    public void setNome_tag(String nome_tag) {
        this.nome_tag = nome_tag;
    }

    public void newName_IMG(String img) {
        setName_img("Tags_IMG_"+img);
    }
    
}
