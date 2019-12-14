/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaTest;

import model.DAO;
import model.Product;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import model.Client;
import model.Commande;
import model.Panier;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import org.hsqldb.cmdline.SqlFile;
import org.hsqldb.cmdline.SqlToolError;

public class javaTest {
    
    private static DataSource myDataSource;
	private static Connection myConnection ;
	
	private DAO dao;
	
	@Before
	public  void setUp() throws IOException, SqlToolError, SQLException {
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
		String sqlFilePath = javaTest.class.getResource(filename).getFile();
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
             	dao = null; // Pas vraiment utile

	}
        
        
        @Test
	public void addProductTest() throws SQLException {
		Product p = new Product(100, "Chips", 1, 1, "300g", 1.50f, 10, 0, 0, false);
                List<Product> before = dao.allProducts();
                dao.addProduct(p);
                List<Product> after = dao.allProducts();
		assertEquals("Nombre de produits incorrect !", after.size(), before.size()+1);
	}
        
        
        @Test
        public void addCommandeTest() throws SQLException {
            Commande c = new Commande("ALFKI",0, "Alfreds Futterkiste", "Obere Str. 57", "Berlin", "", "12209", "Allemagne", 0);
            Panier panier = new Panier();
            Product p = dao.getProduct(1);
            panier.ajout(p);
            int ok = dao.addCommande(c, panier);
            assertEquals("Erreur ajout commande !", 1, ok);
        }
        
        @Test
        public void addCommandeTest2() throws SQLException {
            Commande c = new Commande("ALFKI",0, "Alfreds Futterkiste", "Obere Str. 57", "Berlin", "", "12209", "Allemagne", 0);
            Panier panier = new Panier();
            Product p = dao.getProduct(1);
            panier.ajout(p);
            int before = dao.allProducts().get(0).getUniteEnStock();
            dao.addCommande(c, panier);
            int after = dao.allProducts().get(0).getUniteEnStock();
            assertEquals("Erreur ajout commande !", after+1, before);
        }
        
        
        @Test
        public void getClientTest() throws SQLException {
            Client c = dao.getClient("Maria Anders", "ALFKI");
            assertEquals("Erreur getClient", true, c.getContact().equals("Maria Anders"));
        }
        
        @Test
	public void SupprProductTest() throws SQLException {
		Product p = new Product(78, "Chips", 1, 1, "300g", 1.50f, 10, 0, 0, false);
                dao.addProduct(p);
                List<Product> before = dao.allProducts();
                dao.delProduct(p.getReference());
                List<Product> after = dao.allProducts();
		assertEquals("Nombre de produits incorrect !", after.size(), before.size()-1);
	}
    
}
