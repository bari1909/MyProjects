/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.milaifontanals.projete1.model;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author abdel
 */
public class LlistaRep extends Producte{
    
    private List<Producte> itemsLlista;
    private long durada;

    public LlistaRep(long id, String titol, boolean actiu, Estil estil, TipusProducte tipus) {
        super(id, titol, actiu, estil, tipus);
    }

    public LlistaRep(long id, String titol, boolean actiu, Estil estil,  TipusProducte tipus, List<Producte> itemsLlista, long durada) {
        super(id, titol, actiu, estil, tipus);
        setItemsLlista(itemsLlista);
        setDurada(durada);
    }

    public long getDurada() {
        return durada;
    }

    public void setDurada(long durada) {
        this.durada = durada;
    }
    

    public LlistaRep(long id, String titol) {
        super(id, titol);
    }

    public void setItemsLlista(List<Producte> itemsLlista) {
        this.itemsLlista = itemsLlista;
    }

    public List<Producte> getItems(){
        return itemsLlista;
    }
    
    public int getNumItems(){
        return itemsLlista.size();
    }
    
   public Producte getItem(int idx){
       return itemsLlista.get(idx);
    }
    
    public void addItem(Producte p){
        if(p!=null)
            itemsLlista.add(p);
    }
    
    public void removeItem(int idx){
            itemsLlista.remove(idx);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.itemsLlista);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final LlistaRep other = (LlistaRep) obj;
        if (!Objects.equals(this.itemsLlista, other.itemsLlista)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "LlistaRep{" + "itemsLlista=" + itemsLlista + '}';
    }
    
    
//    @Override
//    public int getDurada() {
//        return super.getDurada(); //To change body of generated methods, choose Tools | Templates.
//    }
    
}
