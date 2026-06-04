package view;

import java.util.Scanner;

public class MenuUtama {
    public static void tampilkan(Scanner scanner) {
        boolean running = true;

        while (running){
            System.out.println("Menu RoTan");
            System.out.println("1. Kelola Lahan");
            System.out.println("2. Kelola Aktivitas");
            System.out.println("3. Kelola Fase Aktivitas");
            System.out.println("0. Keluar Aplikasi");
            System.out.println("Masukkan angka sesuai opsi menu");

            String opsi = scanner.nextLine();
            switch (opsi) {
                case "1":
                    MenuLahan.tampilkan(scanner);
                    break;
                case "2":
                    MenuAktivitas.tampilkan(scanner);
                    break;
                case "3":
                    MenuFase.tampilkan(scanner);
                    break;
                case "0":
                    System.out.println("Keluar dari RoTan...");
                    running = false;
                    break;
                default:
                    System.out.println("Masukkan perintah yang valid");
            }
        }
    }
}
