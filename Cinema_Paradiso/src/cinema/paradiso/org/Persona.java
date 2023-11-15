/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cinema.paradiso.org;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author abdel
 */
public class Persona {
    private String nom;
    private String dni;

    public Persona(String nom, String dni) throws Exception {
        setNom(nom);
        setDni(dni);
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) throws Exception {
        if(nom ==null || nom.length() == 0){
            throw new Exception("Nom no pot ser null o buit");
        }
        this.nom = nom;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) throws Exception {
        if(dni == null || dni.length()==0)
            throw new Exception("El DNI es obligatori");
        
        String patron = "\\d{8}[A-Z]";
        Pattern pattern = Pattern.compile(patron);
        Matcher matcher = pattern.matcher(dni);

        if (!matcher.matches()) {
            throw new Exception("El Format del DNI no es válido");
        }
        this.dni = dni;
    }

    @Override
    public String toString() {
        return "Persona{" + "nom=" + nom + ", dni=" + dni + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.nom);
        hash = 17 * hash + Objects.hashCode(this.dni);
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
        final Persona other = (Persona) obj;
        if (!Objects.equals(this.nom, other.nom)) {
            return false;
        }
        if (!Objects.equals(this.dni, other.dni)) {
            return false;
        }
        return true;
    }
    
    
}
