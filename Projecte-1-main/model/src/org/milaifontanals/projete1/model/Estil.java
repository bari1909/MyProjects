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
public class Estil {
    private long id;
    private String nom;

    public Estil(long id, String nom) {
        setId(id);
        setNom(nom);

    }
    public Estil(String nom) {
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

    @Override
    public String toString() {
        return "Estil { id :"+id+", Nom : "+nom+" }"; 
    }

    @Override
    public boolean equals(Object obj) {
        
        if (!(obj instanceof Estil)) {
            return false;
        }
        Estil other = (Estil) obj;
        if((this.id != other.id) || (this.nom != other.nom) ){
            return false;
        }
        return true;
    
    
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + (int) (this.id ^ (this.id >>> 32));
        hash = 79 * hash + Objects.hashCode(this.nom);
        return hash;
    }
    
    
}
