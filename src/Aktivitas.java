import java.time.Instant;
import java.time.LocalDate;
import java.util.EmptyStackException;
import java.util.Stack;

public class Aktivitas {
    long kode;
    String nama;
    LocalDate tanggalMulai;
    String namaTumbuhan;
    String catatan;
    Stack<FaseAktivitas> fase = new Stack<FaseAktivitas>();

    public Aktivitas(LocalDate tanggalMulai, String namaTumbuhan, String catatan) {
        this.tanggalMulai = tanggalMulai;
        this.namaTumbuhan = namaTumbuhan;
        this.catatan = catatan;

        this.kode = Instant.now().toEpochMilli();
    }

    public void rollbackFase() throws EmptyStackException {
        try {
            this.fase.pop();
        } catch (EmptyStackException e) {
            throw e;
        }
    }

    public void hapus() throws Exception {

    }

    public void ubah() throws Exception {

    }
}
