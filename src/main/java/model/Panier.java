/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.ArrayList;

public class Panier extends ArrayList<Product> implements Serializable {
    
    public Panier(){
        super();
    }
    
    public boolean contains(Product pr){
        for (int i = 0; i < this.size(); i++) {
            Product get = this.get(i);
            if (get.getReference()==pr.getReference()){
                return true;
            }
        }
        return false;
    }
    
    public int indexOf(Product pr){
        for (int i = 0; i < this.size(); i++) {
            Product get = this.get(i);
            if (get.getReference()==pr.getReference()){
                return i;
            }
        }
        return -1;
    }
    
    public void ajout(Product pr){
        if (this.contains(pr)){
            Product p = this.remove(this.indexOf(pr));
            p.setQuantitePanier(p.getQuantitePanier()+1);
            this.add(p);
        } else {
            pr.setQuantitePanier(1);
            this.add(pr);
        }
    }
    
    public void reduireQte(Product pr){
        if (this.contains(pr)){
            Product p = this.remove(this.indexOf(pr));
            p.setQuantitePanier(p.getQuantitePanier()-1);
            if (p.getQuantitePanier()>0){
                this.add(p);
            } else {
                this.remove(this.indexOf(pr));
            }
        }
    }
    
    public void supprimer(Product pr){
        if (this.contains(pr)){
            this.remove(this.indexOf(pr));
        }
    }
    
    
    
}
