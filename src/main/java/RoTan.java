import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

import utils.DataPengguna;

public class RoTan {
    Queue<Aktivitas> lahan = new LinkedList<Aktivitas>();

    public void mulai() {
        try {
            DataPengguna.muat();
        } catch (IOException e) {
            System.out.println("Terjadi error saat memuat data: " + e.getMessage());
            System.exit(1);
        }

        System.out.println("Selamat datang di RoTan..!");
        System.out.println("Aplikasi ini dapat membantu kamu buat mencatat pertumbuhan tanaman.");
    }
}
