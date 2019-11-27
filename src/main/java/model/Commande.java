/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Date;

/**
 *
 * @author pedago
 */
public class Commande {

    private int m_numeroa;
    private String m_client;
    private Date m_saisieLe;
    private Date m_envoyeeLe;
    private int m_port;
    private String m_destinataire;
    private String m_adresseDeLivraison;
    private String m_villeDeLivraison;
    private String m_regionDeLivraison;
    private String m_codePostalDeLivraison;
    private String m_paysDeLivraison;
    private float m_remise;

    Commande(int numero, String client, Date saisiLe, Date envoyeeLe, int port, String destinataire, String adresseDeLivraison, String villeDeLivraison, String regionDeLivraison, String codePostalDeLivraison, String paysDeLivraison, float remise) {
        m_numeroa = numero;
        m_client = client;
        m_saisieLe = saisiLe;
        m_envoyeeLe = envoyeeLe;
        m_port = port;
        m_destinataire = destinataire;
        m_adresseDeLivraison = adresseDeLivraison;
        m_villeDeLivraison = villeDeLivraison;
        m_regionDeLivraison = regionDeLivraison;
        m_codePostalDeLivraison = codePostalDeLivraison;
        m_paysDeLivraison = paysDeLivraison;
        m_remise = remise;
    }

    //geteurs
    public int getNumeroa() {
        return m_numeroa;
    }

    public String getClient() {
        return m_client;
    }

    public Date getSaisieLe() {
        return m_saisieLe;
    }

    public Date getEnvoyeeLe() {
        return m_envoyeeLe;
    }

    public int getPort() {
        return m_port;
    }

    public String getDestinataire() {
        return m_destinataire;
    }

    public String getAdresseDeLivraison() {
        return m_adresseDeLivraison;
    }

    public String getVilleDeLivraison() {
        return m_villeDeLivraison;
    }

    public String getRegionDeLivraison() {
        return m_regionDeLivraison;
    }

    public String getCodePostalDeLivraison() {
        return m_codePostalDeLivraison;
    }

    public String getPaysDeLivraison() {
        return m_paysDeLivraison;
    }

    public float getRemise() {
        return m_remise;
    }

    //setteurs
    public void setNumeroa(int numero) {
        m_numeroa = numero;
    }

    public void setClient(String client) {
        m_client = client;
    }

    public void setSaisieLe(Date saisieLe) {
        m_saisieLe = saisieLe;
    }

    public void setEnvoyeeLe(Date envoyeeLe) {
        m_envoyeeLe = envoyeeLe;
    }

    public void setPort(int port) {
        m_port = port;
    }

    public void setDestinataire(String destinataire) {
        m_destinataire = destinataire;
    }

    public void setAdresseDeLivraison(String adresseDeLivraison) {
        m_adresseDeLivraison = adresseDeLivraison;
    }

    public void setVilleDeLivraison(String villeDeLivraison) {
        m_villeDeLivraison = villeDeLivraison;
    }

    public void setRegionDeLivraison(String regionDeLivraison) {
        m_regionDeLivraison = regionDeLivraison;
    }

    public void setCodePostalDeLivraison(String codePostalDeLivraison) {
        m_codePostalDeLivraison = codePostalDeLivraison;
    }

    public void setPaysDeLivraison(String paysDeLivraison) {
        m_paysDeLivraison = paysDeLivraison;
    }

    public void setRemise(float remise) {
        m_remise = remise;
    }

}
