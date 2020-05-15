/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 *
 * @author nico2
 */
public class ServidorAuxiliar {

    private ArrayList<ArrayList> retraso; //va a tener el formato [[nombreNodo, tiempoEnvio, tiempoLlegada]]
    private ArrayList<ArrayList> anchoBanda;

    public ServidorAuxiliar() {
    }

    public ArrayList<ArrayList> getRetraso() {
        return retraso;
    }

    public void setRetraso(ArrayList<ArrayList> retraso) {
        this.retraso = retraso;
    }

    public ArrayList<ArrayList> getAnchoBanda() {
        return anchoBanda;
    }

    public void setAnchoBanda(ArrayList<ArrayList> anchoBanda) {
        this.anchoBanda = anchoBanda;
    }

    public void exportarRetraso(String nombreNodo, Long minimo, Long maximo, Long promedio) throws FileNotFoundException {
        BufferedWriter bw = null;
        FileWriter fw = null;

        while (true) {
            try {
                String data = "";
                data = nombreNodo + ',' + minimo + ',' + promedio + ',' + maximo + ',' + '\n';
                File file = new File("/resultados/resultados_retraso.csv");
                // Si el archivo no existe, se crea!

                if (!file.exists()) {
                    PrintWriter pw = new PrintWriter(file);
                    StringBuilder sb = new StringBuilder();

                    //Cabecera
                    sb.append("Nombre nodo");
                    sb.append(',');
                    sb.append("Tiempo minimo");
                    sb.append(',');
                    sb.append("Tiempo promedio");
                    sb.append(',');
                    sb.append("Tiempo maximo");
                    sb.append(',');
                    sb.append('\n');

                    pw.append(sb.toString());
                    pw.close();
                }
                // flag true, indica adjuntar información al archivo.
                fw = new FileWriter(file.getAbsoluteFile(), true);
                bw = new BufferedWriter(fw);
                bw.write(data);
                break;

            } catch (IOException e) {
            }
        }

        try {
            //Cierra instancias de FileWriter y BufferedWriter
            if (bw != null) {
                bw.close();
            }
            if (fw != null) {
                fw.close();
            }
        } catch (IOException ex) {
        }
    }

    public void exportarAnchoBanda(String nombreNodo, long tiempoTotal, int cantEventos) {
        BufferedWriter bw = null;
        FileWriter fw = null;

        while (true) {
            try {
                String data = "";
                data = nombreNodo + ',' + cantEventos + ',' + tiempoTotal + '\n';
                File file = new File("/resultados/resultados_ancho_banda.csv");
                // Si el archivo no existe, se crea!
                if (!file.exists()) {
                    PrintWriter pw = new PrintWriter(file);
                    StringBuilder sb = new StringBuilder();

                    //Cabecera
                    sb.append("Nombre nodo");
                    sb.append(',');
                    sb.append("Cantidad de eventos");
                    sb.append(',');
                    sb.append("Tiempo total");
                    sb.append(',');
                    sb.append('\n');

                    pw.append(sb.toString());
                    pw.close();
                }
                // flag true, indica adjuntar información al archivo.
                fw = new FileWriter(file.getAbsoluteFile(), true);
                bw = new BufferedWriter(fw);
                bw.write(data);
                break;

            } catch (IOException e) {
            }
        }
        try {
            //Cierra instancias de FileWriter y BufferedWriter
            if (bw != null) {
                bw.close();
            }
            if (fw != null) {
                fw.close();
            }
        } catch (IOException ex) {
        }
    }

}
