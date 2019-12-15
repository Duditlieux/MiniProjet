package model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.HashMap;
import java.util.Map;


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

    public Client(String code, String societe, String contact, String fonction, String adresse, String ville, String region, String codePostal, String pays, String telephone, String fax) {
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

    public Client(){
        m_code = "";
        m_societe = "";
        m_contact = "";
        m_fonction = "";
        m_adresse = "";
        m_ville = "";
        m_region = "";
        m_codePostal = "";
        m_pays = "";
        m_telephone = "";
        m_fax = "";
    }
    
    public String getCode() {
        return m_code;
    }

    public String getSociete() {
        return m_societe;
    }

    public String getContact() {
        return m_contact;
    }

    public String getFonction() {
        return m_fonction;
    }

    public String getAdresse() {
        return m_adresse;
    }

    public String getVille() {
        return m_ville;
    }

    public String getRegion() {
        return m_region;
    }

    public String getCodePostal() {
        return m_codePostal;
    }

    public String getPays() {
        return m_pays;
    }

    public String getTelephone() {
        return m_telephone;
    }

    public String getFax() {
        return m_fax;
    }

    public void setCode(String code) {
        m_code = code;
    }

    public void setSociete(String societe) {
        m_societe = societe;
    }

    public void setContact(String contact) {
        m_contact = contact;
    }

    public void setFonction(String fonction) {
        m_fonction = fonction;
    }

    public void setAdresse(String adresse) {
        m_adresse = adresse;
    }

    public void setVille(String ville) {
        m_ville = ville;
    }

    public void setRegion(String region) {
        m_region = region;
    }

    public void setCodePostal(String codePostal) {
        m_codePostal = codePostal;
    }

    public void setPays(String pays) {
        m_pays = pays;
    }

    public void setTelephone(String telephone) {
        m_telephone = telephone;
    }

    public void setFax(String fax) {
        m_fax = fax;
    }
    
    public String toJson(){
        Map<String,String> client = new HashMap<>();
        client.put("m_code", m_code);
        client.put("m_societe", m_societe);
        client.put("m_contact", m_contact);
        client.put("m_fonction", m_fonction);
        client.put("m_adresse", m_adresse);
        client.put("m_ville", m_ville);
        client.put("m_region", m_region);
        client.put("m_codePostal", m_codePostal);
        client.put("m_pays", m_pays);
        client.put("m_telephone", m_telephone);
        client.put("m_fax", m_fax);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(client);
        
        
        //return null;
    }

}
