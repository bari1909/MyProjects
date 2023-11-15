/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.milaifontanals.projete1.model;

/**
 *
 * @author abdel
 */
public abstract class Producte {
    private long id;
    private String titol;
    private boolean actiu;
    private Estil estil;
    private TipusProducte tipus;

    public Producte(long id, String titol, boolean actiu, Estil estil, TipusProducte tipus) {
        setId(id);
        setTitol(titol);
        setActiu(actiu);
        setEstil(estil);
        setTipus(tipus);
    }

    public Producte(long id, String titol) {
        setId(id);
        setTitol(titol);
    }
    
    

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitol() {
        return titol;
    }

    public void setTitol(String titol) {
        this.titol = titol;
    }

    public boolean isActiu() {
        return actiu;
    }

    public void setActiu(boolean actiu) {
        this.actiu = actiu;
    }

    public Estil getEstil() {
        return estil;
    }

    public void setEstil(Estil estil) {
        this.estil = estil;
    }

    public TipusProducte getTipus() {
        return tipus;
    }

    public void setTipus(TipusProducte tipus) {
        this.tipus = tipus;
    }
    
//    public int getDurada(){
//        return this.durada;
//    }  

    @Override
    public String toString() {
        return "Producte{" + "id=" + id + ", titol=" + titol + ", actiu=" + actiu + ", estil=" + estil + ", tipus=" + tipus + '}';
    }
    
    
}
