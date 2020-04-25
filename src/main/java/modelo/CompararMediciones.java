package modelo;

import org.opencompare.hac.experiment.DissimilarityMeasure;
import org.opencompare.hac.experiment.Experiment;

public class CompararMediciones implements DissimilarityMeasure {

    private double miComputeDissimilarity(Mediciones experiment, int i, int i1) {
        return experiment.getRelacionMediciones(i,i1);
    }

    @Override
    public double computeDissimilarity(Experiment experiment, int i, int i1) {
        return this.miComputeDissimilarity((Mediciones) experiment,i,i1);
    }
}
