package view;

import java.util.Scanner;

import utils.ColorCode;
import utils.Displayer;

public class MenuUtama {
    public static void tampilkan(Scanner scanner) {
        boolean running = true;

        while (running){
            System.out.println("Menu Utama");
            System.out.println("    1    Kelola Lahan");
            System.out.println("    2    Kelola Aktivitas");
            System.out.println("    0    Keluar Aplikasi");
            System.out.println("Masukkan angka sesuai opsi menu");

            System.out.printf("\n%s# ", Displayer.colorizeText("[RoTan]", ColorCode.KUNING, true));

            String opsi = scanner.nextLine();
            switch (opsi) {
                case "1":
                    MenuLahan.tampilkan(scanner);
                    return;
                case "2":
                    MenuAktivitas.tampilkan(scanner);
                    return;
                case "0":
                    System.out.println("Keluar dari RoTan...");
                    return;
                default:
                    System.out.println("Perintah tidak valid");
                    break;
            }
        }
    }
}
