/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.milaifontanals;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.lowagie.text.Cell;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import static java.lang.System.out;
import org.milaifontanals.Users;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Element;


/**
 *
 * @author Usuari
 */
public class DBManager {
    private String url = "jdbc:mysql://localhost:3306/pracjsp?allowMultiQueries=true";
    private Connection con = null;
    
    public void connect(){
        try {
            Class.forName(("com.mysql.jdbc.Driver"));
            con = (Connection) DriverManager.getConnection(url,"root","");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void close(){
        try {
            if(con!=null){
                con.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean validUser(String mail, String pass) {
        PreparedStatement st = null;
        try{
            st = con.prepareStatement("select mail, pass from users WHERE mail=? and pass=?;");
            st.setString(1, mail);
            st.setString(2, pass);
            ResultSet rs = st.executeQuery();
            Users u = new Users();
            if(rs.next()) {
                u.setMail(rs.getString(1));
                u.setPass(rs.getString(2));
                return true;
            }  
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public boolean registrarUsuari(String nom,String mail,String pass){
        PreparedStatement st = null;
        int updateQuery = 0;
        try
        {
            String insertQuery = "INSERT into users(nom,mail,pass) values (?,?,?)";
            st = con.prepareStatement(insertQuery);
            st.setString(1,nom);
            st.setString(2,mail);
            st.setString(3,pass);

            updateQuery = st.executeUpdate();
            
            if(updateQuery !=0){
                return true;
            }
        }catch (SQLException ex){
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
        
    }
    
    public String mostrarTodo(Integer userId){
        String result = "";
        String miQuery = "SELECT nom,cognoms,correu,adreca,telefon,ca.nomCat FROM contacte c "
                                + "JOIN categories ca ON c.categoria = ca.idCat "
                        + "WHERE user = ?";
        PreparedStatement pstmt = null;
        ResultSet rst = null;
        try {
           //Class.forName(("com.mysql.jdbc.Driver"));
           //con = DriverManager.getConnection(JdbcURL, Username, password);
           pstmt = con.prepareStatement(miQuery);
           pstmt.setInt(1,userId);
           rst = pstmt.executeQuery(); 
           result +="<table id='taula'><tr><th>Nom</th><th>Cognoms</th><th>Correu</th><th>Adreça</th><th>Teléfon</th><th>Categoria</th></tr>";
           while(rst.next()) {
                result +="<tr>";
                result +="<td>"+rst.getString(1)+"</td>";
                result +="<td>"+rst.getString(2)+"</td>";
                result +="<td>"+rst.getString(3)+"</td>";
                result +="<td>"+rst.getString(4)+"</td>";
                result +="<td>"+rst.getString(5)+"</td>";
                result +="<td>"+rst.getString(6)+"</td>";
                result +="</tr>";
           }
           result +="</table>";
           return result;
        } catch(SQLException exec) {
            return exec.getMessage();
        }
        
    }
    
    public boolean afegirContacte(String nom, String cognoms, String adreca, String correu, String telefon,String categoria,int userId){
        PreparedStatement st = null;
        int updateQuery = 0;
        try
        {
            String insertQuery = "INSERT into contacte(nom,cognoms,adreca,correu,telefon,categoria,user) values (?,?,?,?,?,?,?)";
            st = con.prepareStatement(insertQuery);
            st.setString(1,nom);
            st.setString(2,cognoms);
            st.setString(3,adreca);
            st.setString(4,correu);
            st.setString(5,telefon);
            st.setString(6,categoria);
            st.setInt(7,userId);
            updateQuery = st.executeUpdate();
            
            if(updateQuery !=0){
                return true;
            }
        }catch (SQLException ex){
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public boolean canviarContrasenya(String mail, String passNou){
         PreparedStatement st = null;
         int updateQuery = 0;
        try
        {
            String insertQuery = "update users set pass = ? where mail = ?;";
            st = con.prepareStatement(insertQuery);
            st.setString(1,passNou);
            st.setString(2,mail);
            updateQuery = st.executeUpdate();
            
            if(updateQuery !=0){
                return true;
            }
        }catch (SQLException ex){
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    public boolean esAdmin(String user) {
        PreparedStatement st = null;
        try{
            st = con.prepareStatement("select mail, pass from users WHERE mail=?;");
            st.setString(1, user);
            ResultSet rs = st.executeQuery();
            Users u = new Users();
            if(rs.next()) {
                u.setMail(rs.getString(1));
                u.setEsAdmin(rs.getInt(3));
                if(u.getEsAdmin() == 1) {
                    return true;
                }
            }  
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
   /* public static List <Users> getUsers() {
        String url = "jdbc:mysql://localhost:3306/pracjsp?allowMultiQueries=true";
        List <Users>  users =  new ArrayList();
        try {
            Connection con = (Connection) DriverManager.getConnection(url,"root","");
            PreparedStatement st = null;
            st = con.prepareStatement("select mail  from users;");
            ResultSet rs = st.executeQuery();
            while(rs.next()) {
                Users u = new Users();
                u.setMail(rs.getString(1));
                u.setEsAdmin(rs.getInt(2));
                users.add(u);
            }
        }
        catch (SQLException message) {
            out.print(message);
            users = null;
        }
        return users;
    }*/
    //ArrayList<String> alNoms,ArrayList<String> alCognoms,
     //       ArrayList<String> alAdreces,ArrayList<String> alCorreus,ArrayList<String> alTelefons,ArrayList<String> alCategories
    public boolean crearPDF(Integer userId) throws FileNotFoundException, DocumentException{

        String fileName = "A:\\DAW2\\M07-DWS\\JSP\\PRACTICA\\pracJSP\\contactes.pdf";
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(fileName));
            document.open();
            Paragraph prgf = new Paragraph("Contactes");
            document.add(prgf);
            //
            PdfPTable table = new PdfPTable(6);
            String[] headers = {"Nom","Cognoms","Adreça","Correu","Telefon","Categoria"};
            PdfPCell cell;
            for(int i=0;i<headers.length;i++){
                cell = new PdfPCell(new Phrase(headers[i]));
                table.addCell(cell);
                
            }
            //String url = "jdbc:mysql://localhost:3306/pracjsp?allowMultiQueries=true";
//            List <Users>  users =  new ArrayList();
            PreparedStatement st = null;
            ResultSet rs = null;
//            String query = "SELECT c.nom,c.cognoms,c.correu,c.adreca,c.telefon,ca.nomCat "
//                    + "FROM contacte c JOIN categories ca ON c.categoria = ca.idCat"
//                    + "WHERE c.user = ?";
            String query = "SELECT c.nom,c.cognoms,c.correu,c.adreca,c.telefon,ca.nomCat,c.user "
                    + "FROM contacte c JOIN categories ca ON c.categoria = ca.idCat";
            try {
                st = con.prepareStatement(query);
                //st.setInt(1, userId);
                rs = st.executeQuery();
                while(rs.next()) {    
                    if(rs.getInt(7) == userId){
                        cell = new PdfPCell(new Phrase(rs.getString(1)));
                        table.addCell(cell);
                        cell = new PdfPCell(new Phrase(rs.getString(2)));
                        table.addCell(cell);
                        cell = new PdfPCell(new Phrase(rs.getString(3)));
                        table.addCell(cell);
                        cell = new PdfPCell(new Phrase(rs.getString(4)));
                        table.addCell(cell);
                        cell = new PdfPCell(new Phrase(rs.getString(5)));
                        table.addCell(cell);
                        cell = new PdfPCell(new Phrase(rs.getString(6)));
                        table.addCell(cell);
                    }
                    
                }
            }
            catch (SQLException message) {
                System.out.print(message.getMessage());
//                users = null;
            }
            document.add(table);
            document.close();
            return true;
        } catch (DocumentException | FileNotFoundException e) {
            return false;
        }
        
        
    }
    
    public String getCategories(){
        PreparedStatement pstmt = null;
        ResultSet rst = null;
        String myQuery = "select idCat,nomCat from categories";
        try {
           pstmt = con.prepareStatement(myQuery);
           rst = pstmt.executeQuery(); 
           String options = "";
           while(rst.next()) {
              options += "<option value='"+rst.getInt(1)+"'>"+rst.getString(2)+"</option>";
           }
           return options;
        } catch(SQLException exec) {
           return exec.getMessage();
        }       
    }
    
    public String buscarContacte(String buscarTxt){
        String query = "SELECT nom,cognoms,correu,adreca,telefon,ca.nomCat FROM contacte c "
                             + "JOIN categories ca ON c.categoria = ca.idCat "
                             + "WHERE nom=? OR cognoms = ? OR correu = ? OR adreca = ? OR telefon = ?;";
        PreparedStatement pstmt = null;
        ResultSet rst = null;
        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, buscarTxt);
            pstmt.setString(2, buscarTxt);
            pstmt.setString(3, buscarTxt);
            pstmt.setString(4, buscarTxt);
            pstmt.setString(5, buscarTxt);

           rst = pstmt.executeQuery(); 
           String table = "";
           table += "<table><tr><th>Nom</th><th>Cognoms</th><th>Correu</th><th>Adreça</th><th>Teléfon</th><th>Categoria</th></tr>";
           while(rst.next()) {
                table += "<tr>";
                table += "<td>"+rst.getString(1)+"</td>";
                table += "<td>"+rst.getString(2)+"</td>";
                table += "<td>"+rst.getString(3)+"</td>";
                table += "<td>"+rst.getString(4)+"</td>";
                table += "<td>"+rst.getString(5)+"</td>";
                table += "<td>"+rst.getString(6)+"</td>";
                table += "</tr>";
           }
           table += "</table>";
           return table;
        } catch(SQLException exec) {
           return exec.getMessage();
        }
    }
    
    public Integer getUserID(String mail){
        String query = "select id from users where mail = ?";
        PreparedStatement pstmt= null;
        ResultSet rst = null;
        int userID= 0;
        try {
           
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, mail);
            rst = pstmt.executeQuery();
             while(rst.next()) {
                 userID = rst.getInt(1);
             }
            return userID;
        } catch (SQLException e) {
            return -1;
        }
    }
    
    public String filtrar(String filterQuery,  String[] cats ,Integer userId){
        PreparedStatement pstmt= null;
        ResultSet rst = null;
          try {
            pstmt = con.prepareStatement(filterQuery);
            int indice = 1;
            for(int i= 0; i< cats.length ;i++){
                if(cats[i] != null){
                    pstmt.setString(indice, cats[i]);
                    indice++;
                }  
            }

            rst = pstmt.executeQuery();
            String table = "";
            table += "<table><tr><th>Nom</th><th>Cognoms</th><th>Correu</th><th>Adreça</th><th>Teléfon</th><th>Categoria</th></tr>";
            
                while(rst.next()) {
                  if(userId == rst.getInt(7)){
                        table += "<tr>";
                        table += "<td>"+rst.getString(1)+"</td>";
                        table += "<td>"+rst.getString(2)+"</td>";
                        table += "<td>"+rst.getString(3)+"</td>";
                        table += "<td>"+rst.getString(4)+"</td>";
                        table += "<td>"+rst.getString(5)+"</td>";
                        table += "<td>"+rst.getString(6)+"</td>";
                        table += "</tr>";
                  }
              
                }

               table += "</table>";
               return table;
            } catch(Exception exec) {
               return exec.getMessage();
            }
    }
    
    public boolean exportarEnXML(){
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();

            org.w3c.dom.Document doc = db.newDocument();
            Element arrel = doc.createElement("contactes");
            doc.appendChild(arrel);     // És l'arrel per què s'afegeix al document
            
           // String url = "jdbc:mysql://localhost:3306/pracjsp?allowMultiQueries=true";
            
            try {
                //java.sql.Connection con = (java.sql.Connection) DriverManager.getConnection(url,"root","");
                PreparedStatement st = null;
                String query = "SELECT nom,cognoms,correu,adreca,telefon,ca.nomCat FROM contacte c "
                        + "JOIN categories ca ON c.categoria = ca.idCat ";
                st =  con.prepareStatement(query);
                ResultSet rs = st.executeQuery();
                while(rs.next()) {
                    Element contacte = doc.createElement("contacte");
                    contacte.setAttribute("categoria", rs.getString(6));
                    //
                    Element nom = doc.createElement("nom");
                    nom.appendChild(doc.createTextNode(rs.getString(1)));
                    contacte.appendChild(nom);
                    //
                    Element cognoms = doc.createElement("cognoms");
                    cognoms.appendChild(doc.createTextNode(rs.getString(2)));
                    contacte.appendChild(cognoms);
                    //
                    Element adreca = doc.createElement("Adreça");
                    adreca.appendChild(doc.createTextNode(rs.getString(4)));
                    contacte.appendChild(adreca);
                    //
                    Element correu = doc.createElement("correu");
                    correu.appendChild(doc.createTextNode(rs.getString(3)));
                    contacte.appendChild(correu);
                    //
                    Element telefon = doc.createElement("telefon");
                    telefon.appendChild(doc.createTextNode(rs.getString(5)));
                    contacte.appendChild(telefon);
                    //
                    arrel.appendChild(contacte);
                }
                
            }
            catch (SQLException message) {
                out.print(message);
            }
            // Enregistrar el contingut a disc - Fórmula a copiar sempre!!!
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);

// Per visualitzar resultat per consola
            StreamResult resultConsola = new StreamResult(System.out);
// Per generar fitxer
            StreamResult resultDisc = new StreamResult(new File("contactes.xml"));     // Es crea dins l'arrel del projecte

// Les següents línies són per fer-ho "llegible" per pantalla i tambér per arxiu
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");    // Per indicar el nombre d'espais d'indentació
            transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "contactes.dtd");
            
            transformer.transform(source, resultConsola);
            transformer.transform(source, resultDisc);
            return true;
        } catch (ParserConfigurationException | TransformerException pce) {
            return false;
        }
        
        
    }
    
    public String showMessage(String msg,String titul){    
        String result = "";
        
        result += "<div class=\"toast-container position-fixed bottom-0 end-0 p-3\">\n" +
                "  <div id=\"liveToast\" class=\"toast\" role=\"alert\" aria-live=\"assertive\" aria-atomic=\"true\">\n" +
                "    <div class=\"toast-header\">\n" +
                "      <img src=\"...\" class=\"rounded me-2\" alt=\"...\">\n" +
                "      <strong class=\"me-auto\">"+titul+"</strong>" +
                "      <small>1 sec ago</small>\n" +
                "      <button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"toast\" aria-label=\"Close\"></button>\n" +
                "    </div>\n" +
                "    <div class=\"toast-body\">\n" + msg +
                "    </div>\n" +
                "  </div>\n" +
                "</div>";

        return result;
    }
    
    public static void main(String[] args) throws FileNotFoundException, DocumentException, SQLException {
        DBManager dm = new DBManager();
        dm.connect();
        //System.out.println(dm.validUser("bari","bari"));
        //System.out.println(dm.afegirContacte("nom1", "cognoms1", "adreca1", "email1", "telefon1"));
        //System.out.println(dm.canviarContrasenya("bari", "bari"));
        System.out.println("");
        //System.out.println( dm.afegirContacte("bari", "bari", "adresabari", "bari@gmail.com", "631088138",null));
        //ExportarXML exprt = new ExportarXML();
        int userID = dm.getUserID("xavi@gmail.com");
        System.out.println(userID);
        System.out.println(dm.exportarEnXML());
        
//        System.out.println(dm.crearPDF(1));
       
    }
}
