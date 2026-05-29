package internal;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import utils.Kode;

public class Lahan {
    int kode;
    public String nama;
    double luas;
    double panjang;
    double lebar;

    public Lahan(String nama, double luas) {
        this.kode = Kode.generateKode("lahan");
        this.nama = nama;
        this.luas = luas;
    }

    public Lahan(String nama, double panjang, double lebar) {
        this.kode = Kode.generateKode("lahan");
        this.nama = nama;
        this.panjang = panjang;
        this.lebar = lebar;
        this.luas = panjang * lebar;
    }

    public static ArrayList<Lahan> getSemua() {
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

    public static void hapus(int kode) throws Exception {
        try {
            ArrayList<Lahan> lahan = Lahan.getSemua();
            boolean result = lahan.removeIf((item) -> item.kode == kode);

            if (!result) throw new Exception("Tidak ada lahan yang dihapus");

            Aktivitas.hapusAktivitasLahan(kode);
            Lahan.commit(lahan);
        } catch (Exception e) {
            throw e;
        }
    }

    public static void insert(Lahan lahanBaru) throws Exception {

        ArrayList<Lahan> lahan = Lahan.getSemua();

        lahan.add(lahanBaru);
        lahan.sort((l1, l2) -> Integer.compare(l1.kode, l2.kode));

        Lahan.commit(lahan);
    }

    private static void commit(ArrayList<Lahan> arr) throws Exception {
        Path FILE_PATH = Paths.get(System.getProperty("user.home"), ".rotan", "lahan.json");
        Gson gson = new Gson();

        String json = gson.toJson(arr);

        try {
            Files.writeString(FILE_PATH, json);
        } catch (Exception e) {
            throw e;
        }
    }
}
