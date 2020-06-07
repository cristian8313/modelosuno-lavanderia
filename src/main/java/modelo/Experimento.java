package modelo;

import java.util.*;

public class Experimento {

    private Configuracion config;
    private Map<Integer, HashSet<Prenda>> incomp;
    private ArrayList<Prenda> prendas;

    public Experimento() {
        this.config = Configuracion.getConfiguracion();
        this.incomp = new HashMap<Integer, HashSet<Prenda>>();
        this.prendas = new ArrayList<Prenda>();
    }

    public void agregarMedicionIncompatible(int x, int y, double medicion) {
        insertarIncompatible(x, y);
        insertarIncompatible(y, x);
    }

    private void insertarIncompatible(int x, int y) {
        Prenda px = new Prenda(x + 1);
        Prenda py = new Prenda(y + 1);

        if (!this.prendas.contains(px))
            this.prendas.add(px);
        else
            px = this.prendas.get(this.prendas.indexOf(px));

        if (!this.prendas.contains(py))
            this.prendas.add(py);
        else
            py = this.prendas.get(this.prendas.indexOf(py));

        if (this.incomp.containsKey(px.getIdPrenda())) {
            this.incomp.get(px.getIdPrenda()).add(py);
        } else {
            HashSet<Prenda> priQx = new HashSet<Prenda>();
            priQx.add(py);
            this.incomp.put(px.getIdPrenda(), priQx);
        }
    }

    public void agregarMedicionPrenda(int x, double medicion) {
        Prenda px = new Prenda(x + 1);

        if (!this.prendas.contains(px)) {
            px.setTiempoLavado(medicion);
            this.prendas.add(px);
            this.incomp.put(px.getIdPrenda(), new HashSet<Prenda>());
        } else {
            Prenda p1 = this.prendas.get(this.prendas.indexOf(px));
            p1.setTiempoLavado(medicion);
            p1.setNPrendasIncompatibles(this.incomp.get(p1.getIdPrenda()).size());
        }
    }

    public ArrayList<Prenda> getPrendas() {
        return this.prendas;
    }

    public Map<Integer, HashSet<Prenda>> getIncompatibles() {
        return this.incomp;
    }

    public List<Prenda> sortPrendasByGrado() {
        // 1. Convert Map to List of Map
        List<Prenda> list = new LinkedList<Prenda>(this.prendas);

        // 2. Sort list with Collections.sort(), provide a custom Comparator
        // Try switch the o1 o2 position for a different order
        /*Collections.sort(list, new Comparator<Prenda>() {
            public int compare(Prenda p1, Prenda p2) {
                int i = p1.getNPrendasIncompatibles().compareTo(p2.getNPrendasIncompatibles());
                if (i != 0) return -i;
                return (p1.getNPrendasIncompatibles().compareTo(p2.getNPrendasIncompatibles()));
            }
        });*/
        Collections.sort(list, new Comparator<Prenda>() {
            public int compare(Prenda p1, Prenda p2) {
                int i = (new Double(p1.getTiempoLavado()).compareTo(new Double(p2.getTiempoLavado())));
                if (i != 0) return -i;
                else {
                    i = (p1.getNPrendasIncompatibles().compareTo(p2.getNPrendasIncompatibles()));
                    if (i != 0) return -i;
                    return (p1.getNPrendasIncompatibles().compareTo(p2.getNPrendasIncompatibles()));
                }
            }
        });

        return list;
    }

    public List<Prenda> sortPrendasByIncomp() {
        // 1. Convert Map to List of Map
        List<Prenda> list = new LinkedList<Prenda>(this.prendas);

        // 2. Sort list with Collections.sort(), provide a custom Comparator
        // Try switch the o1 o2 position for a different order
        Collections.sort(list, new Comparator<Prenda>() {
            public int compare(Prenda p1, Prenda p2) {
                int i = p1.getNPrendasIncompatibles().compareTo(p2.getNPrendasIncompatibles());
                if (i != 0) return -i;
                return (p1.getNPrendasIncompatibles().compareTo(p2.getNPrendasIncompatibles()));
            }
        });

        return list;
    }

    public List<Prenda> sortPrendasBySatur() {
        // 1. Convert Map to List of Map
        List<Prenda> list = new LinkedList<Prenda>(this.prendas);

        // 2. Sort list with Collections.sort(), provide a custom Comparator
        // Try switch the o1 o2 position for a different order
        Collections.sort(list, new Comparator<Prenda>() {
            public int compare(Prenda p1, Prenda p2) {
                int i = p1.getNlavadosIncompatibles().compareTo(p2.getNlavadosIncompatibles());
                if (i != 0) return -i;
                else {
                    i = (new Double(p1.getTiempoLavado()).compareTo(new Double(p2.getTiempoLavado())));
                    if (i != 0) return -i;
                    else {
                        i = p1.getNPrendasIncompatibles().compareTo(p2.getNPrendasIncompatibles());
                        if (i != 0) return -i;
                        return (p1.getNPrendasIncompatibles().compareTo(p2.getNPrendasIncompatibles()));
                    }
                    //return (p1.getNlavadosIncompatibles().compareTo(p2.getNlavadosIncompatibles()));
                }
            }
        });

        return list;
    }
}
