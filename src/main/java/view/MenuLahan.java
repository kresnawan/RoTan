package view;

import java.util.ArrayList;
import java.util.Scanner;

import internal.Aktivitas;
import internal.Lahan;
import utils.ColorCode;
import utils.Displayer;

public class MenuLahan {
    public static void tampilkan(Scanner scanner) {
        boolean running = true;

        while (running){
            System.out.println("Menu Lahan");
            System.out.println("    1    Lihat daftar lahan");
            System.out.println("    2    Tambah Lahan baru");
            System.out.println("    3    Hapus Lahan");
            System.out.println("    0    Kembali ke menu");
            System.out.println("Masukkan angka sesuai opsi menu");

            System.out.printf("\n%s (Lahan)> ", Displayer.colorizeText("[RoTan]", ColorCode.KUNING, true));
                    
            String opsi = scanner.nextLine();
            switch (opsi) {
                case "1":
                    getDaftarLahan();
                    promptDetailLahan(scanner);
                    break;
                case "2":
                    tambahLahan(scanner);
                    break;
                case "3":
                    hapusLahan(scanner);
                    break;
                case "0":
                    MenuUtama.tampilkan(scanner);
                    return;
                default:
                    System.out.println("Masukkan opsi yang valid");
                    break;
            }
        }
    }

    public static void getDaftarLahan(){
        ArrayList<Lahan> daftarLahan = Lahan.getSemua();

        if (daftarLahan.isEmpty()){
            System.out.println("+--------------------------------------------+");
            System.out.println("|        Belum ada lahan yang terdaftar      |");
            System.out.println("+--------------------------------------------+\n");
            return;
        }

        String garis = "+------+--------------------+------------+------------+------------+-----------+";
        String header = String.format("| %-4s | %-18s | %-10s | %-10s | %-10s | %-9s |",
                "Kode", "Nama", "Luas", "Terpakai", "Sisa", "Aktivitas");

        System.out.println(garis);
        System.out.println(Displayer.colorizeText(header, ColorCode.CYAN, true));
        System.out.println(garis);

        for (Lahan l : daftarLahan) {
            double luasTerpakai = Lahan.getLuasTerpakai(l.kode);
            double sisa = l.luas - luasTerpakai;
            int jumlahAktivitas = Aktivitas.getAktivitasLahan(l.kode).size();

            String infoAktivitas = jumlahAktivitas == 0 ? "Tidak ada" : String.valueOf(jumlahAktivitas);

            String baris = String.format("| %-4d | %-18s | %7.2f ha | %7.2f ha | %7.2f ha | %-9s |",
                    l.kode, l.nama, l.luas, luasTerpakai, sisa, infoAktivitas);

            if (sisa <= 0) {
                System.out.println(Displayer.colorizeText(baris, ColorCode.MERAH, false));
            } else {
                System.out.println(Displayer.colorizeText(baris, ColorCode.HIJAU, false));
            }
        }
        System.out.println(garis);
    }

    private static void promptDetailLahan(Scanner scanner) {
        ArrayList<Lahan> daftarLahan = Lahan.getSemua();
        if (daftarLahan.isEmpty()) return;

        System.out.println("\nMasukkan kode lahan untuk melihat detail, atau ketik '0' untuk kembali");
        System.out.printf("%s (Detail)> ", Displayer.colorizeText("[RoTan]", ColorCode.KUNING, true));

        try {
            int kode = Integer.parseInt(scanner.nextLine().trim());

            if (kode == 0) return;

            lihatDetailLahan(kode);
        } catch (NumberFormatException e) {
            System.out.println(Displayer.colorizeText("Input harus berupa angka", ColorCode.MERAH, false));
        }
    }

    private static void lihatDetailLahan(int kodeLahan) {
        ArrayList<Lahan> semua = Lahan.getSemua();
        Lahan target = null;

        for (Lahan l : semua) {
            if (l.kode == kodeLahan) {
                target = l;
                break;
            }
        }

        if (target == null) {
            System.out.println(Displayer.colorizeText("Lahan dengan kode " + kodeLahan + " tidak ditemukan", ColorCode.MERAH, true));
            return;
        }

        double luasTerpakai = Lahan.getLuasTerpakai(kodeLahan);
        double sisa = target.luas - luasTerpakai;

        System.out.println("\n" + Displayer.colorizeText("=== Detail Lahan: " + target.nama + " ===", ColorCode.CYAN, true));
        System.out.printf("  Kode          : %d\n", target.kode);
        System.out.printf("  Luas Total    : %.2f ha\n", target.luas);
        System.out.printf("  Luas Terpakai : %.2f ha\n", luasTerpakai);
        System.out.printf("  Sisa Lahan    : %s\n",
                Displayer.colorizeText(String.format("%.2f ha", sisa),
                        sisa <= 0 ? ColorCode.MERAH : ColorCode.HIJAU, true));

        ArrayList<Aktivitas> aktivitas = Aktivitas.getAktivitasLahan(kodeLahan);

        if (aktivitas.isEmpty()) {
            System.out.println("\n  " + Displayer.colorizeText("Belum ada aktivitas di lahan ini", ColorCode.KUNING, false));
            System.out.println();
            return;
        }

        System.out.println("\n" + Displayer.colorizeText("  --- Daftar Aktivitas ---", ColorCode.CYAN, false));

        String garisAkt  = "  +------+--------------------+------------------+----------+---------+";
        String headerAkt = String.format("  | %-4s | %-18s | %-16s | %-8s | %-7s |",
                "Kode", "Nama Proyek", "Tumbuhan", "Luas", "Status");

        System.out.println(garisAkt);
        System.out.println(Displayer.colorizeText(headerAkt, ColorCode.CYAN, true));
        System.out.println(garisAkt);

        for (Aktivitas a : aktivitas) {
            String status = a.selesai ? "Selesai" : "Aktif";

            String baris = String.format("  | %-4d | %-18s | %-16s | %5.2f ha | %-7s |",
                    a.kode, a.nama, a.namaTumbuhan, a.luas, status);

            if (a.selesai) {
                System.out.println(Displayer.colorizeText(baris, ColorCode.KUNING, false));
            } else {
                System.out.println(Displayer.colorizeText(baris, ColorCode.HIJAU, false));
            }
        }
        System.out.println(garisAkt);
        System.out.println();
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

            System.out.printf("\n%s\n", Displayer.colorizeText("Lahan berhasil ditambahkan", ColorCode.HIJAU, true));

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

