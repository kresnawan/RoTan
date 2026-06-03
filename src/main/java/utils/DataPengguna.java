package utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DataPengguna {
    public static void muat() throws IOException {
        String userHome = System.getProperty("user.home");
        Path folderPath = Paths.get(userHome, ".rotan");

        Path lahanFilePath = folderPath.resolve("lahan.json");
        Path aktivitasFilePath = folderPath.resolve("aktivitas.json");
        Path faseFilePath = folderPath.resolve("fase.json");
        Path templateAktivitasFilePath = folderPath.resolve("template_aktivitas.json");
        Path autoIncFilePath = folderPath.resolve("auto_inc");

        if (!(Files.exists(lahanFilePath) && Files.exists(aktivitasFilePath) && Files.exists(faseFilePath)
                && Files.exists(autoIncFilePath) && Files.exists(templateAktivitasFilePath))) {
            try {
                if (!Files.exists(folderPath)) {
                    Files.createDirectories(folderPath);
                }

                Files.deleteIfExists(templateAktivitasFilePath);
                Files.deleteIfExists(lahanFilePath);
                Files.deleteIfExists(aktivitasFilePath);
                Files.deleteIfExists(faseFilePath);
                Files.deleteIfExists(autoIncFilePath);

                Files.createFile(templateAktivitasFilePath);
                Files.createFile(lahanFilePath);
                Files.createFile(aktivitasFilePath);
                Files.createFile(faseFilePath);
                Files.writeString(autoIncFilePath, "0,0,0,0");
            } catch (IOException e) {
                throw e;
            }

        }
    }
}
