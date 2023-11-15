/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.milaifontanals.projete1.model;

import java.util.Iterator;

/**
 *
 * @author abdel
 */
public final class Canso extends Producte{
    private int anyCreacio;
    private long durada; 
    private Artista interpret; 
    
    public Canso(long id, String titol, boolean actiu, Estil estil, TipusProducte tipus,int anyCreacio, long durada, Artista interpret) {
        super(id, titol, actiu, estil, tipus);
        setAnyCreacio(anyCreacio);
        setDurada(durada);
        setInterpret(interpret);
    }
    
    public Canso(long id, String titol) {
        super(id, titol);
    }
    
    public Canso(long id, String titol, boolean actiu, Estil estil, TipusProducte tipus) {
        super(id, titol, actiu, estil, tipus);
    }

    public Artista getInterpret() {
        return interpret;
    }

    public void setInterpret(Artista interpret) {
        this.interpret = interpret;
    }
    
    public int getAnyCreacio() {
        return anyCreacio;
    }

    public void setAnyCreacio(int anyCreacio) {
        this.anyCreacio = anyCreacio;
    }

    public void setDurada(long durada) {
        this.durada = durada;
    }
    
    public long getDurada(){
        return durada;
    }
    
//    public Artista getInterpret(){
//        
//    }
//    
//    public Iterator<Artista> getAutors(){
//    
//    }
    
//    @Override
//    public int getDurada() {
//        return return durada;
//    }

    @Override
    public String toString() {
        String canso = "";
        canso += "Canso{" +super.toString();
        if (anyCreacio != 0){
            canso += "Any creacio : " + anyCreacio;
        }
        if (durada != 0){
            canso += "Durada : " + durada;
        }
        
        if(interpret != null){
            canso += "Interpret : " + interpret.getNom();
        }
        canso += "}";
        
        return canso;
    }
    
    
}
