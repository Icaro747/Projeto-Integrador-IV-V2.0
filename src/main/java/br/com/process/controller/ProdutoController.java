package br.com.process.controller;

import br.com.process.DAO.ImagensDAO;
import br.com.process.DAO.ProdutoDAO;
import br.com.process.DAO.TagDAO;
import br.com.process.entidade.Pagina;
import br.com.process.entidade.Produto;
import br.com.process.uteis.PropriedadeStatus;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Vinicius
 * @author Icaro
 */
@Controller @Slf4j
public class ProdutoController {

    @GetMapping("/admin/CadastroProduto")
    public String Cadastro(Model model) {
        model.addAttribute("produto", new Produto());
        model.addAttribute("listTags", TagDAO.GetTags());
        return "cadastroProduto";
    }
    
    @PostMapping("/admin/CadastroProduto")
    public String Add(Model model, @Valid @ModelAttribute(value="produto") Produto produto, BindingResult result) {
        log.info("Produto é válido:"+ !result.hasErrors());
        if (!result.hasErrors()) {
            try {
                int resotadoDAO = ProdutoDAO.Adicionar(produto);
                if (resotadoDAO != -1) {
                    if (resotadoDAO != 0) {
                        produto.setId_produto(resotadoDAO);
                        model.addAttribute("produto", produto);
                        return "newImagemProduto";
                    }else{
                        log.error("Erro ao pegar ID do produto. ID do Produto Add:" + resotadoDAO);
                        model.addAttribute("MSG", "Erro produto não encontrado");
                    }
                } else {
                    log.error("Erro ao cadastrar produtos no banco de dados");
                    model.addAttribute("MSG", "Erro ao Adicionar");
                }
            } catch (Exception e) {
                log.error(""+e);
                model.addAttribute("MSG", e.getMessage());
            }
            return "mensagem";
        }else{
            model.addAttribute("listTags", TagDAO.GetTags());
            return "cadastroProduto";
        }
    }

    @RequestMapping("/admin/AtualizarProduto")
    public String TeleAtualizar(Model model, Produto produto) {
        model.addAttribute("listTags", TagDAO.GetTags());
        model.addAttribute("Produto", ProdutoDAO.getProduto(produto));
        return "atualizarProduto";
    }

    @PostMapping("/Atualizar")
    public String Atualizar(Model model, Produto produto) {
        try {
            if (ProdutoDAO.Atualizar(produto)) {
                model.addAttribute("MSG", "Atualizar com Sucesso");
            } else {
                model.addAttribute("MSG", "Erro ao Atualizar");
            }
        } catch (Exception e) {
            log.error(""+e);
            model.addAttribute("MSG", e.getMessage());
        }
        return "mensagem";
    }

    @RequestMapping(value = "/produto/{id}")
    public String DetalhesProduto(Model model, @PathVariable int id) {
        try {
            log.info("iniciando busca por produto");
            Produto produto = new Produto(id);
            log.info("Produto ID:" + produto.getId_produto());
            produto = ProdutoDAO.getProduto(produto);
            if (produto.getNome()!=null) {
                model.addAttribute("imagens", ImagensDAO.getImgs(produto));
                model.addAttribute("produto", produto);
                log.info("Produto encontrado");
                log.info(produto.toString());
                return "produto";
            }else{
                model.addAttribute("MSG", "Produto não encontrado");
                log.info("Produto não encontrado. ID do produto:"+id);
            }
        } catch (Exception e) {
            model.addAttribute("MSG", e);
            log.error("erro ao procurar um produto em específico ID do produto:"+id);
            log.error(""+e);
        }
        return "mensagem";
    }
    
    @RequestMapping("/admin/listaProduto")
    public String ListaProduto(Model model) {
        try {
            Pagina pagina = new Pagina();
            pagina.setPageAtual(0);
            pagina.setQuantidadeItems(100);
            List<Produto> Estoque = ProdutoDAO.getEstoque(PropriedadeStatus.Desativa, pagina);
            Estoque.forEach(produto -> {
                produto.setQtdImg(ProdutoDAO.QuantidadeImagensProduto(produto));
            });
            model.addAttribute("listaProduto", Estoque);
            return "listaProduto";
        } catch (Exception e) {
            log.error(""+e);
            model.addAttribute("MSG", e.getMessage());
            return "mensagem";
        }
    }
    
    @RequestMapping("/admin/buscar")
    public String AdimBuscar(Model model, Produto produto) {
        try {
            log.info("iniciando busca por produtos");
            List<Produto> Estoque = ProdutoDAO.BuscarProdutos(produto, PropriedadeStatus.Desativa);
            log.info(Estoque.size() + " produtos encontrados");
            Estoque.forEach(pro -> {
                pro.setQtdImg(ProdutoDAO.QuantidadeImagensProduto(pro));
            });
            model.addAttribute("listaProduto", Estoque);
            return "listaProduto";
        } catch (Exception e) {
            log.error(""+e);
            model.addAttribute("MSG", e.getMessage());
            return "mensagem";
        }
    }
    
    @RequestMapping("Desativar")
    public String Desativar(Model model, Produto produto) {
        try {
            if (ProdutoDAO.MudancaStatus(produto, PropriedadeStatus.Desativa)) {
                return ListaProduto(model);
            } else {
                model.addAttribute("MSG", "Erro ao Desativado");
            }
        } catch (Exception e) {
            log.error(""+e);
            model.addAttribute("MSG", e.getMessage());
        }
        return "mensagem";
    }
    
    @RequestMapping("Ativar")
    public String Ativar(Model model, Produto produto) {
        try {
            if (ProdutoDAO.MudancaStatus(produto, PropriedadeStatus.Ativo)) {
                return ListaProduto(model);
            } else {
                model.addAttribute("MSG", "Erro ao Ativar");
            }
        } catch (Exception e) {
            log.error(""+e);
            model.addAttribute("MSG", e.getMessage());
        }
        return "mensagem";
    }
    
    @RequestMapping("/Pages")
    public String Pagina(Model model) {
        try {
            model.addAttribute("lista", ProdutoDAO.ProdutoPagn(PropriedadeStatus.Ativo, 0));
            ArrayList<Pagina> paginas = new ArrayList<>();
            for (int i = 0; i < ProdutoDAO.QuantProd(); i++) {
                if (i % 10 == 0) {
                    int result = i / 10 + 1;
                    Pagina pagina = new Pagina(result, 0,1000);
                    paginas.add(pagina);
                }
            }
            model.addAttribute("listaPagina", paginas);
            return "buscarProduto";
        } catch (Exception e) {
            log.error(""+e);
            model.addAttribute("MSG", e.getMessage());
            return "mensagem";
        }
    }
    
    @RequestMapping("/Pages/{Id}")
    public String Paginas(Model model, @PathVariable int Id) {
        try {
            model.addAttribute("lista", ProdutoDAO.ProdutoPagn(PropriedadeStatus.Ativo, Id - 1));
            ArrayList<Pagina> paginas = new ArrayList<>();
            for (int i = 0; i < ProdutoDAO.QuantProd(); i++) {
                if (i % 10 == 0) {
                    int result = i / 10 + 1;
                    Pagina pagina = new Pagina(result, Id, 12);
                    paginas.add(pagina);
                }
            }
            model.addAttribute("listaPagina", paginas);
            return "buscarProduto";
        } catch (Exception e) {
            log.error(""+e);
            model.addAttribute("MSG", e.getMessage());
            return "mensagem";
        }
    }
}
