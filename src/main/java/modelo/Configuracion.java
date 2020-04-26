package modelo;

import java.io.*;
import java.util.Properties;

public class Configuracion {

    public String FILE_OUT;
    public String PATH_OUT;
    public String SEPARADOR_PARSER;
    public String PATH_INPUT;
    public String FILE_PROBLEMA;
    public double HORA_MAXIMA;
    
    private static Configuracion configuracion;

    private Configuracion() {
        this.cargarConfiguracion();

        try (InputStream input = new FileInputStream("./src/main/resources/config.properties")) {

            Properties prop = new Properties();

            // load a properties file
            prop.load(input);

            // get the property value and print it out
            this.PATH_INPUT = prop.getProperty("PATH_INPUT");
            this.PATH_OUT = prop.getProperty("PATH_OUT");
            this.FILE_PROBLEMA = prop.getProperty("FILE_PROBLEMA");
            this.FILE_OUT = prop.getProperty("FILE_OUT");
            this.HORA_MAXIMA = Double.parseDouble(prop.getProperty("HORA_MAXIMA"));
            this.SEPARADOR_PARSER = prop.getProperty("SEPARADOR_PARSER");
            System.out.println(this.SEPARADOR_PARSER);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static Configuracion getConfiguracion() {
        if (configuracion == null)
            configuracion = new Configuracion();

        return configuracion;
    }

    private void cargarConfiguracion() {
        try (OutputStream output = new FileOutputStream("./src/main/resources/config.properties")) {

            Properties prop = new Properties();

            // set the properties value
            prop.setProperty("PATH_INPUT", "./src/main/resources/");
            prop.setProperty("FILE_PROBLEMA", "primer_problema.txt");
            prop.setProperty("HORA_MAXIMA", "24.0\0");
            prop.setProperty("SEPARADOR_PARSER", " ");
            prop.setProperty("PATH_OUT", "./src/main/resources/");
            prop.setProperty("FILE_OUT", "primer_problema_solucion.txt");

            // save properties to project root folder
            prop.store(output, null);

            //System.out.println(prop);

        } catch (IOException io) {
            io.printStackTrace();
        }
    }

}
