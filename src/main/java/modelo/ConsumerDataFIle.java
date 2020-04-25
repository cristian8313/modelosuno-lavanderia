package modelo;

import java.util.function.*;
import org.jgrapht.*;
import org.jgrapht.generate.*;
import org.jgrapht.graph.*;
import org.jgrapht.traverse.*;
import org.jgrapht.util.*;

public class ConsumerDataFIle implements Consumer {

    private Graph<Vertice, DefaultEdge> directedGraph;

    public ConsumerDataFIle () {
        this.directedGraph = new DefaultDirectedGraph<Vertice, DefaultEdge>(DefaultEdge.class);
    }

    private void saveData(String s) {
        //System.out.println(s);
        ParserLineFile parserLineFile = new ParserLineFile();
        parserLineFile.parseredLine(s);

        if(parserLineFile.isProblema()) {

        }

        if(parserLineFile.isIncompatibilidad()) {

        }

        if(parserLineFile.isTiempoLavado()) {
            this.directedGraph.addVertex(parserLineFile.getVertice());
        }
    }

    @Override
    public void accept(Object o) {
        saveData((String) o);
    }
}
