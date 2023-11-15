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
public class Client {
    private long id;
    private String email;
    private String nom;
    private String cognoms;
    private Date dataNaix;
    private String cp;
    private String dom1;
    private String dom2;
    private String poblacio; 
    private Pais cli_pais;

    public Client(long id, String email, String nom, String cognoms, Date dataNaix, String cp, String dom1, String dom2, String poblacio, Pais cli_pais) {
        setId(id);
        setEmail(email);
        setNom(nom);
        setCognoms(cognoms);
        setDataNaix(dataNaix);
        setCp(cp);
        setDom1(dom1);
        setDom2(dom2);
        setPoblacio(poblacio);
        setCli_pais(cli_pais);
    }

    public Client(long id, String email, String nom) {
        setId(id);
        setNom(nom);
        setEmail(email);
    }
    
    

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCognoms() {
        return cognoms;
    }

    public void setCognoms(String cognoms) {
        this.cognoms = cognoms;
    }

    public Date getDataNaix() {
        return dataNaix;
    }

    public void setDataNaix(Date dataNaix) {
        this.dataNaix = dataNaix;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public String getDom1() {
        return dom1;
    }

    public void setDom1(String dom1) {
        this.dom1 = dom1;
    }

    public String getDom2() {
        return dom2;
    }

    public void setDom2(String dom2) {
        this.dom2 = dom2;
    }

    public String getPoblacio() {
        return poblacio;
    }

    public void setPoblacio(String poblacio) {
        this.poblacio = poblacio;
    }

    public Pais getCli_pais() {
        return cli_pais;
    }

    public void setCli_pais(Pais cli_pais) {
        this.cli_pais = cli_pais;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + (int) (this.id ^ (this.id >>> 32));
        hash = 29 * hash + Objects.hashCode(this.email);
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
        final Client other = (Client) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Client{" + "id=" + id + ", email=" + email + ", nom=" + nom + ", cognoms=" + cognoms + ", dataNaix=" + dataNaix + ", cp=" + cp + ", dom1=" + dom1 + ", dom2=" + dom2 + ", poblacio=" + poblacio + ", cli_pais=" + cli_pais + '}';
    }
    
    
}
