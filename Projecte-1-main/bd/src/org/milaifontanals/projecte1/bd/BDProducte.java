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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.milaifontanals.projete1.model.Album;
import org.milaifontanals.projete1.model.ArtistaIndividual;
import org.milaifontanals.projete1.model.Canso;
import org.milaifontanals.projete1.model.Estil;
import org.milaifontanals.projete1.model.Grup;
import org.milaifontanals.projete1.model.LlistaRep;
import org.milaifontanals.projete1.model.Producte;
import org.milaifontanals.projete1.model.TipusArtista;
import org.milaifontanals.projete1.model.TipusProducte;

/**
 *
 * @author abdel
 */
public class BDProducte {
    private static Connection conn = null;
    PreparedStatement pConsultarProd;
    PreparedStatement qAfegirProd;
    PreparedStatement qModifProd;    
    PreparedStatement pModifCanso;       
    PreparedStatement qDelProd;
    //
    PreparedStatement pAfegir;

    public BDProducte() {
        conn = GestorBDMilaSpotify.getConexio();
    }
        
    public static List<Producte> getProductes() throws GestorBDMilaSpotifyException{
        List<Producte> llProd = new ArrayList<Producte>();
        String inst = null;
        Statement s = null;
        try {
            inst =  "select p.prod_id, p.prod_titol,p.prod_tipus,p.prod_actiu, e.est_nom " +
                    "from producte p left join estil e on p.prod_estil = e.est_id " +
                    "order by 2 asc "; 
            s = conn.prepareStatement(inst);
            ResultSet rs = s.executeQuery(inst);
            Canso can;
            Album alb;
            LlistaRep llr;
            String tipus;
            boolean actiu;
            Estil estil;
            TipusProducte tipProd =null;
            while (rs.next()) { 
                //desa valor de l'activitat 
                String esActiu = rs.getString(4);
                //comprovar si es actiu o inactiu
                if(esActiu.equals("S")) actiu = true;
                else actiu = false;
                //Desar valor estil del producte 
                estil = new Estil(rs.getString(5));
                //desar valor del tipus
                tipus = rs.getString(3);
                switch (tipus){
                    case "C" : tipProd = TipusProducte.CANSO;
                               can = new Canso(rs.getLong(1), rs.getString(2),actiu,estil,tipProd);
                               llProd.add(can); 
                               break;
                                
                    case "A" : tipProd = TipusProducte.ALBUM;
                               alb = new Album(rs.getLong(1), rs.getString(2),actiu,estil,tipProd);
                               llProd.add(alb); 
                               break;
                               
                    case "L" : tipProd = TipusProducte.LLISTA_REP;
                               llr = new LlistaRep(rs.getLong(1), rs.getString(2),actiu,estil,tipProd);
                               llProd.add(llr); 
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
        return llProd;
    }
//    
//    public Producte getProducte(long id){
//    }
//    
//    public List<Canso> getCansonsInterppretades(long idArtista){
//    
//    }
//  
    public static List<Producte> getProductes(TipusProducte tipus, String fragNom, Boolean estat) throws GestorBDMilaSpotifyException{
        List<Producte> llProd = new ArrayList<Producte>();
        String prodTipus = null;
        
        if(tipus == TipusProducte.ALBUM){
            prodTipus = "A";
        }else if (tipus == TipusProducte.CANSO){
            prodTipus = "C";
        }else if (tipus == TipusProducte.LLISTA_REP){
            prodTipus = "L";
        }else{
            prodTipus = "";
        }
        
        String actiu = null;
        if(estat == true){
            actiu = "S";
        }else if(estat != true){
            actiu = "N";
        }else{
            actiu = "";
        }
        String inst = null;
        PreparedStatement ps = null;
        try {
            inst =  "select p.prod_id, p.prod_titol,p.prod_tipus,p.prod_actiu, e.est_nom " +
                    "from producte p left join estil e on p.prod_estil = e.est_id " +
                    "where (upper(p.prod_titol) like upper(?) OR ? = '') AND (upper(p.prod_tipus) like upper(?) OR ? = '') AND (upper(p.prod_actiu) like ? OR ? = '') "+
                    "order by 2 asc "; 
            
            ps = conn.prepareStatement(inst);
        } catch (SQLException ex) {
            throw new GestorBDMilaSpotifyException("No es pot crear el PreparedStatement:\n " + inst + "\n" + ex.getMessage());
        }
        
        try{
            ps.setString(1, "%"+fragNom+"%");
            ps.setString(2, fragNom);
            ps.setString(3, prodTipus);
            ps.setString(4, prodTipus);
            ps.setString(5, actiu);
            ps.setString(6, actiu);
            
            ResultSet rs = ps.executeQuery();
            String ptipus;
            boolean pactiu;
            Estil estil;
            TipusProducte tipProd =null;
            while (rs.next()) {     
                //desa valor de l'activitat 
                String esActiu = rs.getString(4);
                //comprovar si es actiu o inactiu
                if(esActiu.equals("S")) pactiu = true;
                else pactiu = false;
                //Desar valor estil del producte 
                estil = new Estil(rs.getString(5));
                //desar valor del tipus
                ptipus = rs.getString(3);
                switch (ptipus){
                    case "C" : tipProd = TipusProducte.CANSO;
                               llProd.add( new Canso(rs.getLong(1), rs.getString(2),pactiu,estil,tipProd)); 
                               break;
                                
                    case "A" : tipProd = TipusProducte.ALBUM;
                               llProd.add(new Album(rs.getLong(1), rs.getString(2),pactiu,estil,tipProd)); 
                               break;
                               
                    case "L" : tipProd = TipusProducte.LLISTA_REP;
                               llProd.add(new LlistaRep(rs.getLong(1), rs.getString(2),pactiu,estil,tipProd)); 
                               break;
                }
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
        return llProd;
    }
    
     public static List<Producte> getProductes(List<TipusProducte> llTipus, String fragNom, Boolean estat,boolean ordAsc) throws GestorBDMilaSpotifyException{
        List<Producte> llProd = new ArrayList<Producte>();
        String prodTipus = null;
        String concat = "";
        String ordre = null;
        List<String> llFiltrar = new ArrayList<>();
        
        for(int i =0; i<llTipus.size();i++){
            concat += " OR ";
            if(llTipus.get(i).equals(TipusProducte.ALBUM)){
                concat += "upper(p.prod_tipus) like upper(?)";
                llFiltrar.add("A");
            }else if(llTipus.get(i).equals(TipusProducte.CANSO)){
                concat += "upper(p.prod_tipus) like upper(?)";
                llFiltrar.add("C");
            }else if(llTipus.get(i).equals(TipusProducte.LLISTA_REP)){
                concat += "upper(p.prod_tipus) like upper(?)";
                llFiltrar.add("L");
            }
        }
        
        if(ordAsc){
            ordre = "asc";
        }else{
            ordre = "desc";
        }
        
        String actiu = "";
        if(estat == null){
            actiu = " ";
        }else if(estat != null && estat == true){
            actiu = "S";
        }else if (estat != null && estat != true){
            actiu = "N";
        }
        String inst = null;
        PreparedStatement ps = null;
        try {
            inst =  "select p.prod_id, p.prod_titol,p.prod_tipus,p.prod_actiu, e.est_nom " +
                    "from producte p left join estil e on p.prod_estil = e.est_id " +
                    "where (upper(p.prod_titol) like upper(?) OR ? = '') AND (upper(p.prod_tipus) like upper(?) "+ concat +" OR ? = ' ')  AND (upper(p.prod_actiu) like ? OR ? = ' ') "+
                    "order by 2 " + ordre; 
            
            ps = conn.prepareStatement(inst);
        } catch (SQLException ex) {
            throw new GestorBDMilaSpotifyException("No es pot crear el PreparedStatement:\n " + inst + "\n" + ex.getMessage());
        }
        
        try{
            int psInd = 1;
            ps.setString(psInd, "%"+fragNom+"%");
            psInd++;
            ps.setString(psInd, fragNom);
            psInd++;
            ps.setString(psInd, prodTipus);
            psInd++;
            //POSAR EL VALOR DEL PARAMETRE CONCATENAT 
            for(int i=0;i <llFiltrar.size();i++){
                ps.setString(psInd, llFiltrar.get(i));
                psInd++;
            }  
            ps.setString(psInd, prodTipus);
            psInd++;
            ps.setString(psInd, actiu);
            psInd++;
            ps.setString(psInd, actiu);
            
            ResultSet rs = ps.executeQuery();
            String ptipus;
            boolean pactiu;
            Estil estil;
            TipusProducte tipProd =null;
            while (rs.next()) {     
                //desa valor de l'activitat 
                String esActiu = rs.getString(4);
                //comprovar si es actiu o inactiu
                if(esActiu.equals("S")) pactiu = true;
                else pactiu = false;
                //Desar valor estil del producte 
                estil = new Estil(rs.getString(5));
                //desar valor del tipus
                ptipus = rs.getString(3);
                switch (ptipus){
                    case "C" : tipProd = TipusProducte.CANSO;
                               llProd.add( new Canso(rs.getLong(1), rs.getString(2),pactiu,estil,tipProd)); 
                               break;
                                
                    case "A" : tipProd = TipusProducte.ALBUM;
                               llProd.add(new Album(rs.getLong(1), rs.getString(2),pactiu,estil,tipProd)); 
                               break;
                               
                    case "L" : tipProd = TipusProducte.LLISTA_REP;
                               llProd.add(new LlistaRep(rs.getLong(1), rs.getString(2),pactiu,estil,tipProd)); 
                               break;
                }
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
        return llProd;
    }
//    
//    public List<Canso> getCansonsAutor(long idArtista){
//    }
//    
//    public List<Producte> getProductesLlista(long idLlista){
//    }
//    
    public  void inserirProducte(Producte nou) throws GestorBDMilaSpotifyException, SQLException{
        String inst = null;
        try {
            inst = "INSERT INTO producte (prod_titol, prod_actiu, prod_estil, prod_tipus) VALUES (?,?,?,?)";
            qAfegirProd = conn.prepareStatement(inst);
        } catch (SQLException ex) {
            throw new GestorBDMilaSpotifyException("No es pot crear el PreparedStatement:\n " + inst + "\n" + ex.getMessage());
        }
        String prod_titol = nou.getTitol();
        String prod_Actiu;
        if(nou.isActiu()){
            prod_Actiu = "S"; 
        }else{
            prod_Actiu = "N"; 
        }
        Estil estil = nou.getEstil();
        long prod_estil = estil.getId();
        String prod_tipus;
        if(nou.getTipus() == TipusProducte.CANSO){
            prod_tipus = "C";
        }else if(nou.getTipus() == TipusProducte.ALBUM){
            prod_tipus = "A";
        }else {
            prod_tipus = "L";
        }
        
        try {
            qAfegirProd.setString(1, prod_titol);
            qAfegirProd.setString(2, prod_Actiu);
            qAfegirProd.setLong(3, prod_estil);
            qAfegirProd.setString(4, prod_tipus);
            qAfegirProd.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(BDReproduccio.class.getName()).log(Level.SEVERE, null, ex);
        }
        qAfegirProd.close();
        if(nou.getTipus() == TipusProducte.CANSO){
            Canso canso  = (Canso) nou;
            inserirCanso(canso);
            
        }else if(nou.getTipus() == TipusProducte.ALBUM){
            Album album = (Album) nou;
            inserirAlbum(album);
            long idAlbum = obtenirUltimID();
            List<Canso> albCansons =  album.getCansons();
            for(int i = 0; i < albCansons.size();i++)
                afegirCansoAlbum(idAlbum, albCansons.get(i).getId(), i+1);
            
        }else {
            LlistaRep ll = (LlistaRep) nou;
            inserirLlista(ll);
            long idLli = obtenirUltimID();
            List<Producte> llProd = ll.getItems();
            for(int i = 0; i < llProd.size();i++)
                afegirProducteLlista(idLli, llProd.get(i).getId(), i+1);
        }
        
    }
    public void inserirCanso(Canso can) throws GestorBDMilaSpotifyException{
        String inst = null;
        try {
            inst = "INSERT INTO canco (can_id, can_any_creacio, can_interpret, can_durada) VALUES ((SELECT MAX(prod_id) FROM producte),?,?,?)";
            pAfegir = conn.prepareStatement(inst);
            pAfegir.setInt(1,can.getAnyCreacio());
            pAfegir.setLong(2,can.getInterpret().getId());
            pAfegir.setLong(3,can.getDurada());
            pAfegir.executeUpdate();
            pAfegir.close();
        } catch (SQLException ex) {
            Logger.getLogger(BDReproduccio.class.getName()).log(Level.SEVERE, null, ex);
            throw new GestorBDMilaSpotifyException("Objecte passat : " + can + " " + ex.getMessage());
        }
    }
//    public List<Canso> getCansonsAlbum(long idAlbum){
//    }
//    
    
    private long obtenirUltimID(){
        Statement s = null; 
        String inst = null; 
        long ultimId = 0;
        try{
            inst = "SELECT MAX(prod_id) FROM producte ";
            s = conn.prepareStatement(inst);
            ResultSet rs = s.executeQuery(inst);
            if(rs.next()) ultimId = rs.getLong(1);
        } catch (SQLException ex) {
            Logger.getLogger(BDProducte.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ultimId;
    }
    public void modificarProducte(Producte modificat) throws GestorBDMilaSpotifyException{
        String actiu = null;
        if(modificat.isActiu() == true) actiu = "S"; else actiu = "N";
        String tipus = null;
        if(modificat.getTipus().equals(TipusProducte.ALBUM))
            tipus = "A";
        else if(modificat.getTipus().equals(TipusProducte.CANSO))
            tipus = "C";
        else
            tipus = "L";
        //
        String inst = null;
        try {
            inst = "UPDATE  producte SET prod_titol = ? , prod_actiu = ? , prod_estil = ? , prod_tipus = ? WHERE prod_id = ? ";
            qModifProd = conn.prepareStatement(inst);
        } catch (SQLException ex) {
            throw new GestorBDMilaSpotifyException("No es pot crear el PreparedStatement:\n " + inst + "\n" + ex.getMessage());
        }
        try {
            qModifProd.setString(1, modificat.getTitol());
            qModifProd.setString(2, actiu);
            qModifProd.setLong(3, modificat.getEstil().getId());
            qModifProd.setString(4, tipus);
            qModifProd.setLong(5,modificat.getId());
            qModifProd.executeUpdate();
            qModifProd.close();
        } catch (SQLException ex) {
            Logger.getLogger(BDProducte.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(modificat.getTipus() == TipusProducte.CANSO){
            Canso can = (Canso) modificat;
            modificarCanso(can);
        }else if(modificat.getTipus() == TipusProducte.ALBUM){
            Album alb = (Album) modificat;
            modificarAlbum(alb);
        }else{
            LlistaRep ll = (LlistaRep) modificat;
            modificarLlista(ll);
        }

       
    }
    
    public void modificarCanso(Canso canModificat){
        String updQuery = "";
        try{
                updQuery = "UPDATE canco SET can_any_creacio = ? , can_interpret = ? , can_durada = ? WHERE can_id = ? ";
                pModifCanso = conn.prepareStatement(updQuery);
                pModifCanso.setInt(1, canModificat.getAnyCreacio());
                pModifCanso.setLong(2, canModificat.getInterpret().getId());
                pModifCanso.setLong(3, canModificat.getDurada());
                pModifCanso.setLong(4, canModificat.getId());
                pModifCanso.executeUpdate();
                pModifCanso.close();
        } catch (SQLException ex) {
            Logger.getLogger(BDProducte.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void inserirLlista(LlistaRep ll){
         String inst = null;
        try {
            inst = "INSERT INTO llista (lli_id, lli_durada) VALUES ((SELECT MAX(prod_id) FROM producte),?)";
            pAfegir = conn.prepareStatement(inst);
            pAfegir.setLong(1,ll.getDurada());
            pAfegir.executeUpdate();
            pAfegir.close();
        } catch (SQLException ex) {
            Logger.getLogger(BDReproduccio.class.getName()).log(Level.SEVERE, null, ex);
             try {
                 throw new GestorBDMilaSpotifyException("Objecte passat : " + ll + " " + ex.getMessage());
             } catch (GestorBDMilaSpotifyException ex1) {
                 Logger.getLogger(BDProducte.class.getName()).log(Level.SEVERE, null, ex1);
             }
        }
    }
    
    private void modificarLlista(LlistaRep ll){
         String updQuery = "";
        try{
                updQuery = "UPDATE ll SET durada = ? WHERE alb_id = ? ";
                pModifCanso = conn.prepareStatement(updQuery);
                pModifCanso.setLong(1, ll.getDurada());
                pModifCanso.setLong(2, ll.getId());
                pModifCanso.executeUpdate();
                pModifCanso.close();
        } catch (SQLException ex) {
            Logger.getLogger(BDProducte.class.getName()).log(Level.SEVERE, null, ex);
        }
        //PRIMER ELIMINEM TOTES LES CANÇONS DE L'ALBUM Y TORNEM A AFEGIRLES DE NOU
        try{
                updQuery = "DELETE FROM llista_contingut where id_llista = ? ";
                pModifCanso = conn.prepareStatement(updQuery);
                pModifCanso.setLong(1, ll.getId());
                pModifCanso.executeUpdate();
                pModifCanso.close();
        } catch (SQLException ex) {
            Logger.getLogger(BDProducte.class.getName()).log(Level.SEVERE, null, ex);
        }
        int pos = 1;
        for(Producte p : ll.getItems()){
            afegirCansoAlbum(ll.getId(), p.getId(), pos);
            pos++;
        }
    }
    
    private void inserirAlbum(Album album){
        String inst = null;
        try {
            inst = "INSERT INTO album (alb_id, any_creacio, alb_durada) VALUES ((SELECT MAX(prod_id) FROM producte),?,?)";
            pAfegir = conn.prepareStatement(inst);
            pAfegir.setInt(1,album.getAnyCreacio());
            pAfegir.setLong(2,album.getDurada());
            pAfegir.executeUpdate();
            pAfegir.close();
        } catch (SQLException ex) {
            Logger.getLogger(BDReproduccio.class.getName()).log(Level.SEVERE, null, ex);
             try {
                 throw new GestorBDMilaSpotifyException("Objecte passat : " + album + " " + ex.getMessage());
             } catch (GestorBDMilaSpotifyException ex1) {
                 Logger.getLogger(BDProducte.class.getName()).log(Level.SEVERE, null, ex1);
             }
        }
    }
    private void modificarAlbum(Album albModificat){
        String updQuery = "";
        try{
                updQuery = "UPDATE album SET any_creacio = ? , alb_durada = ? ,  WHERE alb_id = ? ";
                qModifProd = conn.prepareStatement(updQuery);
                qModifProd.setInt(1, albModificat.getAnyCreacio());
                qModifProd.setLong(2, albModificat.getDurada());
                qModifProd.setLong(3, albModificat.getId());
                qModifProd.executeUpdate();
                qModifProd.close();
        } catch (SQLException ex) {
            Logger.getLogger(BDProducte.class.getName()).log(Level.SEVERE, null, ex);
        }
        //PRIMER ELIMINEM TOTES LES CANÇONS DE L'ALBUM Y TORNEM A AFEGIRLES DE NOU
        try{
                updQuery = "DELETE FROM album_contingut where id_album = ? ";
                pModifCanso = conn.prepareStatement(updQuery);
                pModifCanso.setLong(1, albModificat.getId());
                pModifCanso.executeUpdate();
                pModifCanso.close();
        } catch (SQLException ex) {
            Logger.getLogger(BDProducte.class.getName()).log(Level.SEVERE, null, ex);
        }
        int pos = 1;
        for(Canso c : albModificat.getCansons()){
            afegirCansoAlbum(albModificat.getId(), c.getId(), pos);
            pos++;
        }
    }
    
    public  void eliminarProducte(long idProducte){
        String inst = null;
        try {
            inst = "DELETE  FROM producte WHERE prod_id = ? ";
            qDelProd = conn.prepareStatement(inst);
            qDelProd.setLong(1, idProducte);
            qDelProd.executeUpdate();
        } catch (SQLException ex) {
             try {
                 throw new GestorBDMilaSpotifyException("No es pot crear el PreparedStatement:\n " + inst + "\n" + ex.getMessage());
             } catch (GestorBDMilaSpotifyException ex1) {
                 Logger.getLogger(BDProducte.class.getName()).log(Level.SEVERE, null, ex1);
             }
        }
    }
    
    public void afegirCansoAlbum(long albumId, long cansoId,int pos){
        String inst = null;
        try {
            inst = "INSERT INTO album_contingut (id_album, id_canco, pos) VALUES (?,?,?)";
            pAfegir = conn.prepareStatement(inst);
            pAfegir.setLong(1, albumId);
            pAfegir.setLong(2,cansoId);
            pAfegir.setInt(3,pos);
            pAfegir.executeUpdate();
            pAfegir.close();
        } catch (SQLException ex) {
            Logger.getLogger(BDReproduccio.class.getName()).log(Level.SEVERE, null, ex);
             try {
                 throw new GestorBDMilaSpotifyException("Objecte passat : " + ex.getMessage());
             } catch (GestorBDMilaSpotifyException ex1) {
                 Logger.getLogger(BDProducte.class.getName()).log(Level.SEVERE, null, ex1);
             }
        }
    }
    
    public void eliminarCansoAlbum(long albumId, long cansoId){
        String inst = null;
        try {
            inst = "DELETE  FROM album_contingut WHERE id_album = ? AND id_canco = ? ";
            qDelProd = conn.prepareStatement(inst);
            qDelProd.setLong(1, albumId);
            qDelProd.setLong(2, cansoId);
            qDelProd.close();
        } catch (SQLException ex) {
             try {
                 throw new GestorBDMilaSpotifyException("No es pot eliminar cancço d'album:\n " + inst + "\n" + ex.getMessage());
             } catch (GestorBDMilaSpotifyException ex1) {
                 Logger.getLogger(BDProducte.class.getName()).log(Level.SEVERE, null, ex1);
             }
        }
    }
    
    public void afegirProducteLlista(long idLlista, long idProducte,int pos){
        String inst = null;
        try {
            inst = "INSERT INTO llista_contingut (id_llista, id_producte, pos) VALUES (?,?,?)";
            pAfegir = conn.prepareStatement(inst);
            pAfegir.setLong(1, idLlista);
            pAfegir.setLong(2,idProducte);
            pAfegir.setInt(3,pos);
            pAfegir.executeUpdate();
            pAfegir.close();
        } catch (SQLException ex) {
            Logger.getLogger(BDReproduccio.class.getName()).log(Level.SEVERE, null, ex);
             try {
                 throw new GestorBDMilaSpotifyException("Objecte passat : " + ex.getMessage());
             } catch (GestorBDMilaSpotifyException ex1) {
                 Logger.getLogger(BDProducte.class.getName()).log(Level.SEVERE, null, ex1);
             }
        }
    }
    
    public void eliminarProducteLlista(long idLlista, long idProducte){
    
    }
    
    
    public static Canso getCanso(long id ) throws SQLException {
        Canso  can = null;
        PreparedStatement ps = null;
        String inst  = null;
        try {
            inst = "select p.prod_id, p.prod_titol, p.prod_actiu, p.prod_estil, c.can_any_creacio, c.can_interpret, "
                    + " c.can_durada, a.art_nom, a.art_tipus, e.est_id, e.est_nom " +
                    "from producte p join canco c on c.can_id = p.prod_id " +
                    "                join artista a on a.art_id = c.can_interpret  "+ 
                    "                join estil e  on e.est_id = p.prod_estil  "+ 
                    "where c.can_id = ? " + 
                    "order by 2 asc ";
            ps = conn.prepareStatement(inst);
            ps.setLong(1, id);
        } catch (SQLException ex) {
            Logger.getLogger(BDProducte.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                String artTipus = rs.getString(9);
                boolean estat = false;
                if(rs.getString(3).equals("S")) estat = true;
                switch (artTipus) {
                    case "I" : can = new Canso(rs.getLong(1), rs.getString(2), estat, new Estil(rs.getLong(10),rs.getString(11)), TipusProducte.CANSO, rs.getInt(5), rs.getInt(7), new ArtistaIndividual(rs.getLong(6), rs.getString(8), TipusArtista.INDIVIDUAL));
                    break;
                    case "G" : can = new Canso(rs.getLong(1), rs.getString(2), estat, new Estil(rs.getLong(10),rs.getString(11)), TipusProducte.CANSO, rs.getInt(5), rs.getInt(7), new Grup(rs.getLong(6), rs.getString(8), TipusArtista.GRUPAL));
                    break; 
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error en obtenir canso. " + ex.getMessage());
        }
        ps.close();
        return can;
    }
    
    public static Album getAlbum(long idAlbum){
        Album  alb = null;
        PreparedStatement ps = null;
        String inst  = null;
        List<Canso> cansAlbum =  BDProducte.getCansonsAlbum(idAlbum);
        try {
            inst = "select  p.prod_id, p.prod_titol,p.prod_actiu, p.prod_tipus, p.prod_estil, e.est_nom, a.any_creacio, a.alb_durada " +
                    "from producte p join album a on p.prod_id = a.alb_id " +
                    "                join estil e on e.est_id = p.prod_estil " +
                    "where p.prod_id = ? ";
            ps = conn.prepareStatement(inst);
            ps.setLong(1, idAlbum);
        } catch (SQLException ex) {
            Logger.getLogger(BDProducte.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                boolean estat = false;
                if(rs.getString(3).equals("S")) estat = true;
                Estil estil = new Estil(rs.getLong(5), rs.getString(6));
                alb = new Album(rs.getLong(1),rs.getString(2),estat,estil,TipusProducte.ALBUM,rs.getInt(7),cansAlbum,rs.getLong(8));
                
            }
        } catch (SQLException ex) {
            System.out.println("Error en obtenir album. " + ex.getMessage());
        }

        return alb;
    }
    
    public static List<Canso> getCansons(){
        String inst = null; 
        List<Canso> llCans = new ArrayList<>();
        TipusProducte tp = TipusProducte.CANSO;
        Statement s = null;
        try {
            inst = "select p.prod_id, p.prod_titol, p.prod_tipus, p.prod_actiu, p.prod_estil, e.est_nom, " +
                    "c.can_any_creacio, c.can_durada, c.can_interpret, a.art_nom, a.art_tipus  " +
                    "from producte p join canco c on c.can_id = p.prod_id " +
                    "                join estil e on e.est_id = p.prod_estil " +
                    "                join artista a on a.art_id = c.can_interpret " +
                    "where p.prod_tipus like 'C' " +
                    "order by 2 asc ";
            s = conn.prepareStatement(inst);
            ResultSet rs = s.executeQuery(inst);
            while(rs.next()){
                boolean actiu = false;
                if(rs.getString(4).equals("S")) actiu = true;
                Estil estil = new Estil(rs.getLong(5),rs.getString(6));
                if(rs.getString(11).equals("I"))
                    llCans.add(new Canso(rs.getLong(1),rs.getString(2),actiu,estil,tp,rs.getInt(7),rs.getLong(8),new ArtistaIndividual(rs.getLong(9), rs.getString(10), TipusArtista.INDIVIDUAL)));
                else
                    llCans.add(new Canso(rs.getLong(1),rs.getString(2),actiu,estil,tp,rs.getInt(7),rs.getLong(8),new Grup(rs.getLong(9), rs.getString(10), TipusArtista.INDIVIDUAL)));
            }
            s.close();
        } catch (SQLException ex) {
            Logger.getLogger(BDProducte.class.getName()).log(Level.SEVERE, null, ex);
        }
        return llCans;
    }
    
    public static List<Canso> getCansonsAlbum(long idAlbum){
        List<Canso> cans =  new ArrayList<>();
        String inst  = null;
        PreparedStatement ps = null; 
        TipusProducte tp = TipusProducte.CANSO;
        try {
            inst = "select  p.prod_id, p.prod_titol,p.prod_actiu, p.prod_tipus,  p.prod_estil, e.est_nom, "
                    + "c.can_any_creacio, c.can_interpret,a.art_nom,a.art_tipus, c.can_durada " +
                    "from album_contingut ac join canco c on c.can_id = ac.id_canco " +
                    "                        join producte p on p.prod_id = c.can_id  " +
                    "                        join artista a on a.art_id = c.can_interpret " +
                    "                        join estil e on e.est_id = p.prod_estil " +
                    "where ac.id_album = ? " +
                    "order by ac.pos asc ";
            ps = conn.prepareStatement(inst);
            ps.setLong(1, idAlbum);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                boolean actiu = false;
                if(rs.getString(3).equals("S")) actiu = true;
                Estil estil = new Estil(rs.getLong(5),rs.getString(6));
                if(rs.getString(10).equals("I"))
                    cans.add(new Canso(rs.getLong(1),rs.getString(2),actiu,estil,tp,rs.getInt(7),rs.getLong(11),new ArtistaIndividual(rs.getLong(8), rs.getString(9), TipusArtista.INDIVIDUAL)));
                else
                    cans.add(new Canso(rs.getLong(1),rs.getString(2),actiu,estil,tp,rs.getInt(7),rs.getLong(11),new Grup(rs.getLong(8), rs.getString(9), TipusArtista.INDIVIDUAL)));

            }
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(BDProducte.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cans;
    }
    
    
    
    
    
}
