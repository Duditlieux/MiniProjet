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
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;

public class DAO {

    private final DataSource myDataSource;

    public DAO(DataSource dataSource) {
        myDataSource = dataSource;
    }

    public int addProduct(Product pr) throws SQLException {
        int result = 0;
        String sql = "INSERT INTO PRODUIT(Nom,Fournisseur,Categorie,Quantite_par_unite,Prix_unitaire,Unites_en_stock,Unites_commandees,Niveau_de_reappro,Indisponible) VALUES(?,?,?,?,?,?,?,?,?)";
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
        int result = 0;
        String sql = "DELETE FROM PRODUIT WHERE Reference = ?";
        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            result = stmt.executeUpdate();
        }
        return result;
    }
    
    public int updateProduct(Product pr) throws SQLException {
        int result = 0;
        String sql = "UPDATE produit SET nom=?, fournisseur=?, categorie=?, quantite_par_unite=?, prix_unitaire=?, unites_en_stock=?, unites_commandees=?, niveau_de_reappro=?, indisponible=? WHERE reference=?";
        try (Connection myConnection = myDataSource.getConnection();
                PreparedStatement statement = myConnection.prepareStatement(sql)){
            statement.setString(1,pr.getNom());
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
                int reappro = rs.getInt("Niveau_de_reappro");
                int indispo = rs.getInt("Indisponible");
                Product p = new Product(id, name, four, cat, qteu, prix, stock, ucomm, reappro, (indispo > 0));
                result.add(p);
            }
        }
        return result;
    }

    public List<Product> allProductsCat(String id_cat) throws SQLException {

        List<Product> result = new ArrayList<>();

        String sql = "SELECT * FROM Produit WHERE CATEGORIE=? ORDER BY Reference";
        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, Integer.parseInt(id_cat));
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
                int reappro = rs.getInt("Niveau_de_reappro");
                int indispo = rs.getInt("Indisponible");
                Product p = new Product(id, name, four, cat, qteu, prix, stock, ucomm, reappro, (indispo > 0));
                result.add(p);
            }
        }
        return result;
    }

    public List<Categorie> allCategories() throws SQLException {

        List<Categorie> result = new ArrayList<>();

        String sql = "SELECT * FROM Categorie ORDER BY Code";
        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("Code");
                String libelle = rs.getString("Libelle");
                String desc = rs.getString("Description");
                Categorie c = new Categorie(id, libelle, desc);
                result.add(c);
            }
        }
        return result;
    }

    public int addCommande(Commande c, Panier panier) throws SQLException {
        int result = 0;
        String sql = "INSERT INTO COMMANDE(Client,Port,Destinataire,Adresse_livraison,Ville_livraison,Region_livraison,Code_postal_livrais,Pays_livraison,Remise) VALUES(?,?,?,?,?,?,?,?,?)";
        String sql2 = "UPDATE PRODUIT SET unites_en_stock = unites_en_stock-? WHERE reference=?";
        String sql3 = "INSERT INTO LIGNE VALUES(?,?,?)";
        try (Connection myConnection = myDataSource.getConnection();
                PreparedStatement statement = myConnection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                PreparedStatement statement2 = myConnection.prepareStatement(sql2);
                PreparedStatement statement3 = myConnection.prepareStatement(sql3)) {
            myConnection.setAutoCommit(false);
            try {
                // Commande
                statement.setString(1, c.getClient());
                statement.setFloat(2, c.getPort());
                statement.setString(3, c.getDestinataire());
                statement.setString(4, c.getAdresseDeLivraison());
                statement.setString(5, c.getVilleDeLivraison());
                statement.setString(6, c.getRegionDeLivraison());
                statement.setString(7, c.getCodePostalDeLivraison());
                statement.setString(8, c.getPaysDeLivraison());
                statement.setFloat(9, c.getRemise());
                result = statement.executeUpdate();

                int idC = -1;
                ResultSet rs = statement.getGeneratedKeys();
                if (rs.next()) {
                    idC = rs.getInt(1);
                }

                for (Map.Entry<Integer, Integer> entry : panier.entrySet()) {
                    Integer ref = entry.getKey();
                    Integer qte = entry.getValue();

                    // Produit
                    statement2.setInt(1, qte);
                    statement2.setInt(2, ref);
                    statement2.executeUpdate();

                    // Ligne
                    statement3.setInt(1, idC);
                    statement3.setInt(2, ref);
                    statement3.setInt(3, qte);
                }

                myConnection.commit();
            } catch (Exception ex) { // Une erreur s'est produite
                // On logge le message d'erreur
                Logger.getLogger("DAO").log(Level.SEVERE, "Transaction en erreur", ex);
                myConnection.rollback(); // On annule la transaction
                throw (ex); // On relève l'exception pour l'appelant
            } finally {
                myConnection.setAutoCommit(true); // On revient au mode de fonctionnement sans transaction
            }
        }
        return result;
    }

    public Client getClient(String code) throws SQLException {
        String sql = "SELECT * FROM client WHERE code=?";
        Client c = new Client();
        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, code);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                c.setSociete(rs.getString("societe"));
                c.setContact(rs.getString("contact"));
                c.setFonction(rs.getString("fonction"));
                c.setAdresse(rs.getString("adresse"));
                c.setVille(rs.getString("ville"));
                c.setRegion(rs.getString("region"));
                c.setCodePostal(rs.getString("code_postal"));
                c.setPays(rs.getString("pays"));
                c.setTelephone(rs.getString("telephone"));
                c.setFax(rs.getString("fax"));
            }
        }
        return c;
    }
    
    public Client getClient(String contact, String code) throws SQLException {
        String sql = "SELECT * FROM client WHERE contact='?' AND code=?";
        Client c = null;
        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, contact);
            stmt.setString(2, code);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                c = new Client();
                c.setCode(code);
                c.setSociete(rs.getString("societe"));
                c.setContact(contact);
                c.setFonction(rs.getString("fonction"));
                c.setAdresse(rs.getString("adresse"));
                c.setVille(rs.getString("ville"));
                c.setRegion(rs.getString("region"));
                c.setCodePostal(rs.getString("code_postal"));
                c.setPays(rs.getString("pays"));
                c.setTelephone(rs.getString("telephone"));
                c.setFax(rs.getString("fax"));
            }
        }
        return c;
    }
    
    public int updateClient(Client c) throws SQLException {
        int result = 0;
        String sql = "UPDATE client SET societe=?, contact=?, fonction=?, adresse=?, ville=?, region=?, code_postal=?, pays=?, telephone=?, fax=? WHERE code=?";
        try (Connection myConnection = myDataSource.getConnection();
                PreparedStatement statement = myConnection.prepareStatement(sql)){
            statement.setString(1,c.getSociete());
            statement.setString(2, c.getContact());
            statement.setString(3, c.getFonction());
            statement.setString(4, c.getAdresse());
            statement.setString(5, c.getVille());
            statement.setString(6, c.getRegion());
            statement.setString(7, c.getCodePostal());
            statement.setString(8, c.getPays());
            statement.setString(9, c.getTelephone());
            statement.setString(10, c.getFax());
            statement.setString(11, c.getCode());
            result = statement.executeUpdate();
        }
        return result;
    }

}
