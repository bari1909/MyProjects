/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.milaifonatanals.projecte1.vista;

import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JYearChooser;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.RadioButton;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.table.DefaultTableModel;
import org.milaifontanals.projecte1.bd.BDArtista;
import org.milaifontanals.projecte1.bd.BDClient;
import org.milaifontanals.projecte1.bd.BDEstil;
import org.milaifontanals.projecte1.bd.BDProducte;
import org.milaifontanals.projecte1.bd.BDReproduccio;
import org.milaifontanals.projecte1.bd.GestorBDMilaSpotify;
import org.milaifontanals.projecte1.bd.GestorBDMilaSpotifyException;
import org.milaifontanals.projete1.model.Album;
import org.milaifontanals.projete1.model.Artista;
import org.milaifontanals.projete1.model.ArtistaIndividual;
import org.milaifontanals.projete1.model.Canso;
import org.milaifontanals.projete1.model.Client;
import org.milaifontanals.projete1.model.Estil;
import org.milaifontanals.projete1.model.Grup;
import org.milaifontanals.projete1.model.LlistaRep;
import org.milaifontanals.projete1.model.Producte;
import org.milaifontanals.projete1.model.Reproduccio;
import org.milaifontanals.projete1.model.TipusArtista;
import org.milaifontanals.projete1.model.TipusProducte;

/**
 *
 * @author abdel
 */
public class MantenimentDades_V2 {
    private GestorBDMilaSpotify gbdms = null;
    private JFrame f;
    private JTable taula;
    private DefaultTableModel tReproduccions;
    private JDialog form;
    private Boolean esAlta;
    private int filaModificada;
    private JTextArea txtInfo;
    private JButton bCercar;
    private JButton bNetejar;
    private JButton bEliminar;
    private JButton bAfegir;
    private JButton bModificar;
    private JButton bCancelar;
    private JButton bEnregistrar;
    private JComboBox despClients;
    private JComboBox despClientsFormolari;
    private JComboBox despProductes;
    private List<Client> llCli;
    private List<Reproduccio> llRep;
    private List<Producte> llProd;
    private JTextField tfProdNom;
    private JDateChooser dataIni;
    private JDateChooser dataFi;
    private JDateChooser momentTmp;
    private Date momentTmpAntic;
    //PANELS PRINCIPALS PER TREBALLAR 
    private JPanel pReproduccio;
    private JMenu productes;
    private JMenu clients;
    private JMenu reproduccions;
    //COMPONENTS PER GESTIONAR PRODUCTES 
    private JPanel pProducte;
    private JButton bAfegirProd;
    private JButton bEliminarProd;
    private JButton bModificarProd;
    private JButton bImprimirProd;
    private JButton bEnregProd;
    private JButton bCancelProd;
    private JComboBox despTipusProd;
    private DefaultTableModel tProductes;
    private JTable taulaProds;
    private JRadioButton actiu;
    private JRadioButton inactiu;
    private JRadioButton tots;
    private JRadioButton rbActiu;
    private JRadioButton rbInactiu;
    private ButtonGroup btnGroup;
    private JCheckBox ckCanso;
    private JCheckBox ckAlbum;
    private JCheckBox ckLlista;
    private JTextField tfProdTitol;
    private JButton bNetejarProd;
    private JButton bCercarProd;
    //private String tipusPerAfegir = null;
    private JDialog formCanso;
    private JDialog formAlbum;
    private JDialog formLlista;
    private JTextField tfTitolCanso;
    private ButtonGroup btnGroupCanso;
    private JYearChooser anyCreacio;
    private List<Artista> llArt;
    private JList<Artista> interpret;
    private JComboBox despEstils;
    private List<Estil> llEst;
    private JSpinner spPosicio;
    private TipusProducte tipuActual;
    private  JSpinner spMinuts;
    private  JSpinner spSegons;
    private boolean esAltaProd = false;
    private int filaSeleccionada;
    private  JTextField tfTitolAlbum;
    private JTextField tfTitolLlista;
    private DefaultTableModel tCansons;
    private JTable taulaCansons;
    private JComboBox despCanso; 
    private List<Canso> llCansons;
    private List<Canso> llCanPerAfegir = new ArrayList<>();
    private List<Producte> llProdPerAfegir = new ArrayList<>();
    private JLabel albDurada;
    private long duradaAlb;
    private long duradaLlista;
    private JComboBox despCanALb;
    private  DefaultTableModel tLlista_rep;
    private JTable taulaLlista_rep;
    private JLabel lbLliDurada;
    private List<Producte> llCanAlb;
    private JButton bElimContingut;
    private JButton bAfegirContingut;
    private JRadioButton rbAsc;
    private JRadioButton rbDesc;
     
    
    
     public static void main(String[] args) {
        String str="javax.swing.plaf.nimbus.NimbusLookAndFeel";
        
        try {
            UIManager.setLookAndFeel(str);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MantenimentDades_V2.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(MantenimentDades_V2.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(MantenimentDades_V2.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(MantenimentDades_V2.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        String arguments[] = {"MilaSpotfiy.properties"};
        MantenimentDades_V2 md = new MantenimentDades_V2();
        md.run(arguments[0]); 
    }
    
    public void run(String  args){
        try {
            gbdms = new GestorBDMilaSpotify(args);
        } catch (GestorBDMilaSpotifyException ex) {
            System.out.println("Error en crear GestorBDMilaSpotify: " + ex.getMessage());
            System.exit(1);
        }
        f = new JFrame("Mila Spotify");
        f.setPreferredSize(new Dimension(1000, 640));
        f.setResizable(false);
        //
        afegirMenu();
        // En tancar la finestra, tancar la connexió
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                try {
                    //gbd.desferCanvis();
                    gbdms.tancar();
                } catch (GestorBDMilaSpotifyException ex) {
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException ex1) {
                    }
                }
                System.exit(0);
            }
        });
        
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }

    private void crearPanelFiltracio() {
        despClients = omplirDespClients();
        JLabel lbCli = new JLabel("Client");
        lbCli.setFont(new Font("Montserrat", Font.BOLD, 12));
        lbCli.setForeground(new Color(234, 234, 234));
        Box bClient = Box.createHorizontalBox();
        bClient.setPreferredSize(new Dimension(200, 25));
        bClient.add(lbCli);
        bClient.add(Box.createHorizontalStrut(10));
        bClient.add(despClients);
        
        Box bTitolProducte = Box.createHorizontalBox();
        JLabel lbTitProd = new JLabel("Producte");
        lbTitProd.setFont(new Font("Montserrat", Font.BOLD, 12));
        lbTitProd.setForeground(new Color(234, 234, 234));
        tfProdNom = new JTextField();
        bTitolProducte.add(lbTitProd);
        bTitolProducte.add(Box.createHorizontalStrut(10));
        bTitolProducte.add(tfProdNom);
        
        Box bIni = Box.createHorizontalBox();
        JLabel lbIni = new JLabel("Inici");
        lbIni.setFont(new Font("Montserrat", Font.BOLD, 12));
        lbIni.setForeground(new Color(234, 234, 234));
        dataIni = new JDateChooser();
        dataIni.setDateFormatString("dd-MM-yyyy");
        dataIni.setOpaque(false);
        bIni.add(lbIni);
        bIni.add(Box.createHorizontalStrut(10));
        bIni.add(dataIni);
        
        Box bFi = Box.createHorizontalBox();
        JLabel lbFi = new JLabel("Fi");
        lbFi.setFont(new Font("Montserrat", Font.BOLD, 12));
        lbFi.setForeground(new Color(234, 234, 234));
        dataFi = new JDateChooser();
        dataFi.setDateFormatString("dd-MM-yyyy");
        dataFi.setOpaque(false);
        bFi.add(lbFi);
        bFi.add(Box.createHorizontalStrut(24));
        bFi.add(dataFi);
        
        JLabel interval = new JLabel("Interval");
        Box bLabel = Box.createHorizontalBox();
        bLabel.add(interval);
        //bLabel.add(separador);
        Box bInterval = Box.createVerticalBox();
        bInterval.add(bLabel);
        bInterval.add(bIni);
        bInterval.add(Box.createVerticalStrut(20));
        bInterval.add(bFi);
        
        Box bButtons = Box.createHorizontalBox();
        bCercar = new JButton("Cercar");
        bCercar.setToolTipText("Cercar reproduccions");
        bNetejar = new JButton("Netejar");
        bNetejar.setToolTipText("Neteja filtres");
        bButtons.add(bNetejar);
        bButtons.add(Box.createHorizontalStrut(20));
        bButtons.add(bCercar);
        
        Box b = Box.createVerticalBox();
        b.add(bClient);
        b.add(Box.createVerticalStrut(20));
        b.add(bTitolProducte);
        b.add(Box.createVerticalStrut(20));
        b.add(bInterval);
        b.add(Box.createVerticalStrut(20));
        b.add(bButtons);
        JPanel pFiltracio = new JPanel();
        pFiltracio.setPreferredSize(new Dimension(300,0));
        pFiltracio.add(b);
//        pFiltracio.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
        pFiltracio.setBackground(new Color(60, 64, 72));
        //pFiltracio.setBackground(new Color(178, 178, 178));
        pReproduccio.add(pFiltracio, BorderLayout.WEST);
    }
    
    private void omplirTaulaReproduccions(){
        tReproduccions.setRowCount(0);
        BDReproduccio bdr = new BDReproduccio();
        SimpleDateFormat dFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        try {
            llRep = bdr.getReproduccions();
            for(Reproduccio r : llRep){
                tReproduccions.addRow( new Object[]{r.getClient().getNom(),r.getProducte().getTitol(),dFormat.format(r.getTimestamp())});
            }
        } catch (GestorBDMilaSpotifyException ex) {
            Logger.getLogger(MantenimentDades_V2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void omplirTaulaProductes(){
        tProductes.setRowCount(0);
        //variable per guardar el valor tipus com a TipusProducte
        TipusProducte tipus;
        //guardar el valor com a string cuan es controli la variable tipus
        String prodTipus = null;
        String prodEstat = null;
        boolean actiu;
        BDProducte bdp = new BDProducte();
        try {
            llProd = bdp.getProductes();
            for(Producte p : llProd){
                tipus = p.getTipus();
                actiu =p.isActiu();
                if(tipus.equals(TipusProducte.CANSO)){
                    prodTipus = "Cançó";
                }else if(tipus.equals(TipusProducte.ALBUM)){
                    prodTipus = "Album";
                }else{
                    prodTipus = "Llista";
                }
                //comprovacio del producte que sigui actiu / inactiu
                if(actiu == true){
                    prodEstat = "Si";  
                }else{
                    prodEstat = "No";
                }
                tProductes.addRow( new Object[]{p.getTitol(),prodTipus,p.getEstil().getNom(),prodEstat});
            }
        } catch (GestorBDMilaSpotifyException ex) {
            Logger.getLogger(MantenimentDades_V2.class.getName()).log(Level.SEVERE, null, ex);
        }
     }
       
    
    
    private void omplirTaulaReproduccions(List<Reproduccio> llr){
        tReproduccions.setRowCount(0);
        SimpleDateFormat dFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        for(Reproduccio r : llr){
            tReproduccions.addRow( new Object[]{r.getClient().getNom(),r.getProducte().getTitol(),dFormat.format(r.getTimestamp())});
        }
        
    }
    
    private void omplirTaulaProductes(List<Producte> llProd){
        tProductes.setRowCount(0);
        boolean pActiu;
        String prodEstat;
        for(Producte p : llProd){
            pActiu =p.isActiu();
            if(pActiu == true){
                prodEstat = "Si";  
            }else{
                prodEstat = "No";
            }
            tProductes.addRow( new Object[]{p.getTitol(),"",p.getEstil().getNom(),prodEstat});
        }
    }    
    private JComboBox omplirDespClients() {
        JComboBox despClis = new JComboBox();
        despClis.addItem("Selecciona un client");
        llCli = new ArrayList<Client>();
        BDClient bdclnt = new BDClient();
        try {
            llCli = bdclnt.getClients();
        } catch (GestorBDMilaSpotifyException ex) {
            Logger.getLogger(MantenimentDades_V2.class.getName()).log(Level.SEVERE, null, ex);
        }
        Iterator<Client> it = llCli.iterator();
        while(it.hasNext()){
            Client cln = it.next();
            despClis.addItem(cln.getId() + " - " + cln.getNom()); 
        }
        return despClis;
    }
   
    private void crearPanelSuperior(){
        JPanel pSuperior = new JPanel(new FlowLayout());
        Box bDreta = Box.createHorizontalBox();
        //bDreta.setPreferredSize(new Dimension(300, 50));
        Box bEsquerra = Box.createVerticalBox();
        JLabel titol = new JLabel("Manteniment Reproduccions");
        titol.setFont(new Font("Montserrat", Font.BOLD, 15));
//      titol.setBorder(BorderFactory.createEmptyBorder(10, 10,10,10));
        titol.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        titol.setForeground(new Color(234, 234, 234));
        bEsquerra.add(titol);

        bAfegir = new JButton("Afegir");
        //bAfegir.setBackground(new Color(234, 234, 234));
        bAfegir.setToolTipText("Permet crear una nova reproduccio");
        bModificar = new JButton("Modificar");
        //bModificar.setBackground(new Color(234, 234, 234));
        bModificar.setToolTipText("Modificar la reproduccio seleccionada");
        bEliminar = new JButton("Eliminar");
        bEliminar.setToolTipText("Elimina els registres seleccionats");
        bEliminar.setBackground(new Color(207, 10, 10));

        Box boxButtons = Box.createHorizontalBox();
        boxButtons.add(Box.createHorizontalStrut(40));
        boxButtons.add(bEliminar);
        boxButtons.add(Box.createHorizontalStrut(360));
        boxButtons.add(bAfegir);
        boxButtons.add(Box.createHorizontalStrut(30));
        boxButtons.add(bModificar);
        //boxButtons.add(Box.createHorizontalStrut(10));
        bDreta.add(boxButtons);
        //bDreta.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
        
        pSuperior.add(bEsquerra);
        pSuperior.add(Box.createHorizontalStrut(15));
        pSuperior.add(boxButtons);
        pSuperior.setPreferredSize(new Dimension(500, 50));
        pSuperior.setBackground( new Color(60, 64, 72));
        pReproduccio.add(pSuperior, BorderLayout.NORTH);
    }

    private void crearTaulaReproduccions() {
        tReproduccions = new DefaultTableModel();
        taula = new JTable(tReproduccions){
            @Override
            public boolean isCellEditable(int row, int column) // Per aconseguir que la columna 2 sigui editable
            {
                return column == 2;
            }

            @Override
            public Class<?> getColumnClass(int column) {
                Class clazz = String.class;
                switch (column) {
                    case 0:
                        clazz = String.class;
                        break;
                    case 1:
                        clazz = String.class;
                        break;
                    case 2:
                        clazz = String.class;
                        break;
                }
                return clazz;
            }
        };
        tReproduccions.addColumn("Client");
        tReproduccions.addColumn("Producte");
        tReproduccions.addColumn("Moment Temporal");
        JScrollPane scrollPane = new JScrollPane(taula,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(600, 400));
        omplirTaulaReproduccions();
        JPanel pCentre = new JPanel();
        pCentre.add(scrollPane);
        pCentre.setBackground(new Color(60, 64, 72));
        pReproduccio.add(pCentre);
    }

    private void crearZonaInformativa(JPanel panel) {
        
        //Zona informativa 
        JLabel labInfo = new JLabel("Zona de missatges");
        labInfo.setFont(new Font("Montserrat", Font.BOLD, 14));
        txtInfo = new JTextArea(5, 140);
        txtInfo.setEditable(false);
        txtInfo.setForeground(new Color(234, 234, 234));
        Box pInf = Box.createVerticalBox();
        //labInfo.setBackground( new Color(60, 64, 72));
        txtInfo.setBackground( new Color(60, 64, 72));
        pInf.add(labInfo);
        pInf.add(txtInfo);
        pInf.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        panel.add(pInf, BorderLayout.SOUTH);
    }

    private void crearTaulaProductes() {
        tProductes = new DefaultTableModel();
        taulaProds = new JTable(tProductes){
            @Override
            public boolean isCellEditable(int row, int column) // Per aconseguir que la columna 2 sigui editable
            {
                return column == 2;
            }

            @Override
            public Class<?> getColumnClass(int column) {
                Class clazz = String.class;
                switch (column) {
                    case 0:
                        clazz = String.class;
                        break;
                    case 1:
                        clazz = String.class;
                        break;
                    case 2:
                        clazz = String.class;
                        break;
                    case 3:
                        clazz = String.class;
                        break;
                }
                return clazz;
            }
        };
        tProductes.addColumn("Titol");
        tProductes.addColumn("Tipus");
        tProductes.addColumn("Estil");
        tProductes.addColumn("Actiu?");
        JScrollPane scrollPane = new JScrollPane(taulaProds,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(600, 400));
        omplirTaulaProductes();
        JPanel pCentre = new JPanel();
        pCentre.add(scrollPane);
        pCentre.setBackground(new Color(60, 64, 72));
        pProducte.add(pCentre);
    }
    
    class GestioBotons implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ac) {
           JButton btnPremut = (JButton) ac.getSource();
           txtInfo.setText("");
           if(btnPremut.equals(bAfegir)){
               if(form== null){
                   formolari();
               }else{
                   netejaForm();
               }
               form.setTitle("Afegir Reproduccio");
               esAlta = true;
               form.setVisible(true);
               return;
               
           }
           if(btnPremut.equals(bModificar)){
               int[] rows = taula.getSelectedRows();
               if(rows.length == 0){
                   txtInfo.setText("No hi ha cap reproducció seleccionada");
               }else if(rows.length >1){
                   txtInfo.setText("n'hi han mes de 1 reproduccio seleccionades");
               }else{
                    filaModificada = taula.getSelectedRow();
                    if(form== null){
                        formolari();
                    }else{
                        netejaForm();
                    }
                    form.setTitle("Modificació de Reproduccio");
                    esAlta = false;
                    int ind = taula.getSelectedRow();
                    Reproduccio r =  llRep.get(ind);
                    int indCli = obtenirIndexClient(r);
                    int indProd = obtenirIndexProducte(r);
                    System.out.println("index Client : " + indCli);
                    System.out.println("index Producte : " + indProd);
                    despClientsFormolari.setSelectedIndex(indCli);
                    despProductes.setSelectedIndex(indProd);
                    despClientsFormolari.setEnabled(false);
                    despProductes.setEnabled(false);
                    momentTmp.setDate(r.getTimestamp());
                    java.util.Date auxmtmp = momentTmp.getDate();
                    momentTmpAntic = new Date(auxmtmp.getTime());
                    System.out.println("momentTmpAntic "+ momentTmpAntic );
                    form.setVisible(true);
               }
               return;
           }
           
           if(btnPremut.equals(bEnregistrar)){
                BDReproduccio bdr = new BDReproduccio();              
                Client c = llCli.get(despClientsFormolari.getSelectedIndex()-1);
                Producte p = llProd.get(despProductes.getSelectedIndex()-1);
                java.util.Date _mtmp = momentTmp.getDate();
                Date mTmp = new Date(_mtmp.getTime());
                Reproduccio rNova = new Reproduccio(p, c, mTmp);
                SimpleDateFormat dFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
                try {
                    if(esAlta){          
                        bdr.afegirReproduccio(rNova);
                        txtInfo.setText("Alta reproduccio efectuada");
                        tReproduccions.addRow(new Object[]{c.getNom(),p.getTitol(),dFormat.format(mTmp)});
                        llRep.add(rNova);
                    }else{
                        System.out.println("Id client per modificar : " + c.getId());
                        bdr.modificarReproduccio(rNova,momentTmpAntic);
                        txtInfo.setText("Modificació efectuada");
                        tReproduccions.setValueAt(dFormat.format(mTmp), filaModificada, 2);
                    }
                    gbdms.desarCanvis();
                }catch (GestorBDMilaSpotifyException e) {
                    if(esAlta){
                         txtInfo.setText("Alta avortada.\n\nMotiu:\n\n" + e.getMessage());
                    }else{
                         txtInfo.setText("Modificació avortada.\n\nMotiu:\n\n" + e.getMessage());
                    }
                }
                
                form.setVisible(false);
           }
           
           if(btnPremut.equals(bCancelar)){
                form.setVisible(false);
                if (esAlta) {
                    txtInfo.setText("Alta avortada per l'usuari");
                } else {
                    txtInfo.setText("Modificació avortada per l'usuari");
                }

                return;
           }
           
           if(btnPremut.equals(bEliminar)){
               int[] rows = taula.getSelectedRows();
               if(rows.length == 0){
                   txtInfo.setText("No hi ha cap reproducció seleccionada");
               }else{
                    BDReproduccio bdr = new BDReproduccio(); 
                    int nLineas = rows.length;
                    try {
                        for(int i=0;i < nLineas;i++){                            
                            Reproduccio r = new Reproduccio(llRep.get(rows[i]).getProducte(),llRep.get(rows[i]).getClient(),llRep.get(rows[i]).getTimestamp());
                            bdr.eliminarReproduccio(r);
                            llRep.remove(rows[i]);
                            nLineas--;
                            i--;
                        }
                    gbdms.desarCanvis();    
                    } catch (GestorBDMilaSpotifyException ex) {
                         txtInfo.setText("Eliminació avortada.\n\nMotiu:\n\n" + ex.getMessage());
                    }
                    tReproduccions.setRowCount(0);
                    omplirTaulaReproduccions(llRep);
                    txtInfo.setText("Eliminació efectuada.");    
               }
               
           }
           if(btnPremut.equals(bCercar)){
                txtInfo.setText("");
                //Obtenir client
                long cli_id;
                if(despClients.getSelectedIndex() == 0){
                    cli_id = -1;
                }else{
                    cli_id = llCli.get(despClients.getSelectedIndex()-1).getId();
                }
//              System.out.println("Id client seleccionat : " + cli_id );
                String prodTitol = tfProdNom.getText();
//              System.out.println("Nom Producte : " + prodTitol);
                java.util.Date dataIni_tmp =  dataIni.getDate();
                java.util.Date dataFi_tmp =  dataFi.getDate();
                Date dIni = null;
                Date dFi = null;
                if(dataIni_tmp != null && dataIni_tmp != null){
                     dIni = new Date(dataIni_tmp.getTime());
                     dFi = new Date(dataFi_tmp.getTime());
                }
//                System.out.println("Data inici : " +dIni);
//                System.out.println("Data Fi : " +  dFi);
                if(cli_id == -1 && prodTitol.equals("") && dIni == null && dFi==null){
                    omplirTaulaReproduccions();
                }else if(cli_id == -1 || prodTitol.equals("") || dIni == null || dFi==null){
                   txtInfo.setText("Tots els camps son obligatoris"); // de moment
                }else{
                    BDReproduccio bdRep = new BDReproduccio();
                    llRep.clear();
                    try {
                        llRep = bdRep.getReproduccions(cli_id, prodTitol, dIni, dFi);
                    } catch (GestorBDMilaSpotifyException ex) {
                        Logger.getLogger(MantenimentDades_V2.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    if(llRep.size()==0){
                        txtInfo.setText("No s'ha trobat cap reproduccio");
                    }else{
                        txtInfo.setText("S'ha trobat "+llRep.size()+" reproduccions");
                        omplirTaulaReproduccions(llRep);
                    }
                } 
           }
           if(btnPremut.equals(bNetejar)){
               despClients.setSelectedIndex(0);
               tfProdNom.setText("");
               dataIni.setCalendar(null);
               dataFi.setCalendar(null);
           }
            
        }
    }
    
    private int obtenirIndexEstil(Estil estil){
        int aux = -1;
        for(int i= 0; i<llEst.size();i++){
            if(llEst.get(i).getId() == estil.getId()){
                aux = i;
            }
        }
        return aux + 1;   
    }
    
    private int obtenirIndexClient(Reproduccio r){
        int aux = -1;
        for(int i=0;i<llCli.size();i++){
            if(llCli.get(i).getId() == r.getClient().getId()){
                aux = i;
            }
        }
        return aux + 1;
    }
    

    
    class GestioBotonsProd implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
           JButton btnPremut = (JButton) ae.getSource();
           if(btnPremut.equals(bAfegirProd)){
                int index = despTipusProd.getSelectedIndex();
                esAltaProd = true;
                if(index == 1){
                    if(formAlbum == null){
                        formAlbum();
                    }else{
                        netejarFormAlbum();
                    }
                    formAlbum.setTitle("Afegir Album");
                    formAlbum.setVisible(true);
                }else if(index == 2){
                    if(formCanso== null){
                        try {
                            formolariCanso();
                        } catch (GestorBDMilaSpotifyException ex) {
                            Logger.getLogger(MantenimentDades_V2.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }else{
                        netejaFormCanso();
                    }
                    formCanso.setTitle("Afegir Cançó");
                    formCanso.setVisible(true);
                }else if(index == 3){
                    if(formLlista == null){
                        formLlista();
                    }else{
                        netejaFormLlista();
                    }
                    formLlista.setTitle("Afegir Llista");
                    formLlista.setVisible(true);
                }
                return;
           }
           
           if(btnPremut.equals(bModificarProd)){
               esAltaProd = false;
               filaSeleccionada = taulaProds.getSelectedRow();
               int[] rows = taulaProds.getSelectedRows();
               if(rows.length == 0){
                    JOptionPane.showMessageDialog(f,
                    "No s'ha seleccionat cap producte", "",
                    JOptionPane.INFORMATION_MESSAGE);
               }else if(rows.length >1){
                   JOptionPane.showMessageDialog(f,
                    "Nomes es pot seleccionar un producte", "",
                    JOptionPane.INFORMATION_MESSAGE);
               }else{
                    TipusProducte pTipus = llProd.get(filaSeleccionada).getTipus();
                    if(pTipus.equals(TipusProducte.CANSO)){
                        if(formCanso == null){
                            try {
                                formolariCanso();
                            } catch (GestorBDMilaSpotifyException ex) {
                                Logger.getLogger(MantenimentDades_V2.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }else{
                            netejarFormCanso();
                        }
                        formCanso.setTitle("Modificació cançó");
//                        Canso can = (Canso) llProd.get(filaSeleccionada);
                        long idCanso = llProd.get(filaSeleccionada).getId();
                        System.out.println("Canso : " + llProd.get(filaSeleccionada));
                        Canso can = null;
                        try {
                            can = BDProducte.getCanso(idCanso);
                        } catch (SQLException ex) {
                            Logger.getLogger(MantenimentDades_V2.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        System.out.println("Canso :" + can);
                        tfTitolCanso.setText(can.getTitol());
                        if(can.isActiu()) 
                            rbActiu.setSelected(true); 
                        else 
                            rbInactiu.setSelected(true);
                        
                        int indEstil = obtenirIndexEstil(can.getEstil());
                        despEstils.setSelectedIndex(indEstil);
                        anyCreacio.setValue(can.getAnyCreacio());
                        int indArt = obtenirIndexInterpret( can.getInterpret().getId());
                        interpret.setSelectedIndex(indArt);
                        spMinuts.setValue(can.getDurada() / 60);
                        spSegons.setValue(can.getDurada() % 60);
                        formCanso.setVisible(true);
                        return;
                        
                    }else if(pTipus.equals(TipusProducte.ALBUM)){
                        if(formAlbum== null)
                            formAlbum();
                        else
                            netejarFormAlbum();
                        
                        Album alb = null;
                        try {
                            alb = BDProducte.getAlbum(llProd.get(filaSeleccionada).getId());
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null, "No s'ha pogut obtenir l'album \n" + e.getMessage(),
                                "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        
                        System.out.println("Album" + alb);
                        tfTitolAlbum.setText(alb.getTitol());
                        if(alb.isActiu()) rbActiu.setSelected(true);
                        else rbInactiu.setSelected(true);
                        int indEst = obtenirIndexEstil(alb.getEstil());
                        despEstils.setSelectedIndex(indEst);
                        anyCreacio.setValue(alb.getAnyCreacio());
                        llCanPerAfegir = new ArrayList<>();
                        llCanPerAfegir = alb.getCansons();
                        int pos = 1;
                        for(Canso c : llCanPerAfegir){
                            tCansons.addRow(new Object[]{c.getTitol(),pos});
                            pos++;
                        }
                        mostrarDurada(alb.getDurada());
                        formAlbum.setTitle("Modificació album");
                        formAlbum.setVisible(true);
                        return;
                    }else{
                        if(formLlista== null){
                            formLlista();
                        }else{
                            netejaFormLlista();
                        }
                        formLlista.setTitle("Modificació llista");
                        formLlista.setVisible(true);
                        return;
                    }
                    
               }
               return;
           }
           
           if(btnPremut.equals(bCancelProd)){
               if(formCanso != null){
                    formCanso.setVisible(false);
                    //formCanso.dispose();
               }
               if(formAlbum != null){
                    formAlbum.setVisible(false);
//                    formAlbum.dispose();
               }
               if(formLlista != null){
                    formLlista.setVisible(false);
//                    formLlista.dispose();
               }
                
               return;
           }
           if(btnPremut.equals(bNetejarProd)){
               tfProdTitol.setText("");
               if(!ckAlbum.isSelected()) ckAlbum.setSelected(true);
               if(!ckCanso.isSelected()) ckCanso.setSelected(true);
               if(!ckLlista.isSelected()) ckLlista.setSelected(true);
               
               if(!actiu.isSelected()) actiu.setSelected(true);
               if(inactiu.isSelected()) inactiu.setSelected(false);
               if(tots.isSelected()) tots.setSelected(false);
               
               return;
           }
           
           if(btnPremut.equals(bCercarProd)){
               Boolean estat = null; 
               List<TipusProducte> llTipus = new ArrayList<TipusProducte>();
               String prodTitol = null;
               //OBTENIR L'ESTAT SELECCIONAT
               if(actiu.isSelected()){
                   estat = true;
               }else if(inactiu.isSelected()){
                   estat = false;
               }
               //GUARDAR ELS TIPUS SELECCIONAT PER FILTRAR EN UN ARRAY
               if(ckAlbum.isSelected()) llTipus.add(TipusProducte.ALBUM);
               if(ckCanso.isSelected()) llTipus.add(TipusProducte.CANSO);
               if(ckLlista.isSelected()) llTipus.add(TipusProducte.LLISTA_REP);
               //OBTENIR EL TITOL DEL PRODUCTE  
               prodTitol = tfProdTitol.getText();
               boolean ordAsc = false;
               if(rbAsc.isSelected()) ordAsc = true;
               BDProducte bdp = new BDProducte();
               try {
                   llProd.clear();
                   llProd = bdp.getProductes(llTipus, prodTitol, estat, ordAsc);
               } catch (GestorBDMilaSpotifyException ex) {
                   try {
                       throw new GestorBDMilaSpotifyException("No s'ha pogut obtenir la llista de productes filtrats" + ex.getMessage());
                   } catch (GestorBDMilaSpotifyException ex1) {
                       Logger.getLogger(MantenimentDades_V2.class.getName()).log(Level.SEVERE, null, ex1);
                   }
               }
               omplirTaulaProductes(llProd);
               
           }
           
           if(btnPremut.equals(bEliminarProd)){
               int fila = taulaProds.getSelectedRow(); 
               Producte prod = llProd.get(fila);
               int resp = JOptionPane.showConfirmDialog(null, "¿Vols eliminar el producte " + prod.getTitol() + "?");
               System.out.println("Resp " + resp);
               if(resp==0){
                    try {
                        BDProducte bdp = new BDProducte();
                        bdp.eliminarProducte(prod.getId());
                        GestorBDMilaSpotify.desarCanvis();
                    } catch (GestorBDMilaSpotifyException ex) {
                        Logger.getLogger(MantenimentDades_V2.class.getName()).log(Level.SEVERE, null, ex);
                        JOptionPane.showMessageDialog(null, "No s'ha pogut eliminar el producte !! \n" + ex.getMessage(),
                             "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    tProductes.removeRow(fila);
                    llProd.remove(fila);
                    JOptionPane.showMessageDialog(formCanso, "Eliminacio efectuada correctament", "Eliminació producte",
                            JOptionPane.INFORMATION_MESSAGE);
               }
               
           }
           
           if(btnPremut.equals(bEnregProd)){
               //OBTENIR VALOR CAMPS GENERICS
               BDProducte bdp = new BDProducte();  
               boolean estat = false;
               if(rbActiu.isSelected()) estat = true;
               String titol = ""; 
               if(tfTitolCanso!=null)titol = tfTitolCanso.getText();
               if(tfTitolAlbum!=null)titol = tfTitolAlbum.getText();
               int indEstil = despEstils.getSelectedIndex()-1;
                if(esAltaProd == true){
                    if(tipuActual == TipusProducte.CANSO){
                        Canso can = null;
                        int any = anyCreacio.getYear();
                        int indInterpret = interpret.getSelectedIndex();
                        TipusArtista artTipus = null;
                        if(indInterpret != -1) artTipus = llArt.get(indInterpret).getTipus();
                        int minuts =(Integer) spMinuts.getValue();
                        int segons =(Integer) spSegons.getValue();
                        long durada  =  minuts * 60 + segons;
                        TipusProducte pTipus = TipusProducte.CANSO;
                        if(!validarCampsCanso(titol, any, minuts, segons)){
                            JOptionPane.showMessageDialog(null, "Dades de cançó no valides!!",
                            "Error", JOptionPane.ERROR_MESSAGE);
                        }else{
                            if(artTipus == TipusArtista.INDIVIDUAL){
                                can = new Canso(0, titol,estat,new  Estil(llEst.get(indEstil).getId(),llEst.get(indEstil).getNom()), 
                                                pTipus,any,durada,
                                                new ArtistaIndividual(llArt.get(indInterpret).getId(), llArt.get(indInterpret).getNom(), artTipus));
                            }else {
                                can = new Canso(0,titol,estat,new  Estil(llEst.get(indEstil).getId(),llEst.get(indEstil).getNom()),
                                       pTipus,any,durada,
                                        new Grup(llArt.get(indInterpret).getId(), llArt.get(indInterpret).getNom(), artTipus));       
                            }
                             try {
                                 bdp.inserirProducte(can);
                                 GestorBDMilaSpotify.desarCanvis();
                            } catch (GestorBDMilaSpotifyException ex) {
                                Logger.getLogger(MantenimentDades_V2.class.getName()).log(Level.SEVERE, null, ex);
                                JOptionPane.showMessageDialog(null, ex.getMessage(),
                                "Error", JOptionPane.ERROR_MESSAGE);
                            } catch (SQLException ex) {
                                Logger.getLogger(MantenimentDades_V2.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            llProd.add(can);
                            String actiu;
                            if(estat) actiu = "Si"; else actiu = "No";
                            tProductes.addRow(new Object[]{can.getTitol(),"Cançó",can.getEstil().getNom(),actiu});
                            formCanso.setVisible(false);
                            JOptionPane.showMessageDialog(formCanso, "Alta efectuada correctament", "Alta cançó",
                            JOptionPane.INFORMATION_MESSAGE);
                            
                        }
                    }else if(tipuActual == TipusProducte.ALBUM){
                        String albTitol = tfTitolAlbum.getText();
                        TipusProducte tpProd = TipusProducte.ALBUM;
                        int indEst = despEstils.getSelectedIndex()-1;
                        Estil estil = new Estil(llEst.get(indEst).getId(),llEst.get(indEst).getNom());
                        int any = anyCreacio.getYear();
                        int indDespCanso = despCanso.getSelectedIndex() - 1;
                        if(indDespCanso!=-1) System.out.println(llCansons.get(indDespCanso));
                        long durada = obtenirDuradaAlbum(llCanPerAfegir);
                        System.out.println("durada : " + durada);
                        if(!validarCampsAlbum(titol, any, durada)){
                            JOptionPane.showMessageDialog(null, "Dades d'album no valides!!",
                            "Error", JOptionPane.ERROR_MESSAGE);
                        }else{
                            Album alb = new Album(0,albTitol,estat,estil,tpProd,any,llCanPerAfegir,durada);
                            try {
                                bdp.inserirProducte(alb);
                                GestorBDMilaSpotify.desarCanvis();
                            } catch (GestorBDMilaSpotifyException ex) {
                                Logger.getLogger(MantenimentDades_V2.class.getName()).log(Level.SEVERE, null, ex);
                                 JOptionPane.showMessageDialog(null, "No s'ha pogut afegir l'album \n" + ex.getMessage(),
                                "Error", JOptionPane.ERROR_MESSAGE);
                            } catch (SQLException ex) {
                                JOptionPane.showMessageDialog(null, "No s'ha pogut afegir l'album \n" + ex.getMessage(),
                                "Error", JOptionPane.ERROR_MESSAGE);
                            }
//                            llProd.add(alb);
//                            String actiu;
//                            if(estat) actiu = "Si"; else actiu = "No";
//                            tProductes.addRow(new Object[]{alb.getTitol(),"Album",alb.getEstil().getNom(),actiu});
                            omplirTaulaProductes();
                            formAlbum.setVisible(false);
                            JOptionPane.showMessageDialog(null, "Alta efectuada correctament", "Alta album",
                            JOptionPane.INFORMATION_MESSAGE);
                            
                        }  
                    }else{
                        String lliTitol = tfTitolLlista.getText();
                        TipusProducte tpProd = TipusProducte.LLISTA_REP;
                        int indEst = despEstils.getSelectedIndex()-1;
                        Estil estil = new Estil(llEst.get(indEst).getId(),llEst.get(indEst).getNom());
                        int indDespCanso = despCanALb.getSelectedIndex() - 1;
                        if(indDespCanso!=-1) System.out.println(llCanAlb.get(indDespCanso));
                        long durada = obtenirDuradaLlista(llProdPerAfegir);
                        System.out.println("durada : " + durada);
                         if(!validarCampsLlista(titol, durada)){
                            JOptionPane.showMessageDialog(null, "Dades de llista no valides!!",
                            "Error", JOptionPane.ERROR_MESSAGE);
                        }else{
                            LlistaRep llista =  new LlistaRep(0,lliTitol,estat,estil,tpProd,llProdPerAfegir,durada);
                            try {
                                bdp.inserirProducte(llista);
                                GestorBDMilaSpotify.desarCanvis();
                            } catch (GestorBDMilaSpotifyException ex) {
                                Logger.getLogger(MantenimentDades_V2.class.getName()).log(Level.SEVERE, null, ex);
                                 JOptionPane.showMessageDialog(null, "No s'ha pogut afegir la llista \n" + ex.getMessage(),
                                "Error", JOptionPane.ERROR_MESSAGE);
                            } catch (SQLException ex) {
                                JOptionPane.showMessageDialog(null, "No s'ha pogut afegir la llista \n" + ex.getMessage(),
                                "Error", JOptionPane.ERROR_MESSAGE);
                            }
                            formLlista.setVisible(false);
                            JOptionPane.showMessageDialog(null, "Alta efectuada correctament", "Alta llista",
                            JOptionPane.INFORMATION_MESSAGE);
                            omplirTaulaProductes();
                            
                            
                        }
                    }
                    omplirDespProductes();
              }else{
                    TipusProducte pTipus = llProd.get(filaSeleccionada).getTipus();
                    System.out.println("Prod Tipus : " +  pTipus );
                    if(pTipus.equals(TipusProducte.CANSO)){
                        int indInterpret = interpret.getSelectedIndex();
                        int any = anyCreacio.getYear();
                        TipusArtista artTipus = null;
                        if(indInterpret != -1) artTipus = llArt.get(indInterpret).getTipus();
                        int minuts = Integer.parseInt(spMinuts.getValue().toString()) ;
                        int segons = Integer.parseInt(spSegons.getValue().toString());
                        long durada  =  minuts * 60 + segons;
                        if(!validarCampsCanso(titol, any, minuts, segons)){
                            JOptionPane.showMessageDialog(null, "Dades de cançó no valides!!",
                            "Error", JOptionPane.ERROR_MESSAGE);
                        }else{
                            long idCanso = llProd.get(filaSeleccionada).getId();
                            Canso can = null;
                            if(artTipus == TipusArtista.INDIVIDUAL){

                                can = new Canso(idCanso, titol,estat,new  Estil(llEst.get(indEstil).getId(),llEst.get(indEstil).getNom()), 
                                                pTipus,any,durada,
                                                new ArtistaIndividual(llArt.get(indInterpret).getId(), llArt.get(indInterpret).getNom(), artTipus));
                            }else {
                                can = new Canso(idCanso,titol,estat,new  Estil(llEst.get(indEstil).getId(),llEst.get(indEstil).getNom()),
                                        pTipus,any,durada,
                                        new Grup(llArt.get(indInterpret).getId(), llArt.get(indInterpret).getNom(), artTipus));       
                            }
                            try {
                                 bdp.modificarProducte(can);
                            } catch (GestorBDMilaSpotifyException ex) {
                                Logger.getLogger(MantenimentDades_V2.class.getName()).log(Level.SEVERE, null, ex);
                                JOptionPane.showMessageDialog(null, "Error en modificar cançó \n"+ex.getMessage(),
                                "Error", JOptionPane.ERROR_MESSAGE);
                            }
                            String prodEstat = "";
                            if(can.isActiu()){
                                prodEstat = "Si";
                            }else{
                                prodEstat = "No";
                            }
                            tProductes.setValueAt(can.getTitol(), filaSeleccionada, 0);
                            tProductes.setValueAt("Cançó", filaSeleccionada, 1);
                            tProductes.setValueAt(can.getEstil().getNom(), filaSeleccionada, 2);
                            tProductes.setValueAt(prodEstat, filaSeleccionada, 3);
                            formCanso.setVisible(false);
                        }    
                    }else if(pTipus.equals(TipusProducte.ALBUM)){
                        tfTitolAlbum.getText();
                        boolean albEstat = false;
                        if(rbActiu.isSelected())  albEstat = true ;
                        int indEst =  despEstils.getSelectedIndex()-1;
                        Estil estil = llEst.get(indEst);
                        int any = anyCreacio.getValue();
                        long durada = 0; 
                        for(Canso c : llCanPerAfegir){
                            durada = durada + c.getDurada();
                        }
                        long albId = llProd.get(filaSeleccionada).getId();
                        Album alb = new Album(albId,titol, albEstat, estil, TipusProducte.ALBUM, any, llCanPerAfegir, durada);
                        try {
                            bdp.modificarProducte(alb);
                            GestorBDMilaSpotify.desarCanvis();
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null, "No s'ha pogut mdificar l'album",
                            "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        String prodEstat = "";
                        if(alb.isActiu()){
                            prodEstat = "Si";
                        }else{
                            prodEstat = "No";
                        }
                        tProductes.setValueAt(alb.getTitol(), filaSeleccionada, 0);
                        tProductes.setValueAt("Album", filaSeleccionada, 1);
                        tProductes.setValueAt(alb.getEstil().getNom(), filaSeleccionada, 2);
                        tProductes.setValueAt(prodEstat, filaSeleccionada, 3);
                        formAlbum.setVisible(false); 
                    }else{
                    
                    }
                    
                    JOptionPane.showMessageDialog(null, "Modificació efectuada correctament", "Modificació",
                        JOptionPane.INFORMATION_MESSAGE);
                    omplirDespProductes();
                  
              }
           }
           
           if(btnPremut.equals(bAfegirContingut)){
                TipusProducte tpProd = llProd.get(taulaProds.getSelectedRow()).getTipus();
                if(tpProd.equals(TipusProducte.ALBUM)){
                    Canso can_aux = (Canso) llCansons.get(despCanso.getSelectedIndex()-1);
                    llCanPerAfegir.add(can_aux);
                    int pos = (Integer) spPosicio.getValue();
                    tCansons.addRow(new Object[]{can_aux.getTitol(),pos});
                    //System.out.println(llCanPerAfegir.size());
                    spPosicio.setValue(pos+1);
                   
               }else{
                    Producte prod = llCanAlb.get(despCanALb.getSelectedIndex()-1);
                    System.out.println("PRoducte : " + prod);
                    llProdPerAfegir.add(prod);
                    int pos = (Integer) spPosicio.getValue();
                    tLlista_rep.addRow(new Object[]{prod.getTitol(),pos});
                    //System.out.println(llCanPerAfegir.size());
                    spPosicio.setValue(pos+1);
               }
               
           }
           
           
           if(btnPremut.equals(bElimContingut)){
               int ind = taulaCansons.getSelectedRow();
               tCansons.removeRow(ind);
               llCanPerAfegir.remove(ind);
                
           }
        }    
    }
    
    private void mostrarDurada(long durada){
        long hores = durada /3600;
        long minuts = durada / 60 % 60;
        long segons = durada % 60;
        
        String sdAlbum = ""; 
        if(hores > 0 && hores < 10){
            sdAlbum += "0"+hores+" h";
        }else{
            sdAlbum += hores+" h";
        }
        
        if(minuts > 0 && minuts < 10){
            sdAlbum += " 0"+minuts+" min";
        }else{
            sdAlbum += " "+minuts+" min";
        }
        
        if(segons > 0 && segons <10){
             sdAlbum += " 0"+segons+" seg";
        }else{
             sdAlbum += " "+segons+" seg";
        }
        
        albDurada.setText(sdAlbum);
    }
    //METODE PER VALIDAR CAMPS D'ALBUM 
    private boolean validarCampsAlbum(String titol,int  any,long durada){
        if(titol == null || titol.equals("")){
            return false;
        }
        java.util.Date data = new java.util.Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        simpleDateFormat.applyPattern("yyyy");
        String format = simpleDateFormat.format(data);
        int anyActual = Integer.parseInt(format);
        if(any > anyActual || any <1900 ){
            return false;
        }
        if((llCanPerAfegir.size()>=1 && durada == 0) ){
            return false;
        }
        if(despEstils.getSelectedIndex() == 0){
            return false;
        }
        if(despCanso.getSelectedIndex() == 0){
            return false;
        }
        
        return true;
    }
    
    private boolean validarCampsLlista(String titol,long durada){
         if(titol == null || titol.equals("")){
            return false;
        }
         
        if((llCanAlb.size()>=1 && durada == 0) ){
            return false;
        }
        if(despEstils.getSelectedIndex() == 0){
            return false;
        }
        
        if(llProdPerAfegir.size() == 0)
            return false;
        
        if(despCanALb.getSelectedIndex() == 0){
            return false;
        }
        
        return true;
    }
    
    //METODE PER CALCULAR  LA SUMA TOTAL DE DURADA CANÇONS
    private long obtenirDuradaAlbum(List<Canso> cansons){
        long durada= 0; 
        for(Canso c : cansons) durada = durada + c.getDurada();     
        return durada;
    }
    
    private long obtenirDuradaLlista(List<Producte> llProd){
        long durada= 0; 
        Canso can = null;
        Album alb = null;
        for(Producte p : llProd) 
            if(p.getTipus().equals(TipusProducte.CANSO)){
                can = (Canso)p;
                durada = durada + can.getDurada();
            }else{
                alb= (Album)p;
                durada = durada + alb.getDurada();
            }
        return durada;
    }
    
    private void netejarFormAlbum() {
        tfTitolAlbum.setText("");
        anyCreacio.setValue(2022);
        despEstils.setSelectedIndex(0);
        despCanso.setSelectedIndex(0);
        tCansons.setRowCount(0);
        llCanPerAfegir.clear();  
    }
    
    private void netejaFormLlista() {
        tfTitolLlista.setText("");
        despEstils.setSelectedIndex(0);
        despCanALb.setSelectedIndex(0);
        llCanAlb.clear();
        tLlista_rep.setRowCount(0);
        spPosicio.setValue(1);
      
    }
    public int obtenirIndexInterpret(long idArt){
        int pos = -1;
        for(int i = 0; i < llArt.size();i++){
            if(llArt.get(i).getId() == idArt ) pos = i;
        }
        return pos;
    
    }
    public void netejarFormCanso(){
        tfTitolCanso.setText("");
        despEstils.setSelectedIndex(0);
        interpret.setSelectedIndex(-1);
        spMinuts.setValue(0);
        
    }
    private boolean validarCampsCanso(String titol,int any,int minuts, int segons){
        if(titol == null || titol.equals("")){
            return false;
        }
        
        if(despEstils.getSelectedIndex()== 0){
            return false;
        }
        java.util.Date data = new java.util.Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        simpleDateFormat.applyPattern("yyyy");
        String format = simpleDateFormat.format(data);
        int anyActual = Integer.parseInt(format);
        if(any > anyActual || any <1900 ){
            return false;
        }
        if(interpret.getSelectedIndex() == -1){
            return false;
        }
        
        if(minuts <= 0){
            return false;
        }
        
        if(segons < 0 || segons > 60){
            return false;
        }
        
        return true;
    }
    private int obtenirIndexProducte(Reproduccio r){
        int aux = -1;
        for(int i=0;i<llProd.size();i++){
            if(llProd.get(i).getId() == r.getProducte().getId()){
                aux = i;
            }
        }
        return aux + 1;
    }
    
    private void afegirMenu(){
        JMenuBar barra=new JMenuBar();
        JMenu artistes=new JMenu("Artistes");
        productes=new JMenu("Productes");
        clients=new JMenu("Clients");
        reproduccions=new JMenu("Reproduccions");
        JMenu ayuda=new JMenu("Ayuda");
        JMenuItem salir=new JMenuItem("Salir");
        JMenuItem abrir=new JMenuItem("Abrir");
        JMenuItem guardar=new JMenuItem("Guardar");
        JMenuItem manual=new JMenuItem("Manual");
        barra.add(artistes);
        barra.add(productes);
        barra.add(clients);
        barra.add(reproduccions);
        
        barra.add(ayuda);
        //afegir listeners 
        GestioMenus gm = new GestioMenus();
        productes.addMenuListener(gm);
        reproduccions.addMenuListener(gm);
    //        archivo.add(salir);
    //        archivo.add(abrir);
    //        archivo.add(guardar);  
        ayuda.add(manual);
        f.setJMenuBar(barra);
   }
   
    private void crearPanelGestioReproduccions(){
       
        crearPanelSuperior();
        //Taula per gestionar reproduccions
        crearTaulaReproduccions();
        //Crear Zona de filtració
        crearPanelFiltracio();
        //Crear zona informativa 
        crearZonaInformativa(pReproduccio);
        //Afegir escultadors pels buttons
        GestioBotons gbtns = new GestioBotons();
        bAfegir.addActionListener(gbtns);
        bEliminar.addActionListener(gbtns);
        bModificar.addActionListener(gbtns);
        bNetejar.addActionListener(gbtns);
        bCercar.addActionListener(gbtns);
    }
    
    private void crearPanelGestioProductes(){
        //CREAR TITOL DEL PANEL AMB BUTTONS DE CRUD 
        crearPanelSuperiorProductes();
        //CREAR TAULA REGISTRES DE PRODUCTES
        crearTaulaProductes();
        //PANEL FILTRACIO 
        crearPanelFiltracioProd();
        //crearZonaInformativaProds();
        //crearZonaInformativa(pProducte);
        //AFEGIR ESCULTADOR PER BOTONS DEL CRUD 
        GestioBotonsProd gpd = new GestioBotonsProd();
        bAfegirProd.addActionListener(gpd);
        bEliminarProd.addActionListener(gpd);
        bModificarProd.addActionListener(gpd);
        bImprimirProd.addActionListener(gpd);
        bCercarProd.addActionListener(gpd);
        bNetejarProd.addActionListener(gpd);
    }
    
    private void crearPanelSuperiorProductes(){
        JPanel pSuperior = new JPanel(new FlowLayout());
        Box bDreta = Box.createHorizontalBox();
        //bDreta.setPreferredSize(new Dimension(300, 50));
        Box bEsquerra = Box.createVerticalBox();
        JLabel titol = new JLabel("Manteniment Productes");
        titol.setFont(new Font("Montserrat", Font.BOLD, 15));
//      titol.setBorder(BorderFactory.createEmptyBorder(10, 10,10,10));
        titol.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        titol.setForeground(new Color(234, 234, 234));
        bEsquerra.add(titol);

        bAfegirProd = new JButton("Afegir");
        bAfegirProd.setEnabled(false);
        //bAfegir.setBackground(new Color(234, 234, 234));
        bAfegirProd.setToolTipText("Permet crear un nou producte");
        bModificarProd = new JButton("Modificar");
        //bModificar.setBackground(new Color(234, 234, 234));
        bModificarProd.setToolTipText("Modificar el producte seleccionat");
        
        bImprimirProd = new JButton("Imprimir");
        //bModificar.setBackground(new Color(234, 234, 234));
        bImprimirProd.setToolTipText("Imprimir el llistat de la taula de registres");
        
        
        bEliminarProd = new JButton("Eliminar");
        bEliminarProd.setToolTipText("Elimina els registres seleccionats");
        bEliminarProd.setBackground(new Color(207, 10, 10));
        
        //Crear desplegable per seleccionar el tipus per afegir.
        despTipusProd = new JComboBox();
        despTipusProd.addItem("Selecciona el tipus");
        despTipusProd.addItem("Album");
        despTipusProd.addItem("Cançó");
        despTipusProd.addItem("Llista");
        
        despTipusProd.addItemListener (new ItemListener () {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getItem().equals("Album")){
                    bAfegirProd.setEnabled(true);
                    tipuActual = TipusProducte.ALBUM;
                }
                if(e.getItem().equals("Cançó")){
                    bAfegirProd.setEnabled(true);
                    tipuActual = TipusProducte.CANSO;
                }
                if(e.getItem().equals("Llista")){
                    bAfegirProd.setEnabled(true);
                    tipuActual = TipusProducte.LLISTA_REP;
                }
                if(e.getItem().equals("Selecciona el tipus")){
                    bAfegirProd.setEnabled(false);
                    tipuActual = null;
                }
            }
        }); 

        Box boxButtons = Box.createHorizontalBox();
        boxButtons.add(Box.createHorizontalStrut(60));
        boxButtons.add(bEliminarProd);
        boxButtons.add(Box.createHorizontalStrut(160));
        boxButtons.add(despTipusProd);
        boxButtons.add(Box.createHorizontalStrut(20));
        boxButtons.add(bAfegirProd);
        boxButtons.add(Box.createHorizontalStrut(20));
        boxButtons.add(bModificarProd);
        boxButtons.add(Box.createHorizontalStrut(20));
        boxButtons.add(bImprimirProd);
        //boxButtons.add(Box.createHorizontalStrut(10));
        bDreta.add(boxButtons);
        //bDreta.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
        
        pSuperior.add(bEsquerra);
        pSuperior.add(Box.createHorizontalStrut(15));
        pSuperior.add(boxButtons);
        pSuperior.setPreferredSize(new Dimension(500, 50));
        pSuperior.setBackground( new Color(60, 64, 72));
        pProducte.add(pSuperior, BorderLayout.NORTH);
    }
    
    private void crearPanelFiltracioProd(){
        JLabel lbEstat = new JLabel("Estat : ");
        lbEstat.setFont(new Font("Montserrat", Font.BOLD, 12));
        lbEstat.setForeground(new Color(234, 234, 234));
        //CRAR RADIO BUTTONS PER SELECCIONAR ESTAT DEL PRODUCTE 
        actiu = new JRadioButton("Actiu");
        actiu.setFont(new Font("Montserrat", Font.BOLD, 10));
        actiu.setForeground(new Color(234, 234, 234));
        actiu.setSelected(true);
        inactiu = new JRadioButton("inactiu");
        inactiu.setFont(new Font("Montserrat", Font.BOLD, 10));
        inactiu.setForeground(new Color(234, 234, 234));
        tots = new JRadioButton("Tots");
        tots.setFont(new Font("Montserrat", Font.BOLD, 10));
        tots.setForeground(new Color(234, 234, 234));
        
        btnGroup = new ButtonGroup();
        btnGroup.add(actiu);
        btnGroup.add(inactiu);
        btnGroup.add(tots);
        
        Box bEstat = Box.createHorizontalBox();
        bEstat.setPreferredSize(new Dimension(250, 25));
        bEstat.add(lbEstat);
        bEstat.add(Box.createHorizontalStrut(10));
        bEstat.add(actiu);
        bEstat.add(Box.createHorizontalStrut(5));
        bEstat.add(inactiu);
        bEstat.add(Box.createHorizontalStrut(5));
        bEstat.add(tots);
        
        JLabel lbTipus = new JLabel("Tipus : ");
        lbTipus.setFont(new Font("Montserrat", Font.BOLD, 12));
        lbTipus.setForeground(new Color(234, 234, 234));
        ckCanso = new JCheckBox("Cançó");
        ckCanso.setFont(new Font("Montserrat", Font.BOLD, 10));
        ckCanso.setForeground(new Color(234, 234, 234));
        ckCanso.setSelected(true);
        ckAlbum = new JCheckBox("Album");
        ckAlbum.setFont(new Font("Montserrat", Font.BOLD, 10));
        ckAlbum.setForeground(new Color(234, 234, 234));
        ckAlbum.setSelected(true);
        ckLlista = new JCheckBox("Llista");
        ckLlista.setFont(new Font("Montserrat", Font.BOLD, 10));
        ckLlista.setForeground(new Color(234, 234, 234));
        ckLlista.setSelected(true);
        
        Box bTipus = Box.createHorizontalBox();
        bTipus.setPreferredSize(new Dimension(250, 25));
        bTipus.add(lbTipus);
        bTipus.add(Box.createHorizontalStrut(5));
        bTipus.add(ckCanso);
        bTipus.add(Box.createHorizontalStrut(5));
        bTipus.add(ckAlbum);
        bTipus.add(Box.createHorizontalStrut(5));
        bTipus.add(ckLlista);
        
        Box bTitolProducte = Box.createHorizontalBox();
        JLabel lbTitProd = new JLabel("Producte");
        lbTitProd.setFont(new Font("Montserrat", Font.BOLD, 12));
        lbTitProd.setForeground(new Color(234, 234, 234));
        tfProdTitol = new JTextField();
        bTitolProducte.add(lbTitProd);
        bTitolProducte.add(Box.createHorizontalStrut(10));
        bTitolProducte.add(tfProdTitol);
        
        Box bOrdre = Box.createHorizontalBox();
        JLabel lbOrdre = new JLabel("Ordre : ");
        lbOrdre.setFont(new Font("Montserrat", Font.BOLD, 10));
        lbOrdre.setForeground(new Color(234, 234, 234));
        rbAsc = new JRadioButton("Asc");
        rbAsc.setFont(new Font("Montserrat", Font.BOLD, 10));
        rbAsc.setSelected(true);
        rbAsc.setForeground(new Color(234, 234, 234));
        rbDesc = new JRadioButton("Desc");
        rbDesc.setFont(new Font("Montserrat", Font.BOLD, 10));
        rbDesc.setForeground(new Color(234, 234, 234));
        
        ButtonGroup bOrd = new ButtonGroup();
        bOrd.add(rbAsc);
        bOrd.add(rbDesc);
        
        bOrdre.add(lbOrdre);
        bOrdre.add(Box.createHorizontalStrut(10));
        bOrdre.add(rbAsc);
        bOrdre.add(Box.createHorizontalStrut(10));
        bOrdre.add(rbDesc);
        
        Box bButtons = Box.createHorizontalBox();
        bCercarProd = new JButton("Cercar");
        bCercarProd.setToolTipText("Cercar productes");
        bNetejarProd = new JButton("Netejar");
        bNetejarProd.setToolTipText("Neteja filtres");
        bButtons.add(bNetejarProd);
        bButtons.add(Box.createHorizontalStrut(20));
        bButtons.add(bCercarProd);
        
        Box b = Box.createVerticalBox();
        b.add(bEstat);
        b.add(Box.createVerticalStrut(20));
        b.add(bTipus);
        b.add(Box.createVerticalStrut(20));
        b.add(bTitolProducte);
        b.add(Box.createVerticalStrut(20));
        b.add(bOrdre);
        b.add(Box.createVerticalStrut(20));
        b.add(bButtons);
        JPanel pFiltracio = new JPanel();
        pFiltracio.setPreferredSize(new Dimension(300,0));
        pFiltracio.add(b);
//        pFiltracio.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
        pFiltracio.setBackground(new Color(60, 64, 72));
        //pFiltracio.setBackground(new Color(178, 178, 178));
        pProducte.add(pFiltracio, BorderLayout.WEST);
        GestioBotonsProd gpd = new GestioBotonsProd();
        bCercarProd.addActionListener(gpd);
        bNetejarProd.addActionListener(gpd);
    }
    
    private class GestioMenus implements MenuListener{

        @Override
        public void menuSelected(MenuEvent ms) {
           JMenu menuSeleccionat = (JMenu) ms.getSource();
           if(menuSeleccionat.equals(productes)){
              pProducte = new JPanel(new BorderLayout());
              crearPanelGestioProductes();
              f.getContentPane().removeAll();
              f.getContentPane().add(pProducte, BorderLayout.CENTER);
              f.pack();
              f.show();
           }
           if(menuSeleccionat.equals(reproduccions)){
              pReproduccio = new JPanel(new BorderLayout());
              crearPanelGestioReproduccions();
              f.getContentPane().removeAll();
              f.getContentPane().add(pReproduccio, BorderLayout.CENTER);
              f.pack();
              f.show();
           }
        }

        @Override
        public void menuDeselected(MenuEvent arg0) {
           
        }

        @Override
        public void menuCanceled(MenuEvent arg0) {
            
        }
    
    }
    
    private JComboBox omplirDespProductes(){
        JComboBox despProd = new JComboBox<String>();
        despProd.addItem("Selecciona un producte");
        llProd = new ArrayList<Producte>();
        BDProducte bdProd = new BDProducte();
        try {
            llProd = bdProd.getProductes();
        } catch (GestorBDMilaSpotifyException ex) {
           txtInfo.setText("No s'ha pogut omplir el desplegable de productes .\n\nMotiu:\n\n" + ex.getMessage());
        }
        Iterator<Producte> it = llProd.iterator();
        while(it.hasNext()){
            Producte p = it.next();
            despProd.addItem(p.getId() + " " + p.getTitol()); 
        }
        return despProd;
    }    
        
    private void formolari(){
        form = new JDialog(f, true);
        form.setBackground( new Color(60, 64, 72));
        despClientsFormolari = new JComboBox();
        despClientsFormolari = omplirDespClients();
        despClientsFormolari.setEnabled(true);
        JLabel lbCli = new JLabel("Client");
        lbCli.setFont(new Font("Montserrat", Font.BOLD, 12));
        lbCli.setForeground( new Color(234, 234, 234));
        
        JLabel lbProd = new JLabel("Producte");
        lbProd.setFont(new Font("Montserrat", Font.BOLD, 12));
        lbProd.setForeground( new Color(234, 234, 234));
        despProductes = omplirDespProductes();
        despProductes.setEnabled(true);
        JLabel lbMoment = new JLabel("Moment  temporal");
        lbMoment.setFont(new Font("Montserrat", Font.BOLD, 12));
        lbMoment.setForeground( new Color(234, 234, 234));
        momentTmp = new JDateChooser();
        momentTmp.setDateFormatString("dd-MM-yyyy hh:mm:ss");
        momentTmp.setOpaque(false);
        
        bEnregistrar = new JButton("Enregistra");
        bCancelar = new JButton("Cancel·la");
        
        Box bCli = Box.createHorizontalBox();
        bCli.add(lbCli);
        bCli.add(despClientsFormolari);
        Box bProd = Box.createHorizontalBox();
        bProd.add(lbProd);
        bProd.add(despProductes);
        Box bMtmp = Box.createHorizontalBox();
        bMtmp.add(lbMoment);
        bMtmp.add(momentTmp);
        Box bButtons = Box.createHorizontalBox();
        bButtons.add(bCancelar);
        bButtons.add(bEnregistrar);
        
        Box b = Box.createVerticalBox();
        b.add(bCli);
        b.add(Box.createVerticalStrut(10));
        b.add(bProd);
        b.add(Box.createVerticalStrut(10));
        b.add(bMtmp);
        b.add(Box.createVerticalStrut(10));
        b.add(bButtons);
        b.add(Box.createVerticalStrut(10));
        
        JPanel p = new JPanel();
        p.setPreferredSize(new Dimension(300, 200));
        p.setBackground( new Color(60, 64, 72));
        p.add(b);
        
        form.add(p);
        form.pack();;
        form.setResizable(false);
        form.setLocationRelativeTo(f);
        form.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        
        GestioBotons gbtn = new GestioBotons();
        bCancelar.addActionListener(gbtn);
        bEnregistrar.addActionListener(gbtn);   
    }
    
    private void formolariCanso() throws GestorBDMilaSpotifyException{
        formCanso = new JDialog(f, true);
        formCanso.setBackground( new Color(60, 64, 72));
        formCanso.setPreferredSize(new Dimension(300, 300));
        
        JLabel lbTitol = new JLabel("Titol");
        lbTitol.setFont(new Font("Montserrat", Font.BOLD, 12));
        lbTitol.setForeground( new Color(234, 234, 234));
        tfTitolCanso = new JTextField();
        Box bTitol = Box.createHorizontalBox();
        bTitol.add(lbTitol);
        bTitol.add(tfTitolCanso);
        
        JLabel lbEstat = new JLabel("Estat");
         
        lbEstat.setFont(new Font("Montserrat", Font.BOLD, 12));
        lbEstat.setForeground(new Color(234, 234, 234));
        //CRAR RADIO BUTTONS PER SELECCIONAR ESTAT DEL PRODUCTE 
        rbActiu = new JRadioButton("Actiu");
        rbActiu.setFont(new Font("Montserrat", Font.BOLD, 10));
        rbActiu.setForeground(new Color(234, 234, 234));
        rbActiu.setSelected(true);
        rbInactiu = new JRadioButton("inactiu");
        rbInactiu.setFont(new Font("Montserrat", Font.BOLD, 10));
        rbInactiu.setForeground(new Color(234, 234, 234));
        
        btnGroupCanso = new ButtonGroup();
        btnGroupCanso.add(rbActiu);
        btnGroupCanso.add(rbInactiu);
        
        Box bEstat = Box.createHorizontalBox();
        bEstat.add(lbEstat);
        bEstat.add(rbActiu);
        bEstat.add(rbInactiu);
        
        JLabel lbEstil = new JLabel("Estil");
        lbEstil.setFont(new Font("Montserrat", Font.BOLD, 12));
        lbEstil.setForeground(new Color(234, 234, 234));
        despEstils = obtenirDespEstils();
        
        Box bEstil = Box.createHorizontalBox();
        bEstil.add(lbEstil);
        bEstil.add(despEstils);
        
        
        JLabel lbAny = new JLabel("Any creació"); 
        lbAny.setFont(new Font("Montserrat", Font.BOLD, 12));
        lbAny.setForeground(new Color(234, 234, 234));
        anyCreacio = new JYearChooser();
        
        Box bAny  = Box.createHorizontalBox();
        bAny.add(lbAny);
        bAny.add(anyCreacio);
        
        JLabel lbInterpret = new JLabel("Interpret");
        lbInterpret.setFont(new Font("Montserrat", Font.BOLD, 12));
        lbInterpret.setForeground(new Color(234, 234, 234));
        //MOSTRAR ARTISTES AMB JLIST 
        DefaultListModel modelLlista = new DefaultListModel();
        BDArtista bda = new BDArtista();
        llArt = obtenirLlistaArtistes();
        for(int i = 0; i< llArt.size();i++){
            modelLlista.add(i,llArt.get(i).getNom());
        }
        interpret = new JList(modelLlista);
        interpret.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        interpret.setLayoutOrientation(JList.VERTICAL);
        interpret.setVisibleRowCount(-1);
        JScrollPane listScroller = new JScrollPane(interpret);
        listScroller.setPreferredSize(new Dimension(100, 100));
        
        Box bInterpret = Box.createHorizontalBox();
        bInterpret.add(lbInterpret);
        bInterpret.add(listScroller);
        
        bEnregProd = new JButton("Enregistrar");
        bCancelProd = new JButton("Cancel·lar");
        
        JLabel lbDurada = new JLabel("Durada");
        lbDurada.setFont(new Font("Montserrat", Font.BOLD, 12));
        lbDurada.setForeground(new Color(234, 234, 234));
        
        JLabel lbMinuts = new JLabel("min.");
        lbMinuts.setFont(new Font("Montserrat", Font.BOLD, 12));
        lbMinuts.setForeground(new Color(234, 234, 234));
        JLabel lbsegons= new JLabel("seg.");
        lbsegons.setFont(new Font("Montserrat", Font.BOLD, 12));
        lbsegons.setForeground(new Color(234, 234, 234));
        
        Integer value = new Integer(0);
        Integer min = new Integer(0);
        Integer max = new Integer(59);
        Integer step = new Integer(1);
        SpinnerNumberModel model = new SpinnerNumberModel(value, min, null, step);
        

        SpinnerNumberModel model2 = new SpinnerNumberModel(value, min, max, step);
        
        spMinuts = new JSpinner(model);
        spMinuts.setPreferredSize(new Dimension(60, 10));
        spSegons = new JSpinner(model2);
        Box bDurada = Box.createHorizontalBox();
        bDurada.add(lbDurada);
        bDurada.add(spMinuts);
        bDurada.add(lbMinuts);
        bDurada.add(spSegons);
        bDurada.add(lbsegons);
        
        Box bBtns = Box.createHorizontalBox();
        bBtns.add(bCancelProd);
        bBtns.add(bEnregProd);
        
        ///////////////////////////////////////////
        Box b = Box.createVerticalBox();
        b.add(bTitol);
        b.add(bEstat);
        b.add(bEstil);
        b.add(bAny);
        b.add(bInterpret);
        b.add(bDurada);
        b.add(bBtns);
        
        
        JPanel p = new JPanel();
        p.setPreferredSize(new Dimension(300, 200));
        p.setBackground( new Color(60, 64, 72));
        p.add(b);
        
        formCanso.add(p);
        formCanso.pack();
        formCanso.setResizable(false);
        formCanso.setLocationRelativeTo(f);
        formCanso.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        
        //AFEGIR ESCULTADORS DELS BOTONS DEL FORMOLARI
        GestioBotonsProd gbp = new GestioBotonsProd();
        bEnregProd.addActionListener(gbp);
        bCancelProd.addActionListener(gbp);
    }
    
    private void formAlbum(){
        formAlbum = new JDialog(f, true);
        formAlbum.setBackground( new Color(60, 64, 72));
        formAlbum.setPreferredSize(new Dimension(600, 480));
        
        JLabel lbTitol = new JLabel("Titol");
        lbTitol.setFont(new Font("Montserrat", Font.BOLD, 10));
        lbTitol.setForeground(new Color(234, 234, 234));
        tfTitolAlbum =  new JTextField();
        
        JLabel lbEstat = new JLabel("Estat");
        lbEstat.setFont(new Font("Montserrat", Font.BOLD, 10));
        lbEstat.setForeground(new Color(234, 234, 234));
        //CRAR RADIO BUTTONS PER SELECCIONAR ESTAT DEL PRODUCTE 
        rbActiu = new JRadioButton("Actiu");
        rbActiu.setFont(new Font("Montserrat", Font.BOLD, 10));
        rbActiu.setForeground(new Color(234, 234, 234));
        rbActiu.setSelected(true);
        rbInactiu = new JRadioButton("inactiu");
        rbInactiu.setFont(new Font("Montserrat", Font.BOLD, 10));
        rbInactiu.setForeground(new Color(234, 234, 234));
        
        ButtonGroup bGrp = new ButtonGroup();
        bGrp.add(rbActiu);
        bGrp.add(rbInactiu);
        
        JLabel lbEstil = new JLabel("Estil");
        lbEstil.setFont(new Font("Montserrat", Font.BOLD, 10));
        lbEstil.setForeground(new Color(234, 234, 234));
        despEstils = obtenirDespEstils();
        
        JLabel lbAny = new JLabel("Any creació");
        lbAny.setFont(new Font("Montserrat", Font.BOLD, 10));
        lbAny.setForeground(new Color(234, 234, 234));
        anyCreacio = new JYearChooser();
        
        
        Box bTitol = Box.createHorizontalBox();
        bTitol.add(lbTitol);
        bTitol.add(tfTitolAlbum);
        
        Box bEstat = Box.createHorizontalBox();
        bEstat.add(lbEstat);
        bEstat.add(rbActiu);
        bEstat.add(rbInactiu);
        
        Box bEstil = Box.createHorizontalBox();
        bEstil.add(lbEstil);
        bEstil.add(despEstils);
        
        Box bAnyCreacio = Box.createHorizontalBox();
        bAnyCreacio.add(lbAny);
        bAnyCreacio.add(anyCreacio);
         
        Box bEsquerra = Box.createVerticalBox();
        bEsquerra.setPreferredSize(new Dimension(400, 130));
        bEsquerra.add(bTitol);
        bEsquerra.add(Box.createVerticalStrut(10));
        bEsquerra.add(bEstat);
        bEsquerra.add(Box.createVerticalStrut(10));
        bEsquerra.add(bEstil);
        bEsquerra.add(Box.createVerticalStrut(10));
        bEsquerra.add(bAnyCreacio);
        bEsquerra.add(Box.createVerticalStrut(10));
        
        
        //CREACÓ DE COMPONENTES D'ESQUERRE 
        JLabel lbCansons = new JLabel("Canso");
        lbCansons.setFont(new Font("Montserrat", Font.BOLD, 10));
        lbCansons.setForeground(new Color(234, 234, 234));
        //CREAR CAMPS PER CREAR CANÇONS D'ALBUM
        despCanso = new JComboBox();
        llCansons = new ArrayList<Canso>();
        llCansons = BDProducte.getCansons();
        despCanso.addItem("Selecciona canso");
        for(Producte c : llCansons){
            despCanso.addItem(c.getTitol());
        }
        
        JLabel lbPos = new JLabel("Posició");
        lbPos.setFont(new Font("Montserrat", Font.BOLD, 10));
        lbPos.setForeground(new Color(234, 234, 234));
        Integer value = new Integer(1);
        Integer min = new Integer(1);
        Integer max = new Integer(20);
        Integer step = new Integer(1);
        SpinnerNumberModel model = new SpinnerNumberModel(value, min, max, step);
        spPosicio= new JSpinner(model);
        
        bAfegirContingut = new JButton("Afegir");
        bElimContingut =  new JButton("Eliminar");
        
        Box bCansons = Box.createHorizontalBox();
        bCansons.add(lbCansons);
        bCansons.add(despCanso);
        bCansons.add(lbPos);
        bCansons.add(spPosicio);
        bCansons.add(bAfegirContingut);
        bCansons.add(bElimContingut);
        
        Box bTaulaCansons = Box.createVerticalBox();
        
        tCansons = new DefaultTableModel();
        taulaCansons = new JTable(tCansons){
            @Override
            public boolean isCellEditable(int row, int column) // Per aconseguir que la columna 2 sigui editable
            {
                return column == 2;
            }

            @Override
            public Class<?> getColumnClass(int column) {
                Class clazz = String.class;
                switch (column) {
                    case 0:
                        clazz = String.class;
                        break;
                    case 1:
                        clazz = Integer.class;
                        break;
                }
                return clazz;
            }
        };
        tCansons.addColumn("Titol");
        tCansons.addColumn("Posició");
        JScrollPane scrollPane = new JScrollPane(taulaCansons,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(400, 200));
        
        bCancelProd = new JButton("Cancel·lar");
        bEnregProd = new JButton("Enregistrar");
        
        Box bBtns = Box.createHorizontalBox();
        bBtns.add(bCancelProd);
        bBtns.add(Box.createHorizontalStrut(10));
        bBtns.add(bEnregProd);
        
        JLabel lbDurada = new JLabel("Durada : ");
        albDurada = new JLabel();
        albDurada.setFont(new Font("Montserrat", Font.BOLD, 12));
        albDurada.setForeground(new Color(234, 234, 234));
        lbDurada.setFont(new Font("Montserrat", Font.BOLD, 12));
        lbDurada.setForeground(new Color(234, 234, 234));
        
        Box bDurada = Box.createHorizontalBox();
        bDurada.add(lbDurada);
        bDurada.add(Box.createHorizontalStrut(15));
        bDurada.add(albDurada);

        JPanel pCentre = new JPanel();
        pCentre.add(scrollPane);
        pCentre.setBackground(new Color(60, 64, 72));
        bTaulaCansons.add(pCentre);
        bTaulaCansons.add(bDurada);
        bTaulaCansons.add(new JPopupMenu.Separator());
        bTaulaCansons.add(Box.createVerticalStrut(10));
        bTaulaCansons.add(bBtns);
        Box bDreta = Box.createVerticalBox();
        bDreta.add(bCansons);
        bDreta.add(bTaulaCansons);
        
        Box bPrincipal = Box.createHorizontalBox();
        bPrincipal.add(bEsquerra);
        bPrincipal.add(bDreta);
        
        JPanel p = new JPanel();
        p.setPreferredSize(new Dimension(600, 400));
        p.setBackground( new Color(60, 64, 72));
        p.add(bEsquerra, BorderLayout.WEST);
        p.add(bDreta, BorderLayout.EAST);
        formAlbum.add(p);
        formAlbum.pack();
        formAlbum.setResizable(true);
        formAlbum.setLocationRelativeTo(f);
        formAlbum.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        //AFEGIR ESCULTADRS
        GestioBotonsProd gbp = new GestioBotonsProd();
        bCancelProd.addActionListener(gbp);
        bEnregProd.addActionListener(gbp);
        bAfegirContingut.addActionListener(gbp);
        bElimContingut.addActionListener(gbp);
    } 
    
    private void formLlista(){
        formLlista = new JDialog(f, true);
        formLlista.setBackground( new Color(60, 64, 72));
        formLlista.setPreferredSize(new Dimension(500, 470));
        
        JLabel lbTitol = new JLabel("Titol");
        lbTitol.setFont(new Font("Montserrat", Font.BOLD, 10));
        lbTitol.setForeground(new Color(234, 234, 234));
        tfTitolLlista =  new JTextField();
        
        JLabel lbEstat = new JLabel("Estat");
        lbEstat.setFont(new Font("Montserrat", Font.BOLD, 10));
        lbEstat.setForeground(new Color(234, 234, 234));
        //CRAR RADIO BUTTONS PER SELECCIONAR ESTAT DEL PRODUCTE 
        rbActiu = new JRadioButton("Actiu");
        rbActiu.setFont(new Font("Montserrat", Font.BOLD, 10));
        rbActiu.setForeground(new Color(234, 234, 234));
        rbActiu.setSelected(true);
        rbInactiu = new JRadioButton("inactiu");
        rbInactiu.setFont(new Font("Montserrat", Font.BOLD, 10));
        rbInactiu.setForeground(new Color(234, 234, 234));
        
        ButtonGroup bGrp = new ButtonGroup();
        bGrp.add(rbActiu);
        bGrp.add(rbInactiu);
        
        JLabel lbEstil = new JLabel("Estil");
        lbEstil.setFont(new Font("Montserrat", Font.BOLD, 10));
        lbEstil.setForeground(new Color(234, 234, 234));
        despEstils = obtenirDespEstils();
                
        Box bTitol = Box.createHorizontalBox();
        bTitol.add(lbTitol);
        bTitol.add(tfTitolLlista);
        
        Box bEstat = Box.createHorizontalBox();
        bEstat.add(lbEstat);
        bEstat.add(rbActiu);
        bEstat.add(rbInactiu);
        
        Box bEstil = Box.createHorizontalBox();
        bEstil.add(lbEstil);
        bEstil.add(despEstils);
            
        Box bEsquerra = Box.createVerticalBox();
        bEsquerra.setPreferredSize(new Dimension(400, 130));
        bEsquerra.add(bTitol);
        bEsquerra.add(Box.createVerticalStrut(10));
        bEsquerra.add(bEstat);
        bEsquerra.add(Box.createVerticalStrut(10));
        bEsquerra.add(bEstil);
        bEsquerra.add(Box.createVerticalStrut(10));
        
        
        //CREACÓ DE COMPONENTES D'ESQUERRE 
        JLabel lbCansons = new JLabel("Producte");
        lbCansons.setFont(new Font("Montserrat", Font.BOLD, 10));
        lbCansons.setForeground(new Color(234, 234, 234));
        despCanALb = new JComboBox();
        llCanAlb = new ArrayList<Producte>();
        List<TipusProducte> llTipus = new ArrayList<TipusProducte>();
        llTipus.add(TipusProducte.ALBUM);
        llTipus.add(TipusProducte.CANSO);
        try {
            llCanAlb = BDProducte.getProductes(llTipus, "", true,true);
        } catch (GestorBDMilaSpotifyException ex) {
            Logger.getLogger(MantenimentDades_V2.class.getName()).log(Level.SEVERE, null, ex);
        }
        despCanALb.addItem("Selecciona producte");
        for(Producte c : llCanAlb){
            despCanALb.addItem(c.getTitol());
        }
        
        JLabel lbPos = new JLabel("Posició");
        lbPos.setFont(new Font("Montserrat", Font.BOLD, 10));
        lbPos.setForeground(new Color(234, 234, 234));
        Integer value = new Integer(1);
        Integer min = new Integer(1);
        Integer max = new Integer(999);
        Integer step = new Integer(1);
        SpinnerNumberModel model = new SpinnerNumberModel(value, min, max, step);
        spPosicio = new JSpinner(model);
        
        bAfegirContingut = new JButton("Afegir");
        bElimContingut = new JButton("Eliminar");
        
        
        Box bCansons = Box.createHorizontalBox();
        bCansons.add(lbCansons);
        bCansons.add(despCanALb);
        bCansons.add(lbPos);
        bCansons.add(spPosicio);
        bCansons.add(bAfegirContingut);
        bCansons.add(bElimContingut);
        
        Box bTaulaCansons = Box.createVerticalBox();
        
        tLlista_rep = new DefaultTableModel();
        taulaLlista_rep = new JTable(tLlista_rep){
            @Override
            public boolean isCellEditable(int row, int column) // Per aconseguir que la columna 2 sigui editable
            {
                return column == 2;
            }

            @Override
            public Class<?> getColumnClass(int column) {
                Class clazz = String.class;
                switch (column) {
                    case 0:
                        clazz = String.class;
                        break;
                    case 1:
                        clazz = Long.class;
                        break;
                }
                return clazz;
            }
        };
        tLlista_rep.addColumn("Titol");
        tLlista_rep.addColumn("Posició");
        JScrollPane scrollPane = new JScrollPane(taulaLlista_rep,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(400, 200));
        
        bCancelProd = new JButton("Cancel·lar");
        bEnregProd = new JButton("Enregistrar");

        Box bBtns = Box.createHorizontalBox();
        bBtns.add(bCancelProd);
        bBtns.add(Box.createHorizontalStrut(10));
        bBtns.add(bEnregProd);
        
        JLabel lbDurada = new JLabel("Durada : ");
        lbLliDurada = new JLabel();
        lbLliDurada.setFont(new Font("Montserrat", Font.BOLD, 12));
        lbLliDurada.setForeground(new Color(234, 234, 234));
        lbDurada.setFont(new Font("Montserrat", Font.BOLD, 12));
        lbDurada.setForeground(new Color(234, 234, 234));
        
        Box bDurada = Box.createHorizontalBox();
        bDurada.add(lbDurada);
        bDurada.add(Box.createHorizontalStrut(15));
        bDurada.add(lbLliDurada);
        
        
        JPanel pCentre = new JPanel();
        pCentre.add(scrollPane);
        pCentre.setBackground(new Color(60, 64, 72));
        bTaulaCansons.add(pCentre);
        bTaulaCansons.add(bDurada);
        bTaulaCansons.add(new JPopupMenu.Separator());
        bTaulaCansons.add(Box.createVerticalStrut(10));
        bTaulaCansons.add(bBtns);
        Box bDreta = Box.createVerticalBox();
        bDreta.add(bCansons);
        bDreta.add(bTaulaCansons);
        
        Box bPrincipal = Box.createHorizontalBox();
        bPrincipal.add(bEsquerra);
        bPrincipal.add(bDreta);
        
        JPanel p = new JPanel();
        p.setPreferredSize(new Dimension(600, 400));
        p.setBackground( new Color(60, 64, 72));
        p.add(bEsquerra, BorderLayout.WEST);
        p.add(bDreta, BorderLayout.EAST);
        
        formLlista.add(p);
        formLlista.pack();
        formLlista.setResizable(false);
        formLlista.setLocationRelativeTo(f);
        formLlista.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        //AFEGIR ESCULTADORS 
        GestioBotonsProd gbp = new GestioBotonsProd();
        bCancelProd.addActionListener(gbp);
        bEnregProd.addActionListener(gbp);
        bElimContingut.addActionListener(gbp);
        bAfegirContingut.addActionListener(gbp);
    }
            
    private List<Artista> obtenirLlistaArtistes(){
        List<Artista> llArt = new ArrayList<>();
        BDArtista bda = new BDArtista();
        try {
            llArt = bda.getArtistes();
        } catch (GestorBDMilaSpotifyException ex) {
            try {
                throw new GestorBDMilaSpotifyException("No s'ha pogut recuperar la llista dels artistes." + ex.getMessage());
            } catch (GestorBDMilaSpotifyException ex1) {
                Logger.getLogger(MantenimentDades_V2.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        return llArt;
    }
    
    private void netejaForm(){
        despClientsFormolari.setSelectedIndex(0);
        despClientsFormolari.setEnabled(true);
        despProductes.setSelectedIndex(0);
        despProductes.setEnabled(true);
        momentTmp.setCalendar(null);
    }
    private void netejaFormCanso(){
        tfTitolCanso.setText("");
        despEstils.setSelectedIndex(0);
        interpret.setSelectedIndex(-1);
        anyCreacio.setValue(2022);
        spMinuts.setValue(Integer.valueOf(0));
        spSegons.setValue(Integer.valueOf(0));
    }
    
    private JComboBox obtenirDespEstils(){
        JComboBox despEstils = new JComboBox();
        llEst = new ArrayList<Estil>();
        BDEstil bde = new BDEstil();
        try {
            llEst = bde.getEstils();
        } catch (GestorBDMilaSpotifyException ex) {
            Logger.getLogger(MantenimentDades_V2.class.getName()).log(Level.SEVERE, null, ex);
        }
        despEstils.addItem("Selecciona un estil");
        for(Estil e : llEst){
            despEstils.addItem(e.getNom());
        }
        return despEstils;
    }
}
