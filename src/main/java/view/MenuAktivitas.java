package view;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Stack;

import internal.Aktivitas;
import internal.FaseAktivitas;
import utils.ColorCode;
import utils.Displayer;

public class MenuAktivitas {
    public static void tampilkan(Scanner scanner) {
        boolean running = true;
        System.out.println("Ketik 'help' untuk menampilkan list perintah.");

        while (running) {
            System.out.printf("\n%s (Aktivitas)> ", Displayer.colorizeText("[RoTan]", ColorCode.KUNING, true));
            String opsi = scanner.nextLine().trim();

            if (opsi.startsWith("edit")) {
                String kode = opsi.split(" ")[1];

                MenuAktivitas.tampilkanEditAktivitas(kode, scanner);
                continue;
            }

            switch (opsi) {
                case "1":
                    MenuAktivitas.getDaftarAktivitas();
                    break;
                case "2":
                    MenuAktivitas.getDaftarAktivitas();
                    break;
                case "help":
                    System.out.println("Command Menu Aktivitas");
                    System.out.printf("%-15s - Lihat daftar aktivitas\n", "1");
                    System.out.printf("%-15s - Tambah aktivitas baru\n", "2");
                    System.out.printf("%-15s - Kembali ke menu\n", "0");
                    System.out.printf("%-15s - Mengelola suatu aktivitas\n", "edit <kode>");
                    break;
                case "0":
                    MenuUtama.tampilkan(scanner);
                    return;

                default:
                    break;
            }
        }
    }

    public static void getDaftarAktivitas() {
        ArrayList<Aktivitas> daftarAktivitas = Aktivitas.getSemua();

        if (daftarAktivitas.isEmpty()) {
            System.out.println("----------------------------------------------");
            System.out.println("|             Belum ada aktivitas             |");
            System.out.println("----------------------------------------------\n");
            return;
        }

        String garis = "-----------------------------------------------------------------------------------------------------------------";
        String header = String.format("| %-4s | %-10s | %-20s | %-10s | %-20s | %-30s |",
                "Kode", "Kode Lahan", "Nama", "Tanggal", "Nama Tumbuhan", "Catatan");

        System.out.println(garis);
        System.out.println(Displayer.colorizeText(header, ColorCode.CYAN, true));
        System.out.println(garis);

        for (Aktivitas l : daftarAktivitas) {

            String baris = String.format("| %-4d | %-10d | %-20s | %-10s | %-20s | %-30s |",
                    l.kode, l.kodeLahan, l.nama, l.tanggalMulai, l.namaTumbuhan, l.catatan);
            System.out.println(baris);
        }
        System.out.println(garis);
    }

    public static void tampilkanEditAktivitas(String kode, Scanner scanner) {
        Aktivitas obj = Aktivitas.getByKode(Integer.parseInt(kode));
        if (obj == null) {
            System.out.printf("Aktivitas dengan kode %s tidak ditemukan\n", kode);
            return;
        }

        System.out.println("Ketik 'help' untuk menampilkan list perintah.");

        while (true) {
            System.out.printf("\n%s (Aktivitas: %d)> ", Displayer.colorizeText("[RoTan]", ColorCode.KUNING, true),
                    obj.kode);
            String opsi = scanner.nextLine().trim();

            switch (opsi) {
                case "1":
                    System.out.printf("%-20s : %s\n", "Kode", obj.kode);
                    System.out.printf("%-20s : %s\n", "Kode Lahan", obj.kodeLahan);
                    System.out.printf("%-20s : %s\n", "Nama", obj.nama);
                    System.out.printf("%-20s : %s\n", "Tanggal Mulai", obj.tanggalMulai);
                    System.out.printf("%-20s : %s\n", "Nama Tumbuhan", obj.namaTumbuhan);
                    System.out.printf("%-20s : %s\n", "Luas", obj.luas);
                    System.out.printf("%-20s : %s\n", "Catatan", obj.catatan);
                    System.out.printf("%-20s : %s\n", "Status", obj.selesai);
                    break;
                case "2":
                    MenuAktivitas.tampilkanFaseAktivitas(obj.kode);
                    break;
                case "3":
                    MenuAktivitas.promptTambahFase(obj.kode, scanner);
                    break;
                case "4":
                    MenuAktivitas.rollbackFase(obj.kode);
                    break;
                case "help":
                    System.out.printf("Aktivitas: %d\n", obj.kode);
                    System.out.println("1. Lihat informasi tentang aktivitas ini");
                    System.out.println("2. Lihat fase aktivitas ini");
                    System.out.println("3. Tambahkan fase");
                    System.out.println("4. Rollback fase (mundur satu fase)");
                    System.out.println("0. Kembali ke menu aktivitas");
                    System.out.println("Ketikkan angka sesuai opsi menu");
                    break;
                case "0":
                    MenuAktivitas.tampilkan(scanner);
                    return;

                default:
                    break;
            }
        }
    }

    public static void tampilkanFaseAktivitas(int kode) {
        Stack<FaseAktivitas> arr = FaseAktivitas.getFaseAktivitas(kode);

        if (arr.size() == 0) {
            System.out.println("Aktivitas ini belum memiliki fase");
            return;
        }

        int index = 0;
        Iterator<FaseAktivitas> iter = arr.reversed().iterator();
        while (iter.hasNext()) {
            FaseAktivitas item = iter.next();

            System.out.printf("|\n");
            System.out.printf("%s  %s\n", Displayer.colorizeText("O", ColorCode.CYAN, true),
                    Displayer.colorizeText(item.tanggal.toString(), ColorCode.CYAN, true));
            if (index != arr.size() - 1) {
                System.out.printf("|  %s\n", item.catatan);
            } else {
                System.out.printf("   %s\n", item.catatan);
            }
            
            index += 1;
        }
    }

    public static void promptTambahFase(int kode, Scanner scanner) {
        System.out.printf(
                "Masukkan tanggal fase dengan format YYYY-MM-DD. Contoh: 2026-06-03\n(Ketik 's' untuk memilih tanggal hari ini): ");
        String tanggal = scanner.nextLine();
        System.out.printf("Masukkan catatan untuk fase kali ini: ");
        String catatan = scanner.nextLine();

        LocalDate tgl;

        if (tanggal.toLowerCase().equals("s")) {
            tgl = LocalDate.now();
        } else {
            try {
                tgl = LocalDate.parse(tanggal);
            } catch (Exception e) {
                System.out.println("Format tanggal tidak valid");
                return;
            }
        }

        try {
            FaseAktivitas.insert(new FaseAktivitas(kode, tgl, catatan));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }
    }

    public static void rollbackFase(int kode) {
        try {
            FaseAktivitas.rollbackFaseAktivitas(kode);
            System.out.println("Fase aktivitas ini berhasil dirollback");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }
    }
}
