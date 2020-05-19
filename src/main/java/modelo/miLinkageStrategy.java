package modelo;

import com.apporiented.algorithm.clustering.Distance;
import com.apporiented.algorithm.clustering.LinkageStrategy;

import java.util.Collection;

public class miLinkageStrategy implements LinkageStrategy {
    @Override
    public Distance calculateDistance(Collection<Distance> distances) {
        double max = Double.NaN;

        for (Distance dist : distances) {
            if (Double.isNaN(max) || dist.getDistance() > max)
                max = dist.getDistance();
        }

        return new Distance(max);
    }
}
