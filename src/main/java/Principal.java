import com.apporiented.algorithm.clustering.*;
import com.apporiented.algorithm.clustering.visualization.DendrogramPanel;
import modelo.*;
import org.opencompare.hac.HierarchicalAgglomerativeClusterer;
import org.opencompare.hac.agglomeration.*;
import org.opencompare.hac.dendrogram.*;
import org.opencompare.hac.experiment.DissimilarityMeasure;
import org.opencompare.hac.experiment.Experiment;

import javax.swing.*;
import java.awt.*;

public class Principal {

    private static DendrogramNode dendrogramNode;

    public static void main(String[] args) {

        Configuracion config = new Configuracion();
        //config.cargarConfiguracion();
        config.getConfiguracion();

        Mediciones misMediciones =  new Mediciones();

        FileManager miFileManager = new FileManager();
        miFileManager.loadFile(misMediciones, config.getPath() + config.getFileProblema());

        misMediciones.completarMediciones();
        //System.out.println(misMediciones.toString());

        ClusteringAlgorithm alg = new DefaultClusteringAlgorithm();
        Cluster cluster = alg.performClustering(misMediciones.getMisMediciones(),
                misMediciones.getElementos(), new CompleteLinkageStrategy());
        DendrogramPanel dp = new DendrogramPanel();
        dp.setModel(cluster);

        DendrogramFrame(new JFrame(), cluster);
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
