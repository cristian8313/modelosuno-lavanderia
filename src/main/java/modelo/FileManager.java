package modelo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class FileManager {
    public void loadFile() {
        System.out.println("cargando...");

        Configuracion config = new Configuracion();
        //config.cargarConfiguracion();
        config.getConfiguracion();

        ConsumerDataFIle consumerDataFIle = new ConsumerDataFIle();

        try (Stream<String> stream = Files.lines(Paths.get(config.getPath() + config.getFileProblema()))) {

            stream.forEach(consumerDataFIle);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
