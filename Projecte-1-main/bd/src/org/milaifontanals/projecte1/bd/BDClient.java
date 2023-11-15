/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.milaifontanals.projecte1.bd;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
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
import org.milaifontanals.projete1.model.Reproduccio;

/**
 *
 * @author abdel
 */
public class BDClient {
    
   private static Connection conn;
    
    
    public List<Client> getClients() throws GestorBDMilaSpotifyException{
        List<Client> llCli = new ArrayList<Client>();
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
            inst =" SELECT cli_id, cli_email, cli_nom  FROM client ";
            s = conn.prepareStatement(inst);
            ResultSet rs = s.executeQuery(inst);
            while (rs.next()) {     
                Client clnt = new Client(rs.getLong(1),rs.getString(2) ,rs.getString(3));
                llCli.add(clnt);
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
        return llCli;
   }
   
    public boolean esborraClient(long idClient){
        return true;
    }
    
    public Client getClient(long id) throws GestorBDMilaSpotifyException{
        Client clnt = null;
        GestorBDMilaSpotify gb = null;
        try {
            gb = new GestorBDMilaSpotify();
            conn = gb.getConexio();
        } catch (GestorBDMilaSpotifyException ex) {
            Logger.getLogger(BDReproduccio.class.getName()).log(Level.SEVERE, null, ex);
        }
        String inst = null;
        PreparedStatement ps = null;
        try {
            inst =" SELECT cli_id, cli_email, cli_nom  FROM client  WHERE cli_id = ? ";
            ps = conn.prepareStatement(inst);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                clnt = new Client(rs.getLong(1), rs.getString(2), rs.getString(3));
            }
            rs.close();
        } catch (SQLException ex) {
            throw new GestorBDMilaSpotifyException("Error en intentar recuperar la llista de Reproduccions.\n" + ex.getMessage());
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                    throw new GestorBDMilaSpotifyException("Error en intentar tancar la sentència que ha recuperat la llista de Reproduccions.\n" + ex.getMessage());
                }
            }
        }
        return clnt;
    }
    
    
    
    
}
