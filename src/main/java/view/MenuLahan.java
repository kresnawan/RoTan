package view;

import java.util.ArrayList;
import java.util.Scanner;

import internal.Lahan;

public class MenuLahan {
    public static void tampilkan(Scanner scanner) {
        boolean running = true;

        while (running){
            System.out.println("""
                    Menu Aktivitas
                    1. Lihat daftar lahan
                    2. Tambah Lahan baru
                    3. Hapus Lahan
                    0. Kembali ke menu
                    Masukkan opsi sesuai dengan menu
                    """);
                    
            String opsi = scanner.nextLine();
            switch (opsi) {
                case "1":
                    getDaftarLahan();
                    break;
                case "2":
                    tambahLahan(scanner);
                    break;
                case "3":
                    hapusLahan(scanner);
                    break;
                case "0":
                    MenuUtama.tampilkan(scanner);
                    break;
                default:
                    System.out.println("Masukkan opsi yang valid");
                    break;
            }
        }
    }

    private static void getDaftarLahan(){
        ArrayList<Lahan> daftarLahan = Lahan.getSemua();

        if (daftarLahan.isEmpty()){
            System.out.println("Belum ada lahan yang terdaftar");
            return;
        } else {
            System.out.println("+------+--------------------+----------------+");
            System.out.println("| Kode |        Nama        | Luas           |");
            System.out.println("+------+--------------------+----------------+");
            for (Lahan l: daftarLahan){
                System.out.printf("| %-4d | %-18s | %-14.2f |\n", l.kode, l.nama, l.luas);
            }
            System.out.println("+------+--------------------+----------------+");
        }
    }

    private static void tambahLahan(Scanner scanner) {
        try {
            System.out.println("Nama lahan disesuaikan dengan nama tanaman, contoh Lahan Padi, Lahan Jagung, dll");
            System.out.println("Ketik '0' untuk membatalkan penambahan lahan");
            System.out.println("Masukkan nama lahan");

            String nama = scanner.nextLine().trim();

            if (nama.equals("0")) {
                System.out.println("\nPenambahan lahan dibatalkan");
                return;
            }

            nama = capitalizeWords(nama);

            System.out.println("\nMasukkan luas lahan (dalam hektar)");
            double luas = Double.parseDouble(scanner.nextLine());

            if (luas <= 0) {
                System.out.println("\nLuas harus lebih besar dari nol");
                return;
            }

            Lahan lahanBaru = new Lahan(nama, luas);
            Lahan.insert(lahanBaru);

            System.out.println("\nLahan berhasil ditambahkan");

        } catch (NumberFormatException e) {
            System.out.println("\nLuas harus berupa angka");
        } catch (Exception e){
            System.out.println("\nGagal Menambahkan lahan" + e.getMessage());
            System.out.println("");
        }
    }
    
    private static void hapusLahan(Scanner scanner){
        getDaftarLahan();

        if (internal.Lahan.getSemua().isEmpty()){
            return;
        }

        System.out.println("Masukkan kode lahan yang ingin dihapus");
        System.out.println("Atau ketik '0' Untuk membatalkan penghapusan");

        try {
            int kode = Integer.parseInt(scanner.nextLine());
            
            if (kode == 0) {
                System.out.println("Penghapusan dibatalkan");
                return;
            }

            internal.Lahan.hapus(kode);
            System.out.println("Lahan ke-"+ kode+ " telah berhasil dihapus");

        } catch (NumberFormatException n) {
            System.out.println("Input harus berupa angka");
        } catch (Exception e){
            System.out.println("Gagal Menghapus" + e);
        }
    }

    private static String capitalizeWords(String text) {
        String[] kata = text.toLowerCase().split("\\s+");
        StringBuilder capitalized = new StringBuilder();

        for (String k : kata) {
            if (!k.isEmpty()) {
                capitalized.append(Character.toUpperCase(k.charAt(0)))
                           .append(k.substring(1))
                           .append(" ");
            }
        }

        return capitalized.toString().trim();
    }
}
