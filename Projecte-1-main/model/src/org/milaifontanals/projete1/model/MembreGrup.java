/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.milaifontanals.projete1.model;

import java.util.Date;

/**
 *
 * @author abdel
 */
public class MembreGrup {
    private Grup grup;
    private ArtistaIndividual art_ind;
    private Date dataInici;
    private Date dataFinal;

    public MembreGrup(Grup grup, ArtistaIndividual art_ind, Date dataInici, Date dataFinal) {
        setGrup(grup);
        setArt_ind(art_ind);
        setDataInici(dataInici);
        setDataFinal(dataFinal);
    }

    public Grup getGrup() {
        return grup;
    }

    public void setGrup(Grup grup) {
        this.grup = grup;
    }

    public ArtistaIndividual getArt_ind() {
        return art_ind;
    }

    public void setArt_ind(ArtistaIndividual art_ind) {
        this.art_ind = art_ind;
    }

    public Date getDataInici() {
        return dataInici;
    }

    public void setDataInici(Date dataInici) {
        this.dataInici = dataInici;
    }

    public Date getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(Date dataFinal) {
        this.dataFinal = dataFinal;
    }

    @Override
    public String toString() {
        return "MembreGrup{" + "grup=" + grup + ", art_ind=" + art_ind + ", dataInici=" + dataInici + ", dataFinal=" + dataFinal + '}';
    }
    
    
    
}
