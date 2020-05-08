package modelo;


import java.io.IOException;
import java.io.ObjectOutput;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Random;

import org.apache.commons.math3.distribution.ExponentialDistribution;

/**
 * Generador de logs
 * @author cbiale
 */
public class Generador {
    private final double velocidad;
    private final String nombreNodo;
    private long tiempoArranque = System.currentTimeMillis();
    private long tiempoRecalculado;
    private final ObjectOutput alServidor;
    /**
     * Constructor del generador de logs
     * @param velocidad velocidad del generador
     */
    public Generador(double velocidad, String nombreNodo, ObjectOutput alServidor) {
        this.velocidad = velocidad;
        this.nombreNodo = nombreNodo;
        this.alServidor = alServidor;
    }

    /**
     * Inicia el generador de logs
     * @throws NoSuchAlgorithmException
     * @throws InterruptedException
     */
    public void iniciar() throws NoSuchAlgorithmException, InterruptedException, IOException {
        int cantidad = 0;
        alServidor.writeObject(this.nombreNodo);
        do{
            cantidad++;
            // tiempo
            long tiempo = System.currentTimeMillis();
            this.tiempoRecalculado = tiempo;
            // sha 256
            MessageDigest texto = MessageDigest.getInstance("SHA-256");
            // arreglo de 20 bytes
            byte[] arreglo = new byte[20];
            // cargo con valores
            new Random().nextBytes(arreglo);
            // obtengo la cadena de texto del arreglo
            String cadena = new String(arreglo, StandardCharsets.UTF_8);
            // obtengo el hash de la cadena
            byte[] hash = texto.digest(cadena.getBytes(StandardCharsets.UTF_8));
            // tiempo a esperar
            long esperar = valor();
            // imprimo valores (Prueba)
            String val = tiempo + " " + mostrarHexadecimal(hash) + " " + esperar ;
            alServidor.writeObject(val);
            alServidor.flush();            
            Thread.sleep(esperar);
        }
        while ((this.tiempoRecalculado - this.tiempoArranque) < 100000 );
    }
    
    /**
     * Pasa el resultado del hash a hexadecimal
     * @param hash arreglo de bytes con un valor hash
     * @return String en hexadecimal
     */
    private static String mostrarHexadecimal (byte[] hash) {
        StringBuilder resultado = new StringBuilder();
        for (int i = 0; i < hash.length; i++) {
            String valor = Integer.toHexString(0xff & hash[i]);
            if(valor.length() == 1) {
                resultado.append('0');
            }
            resultado.append(valor);
        }
        return resultado.toString();
    }
    
    /**
     * Obtiene un valor basado en una distribución exponencial
     * @return valor a esperar
     */
    private long valor() {
       double ejemplo = new ExponentialDistribution (1 / this.velocidad).sample() * 1000;
//       var ejemplo = distribucion.sample() * 1000;
       return (long) ejemplo;
    }
    
}
