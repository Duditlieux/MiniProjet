/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

public class DAO {
    
    private final DataSource myDataSource;
    
    public DAO(DataSource dataSource) {
		myDataSource = dataSource;
	}
    
    public int addProduct(Product pr) throws SQLException {
        int result=0;
        String sql = "INSERT INTO PRODUIT(Nom,Fournisseur,Categorie,Quantite_par_unite,Prix_unitaire,Unites_en_stock,Unites_commandees,Niveau_de_reapprovi,Indisponible) VALUES(?,?,?,?,?,?,?,?,?)";
        try (Connection myConnection = myDataSource.getConnection(); 
            PreparedStatement statement = myConnection.prepareStatement(sql)) {
                statement.setString(1, pr.getNom());
                statement.setInt(2, pr.getFournisseur());
                statement.setInt(3, pr.getCategorie());
                statement.setString(4, pr.getQuantiteParUnite());
                statement.setFloat(5, pr.getPrixUnitaire());
                statement.setInt(6, pr.getUniteEnStock());
                statement.setInt(7, pr.getUniteCommandees());
                statement.setInt(8, pr.getNiveauDeReapprovisionnement());
                statement.setInt(9, (pr.getIndisponible()) ? 1 : 0);
                result = statement.executeUpdate();
            }
        return result;
    }
    
    
    public int delProduct(int id) throws SQLException {
        int result=0;
        String sql = "DELETE FROM PRODUIT WHERE Reference = ?";
        try (Connection connection = myDataSource.getConnection(); 
             PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, id);
                result = stmt.executeUpdate();
        }
        return result;
    }
    
    
    public List<Product> allProducts() throws SQLException {

        List<Product> result = new ArrayList<>();

        String sql = "SELECT * FROM Produit ORDER BY Reference";
        try (Connection connection = myDataSource.getConnection(); 
             PreparedStatement stmt = connection.prepareStatement(sql)) {
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                        int id = rs.getInt("Reference");
                        String name = rs.getString("Nom");
                        int four = rs.getInt("Fournisseur");
                        int cat = rs.getInt("Categorie");
                        String qteu = rs.getString("Quantite_par_unite");
                        float prix = rs.getFloat("Prix_unitaire");
                        int stock = rs.getInt("Unites_en_stock");
                        int ucomm = rs.getInt("Unites_commandees");
                        int reappro = rs.getInt("Niveau_de_reapprovi");
                        int indispo = rs.getInt("Indisponible");
                        Product p = new Product(id, name, four, cat, qteu, prix, stock, ucomm, reappro, (indispo>0));
                        result.add(p);
                }
        }
        return result;
	}
    
}
