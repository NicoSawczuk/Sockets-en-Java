package sistema;


import java.io.*;
import java.net.*;
import modelo.*;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase inicial
 * @author cbiale
 */
public class Nodo {

    /**
     * 
     * @param args argumentos de la línea de comandos
     */
    public static void main(String[] args) {
        //Argumentos: nombreNodo, velocidadEventos, host, puerto
        //Validamos los argumentos
        int puerto =0;
        double velocidad =0;
        if (args.length == 4){
            try{
               puerto = Integer.parseInt(args[3]);
            }
            catch (Exception e){
                System.out.println("Debe pasar un entero como puerto");
                }
            try {
                velocidad = Double.parseDouble(args[1]);
            } catch (Exception e) {
                System.out.println("Debe pasar un double como velocidad");
            }
        }else{
            System.out.println("Error en argumentos: debe pasar nombreNodo, velocidadEventos, host, puerto");
            System.exit(0);
        }
        
        String nodo = args[0];
        String ip = args[2];
        
        
        try {
            Socket s = new Socket(ip, puerto);  
            OutputStream os = s.getOutputStream();
            ObjectOutput alServidor = new ObjectOutputStream(os);
            
            Generador generador = new Generador(velocidad, nodo, alServidor);
            generador.iniciar();

            try {
                alServidor.close();
                s.close();
            } catch (IOException e) {
                System.out.println("Error al desconectarse");
            }

        } catch (IOException ex) {
            System.out.println("Error al conectarse al servidor");
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Nodo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(Nodo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
