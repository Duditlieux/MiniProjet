/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.HashMap;
                                //Reference,Quantite
public class Panier extends HashMap<Integer,Integer> {
    
    public Panier(){
        super();
    }
    
    public void ajout(int pr){
        if (this.containsKey(pr)){
            int newQte = this.get(pr)+1;
            this.put(pr, newQte);
        } else {
            this.put(pr, 1);
        }
    }
    
    public void reduireQte(int pr){
        if (this.containsKey(pr)){
            int newQte = this.get(pr)-1;
            if (newQte>0){
                this.put(pr, newQte);
            } else {
                this.remove(pr);
            }
        }
    }
    
    public void supprimer(int pr){
        if (this.containsKey(pr)){
            this.remove(pr);
        }
    }
    
    
    
}
