import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

import internal.Aktivitas;
import utils.DataPengguna;
import view.MenuUtama;

public class RoTan {
    Queue<Aktivitas> lahan = new LinkedList<Aktivitas>();

    public void mulai() {
        try {
            DataPengguna.muat();
        } catch (IOException e) {
            System.out.println("Terjadi error saat memuat data: " + e.getMessage());
            System.exit(1);
        }
        Scanner scanner = new Scanner(System.in);

        System.out.println("Selamat datang di RoTan..!");
        System.out.println("Aplikasi ini dapat membantu kamu buat mencatat pertumbuhan tanaman.");

        MenuUtama.tampilkan(scanner);

    }
}
