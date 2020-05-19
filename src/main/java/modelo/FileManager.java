package modelo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class FileManager {
    public void loadFile(Experimento mediciones, String rutaFile) {
        //System.out.println("cargando...");

        ConsumerDataFIle consumerDataFIle = new ConsumerDataFIle(mediciones);

        try (Stream<String> stream = Files.lines(Paths.get(rutaFile))) {

            stream.forEach(consumerDataFIle);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
