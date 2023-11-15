/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.milaifontanals.projecte1.bd;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.milaifontanals.projete1.model.Artista;
import org.milaifontanals.projete1.model.TipusArtista;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.milaifontanals.projete1.model.ArtistaIndividual;
import org.milaifontanals.projete1.model.Grup;
/**
 *
 * @author abdel
 */
public class BDArtista {
    private static Connection conn= null;
    
    private PreparedStatement pInserirArtista;
    private PreparedStatement pmodifArtista;
    private PreparedStatement pElimArtista;
    

    
    public Artista getArtista(long id){
        GestorBDMilaSpotify gb = null;
        try {
            gb = new GestorBDMilaSpotify();
            conn = gb.getConexio();
        } catch (GestorBDMilaSpotifyException ex) {
            Logger.getLogger(BDReproduccio.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        PreparedStatement pObtenirArt = null;
        
        try {
            pObtenirArt = conn.prepareStatement("SELECT art_id,art_nom,art_tipus FROM artista WHERE art_id = ?");
            pObtenirArt.setLong(1, id);
        } catch (SQLException ex) {
            Logger.getLogger(BDArtista.class.getName()).log(Level.SEVERE, null, ex);
        }
        ResultSet rs = null;
        String tipus = null;
        Artista art = null; 
        try {
            rs = pObtenirArt.executeQuery();
            while (rs.next()) {
                tipus = rs.getString(3);
                switch (tipus) {
                    
                    case "I" : art = new ArtistaIndividual(rs.getLong(1),rs.getString(2),TipusArtista.INDIVIDUAL);
                                break;
                    case "G" : art = new Grup(rs.getLong(1),rs.getString(2),TipusArtista.GRUPAL);
                                break;
                    
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(BDArtista.class.getName()).log(Level.SEVERE, null, ex);
        }
        return art;   
    }
    
    public List<Artista> getArtistes() throws GestorBDMilaSpotifyException{
        GestorBDMilaSpotify gb = null;
        try {
            gb = new GestorBDMilaSpotify();
            conn = gb.getConexio();
        } catch (GestorBDMilaSpotifyException ex) {
            Logger.getLogger(BDReproduccio.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String inst = null;
        Statement s = null;
        List<Artista> llArtistes= new ArrayList<Artista>();
        try {
            inst =  "select art_id, art_nom, art_tipus " +
                    "from artista " +
                    "order by 2,1 asc "; 
            s = conn.prepareStatement(inst);
            ResultSet rs = s.executeQuery(inst);
            String tipus = null;
            TipusArtista tipArt;
            while (rs.next()) { 
                tipus = rs.getString(3);
                switch (tipus) {
                    
                    case "I" : tipArt = TipusArtista.INDIVIDUAL;
                                llArtistes.add(new ArtistaIndividual(rs.getLong(1),rs.getString(2),tipArt));
                                break;
                    case "G" :  tipArt = TipusArtista.GRUPAL;
                                llArtistes.add( new Grup(rs.getLong(1),rs.getString(2),tipArt));
                                break;  
                }
            }
            rs.close();
        } catch (SQLException ex) {
            throw new GestorBDMilaSpotifyException("Error en intentar recuperar la llista d'artistes.\n" + ex.getMessage());
        } finally {
            if (s != null) {
                try {
                    s.close();
                } catch (SQLException ex) {
                    throw new GestorBDMilaSpotifyException("Error en intentar tancar la sentència que ha recuperat la llista d'artistes.\n" + ex.getMessage());
                }
            }
        }
        return llArtistes;
    }
    
    public void inserirArtista(Artista artista){
    }
    
    public void updateArtista(Artista artista){
    
    }
    
    public void deleteArtista(long idArtista){
    
    }
}
