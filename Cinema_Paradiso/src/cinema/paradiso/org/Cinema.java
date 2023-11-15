/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cinema.paradiso.org;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Label;
import java.awt.LayoutManager;
import java.awt.List;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author abdel
 */
public class Cinema {

    private JFrame f;
    private String rutaImgTitol = "recursos/titol.png"; 
    private String butacaLliure = "recursos/butacaLliure.png";
    private String butacaOcupada = "recursos/butacaOcupada.png";
    private String arxiuDades = "recursos/dades.csv";
    private ArrayList<Butaca> listButaques = new ArrayList<Butaca>();
    private ArrayList<Butaca> listButaquesSeleccionades = new ArrayList<Butaca>();
    private Persona persona;
    
    private JButton btnPantallaPagament;
    private JTextField tfNom;
    private JTextField tfDni;
    private TextArea taEntrades;
    private DefaultTableModel tButaques;
    private JTable tbButaques;
    private JDialog formPagament;
    private JSpinner tfEntradaGen;
    private JSpinner tfEntradaJove;
    private JSpinner tfEntradaJubilat;
    private JTextField tfPreuTotal;
    private JButton btnPagament;


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
        crearFormReservacio();
        crearApartatBotaques();
        
        try {
            omplirTaulaButaques();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(f, "Error en omplir dades\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            System.out.println(ex.getMessage());
        }
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
    private void crearFormReservacio(){
        JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER));
        p.setBorder(new EmptyBorder(20, 10, 10, 50));
        Box b = Box.createVerticalBox();
        b.setPreferredSize(new Dimension(200, 330));
        
        Box bnp = Box.createVerticalBox();
        JLabel lbnom = new JLabel("Nom de la persona");
        tfNom = new JTextField();
        bnp.add(lbnom);
        bnp.add(tfNom);
        
        Box bnd = Box.createVerticalBox();
        JLabel lbdni = new JLabel("DNI de la persona");
        tfDni = new JTextField();
        bnd.add(lbdni);
        bnd.add(tfDni);
        
        Box bent = Box.createVerticalBox();
        JPanel pent = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel lbEnt = new JLabel("Entrades reservades");
        pent.add(lbEnt);
        taEntrades = new TextArea(10,150);
        taEntrades.setEditable(false);
        bent.add(pent);
        bent.add(taEntrades);
        
        JPanel pBtn = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnPantallaPagament = new JButton("Anar a pantalla de pagament");
        btnPantallaPagament.addActionListener(new GestioBotons());
        pBtn.add(btnPantallaPagament);
        
        b.add(bnp);
        b.add(bnd);
        b.add(bent);
        b.add(Box.createVerticalStrut(20));
        b.add(pBtn);
        
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
        // Cambiar la anchura de todas las columnas
//        TableColumnModel columnModel = tbButaques.getColumnModel();
//        for (int i = 0; i < columnModel.getColumnCount(); i++) {
//            TableColumn column = columnModel.getColumn(i);
//            column.setPreferredWidth(columnWidth);
//        }
        tbButaques.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = tbButaques.getSelectedRow();
                int column = tbButaques.getSelectedColumn();
                Butaca butSeleccionada = null;
                try {
                    butSeleccionada = new Butaca(row,column);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(f, "Error en crear butaca", "Error", JOptionPane.ERROR_MESSAGE);
                }
                if (row >= 0 && column >= 0 && !checkButacaSeleccionada(butSeleccionada) && !checkButaca(butSeleccionada)) {
                        try {
                            Icon butacaOcuIcon = getButacaOcupada();
                            tButaques.setValueAt(butacaOcuIcon, row, column);
                            tbButaques.repaint();
                            listButaquesSeleccionades.add(new Butaca(row,column));
                        } catch (Exception ex) {
                                    JOptionPane.showMessageDialog(f, "Error en afegir butaca\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        }
                }else if(checkButacaSeleccionada(butSeleccionada)){
                        Icon butacaLliuIcon = getButacaLliure();
                        tButaques.setValueAt(butacaLliuIcon, row, column);
                        listButaquesSeleccionades.remove(obtenirIndexButacaSeleccionada(butSeleccionada));
                        tbButaques.repaint();
                }
                imprimirButaquesOcupades();
            }
        });
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
        // Intentar abrir el archivo y leer su contenido
        try (BufferedReader lector = new BufferedReader(new FileReader(arxiuDades))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                if (linea.length() !=0 ){                    
                    String aux = linea.substring(linea.indexOf("[")+1,linea.indexOf("]"));
                    String[] aux2 = aux.split(";");
                    for (int i = 0; i < aux2.length; i++) {
                        int fila  = (int) Integer.parseInt(aux2[i].split(",")[0]);
                        int columna  = (int) Integer.parseInt(aux2[i].split(",")[1]);
                        listButaques.add(new Butaca(fila, columna));
                    }
                    
                }
                

            }
        } catch (IOException e) {
            System.out.println("Ocurrió un error al leer el archivo: " + e.getMessage());
        }

        for (int i = 0; i < 9; i++) {
            Vector<Icon> vIcons = new Vector<>();
            for (int j = 0; j < 16; j++) {
                Butaca b= new Butaca(i,j);
                Icon butaca = null;
                if(checkButaca(b)){
                    butaca = getButacaOcupada();
                }else{
                    butaca = getButacaLliure();
                }
                vIcons.add(butaca);
            }
            tButaques.addRow(vIcons);
        }
    }
    
    
    
    private void formolariPagament(){
        formPagament = new JDialog(f,true);
        formPagament.setTitle("Pantalla Pagament");
        formPagament.setPreferredSize(new Dimension(400,300));
        formPagament.setLocationRelativeTo(f);
        
        int minValue = 0; 
        int maxValue = 100;
        int initialValue = 0;

        SpinnerNumberModel spinnerModelGen = new SpinnerNumberModel(initialValue, minValue, maxValue, 1); 
        SpinnerNumberModel spinnerModelJo = new SpinnerNumberModel(initialValue, minValue, maxValue, 1); 
        SpinnerNumberModel spinnerModelJu = new SpinnerNumberModel(initialValue, minValue, maxValue, 1); 
        
        JPanel pTitol = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel lbTotalEnt = new JLabel("TOTAL ENTRADES: ");
        pTitol.add(lbTotalEnt);
        Box bEntGeneral = Box.createHorizontalBox();
        JLabel lbEntGen = new JLabel("Entrada General");
        tfEntradaGen = new JSpinner(spinnerModelGen);
        JLabel lbEntJove = new JLabel("Entrada Jove");
        tfEntradaJove = new JSpinner(spinnerModelJo);
        tfEntradaJove.addKeyListener(new adaptadorDigits());
        JLabel lbEntJubilat = new JLabel("Entrada Jubilat");
        tfEntradaJubilat = new JSpinner(spinnerModelJu);
        tfEntradaJubilat.addKeyListener(new adaptadorDigits());
        JLabel lbPreuTotal = new JLabel("PREU TOTAL :");
        tfPreuTotal = new JTextField("0");
        tfPreuTotal.setEditable(false);
//        tfPreuTotal.setEnabled(false);
        
        btnPagament = new JButton("PAGAMENT");
        JPanel pBtn = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pBtn.add(btnPagament);
        btnPagament.addActionListener(new GestioBotons());
        
        Box b = Box.createVerticalBox();
        b.setPreferredSize(new Dimension(250,250));
        
        Box beg = Box.createHorizontalBox();
        beg.add(lbEntGen);
        beg.add(tfEntradaGen);
        
        Box bejo = Box.createHorizontalBox();
        bejo.add(lbEntJove);
        bejo.add(tfEntradaJove);
        
        Box beju = Box.createHorizontalBox();
        beju.add(lbEntJubilat);
        beju.add(tfEntradaJubilat);
        
        Box bpt = Box.createHorizontalBox();
        bpt.add(lbPreuTotal);
        bpt.add(tfPreuTotal);
        
        b.add(pTitol);
        b.add(Box.createVerticalStrut(25));
        b.add(beg);
        b.add(Box.createVerticalStrut(20));
        b.add(bejo);
        b.add(Box.createVerticalStrut(20));
        b.add(beju);
        b.add(Box.createVerticalStrut(20));
        b.add(bpt);
        b.add(Box.createVerticalStrut(25));
        b.add(pBtn);
        b.add(Box.createVerticalStrut(25));
        JPanel p = new JPanel();
        p.add(b);
        formPagament.add(p);    
        formPagament.pack();
        
        tfEntradaGen.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int entGen  = (int) tfEntradaGen.getValue() * 10 ;
                int entJo  = (int) tfEntradaJove.getValue() * 6 ;
                int entJu  = (int) tfEntradaJubilat.getValue() * 7 ;
                int preuTotal = entGen + entJo +entJu;
                System.out.println("Preu : " + preuTotal);
                tfPreuTotal.setText(Integer.toString(preuTotal));
            }
        });
        
        tfEntradaJove.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int entGen  = (int) tfEntradaGen.getValue() * 10 ;
                int entJo  = (int) tfEntradaJove.getValue() * 6 ;
                int entJu  = (int) tfEntradaJubilat.getValue() * 7 ;
                tfPreuTotal.setText(Integer.toString(entGen + entJo +entJu));
            }
            
        });
        
        tfEntradaJubilat.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int entGen  = (int) tfEntradaGen.getValue() * 10 ;
                int entJo  = (int) tfEntradaJove.getValue() * 6 ;
                int entJu  = (int) tfEntradaJubilat.getValue() * 7 ;
                tfPreuTotal.setText(Integer.toString(entGen + entJo +entJu));
            }
        });
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
    
    private class GestioBotons implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ev) {
            JButton btnPremut = (JButton) ev.getSource();
            if(btnPremut.equals(btnPantallaPagament)){
                String nomPersona = tfNom.getText().toString();
                String dniPersona = tfDni.getText().toString();
                if (nomPersona != null && dniPersona != null && listButaquesSeleccionades.size() != 0 ){
                    try {
                        persona = new Persona(nomPersona,dniPersona); 
                        formolariPagament();
                        formPagament.setVisible(true);
                    } catch (Exception ex) {
                         JOptionPane.showMessageDialog(f, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }else {
                    //El nom i dni  els tinc controlats amb Exception.
                    if(listButaquesSeleccionades.size() == 0)
                        JOptionPane.showMessageDialog(f, "Selecciona una butaca", "Info", JOptionPane.INFORMATION_MESSAGE); 
                    
                } 
            }
            
            if(btnPremut.equals(btnPagament)){
                int entGen  = (int) tfEntradaGen.getValue();
                int entJo  = (int) tfEntradaJove.getValue();
                int entJu  = (int) tfEntradaJubilat.getValue();
                int numEntrades = entGen +entJo + entJu;
                if(numEntrades != listButaquesSeleccionades.size()){
                    JOptionPane.showMessageDialog(f, "El número d'entrades és incorrecte", "Error", JOptionPane.ERROR_MESSAGE);
                }else{
                    try (BufferedWriter escritor = new BufferedWriter(new FileWriter(arxiuDades, true))) {
                         Entrada entrada = new Entrada(persona, (int)Integer.parseInt(tfPreuTotal.getText().toString()), listButaquesSeleccionades);
                         String nomPersona = entrada.getPersona().getNom();
                         String dniPersona = entrada.getPersona().getDni();
                         int preu = entrada.getPreuTotal();
                         String posButaques ="[";
                         int index = 0;
                         for (Butaca b : entrada.getLlistaButacques()){
                            if (index != 0)
                               posButaques += ";"+ b.getRow()+","+b.getColumn();
                            else
                               posButaques += b.getRow()+","+b.getColumn();
                            
                            listButaques.add(b);
                            index ++;
                                 
                         }
                         posButaques += "]";
                         String txt = nomPersona +","+dniPersona+","+preu+","+ posButaques;
                         escritor.write(txt);
                         escritor.newLine();
                         escritor.close();
                         listButaquesSeleccionades.clear();
                         omplirTaulaButaques();
                         netejarFormaliris();
                         JOptionPane.showMessageDialog(f, "Entrada registrada correctament", "Éxit", JOptionPane.INFORMATION_MESSAGE);
                     } catch (IOException e) {
                         e.printStackTrace();
                     }catch (Exception  ex){
                         JOptionPane.showMessageDialog(f, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                     }
                    formPagament.dispose();
                    
                     
                }
                    
            }
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
    
    private boolean checkButaca(Butaca b){
        
        for (int i = 0; i < listButaques.size() ; i++) {
            if(b.equals(listButaques.get(i)))
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
    
    private void netejarFormaliris(){
        tfNom.setText("");
        tfDni.setText("");
        taEntrades.setText("");
        tfEntradaGen.setValue(0);
        tfEntradaJove.setValue(0);
        tfEntradaJubilat.setValue(0);
        tfPreuTotal.setText("0");
    }
}
