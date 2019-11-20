/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package java;

/**
 *
 * @author pedago
 */
public class Product {

    private int m_reference;
    private String m_nom;
    private int m_fournisseur;
    private int m_categorie;
    private String m_quantiteParUnite;
    private float m_prixUnitaire;
    private int m_uniteEnStock;
    private int m_uniteCommandees;
    private int m_niveauDeReapprovisionnement;
    private boolean m_indisponible;

    public Product(){
        m_reference = 0;
        m_nom = "";
        m_fournisseur = 0;
        m_categorie = 0;
        m_quantiteParUnite = "";
        m_prixUnitaire = 0;
        m_uniteEnStock = 0;
        m_uniteCommandees = 0;
        m_niveauDeReapprovisionnement = 0;
        m_indisponible = true;
    }
    
    public Product(int reference, String nom, int fournisseur, int categorie, String quantiteParUnite, float prixUnitaire, int uniteEnStock, int uniteCommandees, int niveauDeReapprovisionnement, boolean indisponible) {
        m_reference = reference;
        m_nom = nom;
        m_fournisseur = fournisseur;
        m_categorie = categorie;
        m_quantiteParUnite = quantiteParUnite;
        m_prixUnitaire = prixUnitaire;
        m_uniteEnStock = uniteEnStock;
        m_uniteCommandees = uniteCommandees;
        m_niveauDeReapprovisionnement = niveauDeReapprovisionnement;
        m_indisponible = indisponible;
    }

    //GETTERS
    
    public int getReference() {
        return m_reference;
    }

    public String getNom() {
        return m_nom;
    }

    public int getFournisseur() {
        return m_fournisseur;
    }

    public int getCategorie(){
        return m_categorie;
    }
    public String getQuantiteParUnite() {
        return m_quantiteParUnite;
    }

    public float getPrixUnitaire() {
        return m_prixUnitaire;
    }

    public int getUniteEnStock() {
        return m_uniteEnStock;
    }

    public int getUniteCommandees() {
        return m_uniteCommandees;
    }

    public int getNiveauDeReapprovisionnement() {
        return m_niveauDeReapprovisionnement;
    }

    public boolean getIndisponible() {
        return m_indisponible;
    }
    
    //SETTERS
    
    public void setReference(int reference) {
        m_reference = reference;
    }

    public void setNom(String nom) {
        m_nom = nom;
    }

    public void setFournisseur(int fournisseur) {
        m_fournisseur = fournisseur;
    }

    public void setCategorie(int categorie){
        m_categorie = categorie;
    }
    public void setQuantiteParUnite(String quantiteParUnite) {
        m_quantiteParUnite = quantiteParUnite;
    }

    public void setPrixUnitaire(float prixUnitaire) {
        m_prixUnitaire = prixUnitaire;
    }

    public void setUniteEnStock(int uniteEnStock) {
        m_uniteEnStock = uniteEnStock;
    }

    public void setUniteCommandees(int uniteCommandees) {
        m_uniteCommandees = uniteCommandees;
    }

    public void setNiveauDeReapprovisionnement(int niveauDeReapprovisionnement) {
        m_niveauDeReapprovisionnement = niveauDeReapprovisionnement;
    }

    public void setIndisponible(boolean Indisponible) {
        m_indisponible = Indisponible;
    }

}
