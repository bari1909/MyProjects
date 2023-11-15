/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.milaifontanals.projecte1.bd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import java.util.concurrent.locks.StampedLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.milaifontanals.projete1.model.Album;
import org.milaifontanals.projete1.model.Canso;
import org.milaifontanals.projete1.model.Client;
import org.milaifontanals.projete1.model.LlistaRep;
import org.milaifontanals.projete1.model.Producte;
import org.milaifontanals.projete1.model.Reproduccio;

/**
 *
 * @author abdel
 */
public class BDReproduccio {
    private static Connection conn= null;
    
    PreparedStatement pConsultarRep;
    PreparedStatement qAfegirRep;
    PreparedStatement qModifRep;    
    PreparedStatement qDelRep;
    
    public List<Reproduccio> getReproduccions(long idClient, String fragNomProd, Date inici, Date fi) throws GestorBDMilaSpotifyException{
        List<Reproduccio> llRep = new ArrayList<Reproduccio>();
        GestorBDMilaSpotify gb = null;
        try {
            gb = new GestorBDMilaSpotify();
            conn = gb.getConexio();
        } catch (GestorBDMilaSpotifyException ex) {
            Logger.getLogger(BDReproduccio.class.getName()).log(Level.SEVERE, null, ex);
        }
        String inst = null;
        pConsultarRep = null;
        try {
            inst =  "select c.cli_id, c.cli_nom, c.cli_email, p.prod_id, p.prod_titol, p.prod_tipus, r.moment_temporal " +
                    "from reproduccio r join client c on c.cli_id = r.id_client " +
                    "                   join producte p on p.prod_id = r.id_producte " +
                    " WHERE c.cli_id = ? AND upper(p.prod_titol) like upper(?) AND r.moment_temporal BETWEEN ? AND ? ";
            
            pConsultarRep = conn.prepareStatement(inst);
        } catch (SQLException ex) {
            throw new GestorBDMilaSpotifyException("No es pot crear el PreparedStatement:\n " + inst + "\n" + ex.getMessage());
        }
        
        try{
            pConsultarRep.setLong(1, idClient);
            pConsultarRep.setString(2,"%"+fragNomProd+"%");
            pConsultarRep.setDate(3, inici);
            pConsultarRep.setDate(4, fi);
            
            ResultSet rs = pConsultarRep.executeQuery();
            while (rs.next()) {     
                Client clnt = new Client(rs.getLong(1),rs.getString(3) ,rs.getString(2));
                Date momen_temp = rs.getDate(7);
                String tipus = rs.getString(6);
                Canso cns;
                Album alb;
                LlistaRep llr;
                switch (tipus){
                    case "C": cns = new Canso(rs.getLong(4),rs.getString(5));
                              llRep.add(new Reproduccio(cns, clnt, momen_temp));
                              break;
                    case "A": alb = new Album(rs.getLong(4),rs.getString(5));
                              llRep.add(new Reproduccio(alb, clnt, momen_temp));
                              break;
                    case "L": llr = new LlistaRep(rs.getLong(4),rs.getString(5));
                              llRep.add(new Reproduccio(llr, clnt, momen_temp));
                              break; 
                              
                }  
            }
            rs.close();
        } catch (SQLException ex) {
            throw new GestorBDMilaSpotifyException("Error en intentar recuperar la llista de Reproduccions.\n" + ex.getMessage());
        } finally {
            if (pConsultarRep != null) {
                try {
                    pConsultarRep.close();
                } catch (SQLException ex) {
                    throw new GestorBDMilaSpotifyException("Error en intentar tancar la sentència que ha recuperat la llista de Reproduccions.\n" + ex.getMessage());
                }
            }
        }
        return llRep;
    }
    
    public List<Reproduccio> getReproduccions() throws GestorBDMilaSpotifyException{
        List<Reproduccio> llRep = new ArrayList<Reproduccio>();
        GestorBDMilaSpotify gb = null;
        try {
            gb = new GestorBDMilaSpotify();
            conn = gb.getConexio();
        } catch (GestorBDMilaSpotifyException ex) {
            Logger.getLogger(BDReproduccio.class.getName()).log(Level.SEVERE, null, ex);
        }
        String inst = null;
        Statement s = null;
        try {
            inst =  "select c.cli_id, c.cli_nom, c.cli_email, p.prod_id, p.prod_titol, p.prod_tipus, r.moment_temporal " +
                    "from reproduccio r join client c on c.cli_id = r.id_client " +
                    "                   join producte p on p.prod_id = r.id_producte ";
                    
                   
            s = conn.prepareStatement(inst);
            ResultSet rs = s.executeQuery(inst);
            while (rs.next()) {     
                Client clnt = new Client(rs.getLong(1),rs.getString(3) ,rs.getString(2));
                Date momen_temp = rs.getDate(7);
                String tipus = rs.getString(6);
                Canso cns;
                Album alb;
                LlistaRep llr;
                switch (tipus){
                    case "C": cns = new Canso(rs.getLong(4),rs.getString(5));
                              llRep.add(new Reproduccio(cns, clnt, momen_temp));
                              break;
                    case "A": alb = new Album(rs.getLong(4),rs.getString(5));
                              llRep.add(new Reproduccio(alb, clnt, momen_temp));
                              break;
                    case "L": llr = new LlistaRep(rs.getLong(4),rs.getString(5));
                              llRep.add(new Reproduccio(llr, clnt, momen_temp));
                              break; 
                              
                }  
            }
            rs.close();
        } catch (SQLException ex) {
            throw new GestorBDMilaSpotifyException("Error en intentar recuperar la llista de Reproduccions.\n" + ex.getMessage());
        } finally {
            if (s != null) {
                try {
                    s.close();
                } catch (SQLException ex) {
                    throw new GestorBDMilaSpotifyException("Error en intentar tancar la sentència que ha recuperat la llista de Reproduccions.\n" + ex.getMessage());
                }
            }
        }
        return llRep;
    }
    
    public void afegirReproduccio(Reproduccio r) throws GestorBDMilaSpotifyException{
        GestorBDMilaSpotify gb = null;
        try {
            gb = new GestorBDMilaSpotify();
            conn = gb.getConexio();
        } catch (GestorBDMilaSpotifyException ex) {
            Logger.getLogger(BDReproduccio.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String inst = null;
        try {
            inst = "INSERT INTO reproduccio (id_client, moment_temporal,id_producte) VALUES (?,to_date(?,'YYYY-MM-DD HH:MI:SS'),?)";
            qAfegirRep = conn.prepareStatement(inst);
        } catch (SQLException ex) {
            throw new GestorBDMilaSpotifyException("No es pot crear el PreparedStatement:\n " + inst + "\n" + ex.getMessage());
        }
        
        Producte prod = r.getProducte();
        Client cli = r.getClient();
        try {
            qAfegirRep.setLong(1, cli.getId());
            qAfegirRep.setDate(2, r.getTimestamp());
            qAfegirRep.setLong(3, prod.getId());
            qAfegirRep.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(BDReproduccio.class.getName()).log(Level.SEVERE, null, ex);
        }
        gb.desarCanvis();
    }
    
    public void eliminarReproduccio (Reproduccio r) throws GestorBDMilaSpotifyException{
        GestorBDMilaSpotify gb = null;
        try {
            gb = new GestorBDMilaSpotify();
            conn = gb.getConexio();
        } catch (GestorBDMilaSpotifyException ex) {
            Logger.getLogger(BDReproduccio.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String inst = null;
        try {
            inst = "DELETE  FROM reproduccio WHERE id_client = ? AND moment_temporal = ?  AND id_producte = ? ";
            qDelRep = conn.prepareStatement(inst);
        } catch (SQLException ex) {
            throw new GestorBDMilaSpotifyException("No es pot crear el PreparedStatement:\n " + inst + "\n" + ex.getMessage());
        }
        
        long id_producte = r.getProducte().getId();
        long id_client = r.getClient().getId();
        try {
            qDelRep.setLong(1, id_client);
            qDelRep.setDate(2, r.getTimestamp());
            qDelRep.setLong(3, id_producte);
            qDelRep.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(BDReproduccio.class.getName()).log(Level.SEVERE, null, ex);
        }
        gb.desarCanvis();
    
    }
    
    public void modificarReproduccio(Reproduccio r,java.sql.Date momentTmpAntic) throws GestorBDMilaSpotifyException{
        GestorBDMilaSpotify gb = null;
        try {
            gb = new GestorBDMilaSpotify();
            conn = gb.getConexio();
        } catch (GestorBDMilaSpotifyException ex) {
            Logger.getLogger(BDReproduccio.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String inst = null;
        try {
            inst = "UPDATE reproduccio SET id_client = ?, moment_temporal = ? , id_producte = ? "+
                   "WHERE id_client = ? AND id_producte = ? AND moment_temporal = ? ";
            qModifRep = conn.prepareStatement(inst);

            Producte prod = r.getProducte();
            Client cli = r.getClient();
            qModifRep.setLong(1, cli.getId());
            qModifRep.setDate(2,(java.sql.Date) r.getTimestamp());
            qModifRep.setLong(3, prod.getId());
            
            qModifRep.setLong(4, cli.getId());
            qModifRep.setLong(5, prod.getId());
            qModifRep.setDate(6,momentTmpAntic);
            
            qModifRep.executeUpdate();
           
        } catch (SQLException ex) {
            Logger.getLogger(BDReproduccio.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
