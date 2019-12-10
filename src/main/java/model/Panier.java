/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;

public class Panier extends ArrayList<Product> {
    
    public Panier(){
        super();
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
