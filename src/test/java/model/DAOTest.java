package model;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import org.hsqldb.cmdline.SqlFile;
import org.hsqldb.cmdline.SqlToolError;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


public class DAOTest {
    
    private static DataSource myDataSource;
    private static Connection myConnection ;
    private DAO dao;
    
    public DAOTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws IOException, SqlToolError, SQLException {
        // On crée la connection vers la base de test "in memory"
		myDataSource = getDataSource();
		myConnection = myDataSource.getConnection();
		// On crée le schema de la base de test
		executeSQLScript(myConnection, "comptoirs_schema_derby.sql");
		// On y met des données
		executeSQLScript(myConnection, "comptoirs_data.sql");		

            	dao = new DAO(myDataSource);
    }
    
    private void executeSQLScript(Connection connexion, String filename)  throws IOException, SqlToolError, SQLException {
		// On initialise la base avec le contenu d'un fichier de test
		String sqlFilePath = DAOTest.class.getResource(filename).getFile();
		SqlFile sqlFile = new SqlFile(new File(sqlFilePath));

		sqlFile.setConnection(connexion);
		sqlFile.execute();
		sqlFile.closeReader();		
	}
    
    public static DataSource getDataSource() {
		org.hsqldb.jdbc.JDBCDataSource ds = new org.hsqldb.jdbc.JDBCDataSource();
		ds.setDatabase("jdbc:hsqldb:mem:testcase;shutdown=true");
		ds.setUser("sa");
		ds.setPassword("sa");
		return ds;
	}
    
    @After
    public void tearDown() throws IOException, SqlToolError, SQLException {
        myConnection.close(); // La base de données de test est détruite ici

    }

    /**
     * Test of addProduct method, of class DAO.
     * @throws java.sql.SQLException
     */
    @Test
    public void testAddProduct() throws SQLException {
        System.out.println("addProduct");
        String nom = "Chips";
	Product pr = new Product(78, nom, 1, 1, "300g", 1.50f, 10, 0, 0, false);
        dao.addProduct(pr);
        Product after = dao.getProduct(78);
        assertTrue("Ajout produit incorrect !", after.getNom().equals(nom));
    }

    /**
     * Test of delProduct method, of class DAO.
     * @throws java.sql.SQLException
     */
    @Test
    public void testDelProduct() throws SQLException {
        System.out.println("delProduct");
        Product p = new Product(78, "Chips", 1, 1, "300g", 1.50f, 10, 0, 0, false);
                dao.addProduct(p);
                List<Product> before = dao.allProducts();
                dao.delProduct(p.getReference());
                List<Product> after = dao.allProducts();
		assertEquals("Nombre de produits incorrect !", after.size(), before.size()-1);
    }

    /**
     * Test of updateProduct method, of class DAO.
     * @throws java.sql.SQLException
     */
    @Test
    public void testUpdateProduct() throws SQLException {
        System.out.println("updateProduct");
        Product pr = dao.getProduct(1);
        String nom = "Test";
        pr.setNom(nom);
        dao.updateProduct(pr);
        assertTrue("MAJ Produit incorrect!", nom.equals(dao.getProduct(1).getNom()));
    }

    /**
     * Test of allProducts method, of class DAO.
     * @throws java.sql.SQLException
     */
    @Test
    public void testAllProducts() throws SQLException {
        System.out.println("allProducts");
        Product pr = new Product(100, "Chips", 1, 1, "300g", 1.50f, 10, 0, 0, false);
        List<Product> before = dao.allProducts();
        dao.addProduct(pr);
        List<Product> after = dao.allProducts();
        assertEquals("Nombre de produits incorrect !", after.size(), before.size()+1);
    }

    /**
     * Test of allProductsCat method, of class DAO.
     * @throws java.sql.SQLException
     */
    @Test
    public void testAllProductsCat() throws SQLException {
        System.out.println("allProductsCat");
        // Ajout produit dans catégorie 1
        Product pr = new Product(100, "Chips", 1, 1, "300g", 1.50f, 10, 0, 0, false);
        // Ajout produit dans catégorie 2
        Product pr2 = new Product(100, "Test", 1, 2, "300g", 1.50f, 10, 0, 0, false);
        List<Product> before = dao.allProductsCat("1");
        dao.addProduct(pr);
        List<Product> after = dao.allProductsCat("1");
        assertEquals("Nombre de produits incorrect !", after.size(), before.size()+1);
    }


    /**
     * Test of getProduct method, of class DAO.
     * @throws java.sql.SQLException
     */
    @Test
    public void testGetProduct() throws SQLException {
        System.out.println("getProduct");
        int id = 1;
        Product result = dao.getProduct(id);
        assertTrue("getProduct incorrect!", result.getNom().equals("Chai"));
    }

    /**
     * Test of allCategories method, of class DAO.
     * @throws java.sql.SQLException
     */
    @Test
    public void testAllCategories() throws SQLException {
        System.out.println("allCategories");
        List<Categorie> result = dao.allCategories();
        assertEquals("allCategories incorrect!" , 8, result.size());
    }

    /**
     * Test of addCommande method, of class DAO.
     * @throws java.sql.SQLException
     */
    @Test
    public void testAddCommande() throws SQLException {
        System.out.println("addCommande");
        Commande c = new Commande("ALFKI",0, "Alfreds Futterkiste", "Obere Str. 57", "Berlin", "", "12209", "Allemagne", 0);
        Panier panier = new Panier();
        Product p = dao.getProduct(1);
        panier.ajout(p);
        int ok = dao.addCommande(c, panier);
        assertEquals("Erreur ajout commande !", 1, ok);
    }


    /**
     * Test of getClient method, of class DAO.
     * @throws java.sql.SQLException
     */
    @Test
    public void testGetClient_String() throws SQLException {
        System.out.println("getClient");
        Client c = dao.getClient("ALFKI");
        assertTrue("Erreur getClient", c.getCode().equals("ALFKI"));
    }

    /**
     * Test of getClient method, of class DAO.
     * @throws java.sql.SQLException
     */
    @Test
    public void testGetClient_String_String() throws SQLException {
        System.out.println("getClient");
        Client c = dao.getClient("Maria Anders", "ALFKI");
        assertTrue("Erreur getClient", c.getCode().equals("ALFKI"));
    }

    /**
     * Test of updateClient method, of class DAO.
     * @throws java.sql.SQLException
     */
    @Test
    public void testUpdateClient() throws SQLException {
        System.out.println("updateClient");
        Client c = dao.getClient("ALFKI");
        c.setContact("Test name");
        dao.updateClient(c);
        assertTrue("updateClient incorrect!", dao.getClient("ALFKI").getContact().equals("Test name"));
    }

    /**
     * Test of getCommande method, of class DAO.
     * @throws java.sql.SQLException
     */
    @Test
    public void testGetCommande() throws SQLException {
        System.out.println("getCommande");
        int nbC = 10248;
        Panier result = dao.getCommande(nbC);
        Product p = result.get(0);
        Product p2 = dao.getProduct(11);
        assertTrue("getCommande incorrect!", p.equals(p2));
    }

    /**
     * Test of getCommandesByClient method, of class DAO.
     * @throws java.sql.SQLException
     */
    @Test
    public void testGetCommandesByClient() throws SQLException {
        System.out.println("getCommandesByClient");
        String idC = "ALFKI";
        List<Panier> result = dao.getCommandesByClient(idC);
        assertEquals("getCommandesByClient incorrect!", 4, result.size());
    }
    
}
