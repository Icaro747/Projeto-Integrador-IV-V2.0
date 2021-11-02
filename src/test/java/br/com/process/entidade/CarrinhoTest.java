package br.com.process.entidade;

import java.util.ArrayList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Icaro
 */
public class CarrinhoTest {
    
    private Carrinho isCarrinho;
    
    public CarrinhoTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        isCarrinho = new Carrinho();
        isCarrinho.AddNewProduto(new Produto(1, "roupa1", "marca", "descricao", 1, 12, 25, true));
        isCarrinho.AddNewProduto(new Produto(2, "roupa2", "marca", "descricao", 1, 12, 25, true));
        isCarrinho.AddNewProduto(new Produto(3, "roupa3", "marca", "descricao", 1, 12, 25, true));
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of setProdutos method, of class Carrinho.
     */
    @Test
    public void testSetProdutos() {
        setUp();
        System.out.println("setProdutos");
        ArrayList<Produto> produtos = isCarrinho.getProdutos();
        Carrinho instance = new Carrinho();
        instance.setProdutos(produtos);
        assertEquals(instance.getProdutos(), isCarrinho.getProdutos());
    }

    /**
     * Test of AddNewProduto method, of class Carrinho.
     */
    @Test
    public void testAddNewProduto() {
        System.out.println("AddNewProduto");
        Produto produto = new Produto(0);
        Carrinho instance = new Carrinho();
        instance.AddNewProduto(produto);
        assertTrue(instance.getQTD_Produtos() == 1);
    }

    /**
     * Test of AddProduto method, of class Carrinho.
     */
    @Test
    public void testAddProduto() {
        System.out.println("AddProduto");
        Produto produto = new Produto(1);
        Carrinho instance = new Carrinho();
        instance.AddProduto(produto);
        assertTrue(instance.getQTD_Produtos() == 1);
        assertFalse(instance.getQTD_Produtos() == 0);
    }

    /**
     * Test of RemoveItem method, of class Carrinho.
     */
    @Test
    public void testRemoveItem() {
        setUp();
        System.out.println("RemoveItem");
        int indice = 2;
        Carrinho instance = isCarrinho;
        instance.RemoveItem(2);
        assertTrue(instance.getQTD_Produtos() == indice);
    }

    /**
     * Test of OrderLista method, of class Carrinho.
     */
    @Test
    public void testOrderLista() {
        System.out.println("OrderLista");
        Carrinho instance = new Carrinho();
        instance.OrderLista();
    }

    /**
     * Test of AlignLista method, of class Carrinho.
     */
    @Test
    public void testAlignLista() {
        System.out.println("AlignLista");
        Carrinho instance = new Carrinho();
        instance.AlignLista();
    }

    /**
     * Test of getProduto method, of class Carrinho.
     */
    @Test
    public void testGetProduto() {
        System.out.println("getProduto");
        int indice = 0;
        Carrinho instance = new Carrinho();
        Produto expResult = new Produto(1);
        instance.AddNewProduto(expResult);
        Produto result = instance.getProduto(indice);
        assertEquals(expResult, result);
    }

    /**
     * Test of getQTD_Produtos method, of class Carrinho.
     */
    @Test
    public void testGetQTD_Produtos() {
        System.out.println("getQTD_Produtos");
        Carrinho instance = new Carrinho();
        int expResult = 0;
        int result = instance.getQTD_Produtos();
        assertEquals(expResult, result);
    }

    /**
     * Test of getTotalVenda method, of class Carrinho.
     */
    @Test
    public void testGetTotalVenda() {
        System.out.println("getTotalVenda");
        Carrinho instance = new Carrinho();
        double expResult = 0.0;
        double result = instance.getTotalVenda();
        assertEquals(expResult, result, 0.0);
    }
    
}
