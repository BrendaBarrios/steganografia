package vistas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class VentanaPrincipal extends JFrame implements ActionListener{
    
    private JLabel titulo;
    private JButton botonBuscar;
    private JTextField cajaTexto;
    private JTextField texto;
    private JLabel etiquetaLlave;
    private JLabel etiquetaMsgClaro;
    private JPanel panel;
    private JTextArea msgClaro;
    private JButton ocultar;
    private JButton mostrar;



    /*
    |-------------------
    |Imagen|   Abrir    |
    |------|------------|
    |LLave |            |
    |------|------------|
    |[Imagen]|          |
    |      |            |
    |      |            |
    |      |            |
    |      |            |
    |-------------------
    */
 
 

    public VentanaPrincipal(){

        // Parte y tamaño de la ventana

        this.setSize(800,550);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Cifrando con Esteganografia");
        GridBagLayout grid = new GridBagLayout();    
        this.setLayout(grid);
        GridBagConstraints   gridCons = new GridBagConstraints();
       

        titulo = new JLabel("Imagen:", JLabel.RIGHT);
        gridCons.gridx = 0 ;
        gridCons.gridy = 0 ;                  
        gridCons.gridwidth = 1;
        //gridCons.weighty = 1;
        gridCons.gridheight = 1;
        gridCons.fill = GridBagConstraints.HORIZONTAL;
        gridCons.anchor = GridBagConstraints.NORTHEAST;
        gridCons.ipadx = 4;
        gridCons.ipady = 4;
        gridCons.insets = new Insets(4,4,4,4);
        this.add(titulo,gridCons);
        
       

        botonBuscar = new JButton("Buscar");
        botonBuscar.addActionListener(this);
        gridCons.gridx = 1;
        gridCons.gridy = 0 ;
        gridCons.gridwidth = 2;
        gridCons.gridheight = 1;
        gridCons.weightx = 1;
        this.add(botonBuscar,gridCons);
        gridCons.weightx = 0;
                                                        
        etiquetaLlave = new JLabel("Llave:",JLabel.RIGHT);
        gridCons.gridx = 0 ;
        gridCons.gridy = 1 ;                  
        gridCons.gridwidth = 1;
        gridCons.weighty = 0;
        gridCons.gridheight = 1;
        gridCons.fill = GridBagConstraints.HORIZONTAL;
        gridCons.anchor = GridBagConstraints.NORTHEAST;
        this.add(etiquetaLlave,gridCons);


        texto = new JTextField();
        gridCons.gridx = 1;
        gridCons.gridy = 1;
        gridCons.gridwidth = 2;
        gridCons.gridheight = 1;
        gridCons.weightx = 1;
        this.add(texto,gridCons);
        gridCons.weightx = 0;

        // Otro elemento

        etiquetaLlave = new JLabel("Mensaje:",JLabel.RIGHT);
        gridCons.gridx = 0 ;
        gridCons.gridy = 2 ;                  
        gridCons.gridwidth = 1;
        gridCons.weighty = 0;
        gridCons.gridheight = 1;
        gridCons.fill = GridBagConstraints.HORIZONTAL;
        gridCons.anchor = GridBagConstraints.NORTHEAST;
        this.add(etiquetaLlave,gridCons);

        //AQUI

        msgClaro = new JTextArea();
        gridCons.gridx = 1 ;
        gridCons.gridy = 2 ;                  
        gridCons.gridwidth = 2;
        gridCons.weighty = 1;
        gridCons.gridheight = 1;
        gridCons.fill = GridBagConstraints.BOTH;
        gridCons.anchor = GridBagConstraints.NORTHEAST;
        msgClaro.setLineWrap(true);
        msgClaro.setCaretColor(Color.ORANGE);
        this.add(msgClaro,gridCons);

        ocultar = new JButton("Ocultar");
        gridCons.gridx = 1;
        gridCons.gridy = 3;                  
        gridCons.gridwidth = 1;
        gridCons.weightx = 1;
        gridCons.weighty = 0;
        gridCons.gridheight = 1;
        gridCons.fill = GridBagConstraints.BOTH;
        gridCons.anchor = GridBagConstraints.NORTHEAST;
        this.add(ocultar,gridCons);


        mostrar = new JButton("Mostrar");
        gridCons.gridx = 2;
        gridCons.gridy = 3;                  
        gridCons.gridwidth = 1;
        gridCons.weighty = 0;
        gridCons.weightx = 1;
        gridCons.gridheight = 1;
        gridCons.fill = GridBagConstraints.BOTH;
        gridCons.anchor = GridBagConstraints.NORTHEAST;
        this.add(mostrar,gridCons);



        
        
        this.setVisible(true);
        

    }

    public void actionPerformed(ActionEvent evento){
        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(botonBuscar);
        File archivo = chooser.getSelectedFile();
        botonBuscar.setText(archivo.getName());
    }


}
