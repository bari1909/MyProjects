/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.milaifontanals.projete1.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author abdel
 */
public final class Album extends Producte{
    private  int anyCreacio;
    private List<Canso> cansons;
    private long durada;

    public Album(long id, String titol, boolean actiu, Estil estil, TipusProducte tipus, int anyCreacio, List<Canso> cansons,long durada) {
        super(id, titol, actiu, estil, tipus);
        setAnyCreacio(anyCreacio);
        setDurada(durada);
        setCansons(cansons);
        
    }

    public Album(long id, String titol) {
        super(id, titol);
    }
    
    
    public Album(long id, String titol, boolean actiu, Estil estil, TipusProducte tipus) {
        super(id, titol, actiu, estil, tipus);
    }

    public Album(int anyCreacio, List<Canso> cansons, long durada, long id, String titol, boolean actiu, Estil estil, TipusProducte tipus) {
        super(id, titol, actiu, estil, tipus);
        setAnyCreacio(anyCreacio);
        setCansons(cansons);
        setDurada(durada);
    }
    
    public void setCansons(List<Canso> cansons){
        this.cansons = cansons;
    }
   
    public int getAnyCreacio() {
        return anyCreacio;
    }
    
    public void setAnyCreacio(int anyCreacio){
        this.anyCreacio = anyCreacio;
    }

    public List<Canso> getCansons(){
        
        return cansons;
    }
    
    public int getNumCansons(){
        return cansons.size();
    }
    
    public Canso getCanso(int idx){
        Canso can = null;
        for(int i=0;i<cansons.size();i++){
            if(cansons.get(i).getId() == idx){
                can = cansons.get(i);
            }  
        }
        return can;
    }
    
    public void removeCanso(int idx){
        cansons.remove(idx);
    }
    
    public void addCanso(Canso c){
        cansons.add(c);
    }
    
    public long getDurada() {
        return durada;
    }

    public void setDurada(long durada) {
        this.durada = durada;
    }
    
//    @Override
//    public int getDurada() {
//        return (int) durada;
//    }  

    
    
    
}
