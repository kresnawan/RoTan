package internal;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Stack;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import utils.Kode;

public class FaseAktivitas {
    int kode;
    int kodeAktivitas;
    LocalDate tanggal;
    String catatan;

    public FaseAktivitas(int kodeAktivitas, LocalDate tanggal, String catatan) {
        this.kode = Kode.generateKode("fase");
        this.kodeAktivitas = kodeAktivitas;
        this.tanggal = tanggal;
        this.catatan = catatan;
    }

    public static Stack<FaseAktivitas> getFaseAktivitas(int kodeAktivitas) {
        ArrayList<FaseAktivitas> arr = FaseAktivitas.getSemua();
        arr.removeIf(item -> item.kodeAktivitas != kodeAktivitas);
        arr.sort((item1, item2) -> item1.tanggal.compareTo(item2.tanggal));

        Stack<FaseAktivitas> st = new Stack<FaseAktivitas>();
        st.addAll(arr);

        return st;
    }

    public static void hapusFaseAktivitas(ArrayList<Integer> kodeAktivitas) throws Exception {
        try {
            ArrayList<FaseAktivitas> fa = FaseAktivitas.getSemua();

            boolean result = fa.removeIf(item -> kodeAktivitas.contains(item.kodeAktivitas));

            if (!result)
                throw new Exception("Aktivitas tidak ditemukan");

            FaseAktivitas.commit(fa);
        } catch (Exception e) {
            throw e;
        }
    }

    public static void rollbackFaseAktivitas(int kodeAktivitas) throws Exception {
        FaseAktivitas.cekStatusAktivitas(kodeAktivitas);
        Stack<FaseAktivitas> fase = FaseAktivitas.getFaseAktivitas(kodeAktivitas);
        ArrayList<FaseAktivitas> semuaFase = FaseAktivitas.getSemua();

        try {
            FaseAktivitas temp = fase.pop();
            semuaFase.removeIf(item -> item.kode == temp.kode);

            FaseAktivitas.commit(semuaFase);
        } catch (Exception e) {
            throw e;
        }
    }

    public static ArrayList<FaseAktivitas> getSemua() {
        Path FILE_PATH = Paths.get(System.getProperty("user.home"), ".rotan", "fase.json");
        Gson gson = new Gson();

        if (!Files.exists(FILE_PATH)) {
            return new ArrayList<>();
        }

        try {
            String jsonString = Files.readString(FILE_PATH);

            if (jsonString.trim().isEmpty()) {
                return new ArrayList<>();
            }

            Type listType = new TypeToken<ArrayList<FaseAktivitas>>() {
            }.getType();

            return gson.fromJson(jsonString, listType);

        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    public static void insert(FaseAktivitas faseAktivitasBaru) throws Exception {
        try {
            FaseAktivitas.cekStatusAktivitas(faseAktivitasBaru.kodeAktivitas);
            ArrayList<Aktivitas> akt = Aktivitas.getSemua();

            akt.removeIf(item -> item.kode != faseAktivitasBaru.kodeAktivitas);
            if (akt.size() == 0)
                throw new Exception("Aktivitas tidak ditemukan");

            ArrayList<FaseAktivitas> fase = FaseAktivitas.getSemua();

            fase.add(faseAktivitasBaru);
            fase.sort((l1, l2) -> Integer.compare(l1.kode, l2.kode));

            FaseAktivitas.commit(fase);
        } catch (Exception e) {
            throw e;
        }
    }

    private static void cekStatusAktivitas(int kodeAktivitas) throws Exception {
        ArrayList<Aktivitas> arr = Aktivitas.getSemua();
        arr.removeIf(item -> item.kode != kodeAktivitas);

        if (arr.getFirst().selesai == true) {
            throw new Exception("Aktivitas telah dinyatakan selesai");
        }
    }

    private static void commit(ArrayList<FaseAktivitas> arr) throws Exception {
        Path FILE_PATH = Paths.get(System.getProperty("user.home"), ".rotan", "fase.json");
        Gson gson = new Gson();

        String json = gson.toJson(arr);

        try {
            Files.writeString(FILE_PATH, json);
        } catch (Exception e) {
            throw e;
        }
    }
}
