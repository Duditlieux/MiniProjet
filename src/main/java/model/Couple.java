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
public class Couple {
    private String m_s;
    private int m_n;
    
    Couple(){
        m_s = null;
        m_n = 0;
    }
    
    Couple(String s, int n){
        m_s = s;
        m_n = n;
    }
    
    public void setS(String s){
        m_s = s;
    }
    
    public void setN(int n){
        m_n = n;
    }
    
    public String getS(){
        return m_s;
    }
    
    public int getN(){
        return m_n;
    }
}
