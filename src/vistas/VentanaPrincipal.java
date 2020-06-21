package vistas;
import javax.swing.*;
import lsb.StringLSB;
import rc4.AlgoritmoRc4;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class VentanaPrincipal extends JFrame implements ActionListener{
    
    private JLabel titulo;
    private JButton botonBuscar;
    private JButton ocultar;
    private JButton mostrar;
    private JTextField cajaTexto;
    private JTextField texto;
    private JLabel etiquetaLlave;
    private JLabel etiquetaMsgClaro;
    private JPanel panel;
    private JTextArea msgClaro;
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
       

        // Label para colocar la etiqueta imagen
        titulo = new JLabel("Imagen:", JLabel.RIGHT);
        gridCons.gridx = 0 ;
        gridCons.gridy = 0 ;                  
        gridCons.gridwidth = 1;
        gridCons.gridheight = 1;
        gridCons.fill = GridBagConstraints.HORIZONTAL;
        gridCons.anchor = GridBagConstraints.NORTHEAST;
        gridCons.ipadx = 4;
        gridCons.ipady = 4;
        gridCons.insets = new Insets(4,4,4,4);
        this.add(titulo,gridCons);
        
       
        // Boton para buscar la imagen
        botonBuscar = new JButton("Buscar");
        botonBuscar.addActionListener(this);
        gridCons.gridx = 1;
        gridCons.gridy = 0 ;
        gridCons.gridwidth = 2;
        gridCons.gridheight = 1;
        gridCons.weightx = 1;
        this.add(botonBuscar,gridCons);
        gridCons.weightx = 0;
                                        
        // Etiqueta para la llave.
        etiquetaLlave = new JLabel("Llave:",JLabel.RIGHT);
        gridCons.gridx = 0 ;
        gridCons.gridy = 1 ;                  
        gridCons.gridwidth = 1;
        gridCons.weighty = 0;
        gridCons.gridheight = 1;
        gridCons.fill = GridBagConstraints.HORIZONTAL;
        gridCons.anchor = GridBagConstraints.NORTHEAST;
        this.add(etiquetaLlave,gridCons);

        // Caja para el texto de la llave
        texto = new JTextField();
        gridCons.gridx = 1;
        gridCons.gridy = 1;
        gridCons.gridwidth = 2;
        gridCons.gridheight = 1;
        gridCons.weightx = 1;
        this.add(texto,gridCons);
        gridCons.weightx = 0;

        // Etiqueta para la llave 
        etiquetaMsgClaro = new JLabel("Mensaje:",JLabel.RIGHT);
        gridCons.gridx = 0 ;
        gridCons.gridy = 2 ;                  
        gridCons.gridwidth = 1;
        gridCons.weighty = 0;
        gridCons.gridheight = 1;
        gridCons.fill = GridBagConstraints.HORIZONTAL;
        gridCons.anchor = GridBagConstraints.NORTHEAST;
        this.add(etiquetaMsgClaro,gridCons);

        
        // Caja de texto para colocar el mensje.
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

        // Mensaje para cifrar
        ocultar = new JButton("Cifrar");
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

        // Boton para decifrar mensaje
        mostrar = new JButton("Descifrar");
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

    // Evento para el boton cifrar y descifrar asi como las acciones y metodos a ejecutar.
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

                // Empieza cifrado
                AlgoritmoRc4 cifrarRc4 = new AlgoritmoRc4(); 
                String msg = msgClaro.getText();
                String llave = texto.getText();
                int[] bitsMsg = AlgoritmoRc4.textoABinario(msg);
                System.out.println("\nSin cifrar");
                for (int i = 0; i < bitsMsg.length; i++) {
                    System.out.print("." + bitsMsg[i]);
                }
                int[] bitsLlave = AlgoritmoRc4.obtenerKey(llave);
                int[] resultCifrado= cifrarRc4.cifrarTexto(bitsMsg, bitsLlave);
                if (resultCifrado == null){
                    msgClaro.setText(" Verifica que la clave sea de 5 a 32 bytes");
                    return;
                }
               
                // Termina cifrado

                int[] res = imagenABytes(imagenOriginal);
                StringLSB prueba = new StringLSB(res, resultCifrado);
                if (prueba.siCabe()) {
                    File copia = new File(archivo.getParentFile(), "imgen-msg-oculto-cifrada.bmp");
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
        // Evento para descifrar y ver mensaje.
        if (evento.getSource() == mostrar) {
            try {
                AlgoritmoRc4 cifrarRc4 = new AlgoritmoRc4(); 
                int[] res = imagenABytes(imagenOriginal);
                System.out.println("Imagen en Arreglo");
                StringLSB prueba = new StringLSB(res, new int[0]);
                System.out.println("Probando logitud de cadena");

                // Empieza descifrado
                int[] textoCifrado = prueba.mostrarBytesConLSB();
                for (int i = 0; i < textoCifrado.length; i++) {
                    System.out.print("." + textoCifrado[i]);
                }
                System.out.println();
                String textoLlave = texto.getText();
                System.out.println(textoCifrado);
                System.out.println();
                int[] bitsKey = AlgoritmoRc4.obtenerKey(textoLlave);
                int[] bitsDescifrarMsg = cifrarRc4.cifrarTexto(textoCifrado, bitsKey);
                if (bitsDescifrarMsg == null){
                    msgClaro.setText(" Verifica que la clave sea de 5 a 32 bytes");
                    return;
                }
                for (int i = 0; i < bitsDescifrarMsg.length; i++) {
                    System.out.print("." + bitsDescifrarMsg[i]);
                }
                System.out.println();
                String resultado = AlgoritmoRc4.binarioATexto(bitsDescifrarMsg);
                
               
               
                msgClaro.setText(resultado);
                // Termina descifrado

                //Llave 6 = 00000110
                //01000010 01110010 01100101 01101110 01100100 01100001 - Mensaje (Brenda)
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Convertir la imagen a los bytes que contiene.
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
}

