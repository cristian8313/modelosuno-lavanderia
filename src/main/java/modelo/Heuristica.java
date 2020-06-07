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
        //Iterator i = this.experimento.getPrendasSort().entrySet().iterator();
        Iterator<Prenda> i = this.experimento.sortPrendasByGrado().iterator();

        // tomo la prenda de mayor grado y asigno un lavado
        Prenda p = i.next();
        p.setNlavado(++this.nLavado);
        this.lavados.add(new Lavado(this.nLavado, p.getTiempoLavado(), new HashSet<Prenda>()));

        // sumo 1 al grado de saturacion de los incompatibles de la prenda
        Iterator<Prenda> ip = this.experimento.getIncompatibles().get(p.getIdPrenda()).iterator();
        while (ip.hasNext()) {
            Prenda xp = ip.next();
            xp.setNlavadosIncompatibles(xp.getNlavadosIncompatibles() + 1);
        }

        while (prendaSinAsignar() != null) {
            Prenda pr = prendaSinAsignar();
            boolean asingarLavado = true;
            Iterator<Lavado> iL = this.sortLavadosByNumero().iterator();
            while (iL.hasNext() && asingarLavado) {
                Lavado lvdo = iL.next();
                Iterator<Prenda> ipr = this.experimento.getIncompatibles().get(pr.getIdPrenda()).iterator();
                while (ipr.hasNext()) {
                    if (ipr.next().getNlavado() == lvdo.getIdLavado()) {
                        asingarLavado = false;
                        break;
                    }
                }
                if (asingarLavado) {
                    pr.setNlavado(lvdo.getIdLavado());
                    if (lvdo.getTiempoLavado() < pr.getTiempoLavado())
                        lvdo.setTiempoLavado(pr.getTiempoLavado());
                    // sumo 1 al grado de saturacion de los incompatibles de la prenda
                    Iterator<Prenda> ipr2 = this.experimento.getIncompatibles().get(pr.getIdPrenda()).iterator();
                    while (ipr2.hasNext()) {
                        Prenda pr2 = ipr2.next();
                        pr2.setNlavadosIncompatibles(pr2.getNlavadosIncompatibles() + 1);
                    }
                    asingarLavado = false;
                } else {
                    asingarLavado = true;
                }
            }
            if (asingarLavado) {
                pr.setNlavado(++this.nLavado);
                this.lavados.add(new Lavado(this.nLavado, pr.getTiempoLavado(), new HashSet<Prenda>()));

                // sumo 1 al grado de saturacion de los incompatibles de la prenda
                Iterator<Prenda> ipr2 = this.experimento.getIncompatibles().get(pr.getIdPrenda()).iterator();
                while (ipr2.hasNext()) {
                    Prenda pr2 = ipr2.next();
                    pr2.setNlavadosIncompatibles(pr2.getNlavadosIncompatibles() + 1);
                }
            }
        }
    }

    public double cotaInferior() {
        double cota = 0;
        double cotaAux = 0;
        HashSet<Prenda> descartados = new HashSet<Prenda>();
        Iterator<Prenda> i = this.experimento.sortPrendasByIncomp().iterator();

        while(i.hasNext()) {
            HashSet<Prenda> clique = new HashSet<Prenda>();
            Prenda p = i.next();
            cotaAux = p.getTiempoLavado();

            boolean addPInc = false;
            while (!clique.contains(p)) {
                clique.add(p);
                //iteracion
                //Iterator<Prenda> i2 = this.experimento.getIncompatibles().get(p.getIdPrenda()).iterator();

                ///
                // 1. Convert Map to List of Map
                List<Prenda> list = new LinkedList<Prenda>(this.experimento.getIncompatibles().get(p.getIdPrenda()));

                // 2. Sort list with Collections.sort(), provide a custom Comparator
                // Try switch the o1 o2 position for a different order
                Collections.sort(list, new Comparator<Prenda>() {
                    public int compare(Prenda p1, Prenda p2) {
                        int i = new Double(p1.getTiempoLavado()).compareTo(new Double(p2.getTiempoLavado()));
                        if (i != 0) return -i;
                        return (new Double(p1.getTiempoLavado()).compareTo(new Double(p2.getTiempoLavado())));
                    }
                });

                Iterator<Prenda> i2 = list.iterator();
                ///

                while (i2.hasNext() && !addPInc) {
                    Prenda inc = i2.next();

                    addPInc = true;
                    for (Prenda cp : clique) {
                        if (!this.experimento.getIncompatibles().get(cp.getIdPrenda()).contains(inc))
                            addPInc = false;
                    }
                    if (addPInc) {
                        p = inc;
                        cotaAux += p.getTiempoLavado();
                        addPInc = false;
                        break;
                    }
                }
            }

            /*// asumo que todos estos van juntos en un lavado con el tiempo maximo
            double subCota = 0;
            Iterator<Prenda> iAux = this.experimento.getPrendas().iterator();
            while (iAux.hasNext()) {
                Prenda pAux = iAux.next();
                if (!clique.contains(pAux)) {
                    boolean esta = false;
                    for (Prenda pcl : clique) {
                        if (this.experimento.getIncompatibles().get(pcl.getIdPrenda()).contains(pAux)) {
                            esta = true;
                            break;
                        }
                    }
                    if (!esta) {
                        descartados.add(pAux);
                        if (subCota < pAux.getTiempoLavado())
                            subCota = pAux.getTiempoLavado();
                    }
                }
            }
            System.out.println(cota + ", " + cotaAux + ", " + subCota);
            cotaAux += subCota;*/

            if (cota < cotaAux)
                cota = cotaAux;
        }

        return cota;
    }

    private ArrayList<Lavado> getLavados() {
        return lavados;
    }

    public void imprimirLavados(OutputStream fileOut) throws IOException {
        Configuracion config = Configuracion.getConfiguracion();

        //for (Lavado lavado : this.getLavados())
            for (Prenda prenda : this.experimento.getPrendas())
                fileOut.write((prenda.getIdPrenda() + config.SEPARADOR_PARSER + prenda.getNlavado() + "\n").getBytes());
    }

    public String toString() {
        String listaPrendaLavado = "";
        //for (Lavado lavado : this.getLavados())
            for (Prenda prenda : this.experimento.getPrendas())
                listaPrendaLavado += prenda.getIdPrenda() + " " + prenda.getNlavado() + "\n";
        return listaPrendaLavado;
    }

    public double getTiempoTotal() {
        double tiempoTotal = 0;
        for (Lavado lavado : this.getLavados())
            tiempoTotal += lavado.getTiempoLavado();
        return tiempoTotal;
    }

    private Prenda prendaSinAsignar() {
        Iterator<Prenda> i = this.experimento.sortPrendasBySatur().iterator();
        while (i.hasNext()) {
            Prenda p = i.next();
            if (p.getNlavado() == 0)
                return p;
        }
        return null;
    }

    public List<Lavado> sortLavadosByNumero() {
        // 1. Convert Map to List of Map
        List<Lavado> list = new LinkedList<Lavado>(this.lavados);

        // 2. Sort list with Collections.sort(), provide a custom Comparator
        // Try switch the o1 o2 position for a different order
        Collections.sort(list, new Comparator<Lavado>() {
            public int compare(Lavado l1, Lavado l2) {
                return (new Integer(l1.getIdLavado()).compareTo(new Integer(l2.getIdLavado())));
            }
        });

        return list;
    }

}
