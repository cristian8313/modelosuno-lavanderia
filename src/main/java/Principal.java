import modelo.*;
import org.opencompare.hac.HierarchicalAgglomerativeClusterer;
import org.opencompare.hac.agglomeration.AgglomerationMethod;
import org.opencompare.hac.agglomeration.CompleteLinkage;
import org.opencompare.hac.dendrogram.*;
import org.opencompare.hac.experiment.DissimilarityMeasure;
import org.opencompare.hac.experiment.Experiment;

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
        System.out.println(misMediciones.toString());

        DissimilarityMeasure dissimilarityMeasure = new CompararMediciones();
        AgglomerationMethod agglomerationMethod = new CompleteLinkage();
        DendrogramBuilder dendrogramBuilder = new DendrogramBuilder(misMediciones.getNumberOfObservations());
        HierarchicalAgglomerativeClusterer clusterer = new HierarchicalAgglomerativeClusterer(misMediciones, dissimilarityMeasure, agglomerationMethod);
        clusterer.cluster(dendrogramBuilder);
        Dendrogram dendrogram = dendrogramBuilder.getDendrogram();

        dumpNode("--", dendrogram.getRoot());


    }

    private static void dumpNode(final String indent, final DendrogramNode node) {
        if (node == null) {
            System.out.println(indent + "<null>");
        } else if (node instanceof ObservationNode) {
            int n = ((ObservationNode) node).getObservation() + 1;
            System.out.println(indent + "Observation: " + n);
        } else if (node instanceof MergeNode) {
            System.out.println(indent + "Merge:");
            dumpNode(indent + indent, ((MergeNode) node).getLeft());
            dumpNode(indent + indent, ((MergeNode) node).getRight());
        }
    }
}
