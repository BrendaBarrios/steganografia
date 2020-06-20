package vistas;

import javax.swing.*;

import lsb.StringLSB;
import des.StringDES;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.function.IntFunction;

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
    private File archivo;
    private String msgClaroTexto;
    private BufferedImage imagenOriginal;

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
        ocultar.addActionListener(this);
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
        mostrar.addActionListener(this);
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
        if (evento.getSource() == botonBuscar) {
            // Abrir archivo
            JFileChooser chooser = new JFileChooser();
            chooser.showOpenDialog(botonBuscar);
            archivo = chooser.getSelectedFile();
            botonBuscar.setText(archivo.getName());
            try {
                imagenOriginal = ImageIO.read(archivo);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (evento.getSource() == ocultar) {
            try {
                msgClaroTexto = msgClaro.getText();
                // Empieza cifrado
                String text = msgClaroTexto; 
                String key = texto.getText(); 
        
                StringDES cipher = new StringDES(); 
                text = cipher.encrypt(textoAHexadecimal(text), textoAHexadecimal(key)); 
                // Termina cifrado
                int[] res = imagenABytes(imagenOriginal);
                StringLSB prueba = new StringLSB(res, text);
                if (prueba.siCabe()) {
                    File copia = new File(archivo.getParentFile(), "prueba-lsb.bmp");
                    int[] pixeles = prueba.ocultarBytesConLSB();
                    BufferedImage buffer = new BufferedImage(imagenOriginal.getWidth(), imagenOriginal.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
                    buffer.setRGB(0, 0, imagenOriginal.getWidth(), imagenOriginal.getHeight(), pixeles, 0, imagenOriginal.getWidth());
                    ImageIO.write(buffer, "bmp", copia);
                    System.out.println("TERMINADO");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (evento.getSource() == mostrar) {
            try {
                int[] res = imagenABytes(imagenOriginal);
                System.out.println("Imagen en Arreglo");
                StringLSB prueba = new StringLSB(res, msgClaroTexto);
                System.out.println("Probando logitud de cadena");
                // Empieza descifrado
                StringDES cipher = new StringDES();
                String text = hexToAscii(cipher.decrypt(prueba.mostrarBytesConLSB(), textoAHexadecimal(texto.getText())));
                // Termina descifrado
                msgClaro.setText(text);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private int[] imagenABytes(BufferedImage bImagen) {
        int alto = bImagen.getHeight();
        int ancho = bImagen.getWidth();
        //int  imagen.getRGB(0,0) +
        int[] datos = new int[alto * ancho];
        for (int i = 0; i < alto; i++) {
            for (int j = 0; j < ancho; j++) {
                int pixel = bImagen.getRGB(j, i);
                datos[ancho * i + j] = pixel;
            }
        }
        return datos;
    }

    private String textoAHexadecimal(String texto) {
        String hexadecimal = "";
        for (int i = 0; i < texto.length(); i++) {
            hexadecimal += Integer.toHexString(texto.charAt(i));
        }
        return hexadecimal;
    }

    private String hexToAscii(String hexStr) {
        StringBuilder output = new StringBuilder("");
         
        for (int i = 0; i < hexStr.length(); i += 2) {
            String str = hexStr.substring(i, i + 2);
            output.append((char) Integer.parseInt(str, 16));
        }
         
        return output.toString();
    }
}




