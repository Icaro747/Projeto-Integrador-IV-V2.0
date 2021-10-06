package br.com.process.entidade;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Vinicius
 */
public class Pagina {

    private int paginas;
    private int pageatual;

    public Pagina(int paginas, int pageatual) {
        this.paginas = paginas;
        this.pageatual = pageatual;
    }

    public int getPaginas() {
        return paginas;
    }

    public void setPaginas(int paginas) {
        this.paginas = paginas;
    }

    public int getPageatual() {
        return pageatual;
    }

    public void setPageatual(int pageatual) {
        this.pageatual = pageatual;
    }

}
