package modelo;

public class Prenda {

    private int idPrenda;
    private int cantIncompatibles;

    public Prenda(int idPrenda) {
        this.idPrenda = idPrenda;
    }

    public int getIdPrenda() {
        return idPrenda;
    }

    public String getNombrePrenda() {
        return String.valueOf(idPrenda);
    }

    public int getCantIncompatibles() {
        return cantIncompatibles;
    }

    public void setCantIncompatibles(int cantIncompatibles) {
        this.cantIncompatibles = cantIncompatibles;
    }
}
