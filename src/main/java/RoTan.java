import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.Queue;

public class RoTan {
    Queue<Aktivitas> lahan = new LinkedList<Aktivitas>();

    public void mulai() {
        try {
            this.muatData();
        } catch (IOException e) {
            System.out.println("Terjadi error saat memuat data: " + e.toString());
            System.exit(1);
        }

        System.out.println("Selamat datang di RoTan..!");
        System.out.println("Aplikasi ini dapat membantu kamu buat mencatat pertumbuhan tanaman.");
    }

    private void muatData() throws IOException {
        String userHome = System.getProperty("user.home");
        Path folderPath = Paths.get(userHome, ".rotan");

        Path lahanFilePath = folderPath.resolve("lahan.json");
        Path aktivitasFilePath = folderPath.resolve("aktivitas.json");
        Path faseFilePath = folderPath.resolve("fase.json");

        if (!(Files.exists(lahanFilePath) && Files.exists(aktivitasFilePath) && Files.exists(faseFilePath))) {
            try {
                if (!Files.exists(folderPath)) {
                    Files.createDirectories(folderPath);
                }

                Files.createFile(lahanFilePath);
                Files.createFile(aktivitasFilePath);
                Files.createFile(faseFilePath);
            } catch (IOException e) {
                throw e;
            }

        }
    }
}
