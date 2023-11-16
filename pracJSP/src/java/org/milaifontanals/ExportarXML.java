package org.milaifontanals;


import java.io.File;
import static java.lang.System.out;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ExportarXML {

    public static void main(String[] args) throws SQLException {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();

            Document doc = db.newDocument();
            Element arrel = doc.createElement("contactes");
            doc.appendChild(arrel);     // És l'arrel per què s'afegeix al document
            
            String url = "jdbc:mysql://localhost:3306/pracjsp?allowMultiQueries=true";
            
            try {
                Connection con = (Connection) DriverManager.getConnection(url,"root","");
                PreparedStatement st = null;
                String query = "SELECT nom,cognoms,correu,adreca,telefon,ca.nomCat FROM contacte c "
                        + "JOIN categories ca ON c.categoria = ca.idCat ";
                st = (PreparedStatement) con.prepareStatement(query);
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

            System.out.println("Arxiu enregistrat!");

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
    }


}
