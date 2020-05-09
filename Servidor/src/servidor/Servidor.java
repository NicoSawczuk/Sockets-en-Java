/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import modelo.Hilo;
import modelo.ServidorAuxiliar;


/**
 *
 * @author kachu
 */
public class Servidor {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ClassNotFoundException {
        
        if (args.length != 0) {
            int puerto = 0;
            try {
                puerto = Integer.parseInt(args[0]);
            } catch (Exception e) {
                System.out.println("Por favor envie como parametro un numero no una cadena");
                System.exit(0);
            }

            try {
                ServerSocket svc = new ServerSocket(puerto);
                try {
                    ServidorAuxiliar serviAux = new ServidorAuxiliar();
                    while(true){                        
                        Socket req = svc.accept();
                        Hilo hilo = new Hilo(req, serviAux) ;
                        hilo.start();
                    }
                } catch (IOException ex) {
                    System.out.println("Error al esperar por conexiones");
                }
            } catch (IOException ex) {
                System.out.println("Error al crear socket");
            }
        } else {
            System.out.println("Debe pasar como parametro un puerto por favor");
            System.exit(0);
        }

    }
    
    

}
