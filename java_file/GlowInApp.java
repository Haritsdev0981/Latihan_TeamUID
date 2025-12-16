import java.util.ArrayList;
import java.util.Date;
import java.util.Comparator;
import java.util.Scanner;
import java.util.stream.Collectors;

// --- 1. Kelas Pengguna ---
class Pengguna {
    private String nim;
    private String namaPengguna;
    private String email;
    private int totalPoin;

    public Pengguna(String nim, String namaPengguna, String email) {
        this.nim = nim;
        this.namaPengguna = namaPengguna;
        this.email = email;
        this.totalPoin = 0;
    }

    // Getters and Setters
    public String getNim() { return nim; }
    public String getNamaPengguna() { return namaPengguna; }
    public int getTotalPoin() { return totalPoin; }
    public void tambahPoin(int poin) { this.totalPoin += poin; }

    @Override
    public String toString() {
        return "NIM: " + nim + ", Username: " + namaPengguna + ", Poin: " + totalPoin;
    }
}

// --- 2. Kelas Postingan ---
class Postingan {
    private static int counter = 0;
    private int idPostingan;
    private String nimPengguna;
    private String konten;
    private Date waktuPosting;
    private int jumlahLike;
    private int jumlahKomentar; // Komentar digunakan untuk perhitungan poin

    public Postingan(String nimPengguna, String konten) {
        this.idPostingan = ++counter;
        this.nimPengguna = nimPengguna;
        this.konten = konten;
        this.waktuPosting = new Date();
        this.jumlahLike = 0;
        this.jumlahKomentar = 0;
    }

    // Getters and Setters
    public int getIdPostingan() { return idPostingan; }
    public String getNimPengguna() { return nimPengguna; }
    public String getKonten() { return konten; }
    public Date getWaktuPosting() { return waktuPosting; }
    public int getJumlahLike() { return jumlahLike; }
    public int getJumlahKomentar() { return jumlahKomentar; }

    public void tambahLike() { this.jumlahLike++; }
    public void tambahKomentar() { this.jumlahKomentar++; }

    @Override
    public String toString() {
        return "ID: " + idPostingan +
               " | NIM: " + nimPengguna +
               " | Waktu: " + waktuPosting +
               "\n   Konten: " + konten +
               "\n   Likes: " + jumlahLike +
               " | Komentar: " + jumlahKomentar;
    }
}

// --- 3. Kelas Utama Aplikasi ---
public class GlowInApp {
    private static ArrayList<Pengguna> listPengguna = new ArrayList<>();
    private static ArrayList<Postingan> listPostingan = new ArrayList<>();
    private static Pengguna currentUser = null;
    private static Scanner scanner = new Scanner(System.in);
    
    // Konstanta Poin
    private static final int POIN_PER_LIKE = 10;
    private static final int POIN_PER_KOMENTAR = 5;

    public static void main(String[] args) {
        // Inisialisasi data dummy untuk pengujian
        inisialisasiData();
        
        while (true) {
            System.out.println("\n===== Glow-In Platform Curhat =====");
            System.out.println("1. Login");
            System.out.println("2. Keluar Program");
            System.out.print("Pilih aksi: ");
            
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
                System.out.println("Pilihan tidak valid.");
            }
        }
        scanner.close();
    }
    
    // --- Method Inisialisasi Data ---
    private static void inisialisasiData() {
        // User 1
        listPengguna.add(new Pengguna("607012500045", "Phelia Ramadhani", "phelia@mail.com"));
        // User 2
        listPengguna.add(new Pengguna("607012500092", "Muhammad Sultan Dzaky", "sultan@mail.com"));
        
        // Postingan dummy
        listPostingan.add(new Postingan("607012500045", "Curhatan pertama: Aku merasa lost akhir-akhir ini. Butuh dukungan."));
        listPostingan.get(0).tambahLike(); // Tambah like manual
        listPostingan.add(new Postingan("607012500092", "Curhatan kedua: Senang karena tugas selesai, tapi capek sekali."));
    }

    // --- Method Utama Alur Login ---
    private static void aksiLogin() {
        System.out.print("Masukkan NIM/Username Anda: ");
        String inputId = scanner.nextLine().trim();
        
        // Cari pengguna
        for (Pengguna p : listPengguna) {
            if (p.getNim().equals(inputId) || p.getNamaPengguna().equalsIgnoreCase(inputId)) {
                currentUser = p;
                System.out.println("Login berhasil! Selamat datang, " + currentUser.getNamaPengguna() + ".");
                tampilMenuAksi();
                return;
            }
        }
        System.out.println("NIM/Username tidak ditemukan. Login gagal.");
    }
    
    // --- Method Utama Menu Aksi Setelah Login ---
    private static void tampilMenuAksi() {
        int pilihan = -1;
        while (currentUser != null) {
            System.out.println("\n===== Menu Aksi (" + currentUser.getNamaPengguna() + ") =====");
            System.out.println("1. Buat Postingan (Curhat)");
            System.out.println("2. Tampilkan Semua Postingan (Feed)");
            System.out.println("3. Tampilkan Postingan Saya");
            System.out.println("4. Hapus Postingan Terlama Saya");
            System.out.println("5. Tampilkan Poin Tertinggi"); // Fungsionalitas tambahan
            System.out.println("6. Logout");
            System.out.print("Pilih aksi: ");
            
            if (scanner.hasNextInt()) {
                pilihan = scanner.nextInt();
                scanner.nextLine(); // Consume newline
            } else {
                System.out.println("Input tidak valid. Silakan masukkan angka.");
                scanner.nextLine(); // Consume invalid input
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
    
    // --- Method 1: Buat Postingan ---
    private static void buatPostingan() {
        System.out.println("\n--- Buat Postingan (Curhat) ---");
        System.out.print("Masukkan curhatan Anda: ");
        String konten = scanner.nextLine();
        
        Postingan baru = new Postingan(currentUser.getNim(), konten);
        listPostingan.add(baru);
        System.out.println("✅ Postingan berhasil dibuat! ID: " + baru.getIdPostingan());
    }
    
    // --- Method 2: Tampilkan Semua Postingan (Feed) ---
    private static void tampilSemuaPostingan() {
        System.out.println("\n--- Semua Postingan (Feed) ---");
        if (listPostingan.isEmpty()) {
            System.out.println("Belum ada postingan.");
            return;
        }
        
        for (Postingan p : listPostingan) {
            System.out.println("------------------------------------");
            System.out.println(p.toString());
            System.out.println("------------------------------------");
        }
        
        // Opsi interaksi (Like/Komentar)
        System.out.print("Apakah Anda ingin Like (L) atau Komentar (K) salah satu postingan? (L/K/Tidak): ");
        String aksi = scanner.nextLine().toUpperCase();
        
        if (aksi.equals("L") || aksi.equals("K")) {
            System.out.print("Masukkan ID Postingan yang ingin diinteraksi: ");
            if (scanner.hasNextInt()) {
                int idTarget = scanner.nextInt();
                scanner.nextLine();
                
                Postingan targetPost = cariPostinganById(idTarget);
                if (targetPost == null) {
                    System.out.println("ID Postingan tidak ditemukan.");
                    return;
                }
                
                if (aksi.equals("L")) {
                    beriLike(targetPost);
                } else {
                    beriKomentar(targetPost);
                }
            } else {
                System.out.println("Input ID tidak valid.");
                scanner.nextLine();
            }
        }
    }
    
    // --- Method Bantuan: Cari Postingan ---
    private static Postingan cariPostinganById(int id) {
        for (Postingan p : listPostingan) {
            if (p.getIdPostingan() == id) {
                return p;
            }
        }
        return null;
    }

    // --- Method Interaksi: Like ---
    private static void beriLike(Postingan post) {
        // Implementasi sederhana: user bisa like tanpa double check
        post.tambahLike();
        
        // Tambahkan poin ke pembuat postingan (Poin: 1 Like = 10 Poin)
        Pengguna pembuat = cariPenggunaByNim(post.getNimPengguna());
        if (pembuat != null) {
            pembuat.tambahPoin(POIN_PER_LIKE);
            System.out.println("❤️ Anda menyukai postingan ini. Poin pembuat bertambah.");
        }
    }
    
    // --- Method Interaksi: Komentar ---
    private static void beriKomentar(Postingan post) {
        System.out.print("Masukkan komentar Anda: ");
        String komentar = scanner.nextLine();
        
        // Simulai Filter Kata Kasar (Validasi Konten)
        if (komentar.toLowerCase().contains("kasar") || komentar.toLowerCase().contains("negatif")) {
            System.out.println("❌ Komentar diblokir. Respon harus membangun dan positif.");
            return;
        }
        
        // Jika lolos filter
        post.tambahKomentar();
        
        // Tambahkan poin ke pembuat postingan (Poin: 1 Komentar = 5 Poin)
        Pengguna pembuat = cariPenggunaByNim(post.getNimPengguna());
        if (pembuat != null) {
            pembuat.tambahPoin(POIN_PER_KOMENTAR);
            System.out.println("💬 Komentar berhasil ditambahkan! Poin pembuat bertambah.");
        }
        
        // Catatan: Dalam aplikasi nyata, komentar ini akan disimpan di ArrayList terpisah (listKomentar).
    }

    // --- Method 3: Tampilkan Postingan Saya ---
    private static void tampilPostinganSaya() {
        System.out.println("\n--- Postingan Saya ---");
        
        boolean found = false;
        for (Postingan p : listPostingan) {
            if (p.getNimPengguna().equals(currentUser.getNim())) {
                System.out.println("------------------------------------");
                System.out.println(p.toString());
                found = true;
            }
        }
        if (!found) {
            System.out.println("Anda belum memiliki postingan.");
        }
    }
    
    // --- Method 4: Hapus Postingan Terlama Saya ---
    private static void hapusPostinganTerlama() {
        System.out.println("\n--- Hapus Postingan Terlama ---");
        
        // Filter postingan milik user saat ini
        ArrayList<Postingan> myPosts = listPostingan.stream()
            .filter(p -> p.getNimPengguna().equals(currentUser.getNim()))
            .collect(Collectors.toCollection(ArrayList::new));

        if (myPosts.isEmpty()) {
            System.out.println("Anda tidak memiliki postingan untuk dihapus.");
            return;
        }
        
        // Cari postingan dengan waktu posting paling awal
        Postingan terlama = myPosts.stream()
            .min(Comparator.comparing(Postingan::getWaktuPosting))
            .orElse(null);

        if (terlama != null) {
            listPostingan.remove(terlama);
            System.out.println("✅ Postingan terlama (ID: " + terlama.getIdPostingan() + ") berhasil dihapus.");
        } else {
            System.out.println("Gagal menemukan postingan terlama.");
        }
    }

    // --- Method 5: Tampilkan Poin Tertinggi ---
    private static void tampilIndeksPoinTerbesar() {
        System.out.println("\n--- Indeks Poin Pengguna Tertinggi ---");
        
        // Urutkan pengguna berdasarkan totalPoin secara menurun
        listPengguna.sort(Comparator.comparing(Pengguna::getTotalPoin).reversed());
        
        if (listPengguna.isEmpty()) {
            System.out.println("Belum ada data pengguna.");
            return;
        }

        int rank = 1;
        for (Pengguna p : listPengguna) {
            System.out.println(rank++ + ". " + p.getNamaPengguna() + " - " + p.getTotalPoin() + " Poin");
        }
    }
    
    // --- Method Bantuan: Cari Pengguna ---
    private static Pengguna cariPenggunaByNim(String nim) {
        for (Pengguna p : listPengguna) {
            if (p.getNim().equals(nim)) {
                return p;
            }
        }
        return null;
    }

    // --- Method 6: Logout ---
    private static void logout() {
        System.out.println("Logout berhasil. Kembali ke menu utama.");
        currentUser = null;
    }
}