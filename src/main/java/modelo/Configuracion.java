package modelo;

import java.io.*;
import java.util.Properties;

public class Configuracion {

    private static String path;
    private static String fileProblema;

    public void cargarConfiguracion() {
        try (OutputStream output = new FileOutputStream("./src/main/resources/config.properties")) {

            Properties prop = new Properties();

            // set the properties value
            prop.setProperty("path", "./src/main/resources/");
            prop.setProperty("fileProblema", "primer_problema.txt");

            // save properties to project root folder
            prop.store(output, null);

            //System.out.println(prop);

        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    public String getFileProblema() {
        return fileProblema;
    }

    public String getPath() {
        return path;
    }

    public void getConfiguracion() {
        try (InputStream input = new FileInputStream("./src/main/resources/config.properties")) {

            Properties prop = new Properties();

            // load a properties file
            prop.load(input);

            // get the property value and print it out
            this.path = prop.getProperty("path");
            this.fileProblema = prop.getProperty("fileProblema");

        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
}
