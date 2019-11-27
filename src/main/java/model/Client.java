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
public class Client {

    private String m_code;
    private String m_societe;
    private String m_contact;
    private String m_fonction;
    private String m_adresse;
    private String m_ville;
    private String m_region;
    private String m_codePostal;
    private String m_pays;
    private String m_telephone;
    private String m_fax;

    Client(String code, String societe, String contact, String fonction, String adresse, String ville, String region, String codePostal, String pays, String telephone, String fax) {
        m_code = code;
        m_societe = societe;
        m_contact = contact;
        m_fonction = fonction;
        m_adresse = adresse;
        m_ville = ville;
        m_region = region;
        m_codePostal = codePostal;
        m_pays = pays;
        m_telephone = telephone;
        m_fax = fax;
    }

    private String getCode() {
        return m_code;
    }

    private String getSociete() {
        return m_societe;
    }

    private String getContact() {
        return m_contact;
    }

    private String getFonction() {
        return m_fonction;
    }

    private String getAdresse() {
        return m_adresse;
    }

    private String getVille() {
        return m_ville;
    }

    private String getRegion() {
        return m_region;
    }

    private String getCodePostal() {
        return m_codePostal;
    }

    private String getPays() {
        return m_pays;
    }

    private String getTelephone() {
        return m_telephone;
    }

    private String getFax() {
        return m_fax;
    }

    private void setCode(String code) {
        m_code = code;
    }

    private void setSociete(String societe) {
        m_societe = societe;
    }

    private void setContact(String contact) {
        m_contact = contact;
    }

    private void setFonction(String fonction) {
        m_fonction = fonction;
    }

    private void setAdresse(String adresse) {
        m_adresse = adresse;
    }

    private void setVille(String ville) {
        m_ville = ville;
    }

    private void setRegion(String region) {
        m_region = region;
    }

    private void setCodePostal(String codePostal) {
        m_codePostal = codePostal;
    }

    private void setPays(String pays) {
        m_pays = pays;
    }

    private void setTelephone(String telephone) {
        m_telephone = telephone;
    }

    private void setFax(String fax) {
        m_fax = fax;
    }

}
