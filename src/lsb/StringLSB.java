package lsb;

import java.util.Arrays;

public class StringLSB {

    private int[] datos; //datos de bytes
    private int[] mensajeEnBits;


    public StringLSB(int[] datos, int[] mensaje) {
        this.datos = datos;
        this.mensajeEnBits = mensaje;
    }

    public boolean siCabe() {
        return true || (datos.length / 8) <= mensajeEnBits.length;
    }


    public int[] ocultarBytesConLSB() {
        // Obtener longitud del mensaje
        int longitudMensaje = mensajeEnBits.length;
        // Escribir la bandera en un byte
        int[] bandera = generarBandera(longitudMensaje);
        escribirBanders(bandera, datos);
        System.out.printf("##### LSB Bandera: %d, binario: %s, Mensaje a escribir: %s\n", longitudMensaje, Arrays.toString(bandera), mensajeEnBits);
        // Escribir el mensaje
        for (int i = 0; i < longitudMensaje; i++){
            // Generar indice de escritura para cada caracter
            int indice = 8 + (i * 8);
            escribirCaracterUnico(mensajeEnBits[i], datos, indice);
        }
        return datos;
    }

    private int[] generarBandera(int longitud) {
        // Convertir entero a binario String
        String binario = Integer.toBinaryString(longitud);
        // Guardar digitos binarios en un arreglo
        int[] digitosBinarios = new int[8];
        int indiceBitsMensaje = 0;
        for (int n = 0; n < 8 - binario.length(); n ++) {
            digitosBinarios[indiceBitsMensaje] = 0;
            indiceBitsMensaje ++;
        }
        for (int i = 0; i < binario.length(); i ++){
            digitosBinarios[indiceBitsMensaje] = Integer.parseInt(binario.substring(i, i + 1));
            indiceBitsMensaje ++;
        }
        return digitosBinarios;
    }

    private void escribirBanders(int[] bitsBandera, int[] datos) {
        for (int i = 0; i < 8; i ++) {
            datos[i] = bitMenosSignificativo(datos[i], bitsBandera[i]);
        }
    }

    /**
     * Toma un caracter, el arreglo de pixeles,
     * y escribe los 8 bits del caracter en el
     * arreglo datos a partir del indice dado.
     */
    private void escribirCaracterUnico(int caracter, int[] datos, int indice) {
        int[] bitsCaracter = generarBitsCaracter(caracter);
        System.out.printf("##### LSB Posicion: %3d, Caracter Escrito: '%s', Byte escrito: %s\n", indice, caracter, Arrays.toString(bitsCaracter));
        for (int i = 0; i < 8; i ++) {
            datos[indice + i] = bitMenosSignificativo(datos[indice + i], bitsCaracter[i]);
        }
    }
    private int[] generarBitsCaracter(int caracter) {
        // Extraer cada caracter y guardarlo como entero en el arreglo a retornar
        int[] bitsCaracter = new int[8];
        for (int i = 0; i < 8; i++) {
            bitsCaracter[bitsCaracter.length - i - 1] = caracter >> i & 1;
        }
        System.out.printf("##### LSB Caracter escrito %s\n", Arrays.toString(bitsCaracter));
        return bitsCaracter;
    }

    /**
     * Proceso completo de lectura de mensaje.
     */
    public String mostrarStringConLSB() {
        // Leer la bandera en el LSB de los primeros 8 pixeles y guardarla como un entero: logitudMensaje
        int longitudMensaje = leerBandera(datos);
        System.out.printf(">>>>>> Bandera: %d\n", longitudMensaje);
        // Leer el LSB de cada pixel a partir del 9no, en total longitudMensaje * 8
        String mensaje = "";
        for (int i = 0; i < longitudMensaje; i++){
            // Generar indice de escritura para cada caracter
            int indice = 8 + (i * 8);
            String caracter = leerCaracter(datos, indice);
            System.out.printf("@@@@@@@ Carcater: %s\n", caracter);
            mensaje += caracter;
        }
        return mensaje;
    }
    public int[] mostrarBytesConLSB() {
        // Leer la bandera en el LSB de los primeros 8 pixeles y guardarla como un entero: logitudMensaje
        int longitudMensaje = leerBandera(datos);
        System.out.printf("##### LSB Bandera: %d\n", longitudMensaje);
        // Leer el LSB de cada pixel a partir del 9no, en total longitudMensaje * 8
        int[] mensaje = new int[longitudMensaje];
        for (int i = 0; i < longitudMensaje; i++){
            // Generar indice de escritura para cada caracter
            int indice = 8 + (i * 8);
            int caracter = leerInt(datos, indice);
            System.out.printf("##### LSB Caracter: %s %d\n", caracter, caracter);
            mensaje[i] = caracter;
        }
        return mensaje;
    }

    private int leerBandera(int[] datos) {
        int banderaLogitud = 0;
        System.out.printf("##### LSB Leyendo Bandera: ");
        for (int i = 0; i < 8; i++) {
            String binario = Integer.toBinaryString(datos[i]);
            int unBit = Integer.parseInt(binario.substring(binario.length() - 1, binario.length()));
            System.out.printf("%d", unBit);
            banderaLogitud += Math.pow(2, 7 - i) * unBit;
            // 2 elevado al indice multiplicado por el bit extraido
            //2^0 * 0 = 0
            //2^1 * 0 = 0
            //2^2 * 1 = 4
            //2^3 * 0 = 0
            //2^4 * 1 = 16
            // ...
            //2^8 * 0 = 0
            // Total: 20
        }
        System.out.println();
        return banderaLogitud;
    }

    private String leerCaracter(int[] datos, int indice) {
        // Crea un String con el numero binario de 8 bits que representa un caracter
        char valorAscci = '0';
        String bin = "";
        for (int i = 0; i < 8; i++){
            String binario = Integer.toBinaryString(datos[indice + i]);
            int unBit = Integer.parseInt(binario.substring(binario.length() - 1, binario.length()));
            valorAscci += Math.pow(2, i) * unBit;
            bin += unBit;
        }
        // Parsea la cadena a un entero y lo convierte a caracter en base a la tabla ASCCI
        String caracter = "" + ((char)Integer.parseInt(bin,2));
        return caracter;
    }

    private int leerInt(int[] datos, int indice) {
        // Crea un String con el numero binario de 8 bits que representa un caracter
        String bin = "";
        for (int i = 0; i < 8; i++){
            String binario = Integer.toBinaryString(datos[indice + i]);
            int unBit = Integer.parseInt(binario.substring(binario.length() - 1, binario.length()));
            bin += unBit;
        }
        // Parsea la cadena a un entero y lo convierte a caracter en base a la tabla ASCCI
        return Integer.parseInt(bin,2);
    }


    /**
     * Tomando la numeracion binaria:
     * 0: 0000000
     * 1: 0000001
     * 2: 0000010
     * 3: 0000011
     * 4: 0000100
     * 5: 0000101
     * 6: 0000110
     * 7: 0000111
     * Para almacenar un bit 0 como LSB, se debe regresar un numero par;
     * y para almacenar un 1, se debe regresar un numero impar
     */
    private int bitMenosSignificativo(int byt, int bitToHide){
		int lsb = byt & 1;
		if (lsb == bitToHide)
			return byt;
		else
			return byt ^ 1;
    }
    
}