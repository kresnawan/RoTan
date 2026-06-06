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

public class TemplateAktivitas {
    public static void hapus(int kode) throws Exception {
        ArrayList<Aktivitas> akt = TemplateAktivitas.getSemua();

        akt.removeIf((item) -> item.kode == kode);

        try {
            TemplateAktivitas.commit(akt);
        } catch (Exception e) {
            throw e;
        }
    }

    public static void insert(Aktivitas aktivitas) throws Exception {
        try {
            ArrayList<Aktivitas> akt = TemplateAktivitas.getSemua();
            int kodeTemplate = Kode.generateKode("template_aktivitas");

            Aktivitas newObj = new Aktivitas(aktivitas.tanggalMulai, aktivitas.nama, aktivitas.namaTumbuhan, aktivitas.catatan, 0, aktivitas.luas);

            newObj.kode = kodeTemplate;
            newObj.kodeLahan = 0;

            akt.add(aktivitas);

            TemplateAktivitas.commit(akt);
        } catch (Exception e) {
            throw e;
        }
    }

    public static Aktivitas getByKode(int kode) {
        ArrayList<Aktivitas> arr = TemplateAktivitas.getSemua();
        arr.removeIf(item -> item.kode != kode);

        if (arr.size() == 0)
            return null;

        return arr.getFirst();
    }

    public static ArrayList<Aktivitas> getSemua() {
        Path FILE_PATH = Paths.get(System.getProperty("user.home"), ".rotan", "template_aktivitas.json");
        Gson gson = new Gson();

        if (!Files.exists(FILE_PATH)) {
            return new ArrayList<>();
        }

        try {
            String jsonString = Files.readString(FILE_PATH);

            if (jsonString.trim().isEmpty()) {
                return new ArrayList<>();
            }

            Type listType = new TypeToken<ArrayList<Aktivitas>>() {
            }.getType();

            return gson.fromJson(jsonString, listType);

        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    private static void commit(ArrayList<Aktivitas> arr) throws Exception {
        Path FILE_PATH = Paths.get(System.getProperty("user.home"), ".rotan", "template_aktivitas.json");
        Gson gson = new Gson();

        String json = gson.toJson(arr);

        try {
            Files.writeString(FILE_PATH, json);
        } catch (Exception e) {
            throw e;
        }
    }
}
