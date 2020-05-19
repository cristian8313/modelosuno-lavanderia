package modelo;

import java.util.Set;

public class Lavado {

    private int IdLavado;
    private double tiempoLavado;
    private Set<Prenda> prendas;

    public Lavado(int idLavado, double tiempoLavado, Set<Prenda> prendas) {
        this.tiempoLavado = tiempoLavado;
        this.IdLavado = idLavado;
        this.prendas = prendas;
    }

    public int getIdLavado() {
        return IdLavado;
    }

    public void setIdLavado(int idLavado) {
        IdLavado = idLavado;
    }

    public Set<Prenda> getPrendas() {
        return prendas;
    }

    public void agregarPrenda(Prenda prenda) {
        this.prendas.add(prenda);
    }

    public void removerPrenda(Prenda prenda) {
        this.prendas.remove(prenda);
    }

    public double getTiempoLavado() { return tiempoLavado; }

    public void setTiempoLavado(double tiempoLavado) {
        this.tiempoLavado = tiempoLavado;
    }

}
