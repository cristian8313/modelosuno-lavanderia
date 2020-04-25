package modelo;

import java.util.Arrays;
import java.util.stream.Stream;

public class ParserLineFile {

    private boolean isProblema;
    private boolean isIncompatibilidad;
    private boolean isTiempoLavado;
    private int prenda;
    private int tiempoLavado;
    private int prendaIncompatible;

    public ParserLineFile() {
        this.isIncompatibilidad = false;
        this.isProblema = false;
        this.isTiempoLavado = false;
        this.prenda = 0;
        this.prendaIncompatible = 0;
        this.tiempoLavado = 0;
    }

    public void parseredLine(String s) {
        switch (s.charAt(0)) {
            case 'c':
            case 'C':
                procesoComentario(s); break;
            case 'p':
            case 'P':
                procesoPrograma(s); break;
            case 'e':
            case 'E':
                procesoIncompatibilidad(s); break;
            case 'n':
            case 'N':
                procesoTiempoLavado(s); break;
            default:
                throw new IllegalStateException("Unexpected value: " + s.charAt(0));
        }
    }

    private void procesoPrograma(String s) {
        //System.out.println("problema");
        this.isProblema = true;

        /*String[] arrOfStr = s.split(" ", 4);

        System.out.println("codigo: " + arrOfStr[0]);
        System.out.println("comentario: " + arrOfStr[1]);
        System.out.println("prendas: " + arrOfStr[2]);
        System.out.println("incompatibilidades: " + arrOfStr[3]);*/
    }

    private void procesoIncompatibilidad(String s) {
        //System.out.println("inconpatible");
        this.isIncompatibilidad = true;

        String[] arrOfStr = s.split(" ", 3);

        /*System.out.println("codigo: " + arrOfStr[0]);
        System.out.println("prenda 1: " + arrOfStr[1]);
        System.out.println("prenda 2: " + arrOfStr[2]);*/

       /* this.prenda = 4;//Integer.parseInt(arrOfStr[1]);
        this.prendaIncompatible = 8;//Integer.parseInt(arrOfStr[2]);*/

        this.prenda = Integer.parseInt(arrOfStr[1]) - 1;
        this.prendaIncompatible = Integer.parseInt(arrOfStr[2]) - 1;
    }

    private void procesoTiempoLavado(String s) {
        //System.out.println("tiempo");
        this.isTiempoLavado = true;

        String[] arrOfStr = s.split(" ", 3);

        /*System.out.println("codigo: " + arrOfStr[0]);
        System.out.println("prenda: " + arrOfStr[1]);
        System.out.println("t. lavado: " + arrOfStr[2]);*/

        this.prenda = Integer.parseInt(arrOfStr[1]) - 1;
        this.tiempoLavado = Integer.parseInt(arrOfStr[2]) - 1;
    }

    private void procesoComentario(String s) {
        System.out.println("omitido" + s);
    }

    public boolean isProblema() {
        return this.isProblema;
    }

    public boolean isIncompatibilidad() {
        return this.isIncompatibilidad;
    }

    public boolean isTiempoLavado() {
        return this.isTiempoLavado;
    }

    public int getPrenda() {
        if (!this.isIncompatibilidad && !this.isTiempoLavado)
            throw new IllegalStateException("Pedido incorrecto");
        return this.prenda;
    }

    public int getPrendaIncompatible() {
        if (!this.isIncompatibilidad)
            throw new IllegalStateException("Pedido incorrecto");
        return this.prendaIncompatible;
    }

    public Integer getTiempoLavado() {
        if (!this.isTiempoLavado)
            throw new IllegalStateException("Pedido incorrecto");
        return this.tiempoLavado;
    }
}
