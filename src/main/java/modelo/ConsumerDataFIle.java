package modelo;

import java.util.function.*;

public class ConsumerDataFIle implements Consumer {

    private Experimento mediciones;

    public ConsumerDataFIle (Experimento mediciones) {
        this.mediciones = mediciones;
    }

    private void saveData(String s) {
        Configuracion config = Configuracion.getConfiguracion();
        ParserLineFile parserLineFile = new ParserLineFile();
        parserLineFile.parseredLine(s);

        if (parserLineFile.isProblema()) {
            //this.mediciones.setDimension(parserLineFile.getCantMediciones());
        }

        if(parserLineFile.isIncompatibilidad()) {
            this.mediciones.agregarMedicionIncompatible(parserLineFile.getPrenda(),
                    parserLineFile.getPrendaIncompatible(), config.HORA_MAXIMA);

            this.mediciones.agregarMedicionIncompatible(parserLineFile.getPrendaIncompatible(),
                    parserLineFile.getPrenda(), config.HORA_MAXIMA);
        }

        if(parserLineFile.isTiempoLavado()) {
            this.mediciones.agregarMedicionPrenda(parserLineFile.getPrenda(), parserLineFile.getTiempoLavado());
        }
    }

    @Override
    public void accept(Object o) {
        saveData((String) o);
    }
}
