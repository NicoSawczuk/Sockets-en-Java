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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kachu
 */
public class Hilo extends Thread {

    private Socket req;

    public Hilo(Socket req) {
        this.req = req;

    }

    @Override
    public void run() {
        InputStream is = null;
        try {
            is = this.req.getInputStream();
            ObjectInput o = new ObjectInputStream(is);
            String nombreNodo = (String) o.readObject();

            System.out.println(Long.toString(System.currentTimeMillis()) + " - " + nombreNodo + " conectado");
            while (true) {
                try {
                    String str = (String) o.readObject();
                    
                    System.out.println(str);

                } catch (Exception e) {
                    o.close();
                    is.close();
                    req.close();
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
