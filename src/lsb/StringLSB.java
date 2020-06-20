package lsb;

import java.util.Arrays;

public class StringLSB {

    private int[] datos; //datos de bytes
    private String mensaje;

    public StringLSB(int[] datos, String mensaje) {
        this.datos = datos;
        this.mensaje = mensaje;
    }

    public boolean siCabe() {
        return true || (datos.length / 8) <= mensaje.length();
    }

    public int[] obtenerBytesMensajes(){
        /**
         * "B        r        e        n        d        a"
         *  01011010 01010010 01111010 01011011 01011011 01011011
         * bitsBandera: 00000110
         */
        int[] bitsMensaje = new int[mensaje.length() * 8 + 8];
        int longitudMensaje = mensaje.length();
        String bitsBandera = Integer.toBinaryString(longitudMensaje);
        int indiceBitsMensaje = 0;
        // Rellenar primer byte con bandera de numero de caracteres
        for (int n = 0; n < 8 - bitsBandera.length(); n ++) {
            bitsMensaje[indiceBitsMensaje] = 0;
            indiceBitsMensaje ++;
        }
        for (int i = 0; i < bitsBandera.length(); i ++){
            bitsMensaje[indiceBitsMensaje] = Integer.parseInt(bitsBandera.substring(i, i + 1));
            indiceBitsMensaje ++;
        }
        // Rellenar desde segundo byte el mensaje a ocultar
        for (int i=0; i < mensaje.length(); i ++){
            String binario = Integer.toBinaryString(mensaje.charAt(i));
            for (int n = 0; n < 8 - binario.length(); n ++) {
                bitsMensaje[indiceBitsMensaje] = 0;
                indiceBitsMensaje ++;
            }
            for (int j=0; j < binario.length(); j ++) {
                bitsMensaje[indiceBitsMensaje] = Integer.parseInt(binario.substring(j, j + 1));
                indiceBitsMensaje ++;
            }
        }
        return bitsMensaje;
    }

    /**
     * Proceso completo de ocultamiento
     */
    public int[] ocultarBytesConLSB() {
        // Obtener longitud del mensaje
        int longitudMensaje = mensaje.length();
        // Escribir la bandera en un byte
        int[] bandera = generarBandera(longitudMensaje);
        escribirBanders(bandera, datos);
        System.out.printf("Bandera: %d, binario: %s, Mensaje a escribir: %s\n", longitudMensaje, Arrays.toString(bandera), mensaje);
        // Escribir el mensaje
        for (int i = 0; i < longitudMensaje; i++){
            // Generar indice de escritura para cada caracter
            int indice = 8 + (i * 8);
            escribirCaracterUnico(mensaje.charAt(i), datos, indice);
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
    private void escribirCaracterUnico(char caracter, int[] datos, int indice) {
        int[] bitsCaracter = generarBitsCaracter(caracter);
        System.out.printf("@@@@@@ Posicion: %3d, Caracter Escrito: '%s', Byte escrito: %s\n", indice, caracter, Arrays.toString(bitsCaracter));
        for (int i = 0; i < 8; i ++) {
            datos[indice + i] = bitMenosSignificativo(datos[indice + i], bitsCaracter[i]);
        }
    }
    private int[] generarBitsCaracter(char caracter) {
        String binario = Integer.toBinaryString(caracter);
        // Rellenar con ceros a la izquierda
        for (int i = 0; i < 9 - binario.length(); i++) {
            binario = "0" + binario;
        }
        // Extraer cada caracter y guardarlo como entero en el arreglo a retornar
        int[] bitsCaracter = new int[8];
        for (int i = 0; i < 8; i++) {
            bitsCaracter[i] = Integer.parseInt(binario.substring(i, i + 1));
        }
        return bitsCaracter;
    }

    /**
     * Proceso completo de lectura de mensaje.
     */
    public String mostrarBytesConLSB() {
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

    private int leerBandera(int[] datos) {
        int banderaLogitud = 0;
        System.out.printf(">>>>> Leyendo Bandera: ");
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


