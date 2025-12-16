import java.util.ArrayList;
import java.util.Scanner;
import java.util.Date; // Digunakan untuk mendapatkan waktu saat ini

public class GlowInModularApp {

    // =================================================================
    // A. DEKLARASI STRUKTUR DATA (Parallel ArrayLists)
    // Semua data disimpan dalam list paralel, diakses berdasarkan indeks.
    // =================================================================

    // Data Pengguna
    private static ArrayList<String> nimPengguna = new ArrayList<>();
    private static ArrayList<String> namaPengguna = new ArrayList<>();
    private static ArrayList<Integer> totalPoin = new ArrayList<>();

    // Data Postingan
    // Catatan: Tidak menggunakan ID unik karena diakses berdasarkan index/waktu
    private static ArrayList<String> nimPoster = new ArrayList<>(); // NIM pembuat postingan
    private static ArrayList<String> kontenPostingan = new ArrayList<>();
    private static ArrayList<Long> waktuPostingan = new ArrayList<>(); // Timestamp (milidetik)
    private static ArrayList<Integer> jumlahLike = new ArrayList<>();
    private static ArrayList<Integer> jumlahKomentar = new ArrayList<>(); // Hanya hitungan

    // Variabel Sesi
    private static Scanner scanner = new Scanner(System.in);
    private static int indexUserAktif = -1; // -1 berarti belum login
    
    // Konstanta Poin
    private static final int POIN_PER_LIKE = 10;
    private static final int POIN_PER_KOMENTAR = 5;

    public static void main(String[] args) {
        // Inisialisasi data awal (sekuensial)
        inisialisasiData();
        
        // Perulangan utama program (Loop Dasar)
        while (true) {
            tampilMenuLogin();
            
            int pilihan = -1;
            if (scanner.hasNextInt()) {
                pilihan = scanner.nextInt();
                scanner.nextLine(); // Consume newline
            } else {
                System.out.println("Input tidak valid. Silakan masukkan angka.");
                scanner.nextLine(); // Consume invalid input
                continue;
            }

            if (pilihan == 1) {
                aksiLogin();
            } else if (pilihan == 2) {
                System.out.println("Terima kasih telah menggunakan Glow-In. Sampai jumpa!");
                break;
            } else {
                // Percabangan
                System.out.println("Pilihan tidak valid.");
            }
        }
    }
    
    // --- Method 0. Inisialisasi Data (Modular) ---
    private static void inisialisasiData() {
        // User 1
        nimPengguna.add("607012500045");
        namaPengguna.add("Phelia Ramadhani");
        totalPoin.add(0);
        
        // User 2
        nimPengguna.add("607012500092");
        namaPengguna.add("Muhammad Sultan Dzaky");
        totalPoin.add(0);
        
        // Postingan dummy 1
        nimPoster.add("607012500045");
        kontenPostingan.add("Curhatan pertama: Aku merasa lost akhir-akhir ini. Butuh dukungan.");
        waktuPostingan.add(new Date().getTime() - 500000); // Waktu lebih lama
        jumlahLike.add(1); 
        jumlahKomentar.add(0);
        
        // Postingan dummy 2
        nimPoster.add("607012500092");
        kontenPostingan.add("Curhatan kedua: Senang karena tugas selesai, tapi capek sekali.");
        waktuPostingan.add(new Date().getTime()); // Waktu sekarang
        jumlahLike.add(0);
        jumlahKomentar.add(0);
    }
    
    // --- Method Tampilan Menu Awal ---
    private static void tampilMenuLogin() {
        System.out.println("\n===== Glow-In Platform Curhat =====");
        System.out.println("1. Login");
        System.out.println("2. Keluar Program");
        System.out.print("Pilih aksi: ");
    }

    // --- Method 1. Aksi Login (Percabangan & Perulangan) ---
    private static void aksiLogin() {
        System.out.print("Masukkan NIM/Username Anda: ");
        String inputId = scanner.nextLine().trim();
        
        // Mencari user (Perulangan Dasar)
        indexUserAktif = -1;
        for (int i = 0; i < nimPengguna.size(); i++) {
            // Percabangan
            if (nimPengguna.get(i).equals(inputId) || namaPengguna.get(i).equalsIgnoreCase(inputId)) {
                indexUserAktif = i;
                break;
            }
        }
        
        // Percabangan
        if (indexUserAktif != -1) {
            System.out.println("Login berhasil! Selamat datang, " + namaPengguna.get(indexUserAktif) + ".");
            tampilMenuAksi(); // Masuk ke menu utama setelah login
        } else {
            System.out.println("NIM/Username tidak ditemukan. Login gagal.");
        }
    }
    
    // --- Method 2. Tampil Menu Aksi (Loop & Percabangan) ---
    private static void tampilMenuAksi() {
        int pilihan = -1;
        // Loop selama user aktif (sudah login)
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
            
            // Percabangan
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
    
    // --- Method 3. Buat Postingan (CRUD: Create/Add) ---
    private static void buatPostingan() {
        System.out.println("\n--- Buat Postingan (Curhat) ---");
        System.out.print("Masukkan curhatan Anda: ");
        String konten = scanner.nextLine();
        
        // Menambahkan data secara paralel ke semua ArrayList (Sekuensial)
        nimPoster.add(nimPengguna.get(indexUserAktif));
        kontenPostingan.add(konten);
        waktuPostingan.add(new Date().getTime());
        jumlahLike.add(0);
        jumlahKomentar.add(0);
        
        System.out.println("✅ Postingan berhasil dibuat!");
    }
    
    // --- Method 4. Tampilkan Semua Postingan (CRUD: Read/Loop) ---
    private static void tampilSemuaPostingan() {
        System.out.println("\n--- Semua Postingan (Feed) ---");
        if (kontenPostingan.isEmpty()) {
            System.out.println("Belum ada postingan.");
            return;
        }
        
        // Perulangan untuk menampilkan semua data Postingan
        for (int i = 0; i < kontenPostingan.size(); i++) {
            String nimPembuat = nimPoster.get(i);
            String namaPembuat = cariNamaByNim(nimPembuat);
            
            System.out.println("------------------------------------");
            System.out.println("Index: " + i); // Tampilkan index untuk memudahkan interaksi
            System.out.println("Pembuat: " + namaPembuat + " (" + nimPembuat + ")");
            System.out.println("Konten: " + kontenPostingan.get(i));
            System.out.println("Likes: " + jumlahLike.get(i) + " | Komentar: " + jumlahKomentar.get(i));
            System.out.println("Waktu: " + new Date(waktuPostingan.get(i)));
            System.out.println("------------------------------------");
        }
        
        // Opsi interaksi (Like/Komentar)
        tanyaAksiInteraksi();
    }
    
    // --- Method 5. Interaksi Like/Komentar ---
    private static void tanyaAksiInteraksi() {
        System.out.print("Apakah Anda ingin Like (L) atau Komentar (K) salah satu postingan? (L/K/Tidak): ");
        String aksi = scanner.nextLine().toUpperCase();
        
        // Percabangan
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

            // Percabangan
            if (indexTarget >= 0 && indexTarget < kontenPostingan.size()) {
                String nimPembuat = nimPoster.get(indexTarget);
                // Percabangan
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
    
    // --- Method 5a. Beri Like (CRUD: Update) ---
    private static void aksiBeriLike(int indexTarget, String nimPembuat) {
        int likesBaru = jumlahLike.get(indexTarget) + 1;
        jumlahLike.set(indexTarget, likesBaru); // Update data di ArrayList
        
        // Update Poin Pembuat
        int indexPembuat = cariIndexByNim(nimPembuat);
        if (indexPembuat != -1) {
            int poinBaru = totalPoin.get(indexPembuat) + POIN_PER_LIKE;
            totalPoin.set(indexPembuat, poinBaru);
            System.out.println("❤️ Anda menyukai postingan ini. Poin pembuat bertambah.");
        }
    }
    
    // --- Method 5b. Beri Komentar (CRUD: Update) ---
    private static void aksiBeriKomentar(int indexTarget, String nimPembuat) {
        System.out.print("Masukkan komentar Anda: ");
        String komentar = scanner.nextLine();
        
        // Simulasi Filter Kata Kasar (Percabangan)
        if (komentar.toLowerCase().contains("kasar") || komentar.toLowerCase().contains("negatif")) {
            System.out.println("❌ Komentar diblokir. Respon harus membangun dan positif (Sistem Respon Positif).");
            return;
        }
        
        // Jika lolos filter (Sekuensial & Update)
        int komentarBaru = jumlahKomentar.get(indexTarget) + 1;
        jumlahKomentar.set(indexTarget, komentarBaru);
        
        // Update Poin Pembuat
        int indexPembuat = cariIndexByNim(nimPembuat);
        if (indexPembuat != -1) {
            int poinBaru = totalPoin.get(indexPembuat) + POIN_PER_KOMENTAR;
            totalPoin.set(indexPembuat, poinBaru);
            System.out.println("💬 Komentar berhasil ditambahkan! Poin pembuat bertambah.");
        }
    }

    // --- Method 6. Tampilkan Postingan Saya (CRUD: Read/Filter) ---
    private static void tampilPostinganSaya() {
        System.out.println("\n--- Postingan Saya ---");
        String nimSaya = nimPengguna.get(indexUserAktif);
        boolean found = false;
        
        // Perulangan untuk memfilter postingan berdasarkan NIM (Percabangan)
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
        
        // Percabangan
        if (!found) {
            System.out.println("Anda belum memiliki postingan.");
        }
    }
    
    // --- Method 7. Hapus Postingan Terlama (CRUD: Delete) ---
    private static void hapusPostinganTerlama() {
        System.out.println("\n--- Hapus Postingan Terlama ---");
        String nimSaya = nimPengguna.get(indexUserAktif);
        
        long waktuTerlama = Long.MAX_VALUE;
        int indexTerlama = -1;
        
        // Perulangan untuk mencari index postingan terlama milik user aktif
        for (int i = 0; i < kontenPostingan.size(); i++) {
            if (nimPoster.get(i).equals(nimSaya)) {
                if (waktuPostingan.get(i) < waktuTerlama) {
                    waktuTerlama = waktuPostingan.get(i);
                    indexTerlama = i;
                }
            }
        }
        
        // Percabangan
        if (indexTerlama != -1) {
            // Hapus data secara paralel dari semua ArrayList (Sekuensial)
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

    // --- Method 8. Tampilkan Poin Tertinggi (Perulangan Bersarang/Sorting) ---
    private static void tampilIndeksPoinTerbesar() {
        System.out.println("\n--- Indeks Poin Pengguna Tertinggi ---");
        
        // Karena tidak menggunakan OOP, kita perlu membuat Array sementara
        // untuk mengurutkan NIM dan Poin secara bersamaan (mirip Array Multidimensi)
        
        int n = nimPengguna.size();
        
        // Kita gunakan algoritma Bubble Sort sederhana untuk mengurutkan Poin
        for (int i = 0; i < n - 1; i++) { // Loop Luar
            for (int j = 0; j < n - i - 1; j++) { // Loop Dalam (Perulangan Bersarang)
                // Jika Poin [j] lebih kecil dari Poin [j+1], tukar
                if (totalPoin.get(j) < totalPoin.get(j + 1)) {
                    // Tukar Poin
                    int tempPoin = totalPoin.get(j);
                    totalPoin.set(j, totalPoin.get(j + 1));
                    totalPoin.set(j + 1, tempPoin);
                    
                    // Tukar Nama (harus dilakukan secara paralel)
                    String tempNama = namaPengguna.get(j);
                    namaPengguna.set(j, namaPengguna.get(j + 1));
                    namaPengguna.set(j + 1, tempNama);
                    
                    // Tukar NIM (harus dilakukan secara paralel)
                    String tempNim = nimPengguna.get(j);
                    nimPengguna.set(j, nimPengguna.get(j + 1));
                    nimPengguna.set(j + 1, tempNim);
                }
            }
        }
        
        // Tampilkan hasil setelah diurutkan
        for (int i = 0; i < nimPengguna.size(); i++) {
            System.out.println((i + 1) + ". " + namaPengguna.get(i) + " - " + totalPoin.get(i) + " Poin");
        }
    }
    
    // --- Method Bantuan (Modular) ---
    private static String cariNamaByNim(String nim) {
        // Perulangan untuk mencari nama berdasarkan NIM
        int index = cariIndexByNim(nim);
        if (index != -1) {
            return namaPengguna.get(index);
        }
        return "Pengguna Tidak Dikenal";
    }
    
    private static int cariIndexByNim(String nim) {
        // Perulangan untuk mencari index berdasarkan NIM
        for (int i = 0; i < nimPengguna.size(); i++) {
            if (nimPengguna.get(i).equals(nim)) {
                return i;
            }
        }
        return -1;
    }
    
    // --- Method 9. Logout ---
    private static void logout() {
        System.out.println("Logout berhasil. Kembali ke menu utama.");
        indexUserAktif = -1; // Reset status login
    }
}