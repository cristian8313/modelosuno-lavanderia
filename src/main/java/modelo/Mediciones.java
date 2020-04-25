package modelo;

import org.opencompare.hac.experiment.Experiment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Mediciones implements Experiment {
    private Integer[][] misMediciones;

    public Mediciones() {
        this.misMediciones = new Integer[20][20];
        for (Integer[] l : this.misMediciones) {
            for (int i = 0; i < 20; i++) {
                l[i] = 0;
            }
        }
    }

    @Override
    public int getNumberOfObservations() {
        return this.misMediciones.length;
    }

    public void agregarMedicion(int x, int y, Integer medicion) {
        this.obtenerMediciones(x)[y] = medicion;
    }

    private Integer[] obtenerMediciones(int y) {
        return this.misMediciones[y];
    }
    
    public void completarMediciones() {
        // es simetrica se puede optimizar
        for (int i = 0; i < this.misMediciones.length; i++) {
            int tLavado = this.misMediciones[i][i];
            for (int j = 0; j < this.misMediciones[i].length; j++) {
                int t2Lavado = this.misMediciones[j][j];
                int t3Lavado = this.misMediciones[i][j];
                if (t3Lavado < t2Lavado ||
                    t3Lavado < tLavado) {
                    if (tLavado > t2Lavado)
                        this.misMediciones[i][j] = tLavado;
                    else {
                        this.misMediciones[i][j] = t2Lavado;
                    }
                }
            }
        }
    }

    @Override
    public String toString() {
        System.out.println("length: " + this.misMediciones.length);
        String matriz = new String();

        for (Integer[] l : this.misMediciones) {
            for (int i = 0; i < 20; i++) {
                if (l[i] == Integer.MAX_VALUE) {
                    matriz += "I";
                }
                else {
                    matriz += String.valueOf(l[i]);
                }

                matriz += " ";
            }
            matriz += "\n";
        }

        return matriz;
    }

    public double getRelacionMediciones(int i, int i1) {
        return this.misMediciones[i][i1];
    }
}
