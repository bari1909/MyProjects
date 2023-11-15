/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cinema.paradiso.org;

import java.util.List;

/**
 *
 * @author abdel
 */
public class Entrada {
    private Persona persona;
    private int preuTotal;
    private List<Butaca> llistaButacques;

    public Entrada(Persona persona,int preuTotal ,List<Butaca> llistaButacques) throws Exception {
        setPersona(persona);
        setPreuTotal(preuTotal);
        setLlistaButacques(llistaButacques);
    }

    public Persona getPersona() {
        return persona;
    }

    public int getPreuTotal() {
        return preuTotal;
    }

    public void setPreuTotal(int preuTotal) {
        this.preuTotal = preuTotal;
    }

    public void setPersona(Persona persona) throws Exception {
        if(persona == null)
            throw new Exception("Persona es obligatori");
        
        this.persona = persona;
    }

    public List<Butaca> getLlistaButacques() {
        return llistaButacques;
    }

    public void setLlistaButacques(List<Butaca> llistaButacques) throws Exception {
        if(llistaButacques.size() == 0)
            throw new Exception("Llista de butaques no pot sr buida");
        
        this.llistaButacques = llistaButacques;
    }
    
    
}
