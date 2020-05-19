package modelo;

import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

public class Heuristica {

    int nLavado = 0;
    private ArrayList<Lavado> lavados;
    private Experimento experimento;
    private double umbral = 4.0;


    public Heuristica(Experimento experimento) {
        this.experimento = experimento;
        this.lavados = new ArrayList<Lavado>();
    }

    public void runHeuritica() {
        Iterator i = this.experimento.getPrendasSort().entrySet().iterator();

        while (i.hasNext()) {
            Map.Entry m = (Map.Entry) i.next();
            Double tiempo = (Double) m.getKey();

            ArrayList<String> vPrendas = (ArrayList<String>) m.getValue();

            Iterator it = vPrendas.iterator();
            while (it.hasNext())
                asignarLavados(tiempo, (Prenda) it.next());
        }
    }

    private void asignarLavados(double tiempo, Prenda prenda) {

        Iterator it = this.lavados.iterator();
        boolean asignar = true;

        while (it.hasNext() && asignar) {
            Lavado l = (Lavado) it.next();
            if (Math.abs(l.getTiempoLavado() - tiempo) <= this.umbral && !l.getPrendas().contains(prenda)) {
                for (Prenda prendaLavado : l.getPrendas()) {
                    if (this.experimento.getIncompatibles().get(prenda.getIdPrenda()).contains(prendaLavado.getIdPrenda())) {
                        // tengo que asignar por tiempo
                        // sumando uno al tiempo de la prenda que quiero asignar
                        // recursivo(tiempo + 1, prenda);
                        asignar = false;
                        break;
                    }
                }
                if (asignar) {
                    l.agregarPrenda(prenda);
                    l.setTiempoLavado(Math.max(l.getTiempoLavado(), tiempo));
                    asignar = false;
                }
                else asignar = true;
            }
        }

        if (asignar) {
            Lavado l2 = new Lavado(++nLavado, tiempo, new HashSet<Prenda>());
            l2.agregarPrenda(prenda);
            this.lavados.add(l2);
        }
    }

    private ArrayList<Lavado> getLavados() {
        return lavados;
    }

    public void imprimirLavados(OutputStream fileOut) throws IOException {
        Configuracion config = Configuracion.getConfiguracion();

        for (Lavado lavado : this.getLavados())
            for (Prenda prenda : lavado.getPrendas())
                fileOut.write((prenda.getIdPrenda() + config.SEPARADOR_PARSER + lavado.getIdLavado() + "\n").getBytes());
    }

    public String toString() {
        String listaPrendaLavado = "";
        for (Lavado lavado : this.getLavados())
            for (Prenda prenda : lavado.getPrendas())
                listaPrendaLavado += prenda.getIdPrenda() + " " + lavado.getIdLavado() + "\n";
        return listaPrendaLavado;
    }

    public double getTiempoTotal() {
        double tiempoTotal = 0;
        for (Lavado lavado : this.getLavados())
            tiempoTotal += lavado.getTiempoLavado();
        return tiempoTotal;
    }

}
