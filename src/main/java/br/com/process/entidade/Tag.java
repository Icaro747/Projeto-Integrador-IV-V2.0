package br.com.process.entidade;

/**
 *
 * @author Icaro
 */
public class Tag {
    
    private int id_tag;
    private String nome_tag;

    public Tag() {
    }

    public Tag(int id_tag, String nome_tag) {
        this.id_tag = id_tag;
        this.nome_tag = nome_tag;
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
    
}
