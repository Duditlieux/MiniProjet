/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author pedago
 */
public class Categorie {
    private int m_code;
    private String m_libelle;
    private String m_description;
    
    Categorie(int code, String libelle, String description){
        m_code = code;
        m_libelle = libelle;
        m_description = description;
    }
    
    public int getCode(){
        return m_code;
    }
    
    public String getLibelle(){
        return m_libelle;
    }
    
    public String getDescription(){
        return m_description;
    }
    
    public void setCode(int code){
        m_code = code;
    }
    
    public void setLibelle(String libelle){
        m_libelle = libelle;
    }
    
    public void setDescription(String description){
        m_description = description;
    }
    
    
}
