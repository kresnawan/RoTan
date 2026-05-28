package utils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Kode {
    public static int generateKode(String tipe) {
        Path FILE_PATH = Paths.get(System.getProperty("user.home"), ".rotan", "auto_inc");
        try {
            String fileString = Files.readString(FILE_PATH);
            String[] arr = fileString.split(",");

            if (tipe.equals("lahan")) {
                int kodeTerakhir = Integer.parseInt(arr[0]);
                int kode = kodeTerakhir + 1;
                Files.writeString(FILE_PATH, String.format("%d,%s,%s", kode, arr[1], arr[2]));

                return kodeTerakhir + 1;
            } else if (tipe.equals("aktivitas")) {
                int kodeTerakhir = Integer.parseInt(arr[1]);
                int kode = kodeTerakhir + 1;
                Files.writeString(FILE_PATH, String.format("%s,%d,%s", arr[0], kode, arr[2]));

                return kodeTerakhir + 1;
            } else if (tipe.equals("fase")) {
                int kodeTerakhir = Integer.parseInt(arr[2]);
                int kode = kodeTerakhir + 1;
                Files.writeString(FILE_PATH, String.format("%s,%s,%d", arr[0], arr[1], kode));

                return kodeTerakhir + 1;
            } else {
                return 0;
            }

        } catch (Exception e) {
            return 0;
        }
    }
}
