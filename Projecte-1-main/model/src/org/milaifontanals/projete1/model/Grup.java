/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.milaifontanals.projete1.model;

import java.util.Date;
import java.util.List;

/**
 *
 * @author abdel
 */
public class Grup extends Artista{
    private Date dataCreacio;

    public Grup(long id, String nom, TipusArtista tipus,Date dataCreacio) {
        super(id, nom, tipus);
        setDataCreacio(dataCreacio);
    }
    
    public Grup(long id, String nom,TipusArtista tipus) {
        super(id, nom,tipus);
      
    }

    public Date getDataCreacio() {
        return dataCreacio;
    }

    public void setDataCreacio(Date dataCreacio) {
        this.dataCreacio = dataCreacio;
    }
    
//    public List<ArtistaIndividual> getMembres(){
//    
//    }
//    
    public void addMembre(ArtistaIndividual artista,Date dataInici,Date dataFi){
    
    }
    
    public void eliminarMembre(int idx){
    
    }
    
    
    
}
