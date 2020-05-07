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
        System.out.println("Ingrese la velocidad: ");
        double velocidad ;
        Scanner lectura0 = new Scanner(System.in) ;
        velocidad = lectura0.nextInt();

        System.out.println("Ingrese eventos: ");
        int cantEventos ;
        Scanner lectura = new Scanner(System.in) ;
        cantEventos = lectura.nextInt();
        
        System.out.println("Ingrese Puerto: ");
        int puerto ;
        Scanner lectura2 = new Scanner(System.in) ;
        puerto = lectura2.nextInt();
        
        System.out.println("Ingrese Nombre de Nodo: ");
        String nodo ;
        Scanner lectura3 = new Scanner(System.in) ;
        nodo = lectura3.nextLine();
       
        
        
        
        try {
            Socket s = new Socket("localhost", puerto);  
            OutputStream os = s.getOutputStream();
            ObjectOutput alServidor = new ObjectOutputStream(os);
            
            Generador generador = new Generador(velocidad, cantEventos, nodo, alServidor);
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
