package lsb;

public class StringLSB {

    private byte[] datos; //datos de bytes
    private String mensaje;

    public StringLSB(byte[] datos, String mensaje) {
        this.datos = datos;
        this.mensaje = mensaje;
    }

    public boolean siCabe() {
        return ((int)(datos.length / 8)) <= mensaje.length();
    }

    public int[] obtenerBytesMensajes(){
        int[] bitsMensaje = new int[mensaje.length() * 8];
        int indiceBitsMensaje = 0;
        for (int i=0; i < mensaje.length(); i ++){
            String binario = Integer.toBinaryString(mensaje.charAt(i));
            for (int n = 0; n < 8 - binario.length(); n ++) {
                bitsMensaje[indiceBitsMensaje] = 0;
                indiceBitsMensaje ++;
            }
            for (int j=0; j < binario.length(); j ++) {
                bitsMensaje[indiceBitsMensaje] = Integer.parseInt(binario[j]);
                indiceBitsMensaje ++;
            }
        }
        return bitsMensaje;
    }

    /**
     * Archivo de prueba original con huella: 8b113b2b4c8b1f6bca5a72701fb1318b
     */
    public byte[] obtenerBytesLSB() {
        byte[] copiaDatos = new byte[datos.length()];
        byte[] bitsMensaje = obtenerBytesMensajes();
        for (int i = 0; i < copiaDatos.length(); i++){
            copiaDatos[i] = bitMenosSignificativo(datos[i], bitsMensaje[i]);
        }
        for (int i = copiaDatos.length(); i < datos.length(); i++){
            copiaDatos[i] = datos[i];
        }
        return copiaDatos;
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
    public int bitMenosSignificativo(int unByte, int unBit){
        if (unByte % 2 == 0) { // Entra si es un numero par
            if (unBit == 0) { // Regresar el mismo numero
                return unByte;
            } else { // Regresar el numero restado o sumado con 1
                return unByte > 0 ? unByte - 1 : unByte + 1;
            }
        }else 
        if(unByte % 2 == 1){
            if (unBit == 1){
                return unByte;
            }else{
                return unByte > 1 ? unByte - 1 : unByte + 1;
            }
        }
        return 0;
    }
}