package modelo;

import com.apporiented.algorithm.clustering.Cluster;

import java.io.*;

public class ListadorLavados {

    private Configuracion config;
    private OutputStream fileOut;

    public ListadorLavados() throws FileNotFoundException {
        this.config = Configuracion.getConfiguracion();
        this.fileOut = new FileOutputStream(new File(config.PATH_OUT + config.FILE_OUT));
    }

    // comienza el recorrido Inorden
    public synchronized void recorridoInorden(Cluster cluster, int nLavado) throws IOException {
        ayudanteInorden(cluster, nLavado);
    }

    // metodo recursivo para recorrido inorden
    private void ayudanteInorden( Cluster cluster, int nLavado) throws IOException {
        if ( cluster.getDistance().getDistance() == 0.0 ) {
            this.imprimir(cluster, nLavado);
            return;
        }

        ayudanteInorden(cluster.getChildren().get(1), nLavado);
        ayudanteInorden(cluster.getChildren().get(0), nLavado);
    }

    private void imprimir(Cluster cluster, int nLavado) throws IOException {
        System.out.println(cluster.getName() + this.config.SEPARADOR_PARSER + nLavado);
        this.fileOut.write((cluster.getName() + this.config.SEPARADOR_PARSER + nLavado + "\n").getBytes());
    }

}
