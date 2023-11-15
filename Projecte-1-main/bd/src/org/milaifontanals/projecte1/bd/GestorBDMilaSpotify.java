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
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 * @author abdel
 */
public class GestorBDMilaSpotify {
    private static Connection conn = null;

    public GestorBDMilaSpotify() throws GestorBDMilaSpotifyException {
        this("MilaSpotfiy.properties");
    }
    
    
    
    public  GestorBDMilaSpotify (String nomFitxerPropietats) throws GestorBDMilaSpotifyException{
        try {
            Properties props = new Properties();
            props.load(new FileInputStream(nomFitxerPropietats));
            String[] claus = {"url", "user", "password"};
            String[] valors = new String[3];
            for (int i = 0; i < claus.length; i++) {
                valors[i] = props.getProperty(claus[i]);
                if (valors[i] == null || valors[i].isEmpty()) {
                    throw new GestorBDMilaSpotifyException("L'arxiu " + nomFitxerPropietats + " no troba la clau " + claus[i]);
                }
            }
            conn = DriverManager.getConnection(valors[0], valors[1], valors[2]);
            conn.setAutoCommit(false);
        } catch (IOException ex) {
            throw new GestorBDMilaSpotifyException("Problemes en recuperar l'arxiu de configuració " + nomFitxerPropietats + "\n" + ex.getMessage());
        } catch (SQLException ex) {
            throw new GestorBDMilaSpotifyException("No es pot establir la connexió.\n" + ex.getMessage());
        }
       
    }
    
    public static Connection getConexio(){
        return conn;
    }
    
    public static void desarCanvis() throws GestorBDMilaSpotifyException{
        if(conn != null){
            try {
                conn.commit();
            } catch (SQLException ex) {
                throw new GestorBDMilaSpotifyException("Error en fer rollback final.\n" + ex.getMessage());
            }
        }
    }
    
     public static void tancar() throws GestorBDMilaSpotifyException {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ex) {
                throw new GestorBDMilaSpotifyException("Error en tancar la connexió.\n" + ex.getMessage());
            }
        }
    }
     
     public static void desferCanvis() throws GestorBDMilaSpotifyException {
        try {
            conn.rollback();
        } catch (SQLException ex) {
            throw new GestorBDMilaSpotifyException("Error en desfer els canvis: " + ex.getMessage());
        }
    }
}
