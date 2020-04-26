package modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.*;

public class ConsumerDataFIle implements Consumer {

    private Mediciones mediciones;

    public ConsumerDataFIle (Mediciones mediciones) {
        this.mediciones = mediciones;
    }

    private void saveData(String s) {
        //System.out.println(s);
        Configuracion config = Configuracion.getConfiguracion();
        ParserLineFile parserLineFile = new ParserLineFile();
        parserLineFile.parseredLine(s);

        if (parserLineFile.isProblema()) {
            this.mediciones.setDimension(parserLineFile.getCantMediciones());
        }

        if(parserLineFile.isIncompatibilidad()) {
            this.mediciones.agregarMedicion(parserLineFile.getPrenda(),
                    parserLineFile.getPrendaIncompatible(), config.HORA_MAXIMA);

            this.mediciones.agregarMedicion(parserLineFile.getPrendaIncompatible(),
                    parserLineFile.getPrenda(), config.HORA_MAXIMA);
        }

        if(parserLineFile.isTiempoLavado()) {
            this.mediciones.agregarMedicion(parserLineFile.getPrenda(),
                    parserLineFile.getPrenda(), parserLineFile.getTiempoLavado());
        }
    }

    @Override
    public void accept(Object o) {
        saveData((String) o);
    }
}
