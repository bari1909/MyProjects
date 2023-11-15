/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.milaifontanals.projete1.model;

import java.sql.Date;
import java.util.Objects;

/**
 *
 * @author abdel
 */
public class Reproduccio {
    private Producte producte;
    private Client client;
    private Date timestamp;

    public Reproduccio(Producte producte, Client client, Date timestamp) {
        setProducte(producte);
        setClient(client);
        setTimestamp(timestamp);
        
    }
    
    public void Reproduccio(Client c, Producte p,Date d){
    
    }

    public Producte getProducte() {
        return producte;
    }

    public void setProducte(Producte producte) {
        this.producte = producte;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.producte);
        hash = 59 * hash + Objects.hashCode(this.client);
        hash = 59 * hash + Objects.hashCode(this.timestamp);
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
        final Reproduccio other = (Reproduccio) obj;
        if (!Objects.equals(this.producte, other.producte)) {
            return false;
        }
        if (!Objects.equals(this.client, other.client)) {
            return false;
        }
        if (!Objects.equals(this.timestamp, other.timestamp)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        
        TipusProducte tipus = producte.getTipus();
        return "Reproduccio{" + "producte:" + producte.toString() + ", client=" + client.toString() + ", timestamp=" + timestamp + '}';
    }
    

}
