/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.milaifontanals.projete1.model;

import java.util.Objects;

/**
 *
 * @author abdel
 */
public abstract class Artista {
    private long id;
    private String nom;
    private TipusArtista tipus;

    public Artista(long id, String nom, TipusArtista tipus) {
        setId(id);
        setNom(nom);
        setTipus(tipus);
    }
    
    public Artista(long id, String nom) {
        setId(id);
        setNom(nom);
    }
    

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public TipusArtista getTipus() {
        return tipus;
    }

    public void setTipus(TipusArtista tipus) {
        this.tipus = tipus;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + (int) (this.id ^ (this.id >>> 32));
        hash = 23 * hash + Objects.hashCode(this.nom);
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
        final Artista other = (Artista) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.nom, other.nom)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Artista{" + "id=" + id + ", nom=" + nom + ", tipus=" + tipus + '}';
    }
    
    
    
}
