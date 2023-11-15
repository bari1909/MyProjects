/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.milaifontanals.projete1.model;

import java.util.Date;
import java.util.Objects;

/**
 *
 * @author abdel
 */
public class ArtistaIndividual extends Artista {
    private Date dataNaixement; 
    private Pais nacionalitat;

    public ArtistaIndividual(long id, String nom, TipusArtista tipus,Date dataNaixement,Pais nacionalitat) {
        super(id, nom, tipus);
        setDataNaixement(dataNaixement);
        setNacionalitat(nacionalitat);
    }
    public ArtistaIndividual(long id, String nom,TipusArtista tipus) {
        super(id, nom,tipus);
    }
    
    public Date getDataNaixement() {
        return dataNaixement;
    }

    public void setDataNaixement(Date dataNaixement) {
        this.dataNaixement = dataNaixement;
    }

    public Pais getNacionalitat() {
        return nacionalitat;
    }

    public void setNacionalitat(Pais nacionalitat) {
        this.nacionalitat = nacionalitat;
    }

    @Override
    public int hashCode() {
        int hash = 7;
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
        final ArtistaIndividual other = (ArtistaIndividual) obj;
        if (!Objects.equals(this.dataNaixement, other.dataNaixement)) {
            return false;
        }
        if (!Objects.equals(this.nacionalitat, other.nacionalitat)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ArtistaIndividual{"+ super.toString() + "dataNaixement=" + dataNaixement + ", nacionalitat=" + nacionalitat + '}';
    }
    
    
    
}
