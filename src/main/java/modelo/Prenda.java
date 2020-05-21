package modelo;

import java.util.Objects;

public class Prenda {

    private int idPrenda;
    private double tiempoLavado;
    // grado
    private int nPrendasIncompatibles;
    private int nlavado;
    // grado de saturacion
    private int nlavadosIncompatibles;

    public Prenda(int idPrenda) {
        this.idPrenda = idPrenda;
    }

    public int getIdPrenda() {
        return idPrenda;
    }

    public String getNombrePrenda() {
        return String.valueOf(idPrenda);
    }

    public Integer getNPrendasIncompatibles() {
        return new Integer(nPrendasIncompatibles);
    }

    public void setNPrendasIncompatibles(int nPrendasIncompatibles) {
        this.nPrendasIncompatibles = nPrendasIncompatibles;
    }

    public double getTiempoLavado() {
        return tiempoLavado;
    }

    public void setTiempoLavado(double tiempoLavado) {
        this.tiempoLavado = tiempoLavado;
    }

    public int getNlavado() {
        return nlavado;
    }

    public void setNlavado(int nlavado) {
        this.nlavado = nlavado;
    }

    public Integer getNlavadosIncompatibles() {
        return new Integer(this.nlavadosIncompatibles);
    }

    public void setNlavadosIncompatibles(int nlavadosIncompatibles) {
        this.nlavadosIncompatibles = nlavadosIncompatibles;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Prenda))
            return false;
        Prenda other = (Prenda) o;
        return Objects.equals(other.getIdPrenda(), this.idPrenda);
    }
}
