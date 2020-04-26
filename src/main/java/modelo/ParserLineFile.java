package modelo;

public class ParserLineFile {

    private boolean isProblema;
    private boolean isIncompatibilidad;
    private boolean isTiempoLavado;
    private int prenda;
    private double tiempoLavado;
    private int prendaIncompatible;
    private int cantPrendas;

    public ParserLineFile() {
        this.isIncompatibilidad = false;
        this.isProblema = false;
        this.isTiempoLavado = false;
        this.prenda = 0;
        this.prendaIncompatible = 0;
        this.tiempoLavado = 0;
        this.cantPrendas = 0;
    }

    public void parseredLine(String s) {
        Configuracion config = Configuracion.getConfiguracion();
        
        switch (s.charAt(0)) {
            case 'c':
            case 'C':
                procesoComentario(s, config); break;
            case 'p':
            case 'P':
                procesoPrograma(s, config); break;
            case 'e':
            case 'E':
                procesoIncompatibilidad(s, config); break;
            case 'n':
            case 'N':
                procesoTiempoLavado(s, config); break;
            default:
                throw new IllegalStateException("Unexpected value: " + s.charAt(0));
        }
    }

    private void procesoPrograma(String s, Configuracion config) {
        this.isProblema = true;

        //String[] arrOfStr = s.split(" ", 4);
        String[] arrOfStr = s.split(config.SEPARADOR_PARSER, 4);

        this.cantPrendas = Integer.parseInt(arrOfStr[2]);
    }

    private void procesoIncompatibilidad(String s, Configuracion config) {
        this.isIncompatibilidad = true;

        //String[] arrOfStr = s.split(" ", 3);
        String[] arrOfStr = s.split(config.SEPARADOR_PARSER, 3);

        this.prenda = Integer.parseInt(arrOfStr[1]) - 1;
        this.prendaIncompatible = Integer.parseInt(arrOfStr[2]) - 1;
    }

    private void procesoTiempoLavado(String s, Configuracion config) {
        this.isTiempoLavado = true;

        //String[] arrOfStr = s.split(" ", 3);
        String[] arrOfStr = s.split(config.SEPARADOR_PARSER, 3);

        this.prenda = Integer.parseInt(arrOfStr[1]) - 1;
        this.tiempoLavado = Double.parseDouble(arrOfStr[2]);
    }

    private void procesoComentario(String s, Configuracion config) { System.out.println("omitido" + s); }

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

    public double getTiempoLavado() {
        if (!this.isTiempoLavado)
            throw new IllegalStateException("Pedido incorrecto");
        return this.tiempoLavado;
    }

    public int getCantMediciones() {
        if (!this.isProblema())
            throw  new IllegalStateException("Pedido Incorrecto");
        return this.cantPrendas;
    }
}
