package model;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

    

public class PanierTest {
    
    private Panier panier;
    private Product pr;
    
    
    public PanierTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        pr = new Product(78, "Chips", 1, 1, "300g", 1.50f, 10, 0, 0, false);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of contains method, of class Panier.
     */
    @Test
    public void testContains() {
        System.out.println("contains");
        Panier instance = new Panier();
        instance.ajout(pr);
        boolean result = instance.contains(pr);
        assertTrue("testContains incorrect!", result);
    }

    /**
     * Test of indexOf method, of class Panier.
     */
    @Test
    public void testIndexOf() {
        System.out.println("indexOf");
        Panier instance = new Panier();
        instance.ajout(pr);
        int result = instance.indexOf(pr);
        assertEquals("indexOf incorrect!", 0, result);
    }

    /**
     * Test of ajout method, of class Panier.
     */
    @Test
    public void testAjout() {
        System.out.println("ajout");
        Panier instance = new Panier();
        instance.ajout(pr);
        assertEquals("ajout incorrect!", 1, instance.size());
    }

    /**
     * Test of reduireQte method, of class Panier.
     */
    @Test
    public void testReduireQte() {
        System.out.println("reduireQte");
        Panier instance = new Panier();
        pr.setQuantitePanier(2);
        instance.add(pr);   // ajout produit avec qte 2
        instance.reduireQte(pr);
        Product after = instance.get(0);
        assertEquals("reduireQte incorrect!", 1, after.getQuantitePanier());
    }

    /**
     * Test of supprimer method, of class Panier.
     */
    @Test
    public void testSupprimer() {
        System.out.println("supprimer");
        Panier instance = new Panier();
        instance.ajout(pr);
        int before = instance.size();
        instance.supprimer(pr);
        int after = instance.size();
        assertEquals("supprimer incorrect!", before-1, after);
    }
    
}
