package view;
import java.util.Scanner;

import utils.ColorCode;
import utils.Displayer;

public class MenuAktivitas {
    public static void tampilkan(Scanner scanner) {
        boolean running = true;

        while (running){
            System.out.println("Menu Aktivitas");
            MenuLahan.getDaftarLahan();
            if (internal.Lahan.getSemua().isEmpty()){
                System.out.println("Belum ada lahan");
                MenuUtama.tampilkan(scanner);
                return;
            }
            
            System.out.println("Masukkan nomor lahan untuk mencatat aktivitas");
            System.out.println("Atau ketik '0' untuk kembali ke menu");

            System.out.printf("\n%s (Aktivitas)> ", Displayer.colorizeText("[RoTan]", ColorCode.KUNING, true));
            String opsi = scanner.nextLine().trim();

            //belum ada lanjutan buat inputnya
            //janlup nanti aktivitas sebelumnya yang sudah diinput
            //bakalan di tampilin juga, jadi bisa di reuse
            //kalo di aktivitas itu nyimpen fase atau nggak coba diskusi ke anak anak dlu
            break;
        }
    }

    public static void getAllAktivitas(){

    }
}
