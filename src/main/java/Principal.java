import com.apporiented.algorithm.clustering.*;
import com.apporiented.algorithm.clustering.visualization.DendrogramPanel;
import modelo.*;
import org.opencompare.hac.dendrogram.*;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Principal {

    private static DendrogramNode dendrogramNode;

    public static void main(String[] args) {

        Configuracion config = Configuracion.getConfiguracion();

        Mediciones misMediciones =  new Mediciones();

        FileManager miFileManager = new FileManager();
        miFileManager.loadFile(misMediciones, config.PATH_INPUT + config.FILE_PROBLEMA);

        misMediciones.completarMediciones();

        ClusteringAlgorithm alg = new DefaultClusteringAlgorithm();

        try {
            int nLavado = 1;
            ListadorLavados listadorLavados = new ListadorLavados();
            for (Cluster c : alg.performFlatClustering(misMediciones.getMisMediciones(),
                    misMediciones.getElementos(), new miLinkageStrategy(), 20.00))
                listadorLavados.recorridoInorden(c, nLavado++);
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*Cluster cluster = alg.performClustering(misMediciones.getMisMediciones(),
         misMediciones.getElementos(), new CompleteLinkageStrategy());

        DendrogramPanel dp = new DendrogramPanel();
        dp.setModel(cluster);
        DendrogramFrame(new JFrame(), cluster);*/
    }

    private static void DendrogramFrame(JFrame frame, Cluster cluster) {
        frame.setSize(500, 400);
        frame.setLocation(100, 200);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel content = new JPanel();
        DendrogramPanel dp = new DendrogramPanel();

        frame.setContentPane(content);
        content.setBackground(Color.red);
        content.setLayout(new BorderLayout());
        content.add(dp, BorderLayout.CENTER);
        dp.setBackground(Color.WHITE);
        dp.setLineColor(Color.BLACK);
        dp.setScaleValueDecimals(0);
        dp.setScaleValueInterval(1);
        dp.setShowDistances(false);

        dp.setModel(cluster);
        frame.setVisible(true);
    }
}
