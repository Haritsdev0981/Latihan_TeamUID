import java.util.ArrayList;
import java.util.Scanner;
import java.util.Date;

public class GlowInApp {

    private static ArrayList<String> nimPengguna = new ArrayList<>();
    private static ArrayList<String> namaPengguna = new ArrayList<>();
    private static ArrayList<Integer> totalPoin = new ArrayList<>();

    private static ArrayList<String> nimPoster = new ArrayList<>();
    private static ArrayList<String> kontenPostingan = new ArrayList<>();
    private static ArrayList<Long> waktuPostingan = new ArrayList<>();
    private static ArrayList<Integer> jumlahLike = new ArrayList<>();
    private static ArrayList<Integer> jumlahKomentar = new ArrayList<>();

    private static Scanner scanner = new Scanner(System.in);
    private static int indexUserAktif = -1;

    private static final int POIN_PER_LIKE = 10;
    private static final int POIN_PER_KOMENTAR = 5;

    public static void main(String[] args) {

        inisialisasiData();

        while (true) {
            tampilMenuLogin();

            int pilihan = -1;
            if (scanner.hasNextInt()) {
                pilihan = scanner.nextInt();
                scanner.nextLine();
            } else {
                System.out.println("Input tidak valid. Silakan masukkan angka.");
                scanner.nextLine();
                continue;
            }

            if (pilihan == 1) {
                aksiLogin();
            } else if (pilihan == 2) {
                System.out.println("Terima kasih telah menggunakan Glow-In. Sampai jumpa!");
                break;
            } else {

                System.out.println("Pilihan tidak valid.");
            }
        }
    }

    private static void inisialisasiData() {

        nimPengguna.add("607012500045");
        namaPengguna.add("Phelia Ramadhani");
        totalPoin.add(0);

        nimPengguna.add("607012500092");
        namaPengguna.add("Muhammad Sultan Dzaky");
        totalPoin.add(0);

        nimPoster.add("607012500045");
        kontenPostingan.add("Curhatan pertama: Aku merasa lost akhir-akhir ini. Butuh dukungan.");
        waktuPostingan.add(new Date().getTime() - 500000);
        jumlahLike.add(1);
        jumlahKomentar.add(0);

        nimPoster.add("607012500092");
        kontenPostingan.add("Curhatan kedua: Senang karena tugas selesai, tapi capek sekali.");
        waktuPostingan.add(new Date().getTime());
        jumlahLike.add(0);
        jumlahKomentar.add(0);
    }

    private static void tampilMenuLogin() {
        System.out.println("\n===== Glow-In Platform Curhat =====");
        System.out.println("1. Login");
        System.out.println("2. Keluar Program");
        System.out.print("Pilih aksi: ");
    }

    private static void aksiLogin() {
        System.out.print("Masukkan NIM/Username Anda: ");
        String inputId = scanner.nextLine().trim();

        indexUserAktif = -1;
        for (int i = 0; i < nimPengguna.size(); i++) {

            if (nimPengguna.get(i).equals(inputId) || namaPengguna.get(i).equalsIgnoreCase(inputId)) {
                indexUserAktif = i;
                break;
            }
        }

        if (indexUserAktif != -1) {
            System.out.println("Login berhasil! Selamat datang, " + namaPengguna.get(indexUserAktif) + ".");
            tampilMenuAksi();
        } else {
            System.out.println("NIM/Username tidak ditemukan. Login gagal.");
        }
    }

    private static void tampilMenuAksi() {
        int pilihan = -1;

        while (indexUserAktif != -1) {
            System.out.println("\n===== Menu Aksi (" + namaPengguna.get(indexUserAktif) + ") =====");
            System.out.println("1. Buat Postingan (Curhat)");
            System.out.println("2. Tampilkan Semua Postingan (Feed)");
            System.out.println("3. Tampilkan Postingan Saya");
            System.out.println("4. Hapus Postingan Terlama Saya");
            System.out.println("5. Tampilkan Poin Tertinggi");
            System.out.println("6. Logout");
            System.out.print("Pilih aksi: ");

            if (scanner.hasNextInt()) {
                pilihan = scanner.nextInt();
                scanner.nextLine();
            } else {
                System.out.println("Input tidak valid. Silakan masukkan angka.");
                scanner.nextLine();
                continue;
            }

            switch (pilihan) {
                case 1:
                    buatPostingan();
                    break;
                case 2:
                    tampilSemuaPostingan();
                    break;
                case 3:
                    tampilPostinganSaya();
                    break;
                case 4:
                    hapusPostinganTerlama();
                    break;
                case 5:
                    tampilIndeksPoinTerbesar();
                    break;
                case 6:
                    logout();
                    break;
                default:
                    System.out.println("Pilihan tidak valid.");
            }
        }
    }

    private static void buatPostingan() {
        System.out.println("\n--- Buat Postingan (Curhat) ---");
        System.out.print("Masukkan curhatan Anda: ");
        String konten = scanner.nextLine();

        nimPoster.add(nimPengguna.get(indexUserAktif));
        kontenPostingan.add(konten);
        waktuPostingan.add(new Date().getTime());
        jumlahLike.add(0);
        jumlahKomentar.add(0);

        System.out.println("✅ Postingan berhasil dibuat!");
    }

    private static void tampilSemuaPostingan() {
        System.out.println("\n--- Semua Postingan (Feed) ---");
        if (kontenPostingan.isEmpty()) {
            System.out.println("Belum ada postingan.");
            return;
        }

        for (int i = 0; i < kontenPostingan.size(); i++) {
            String nimPembuat = nimPoster.get(i);
            String namaPembuat = cariNamaByNim(nimPembuat);

            System.out.println("------------------------------------");
            System.out.println("Index: " + i);
            System.out.println("Pembuat: " + namaPembuat + " (" + nimPembuat + ")");
            System.out.println("Konten: " + kontenPostingan.get(i));
            System.out.println("Likes: " + jumlahLike.get(i) + " | Komentar: " + jumlahKomentar.get(i));
            System.out.println("Waktu: " + new Date(waktuPostingan.get(i)));
            System.out.println("------------------------------------");
        }

        tanyaAksiInteraksi();
    }

    private static void tanyaAksiInteraksi() {
        System.out.print("Apakah Anda ingin Like (L) atau Komentar (K) salah satu postingan? (L/K/Tidak): ");
        String aksi = scanner.nextLine().toUpperCase();

        if (aksi.equals("L") || aksi.equals("K")) {
            System.out.print("Masukkan Index Postingan yang ingin diinteraksi: ");

            int indexTarget = -1;
            if (scanner.hasNextInt()) {
                indexTarget = scanner.nextInt();
                scanner.nextLine();
            } else {
                System.out.println("Input Index tidak valid.");
                scanner.nextLine();
                return;
            }

            if (indexTarget >= 0 && indexTarget < kontenPostingan.size()) {
                String nimPembuat = nimPoster.get(indexTarget);

                if (aksi.equals("L")) {
                    aksiBeriLike(indexTarget, nimPembuat);
                } else {
                    aksiBeriKomentar(indexTarget, nimPembuat);
                }
            } else {
                System.out.println("Index Postingan tidak ditemukan.");
            }
        }
    }

    private static void aksiBeriLike(int indexTarget, String nimPembuat) {
        int likesBaru = jumlahLike.get(indexTarget) + 1;
        jumlahLike.set(indexTarget, likesBaru);

        int indexPembuat = cariIndexByNim(nimPembuat);
        if (indexPembuat != -1) {
            int poinBaru = totalPoin.get(indexPembuat) + POIN_PER_LIKE;
            totalPoin.set(indexPembuat, poinBaru);
            System.out.println("❤️ Anda menyukai postingan ini. Poin pembuat bertambah.");
        }
    }

    private static void aksiBeriKomentar(int indexTarget, String nimPembuat) {
        System.out.print("Masukkan komentar Anda: ");
        String komentar = scanner.nextLine();

        if (komentar.toLowerCase().contains("kasar") || komentar.toLowerCase().contains("negatif")) {
            System.out.println("❌ Komentar diblokir. Respon harus membangun dan positif (Sistem Respon Positif).");
            return;
        }

        int komentarBaru = jumlahKomentar.get(indexTarget) + 1;
        jumlahKomentar.set(indexTarget, komentarBaru);

        int indexPembuat = cariIndexByNim(nimPembuat);
        if (indexPembuat != -1) {
            int poinBaru = totalPoin.get(indexPembuat) + POIN_PER_KOMENTAR;
            totalPoin.set(indexPembuat, poinBaru);
            System.out.println("💬 Komentar berhasil ditambahkan! Poin pembuat bertambah.");
        }
    }

    private static void tampilPostinganSaya() {
        System.out.println("\n--- Postingan Saya ---");
        String nimSaya = nimPengguna.get(indexUserAktif);
        boolean found = false;

        for (int i = 0; i < kontenPostingan.size(); i++) {
            if (nimPoster.get(i).equals(nimSaya)) {
                System.out.println("------------------------------------");
                System.out.println("Index: " + i);
                System.out.println("Konten: " + kontenPostingan.get(i));
                System.out.println("Likes: " + jumlahLike.get(i) + " | Komentar: " + jumlahKomentar.get(i));
                System.out.println("------------------------------------");
                found = true;
            }
        }

        if (!found) {
            System.out.println("Anda belum memiliki postingan.");
        }
    }

    private static void hapusPostinganTerlama() {
        System.out.println("\n--- Hapus Postingan Terlama ---");
        String nimSaya = nimPengguna.get(indexUserAktif);

        long waktuTerlama = Long.MAX_VALUE;
        int indexTerlama = -1;

        for (int i = 0; i < kontenPostingan.size(); i++) {
            if (nimPoster.get(i).equals(nimSaya)) {
                if (waktuPostingan.get(i) < waktuTerlama) {
                    waktuTerlama = waktuPostingan.get(i);
                    indexTerlama = i;
                }
            }
        }

        if (indexTerlama != -1) {

            nimPoster.remove(indexTerlama);
            kontenPostingan.remove(indexTerlama);
            waktuPostingan.remove(indexTerlama);
            jumlahLike.remove(indexTerlama);
            jumlahKomentar.remove(indexTerlama);
            System.out.println("✅ Postingan terlama berhasil dihapus!");
        } else {
            System.out.println("Anda tidak memiliki postingan untuk dihapus.");
        }
    }

    private static void tampilIndeksPoinTerbesar() {
        System.out.println("\n--- Indeks Poin Pengguna Tertinggi ---");

        int n = nimPengguna.size();

        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {

                if (totalPoin.get(j) < totalPoin.get(j + 1)) {

                    int tempPoin = totalPoin.get(j);
                    totalPoin.set(j, totalPoin.get(j + 1));
                    totalPoin.set(j + 1, tempPoin);

                    String tempNama = namaPengguna.get(j);
                    namaPengguna.set(j, namaPengguna.get(j + 1));
                    namaPengguna.set(j + 1, tempNama);

                    String tempNim = nimPengguna.get(j);
                    nimPengguna.set(j, nimPengguna.get(j + 1));
                    nimPengguna.set(j + 1, tempNim);
                }
            }
        }

        for (int i = 0; i < nimPengguna.size(); i++) {
            System.out.println((i + 1) + ". " + namaPengguna.get(i) + " - " + totalPoin.get(i) + " Poin");
        }
    }

    private static String cariNamaByNim(String nim) {

        int index = cariIndexByNim(nim);
        if (index != -1) {
            return namaPengguna.get(index);
        }
        return "Pengguna Tidak Dikenal";
    }

    private static int cariIndexByNim(String nim) {

        for (int i = 0; i < nimPengguna.size(); i++) {
            if (nimPengguna.get(i).equals(nim)) {
                return i;
            }
        }
        return -1;
    }

    private static void logout() {
        System.out.println("Logout berhasil. Kembali ke menu utama.");
        indexUserAktif = -1;
    }
}