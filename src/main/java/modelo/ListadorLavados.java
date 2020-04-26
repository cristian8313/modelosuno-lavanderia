package modelo;

import com.apporiented.algorithm.clustering.Cluster;

import java.io.*;

public class ListadorLavados {

    private Cluster cluster;
    private int nLavado;
    private Configuracion config;
    private OutputStream fileOut;


    public ListadorLavados(Cluster cluster) throws FileNotFoundException {
        this.cluster = cluster;
        this.nLavado = 1;
        this.config = Configuracion.getConfiguracion();
        this.fileOut = new FileOutputStream(new File(config.PATH_OUT + config.FILE_OUT));
    }

    private void nuevoLavado(Cluster cluster) {
        if ( cluster.getDistance().getDistance() == this.config.HORA_MAXIMA )
            this.nLavado++;
    }

    // comienza el recorrido Preorden
    public synchronized void recorridoPreorden() throws IOException {
        ayudantePreorden(this.cluster);
    }

    // metodo recursivo para recorrido en preorden
    private void ayudantePreorden(Cluster cluster) throws IOException {
        if ( cluster.getDistance().getDistance() == 0.0 ) {
            System.out.println(cluster.getName() + this.config.SEPARADOR_PARSER + this.nLavado);
            return;
        }

        this.imprimir(cluster);
        ayudantePreorden(cluster.getChildren().get(1));   //recorre subarbol izquierdo
        ayudantePreorden(cluster.getChildren().get(0));   //recorre subarbol derecho
    }

    // comienza el recorrido Inorden
    public synchronized void recorridoInorden() throws IOException {
        ayudanteInorden(this.cluster);
    }

    // metodo recursivo para recorrido inorden
    private void ayudanteInorden( Cluster cluster) throws IOException {
        if ( cluster.getDistance().getDistance() == 0.0 ) {
            this.imprimir(cluster);
            return;
        }

        ayudanteInorden(cluster.getChildren().get(1));
        this.nuevoLavado(cluster);
        ayudanteInorden(cluster.getChildren().get(0));
    }

    // comienza el recorrido Posorden
    public synchronized void recorridoPosorden() throws IOException {
        ayudantePosorden(this.cluster);
    }

    // metodo recursivo para recorrido posorden
    private void ayudantePosorden(Cluster cluster) throws IOException {
        if ( cluster.getDistance().getDistance() == 0.0 ) {
            this.imprimir(cluster);
            return;
        }

        ayudantePosorden(cluster.getChildren().get(1));
        ayudantePosorden(cluster.getChildren().get(0));
        this.nuevoLavado(cluster);
    }

    private void imprimir(Cluster cluster) throws IOException {
        System.out.println(cluster.getName() + this.config.SEPARADOR_PARSER + this.nLavado);
        this.fileOut.write((cluster.getName() + this.config.SEPARADOR_PARSER + this.nLavado + "\n").getBytes());
    }

}
