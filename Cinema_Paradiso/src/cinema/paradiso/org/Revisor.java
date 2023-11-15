/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cinema.paradiso.org;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author abdel
 */
public class Revisor {
    
    private JFrame f;
    private String rutaImgTitol = "recursos/titol.png"; 
    private String butacaLliure = "recursos/butacaLliure.png";
    private String butacaOcupada = "recursos/butacaOcupada.png";
    private String arxiuDades = "recursos/dades.csv";
    private ArrayList<Butaca> listButaques = new ArrayList<Butaca>();
    private ArrayList<Butaca> listButaquesSeleccionades = new ArrayList<Butaca>();
    private ArrayList<Entrada> listEntrades;
    private Persona persona;
    
    private JTextField tfNom;
    private JComboBox despDni;
    private TextArea taEntrades;
    private DefaultTableModel tButaques;
    private JTable tbButaques;


    public void go (){
        f = new JFrame("Cinema Paradiso");
        f.setPreferredSize(new Dimension(1200, 740));
          
        JPanel pTitol = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pTitol.setBackground(new Color(60, 64, 72));
        JLabel lbCinemaTitol = new JLabel();
        lbCinemaTitol.setSize(600, 180);
        SetImageLabel(lbCinemaTitol, rutaImgTitol);
        pTitol.add(lbCinemaTitol);
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        f.getContentPane().add(pTitol,BorderLayout.NORTH);
        try {
            carregarDades();
            crearFormConsultacio();
            crearApartatBotaques();
            omplirTaulaButaques();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(f, "Error en omplir dades\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            System.out.println(ex.getMessage());
        }
        despDni.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent event) {
                if (event.getStateChange() == ItemEvent.SELECTED && despDni.getSelectedIndex() !=0) {
                    try {
                        String selectedValue = (String) event.getItem();
                        for(Entrada e : listEntrades){
                            if(e.getPersona().getDni().equals(selectedValue))
                                mostrarEntrada(e);

                        }
                    } catch (Exception ex) {
                        Logger.getLogger(Revisor.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        f.pack();
        // Per centrar la finestra a la pantalla
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
    private void SetImageLabel(JLabel nomLabel,String ruta){
        ImageIcon image = new ImageIcon(ruta);
        Icon icon = new ImageIcon(
                image.getImage().getScaledInstance(nomLabel.getWidth(), nomLabel.getHeight(), Image.SCALE_DEFAULT)
        );
        nomLabel.setIcon(icon);
        
    }
    
    private void crearFormConsultacio(){
        JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER));
        p.setBorder(new EmptyBorder(20, 10, 10, 50));
        Box b = Box.createVerticalBox();
        b.setPreferredSize(new Dimension(200, 290));
        
        Box bnp = Box.createVerticalBox();
        JLabel lbnom = new JLabel("Nom de la persona");
        tfNom = new JTextField();
        bnp.add(lbnom);
        bnp.add(tfNom);
        
        Box bnd = Box.createVerticalBox();
        JLabel lbdni = new JLabel("DNI de la persona");
        despDni = new JComboBox<String>();
        despDni.addItem("Sel·lecciona DNI");
        carregarDespDni();
        bnd.add(lbdni);
        bnd.add(despDni);
        
        Box bent = Box.createVerticalBox();
        JPanel pent = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel lbEnt = new JLabel("Entrades reservades");
        pent.add(lbEnt);
        taEntrades = new TextArea(10,150);
        taEntrades.setEditable(false);
        bent.add(pent);
        bent.add(taEntrades);
        b.add(bnp);
        b.add(bnd);
        b.add(Box.createVerticalStrut(30));
        b.add(bent);
        b.add(Box.createVerticalStrut(20));
        p.add(b);
        f.getContentPane().add(p,BorderLayout.EAST);
    }
    
    private void crearApartatBotaques(){
        tButaques = new DefaultTableModel();
        tbButaques = new JTable(tButaques) {
            @Override
            public boolean isCellEditable(int row, int column) // Per aconseguir que la columna 2 sigui editable
            {
                return false;
            }
        };
        tbButaques.setDefaultRenderer(Object.class, new ImageTableCellRenderer());
        tbButaques.setRowHeight(50);
        tbButaques.getTableHeader().setVisible(false);
        tbButaques.setShowGrid(false);
        tbButaques.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        for (int i = 0; i < 16; i++) {
            tButaques.addColumn("C" + i);
        }
        int columnWidth = 150; // Anchura deseada en píxeles
        JScrollPane scrollPane = new JScrollPane(tbButaques,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(800, 550));
        JPanel pCentre = new JPanel();
        pCentre.add(scrollPane);
        f.add(pCentre,BorderLayout.CENTER);
    }
    
    private void omplirTaulaButaques() throws Exception{
        tButaques.setRowCount(0);
        for (int i = 0; i < 9; i++) {
            Vector<Icon> vIcons = new Vector<>();
            for (int j = 0; j < 16; j++) {
                Butaca b= new Butaca(i,j);
                Icon butaca = null;
                butaca = getButacaLliure();
                vIcons.add(butaca);
            }
            tButaques.addRow(vIcons);
        }
    }
    
    
    
    
    private static class ImageTableCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (value instanceof ImageIcon) {
                JLabel label = new JLabel((ImageIcon) value);
                label.setHorizontalAlignment(JLabel.CENTER);
                return label;
            }
            return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }
    }
        // Controlem que el camp codi només pugui admetre dígits 
        public class adaptadorDigits extends KeyAdapter {
            public void keyTyped(KeyEvent e) {
                char caracter = e.getKeyChar();
                if (((caracter < '0') || (caracter > '9'))
                        && (caracter != '\b')) {
                    e.consume();
                }
            }
        }
    
    private Icon getButacaOcupada(){
        String img = "recursos/butacaOcupada.png";
        JLabel lbButaca = new JLabel();
        lbButaca.setSize(30,30);
        ImageIcon image = new ImageIcon(img);
        Icon icon = new ImageIcon(
                image.getImage().getScaledInstance(lbButaca.getWidth(), lbButaca.getHeight(), Image.SCALE_DEFAULT)
        );
        lbButaca.setIcon(icon);
        return lbButaca.getIcon();
    }
    private Icon getButacaLliure(){
        String img = "recursos/butacaLliure.png";
        JLabel lbButaca = new JLabel();
        lbButaca.setSize(30,30);
        ImageIcon image = new ImageIcon(img);
        Icon icon = new ImageIcon(
                image.getImage().getScaledInstance(lbButaca.getWidth(), lbButaca.getHeight(), Image.SCALE_DEFAULT)
        );
        lbButaca.setIcon(icon);
        return lbButaca.getIcon();
    }
    
    private boolean checkButaca(Butaca b,List<Butaca> list){
        
        for (int i = 0; i < list.size() ; i++) {
            if(b.equals(list.get(i)))
                return true;
        }
        
        
        return false;
    }
    
    private boolean checkButacaSeleccionada(Butaca b){
        
        for (int i = 0; i < listButaquesSeleccionades.size() ; i++) {
            if(b.equals(listButaquesSeleccionades.get(i)))
                return true;
        }
        
        
        return false;
    }
    
    private int obtenirIndexButacaOcupada(Butaca  b){
        for (int i = 0; i < listButaques.size() ; i++) {
            if(b.equals(listButaques.get(i)))
                return i;
        }
        return -1;
    }
    private int obtenirIndexButacaSeleccionada(Butaca  b){
        for (int i = 0; i < listButaquesSeleccionades.size() ; i++) {
            if(b.equals(listButaquesSeleccionades.get(i)))
                return i;
        }
        return -1;
    }
    
    private void imprimirButaquesOcupades(){
        taEntrades.setText("");
        for(Butaca b : listButaquesSeleccionades)
            taEntrades.append("Fila : " + b.getRow() + " Columna : "+b.getColumn()+"\n");
        
    }
    
    private void carregarDades() throws Exception{
        listEntrades = new ArrayList<Entrada>();
        
        try (BufferedReader lector = new BufferedReader(new FileReader(arxiuDades))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                if (linea.length() !=0 ){                    
                    String[] aux = linea.split(",");
                    String nom = aux[0];
                    String dni = aux[1];
                    int importTotal = Integer.parseInt(aux[2]);
                    ArrayList<Butaca> butaques = new ArrayList<>();
                    String baux = linea.substring(linea.indexOf("[")+1,linea.indexOf("]"));
                    String[] baux2 = baux.split(";");
                    for (int i = 0; i < baux2.length; i++) {
                        int fila  = (int) Integer.parseInt(baux2[i].split(",")[0]);
                        int columna  = (int) Integer.parseInt(baux2[i].split(",")[1]);
                        butaques.add(new Butaca(fila, columna));
                    }
                    listEntrades.add(new Entrada(new Persona(nom,dni), importTotal, butaques));
                }
            }
        } catch (IOException e) {
            System.out.println("Ocurrió un error al leer el archivo: " + e.getMessage());
        }
    }
    
    private void carregarDespDni(){
        if(despDni!= null){
            for(Entrada e: listEntrades){
                 despDni.addItem(e.getPersona().getDni());
             }  
        }
           
    }
    
    private void mostrarEntrada(Entrada e) throws Exception{
        tButaques.setRowCount(0);
        taEntrades.setText("");
        tfNom.setText("");
        for (int i = 0; i < 9; i++) {
            Vector<Icon> vIcons = new Vector<>();
            for (int j = 0; j < 16; j++) {
                Butaca b= new Butaca(i,j);
                Icon butaca = null;
                if(checkButaca(b,e.getLlistaButacques())){
                    butaca = getButacaOcupada();
                    taEntrades.append("Fila :" + b.getRow() + " Columna : " + b.getColumn()+"\n");
                }else{
                    butaca = getButacaLliure();
                }
                vIcons.add(butaca);
            }
            tButaques.addRow(vIcons);
        }
        tfNom.setText(e.getPersona().getNom());
    }
    
    private void netejarFormaliris(){
        tfNom.setText("");
        taEntrades.setText("");
    }
}
