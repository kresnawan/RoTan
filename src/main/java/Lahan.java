import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.ArrayList;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import utils.Kode;

public class Lahan {
    int kode;
    double luas;
    double panjang;
    double lebar;
    ArrayList<Aktivitas> aktivitas = new ArrayList<Aktivitas>();

    public Lahan(double luas) {
        this.kode = Kode.generateKode("lahan");
        this.luas = luas;
    }

    public Lahan(double panjang, double lebar) {
        this.kode = Kode.generateKode("lahan");
        this.panjang = panjang;
        this.lebar = lebar;
        this.luas = panjang * lebar;
    }

    public void hapus() {

    }

    public void ubah() {

    }

    public static String insertLahan(Lahan lahanBaru) {
        ArrayList<Lahan> lahan = Lahan.getSemuaLahan();

        lahan.add(lahanBaru);
        Gson gson = new Gson();

        String json = gson.toJson(lahan);

        return json;
    }

    public static ArrayList<Lahan> getSemuaLahan() {
        Path FILE_PATH = Paths.get(System.getProperty("user.home"), ".rotan", "lahan.json");
        Gson gson = new Gson();
        
        if (!Files.exists(FILE_PATH)) {
            return new ArrayList<>();
        }

        try {
            String jsonString = Files.readString(FILE_PATH);

            if (jsonString.trim().isEmpty()) {
                return new ArrayList<>();
            }

            Type listType = new TypeToken<ArrayList<Lahan>>() {
            }.getType();

            return gson.fromJson(jsonString, listType);

        } catch (IOException e) {
            return new ArrayList<>();
        }
    }
}
