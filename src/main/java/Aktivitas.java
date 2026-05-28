import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import utils.Kode;

public class Aktivitas {
    int kode;
    int kodeLahan;
    String nama;
    LocalDate tanggalMulai;
    String namaTumbuhan;
    String catatan;
    Stack<FaseAktivitas> fase = new Stack<FaseAktivitas>();

    public Aktivitas(LocalDate tanggalMulai, String nama, String namaTumbuhan, String catatan, int kodeLahan) {
        this.tanggalMulai = tanggalMulai;
        this.namaTumbuhan = namaTumbuhan;
        this.catatan = catatan;
        this.nama = nama;

        this.kode = Kode.generateKode("aktivitas");
        this.kodeLahan = kodeLahan;
    }

    public static ArrayList<Aktivitas> getAktivitasLahan(int kodeLahan) {
        ArrayList<Aktivitas> aktivitas = Aktivitas.getSemua();
        aktivitas.removeIf(item -> item.kodeLahan != kodeLahan);
        return aktivitas;
    }

    // public void rollbackFase() throws EmptyStackException {
    // try {
    // this.fase.pop();
    // } catch (EmptyStackException e) {
    // throw e;
    // }
    // }

    public static void hapusAktivitasLahan(int kodeLahan) throws Exception {
        ArrayList<Aktivitas> akt = Aktivitas.getSemua();
        ArrayList<Integer> kodeAktivitas = new ArrayList<>();

        Iterator<Aktivitas> aktIterator = akt.iterator();

        while (aktIterator.hasNext()) {
            Aktivitas item = aktIterator.next();

            if (item.kodeLahan == kodeLahan) {
                kodeAktivitas.add(item.kode);
                aktIterator.remove();
            }
        }

        try {
            FaseAktivitas.hapusFaseAktivitas(kodeAktivitas);
            Aktivitas.commit(akt);
        } catch (Exception e) {
            throw e;
        }
    }

    public static void hapus(int kode) throws Exception {
        ArrayList<Aktivitas> akt = Aktivitas.getSemua();
        ArrayList<Integer> args = new ArrayList<Integer>();

        akt.removeIf((item) -> item.kode == kode);
        args.add(kode);

        try {
            FaseAktivitas.hapusFaseAktivitas(args);
            Aktivitas.commit(akt);
        } catch (Exception e) {
            throw e;
        }
    }

    public static void insert(Aktivitas aktivitas) throws Exception {
        ArrayList<Aktivitas> akt = Aktivitas.getSemua();

        ArrayList<Lahan> lh = Lahan.getSemua();
        lh.removeIf(item -> item.kode != aktivitas.kodeLahan);

        if (lh.size() == 0)
            throw new Exception("Lahan tidak ditemukan");

        akt.add(aktivitas);
        akt.sort((l1, l2) -> Integer.compare(l1.kode, l2.kode));

        Aktivitas.commit(akt);
    }

    public static ArrayList<Aktivitas> getSemua() {
        Path FILE_PATH = Paths.get(System.getProperty("user.home"), ".rotan", "aktivitas.json");
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
        Path FILE_PATH = Paths.get(System.getProperty("user.home"), ".rotan", "aktivitas.json");
        Gson gson = new Gson();

        String json = gson.toJson(arr);

        try {
            Files.writeString(FILE_PATH, json);
        } catch (Exception e) {
            throw e;
        }
    }
}
