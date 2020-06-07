import modelo.*;

import java.io.*;


public class Principal {

    public static void main(String[] args) throws IOException {

        //final long startTime = System.currentTimeMillis();

        Configuracion config = Configuracion.getConfiguracion();

        Experimento miExperimento =  new Experimento();

        FileManager miFileManager = new FileManager();
        miFileManager.loadFile(miExperimento, config.PATH_INPUT + config.FILE_PROBLEMA);

        /*final long endTime = System.currentTimeMillis();
        final long elapsed = endTime - startTime;
        System.out.println("Clustering took " + (double) elapsed/1000 + " seconds");*/

        Heuristica miHeuristica = new Heuristica(miExperimento);
        miHeuristica.runHeuritica();

        try {
            OutputStream fileOut = null;
            fileOut = new FileOutputStream(new File(config.PATH_OUT + config.FILE_OUT));

            miHeuristica.imprimirLavados(fileOut);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println(miHeuristica.toString());
        System.out.println("Resultado: " + miHeuristica.getTiempoTotal());
        System.out.println("Cota inferior: " + miHeuristica.cotaInferior());
    }
}
