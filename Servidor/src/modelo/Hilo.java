/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kachu
 */
public class Hilo extends Thread {

    private Socket req;
    private ServidorAuxiliar serviAux;

    public Hilo(Socket req, ServidorAuxiliar serviAux) {
        this.req = req;
        this.serviAux = serviAux;
    }

    @Override
    public void run() {
        InputStream is = null;
        ArrayList lista = new ArrayList();
        long minimo = 0;
        long maximo = 0;
        long promedio = 0;
        long tiempoInicio = System.currentTimeMillis();
        try {
            is = this.req.getInputStream();
            ObjectInput o = new ObjectInputStream(is);
            String nombreNodo = (String) o.readObject();

            System.out.println(tiempoInicio + " - " + nombreNodo + " conectado");
            while (true) {
                try {
                    long tiempo = (Long) o.readObject();
                    long tiempoLlegada = System.currentTimeMillis();
                    String evento = (String) o.readObject();
                    
                    System.out.println(tiempo+" "+nombreNodo+" "+evento);
                    
                    
                    lista.add(tiempoLlegada - tiempo);

                    
                    minimo=maximo=(long) lista.get(0);
                    for (int i =0; i< lista.size()-1; i++){
                        if(minimo>(long) lista.get(i))
                        {
                                minimo=(long) lista.get(i);
                        }
                        if(maximo<(long) lista.get(0))
                        {
                                maximo=(long) lista.get(i);
                        }

                    }
                    for (int i=0; i < lista.size()-1; i++) {
                        promedio = (Long) lista.get(i) + promedio;
                    }
                    promedio = promedio/lista.size();

                    
                } catch (Exception e) {
                    long tiempoFinal = System.currentTimeMillis();
                    o.close();
                    is.close();
                    req.close();
                    //serviAux.exportarDatos(nombreNodo);
                    serviAux.exportarRetraso(nombreNodo, minimo, maximo, promedio);
                    serviAux.exportarAnchoBanda(nombreNodo,(tiempoFinal-tiempoInicio), lista.size());
                    System.out.println(Long.toString(System.currentTimeMillis()) + " - " + nombreNodo + " desconectado");
                    break;
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Hilo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Hilo.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                is.close();
            } catch (IOException ex) {
                Logger.getLogger(Hilo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
