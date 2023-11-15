/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.milaifontanals.projecte1.bd;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.milaifontanals.projete1.model.Album;
import org.milaifontanals.projete1.model.Canso;
import org.milaifontanals.projete1.model.Client;
import org.milaifontanals.projete1.model.Estil;
import org.milaifontanals.projete1.model.LlistaRep;
import org.milaifontanals.projete1.model.Reproduccio;

/**
 *
 * @author abdel
 */
public class BDEstil {
    private static Connection conn= null;
//    public Estil getEstil(long id){
//    
//    }
    public List<Estil> getEstils() throws GestorBDMilaSpotifyException{
        List<Estil> llEstil = new ArrayList<Estil>();
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
            inst =  "select est_id, est_nom from estil " +
                    "order by 2 asc ";       
            s = conn.prepareStatement(inst);
            ResultSet rs = s.executeQuery(inst);
            while (rs.next()) {
                llEstil.add(new Estil(rs.getLong(1), rs.getString(2)));
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
        return llEstil;
    }
    
    
    
}
