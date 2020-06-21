package rc4;
import java.util.stream.IntStream;

public class AlgoritmoRc4 {

    // Metodo para cifrar texto con utilización de la llave.
    public int[] cifrarTexto(int[] textoPlano, int[] key) {
    
        System.out.println("Entro a cifrarTexto");
        // Se valida que la llave sea entre 5 y 32 bytes regresa  null si es incorrecto
        if (key.length < 5 || key.length > 32) {
            return null;
        }

        int[] ascciCaracteres = rc4(key);
        int[] textoCifrado = new int[textoPlano.length];
        int[] prga = ObteniendoPrga(ascciCaracteres, textoPlano.length);
        for (int i = 0; i < textoPlano.length; i++) {
            textoCifrado[i] = textoPlano[i] ^ prga[i];
        }
        return textoCifrado;
    }
    // Metodo que obtener la clave  utilizar en el cifrado.
    public static int[] obtenerKey(String msgClaro) {
        int[] caracteres = new int[msgClaro.length()];
        for (int i = 0; i < msgClaro.length();  i++) {
            caracteres[i] = msgClaro.charAt(i);
        }
        return caracteres;
    }

    // Metodo para convertir de un texto plano a binario
    public static int[] textoABinario(String msgClaro) {
        int[] bytes = new int[msgClaro.length()];
        for (int i = 0; i < msgClaro.length(); i++) {
            bytes[i] = msgClaro.charAt(i);
            System.out.printf("@@@@@@ Posicion: %3d, Caracter : '%s', Byte: %d\n", i, msgClaro.charAt(i), (int)msgClaro.charAt(i));
        }
        return bytes;
    }

    //Metodo pra convertir de una cadena binaria a texto.
    public static String binarioATexto(int[] bits) {
        String msg = "";
        for (int i = 0; i < bits.length; i++) {
            msg += (char)(bits[i]);
        }
        return msg;
    }

    // Metodo que solo btiene el rc4 de los datos.
    private int[] rc4(int[] key) {
        int[] S = IntStream.range(0, 256).toArray();
        int j = 0;
        int keylength = key.length;
        for (int i = 0; i < 256; i++) {
            j = (j + S[i] + key[i % keylength]) % 256;
            int temp = S[j];
            S[j] = S[i];
            S[i] = temp;
        }
        return S;
    }

    // Se mezclan los bits tomando la clave
    public int[] ObteniendoPrga(int [] S, int length){

        int i=0;
        int j=0;
        int [] prga = new int [length];

        for(int caracter=0; caracter<length; caracter++){
            i = (i + 1) % 256;
            j = (j + S[i]) % 256;

            int swap_var = S[i];
            S[i] = S[j];
            S[j] = swap_var;

            prga[caracter] = S[(S[i] + S[j]) % 256];
        }
        return prga;
    }
}