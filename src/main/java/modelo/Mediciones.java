package modelo;

public class Mediciones {

    private String[] elementos;
    private double[][] misMediciones;

    public void agregarMedicion(int x, int y, double medicion) {
        this.misMediciones[x][y] = medicion;
    }

    public double getRelacionMediciones(int i, int i1) {
        return this.misMediciones[i][i1];
    }

    public double[][] getMisMediciones() {
        return misMediciones;
    }

    public String[] getElementos() {
        return elementos;
    }

    public void setDimension(int dimension) {
        this.elementos = new String[dimension];
        this.misMediciones = new double[dimension][dimension];
        int j = 0;
        for (double[] medicionesPrenda : this.misMediciones) {
            this.elementos[j++] = String.valueOf(j);
            for (int i = 0; i < this.misMediciones.length; i++)
                medicionesPrenda[i] = 0.0;
        }
    }

    public void completarMediciones() {
        // es simetrica se puede optimizar
        for (int i = 0; i < this.misMediciones.length; i++) {
            double tLavado = this.misMediciones[i][i];
            for (int j = 0; j < this.misMediciones[i].length; j++) {
                double t2Lavado = this.misMediciones[j][j];
                double t3Lavado = this.misMediciones[i][j];
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

        //diagonales distancia cero
        for (int i = 0; i < this.misMediciones.length; i++) {
            this.misMediciones[i][i] = 0.0;
        }
    }

    @Override
    public String toString() {
        Configuracion config = Configuracion.getConfiguracion();
        System.out.println("length: " + this.misMediciones.length);
        String matriz = new String();

        for (double[] l : this.misMediciones) {
            for (double dist : l) {
                //if (l[i] == Double.MAX_VALUE) matriz += "I";
                if (dist == config.HORA_MAXIMA) matriz += "I";
                else { matriz += dist; }
                matriz += " ";
            }
            matriz += "\n";
        }

        return matriz;
    }
}
