package modelo;

import java.util.*;

public class Experimento {

    private Configuracion config;
    private Map<Integer, HashSet<Integer>> incomp;
    private SortedMap<Double, ArrayList<Prenda>> prendasSort;

    public Experimento() {
        this.config = Configuracion.getConfiguracion();
        this.incomp = new HashMap<Integer, HashSet<Integer>>();
        this.prendasSort = new TreeMap<Double, ArrayList<Prenda>>(Collections.reverseOrder());
    }

    public void agregarMedicionIncompatible(int x, int y, double medicion) {
        insertarIncompatible(x, y);
        insertarIncompatible(y, x);
    }

    private void insertarIncompatible(int x, int y) {
        if (this.incomp.containsKey(x + 1)) {
            this.incomp.get(x + 1).add(y + 1);
        } else {
            HashSet<Integer> priQx = new HashSet<Integer>();
            priQx.add(y + 1);
            this.incomp.put(x + 1, priQx);
        }
    }

    public void agregarMedicionPrenda(int x, double medicion) {
        Prenda p = new Prenda(x + 1);
        p.setCantIncompatibles(this.incomp.get(p.getIdPrenda()).size());

        if (this.prendasSort.containsKey(medicion))
            this.prendasSort.get(medicion).add(p);
        else {
            ArrayList<Prenda> arrP = new ArrayList<Prenda>();
            arrP.add(p);
            this.prendasSort.put(medicion, arrP);
        }
    }

    public SortedMap<Double, ArrayList<Prenda>> getPrendasSort() {
        return this.prendasSort;
    }

    public Map<Integer, HashSet<Integer>> getIncompatibles() {
        return this.incomp;
    }

    public SortedMap<Double, ArrayList<Prenda>> sortPrendasBy() {
        SortedMap<Double, ArrayList<Prenda>> mapP = new TreeMap<Double, ArrayList<Prenda>>(Collections.reverseOrder());
        Iterator it = this.prendasSort.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry m = (Map.Entry) it.next();
            ArrayList<Prenda> aP = (ArrayList<Prenda>) m.getValue();
            // 1. Convert Map to List of Map
            List<Prenda> list = new LinkedList<Prenda>(aP);

            // 2. Sort list with Collections.sort(), provide a custom Comparator
            // Try switch the o1 o2 position for a different order
            Collections.sort(list, new Comparator<Prenda>() {
                public int compare(Prenda p1, Prenda p2) {
                    int i = p1.getCantIncompatibles().compareTo(p2.getCantIncompatibles());
                    if (i != 0) return -i;
                    return (p1.getCantIncompatibles().compareTo(p2.getCantIncompatibles()));
                }
            });

            mapP.put((Double) m.getKey(), new ArrayList<Prenda>(list));
        }

        return mapP;
    }

}
