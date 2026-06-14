  Monster Collection Game

  Deskripsi Singkat
**Monster Collection Game** adalah sebuah permainan interaktif berbasis *Fullstack* yang berfokus pada mekanisme pengumpulan 
dan pengelolaan entitas monster. Dibangun menggunakan **JavaFX** untuk antarmuka pengguna (Frontend) dan **Spring Boot** 
untuk REST API serta manajemen data (Backend). Pemain dapat melakukan registrasi, memilih monster pertama mereka, 
melakukan eksplorasi untuk menangkap monster baru, dan bersaing skor dengan pemain lain di papan peringkat.

---

  Fitur Utama
* **Autentikasi Aman:** Sistem *Login* dan *Register* pemain yang terintegrasi dengan *database*.
* **Starter Monster Selection:** Pemain baru dapat memilih 1 dari beberapa monster elemen dasar untuk memulai petualangan.
* **Sistem Eksplorasi (Gacha):** Fitur untuk mencari dan mendapatkan monster acak dengan tingkat *rarity* (kelangkaan) yang berbeda.
* **Dashboard Koleksi:** Tampilan visual untuk melihat dan mengelola seluruh monster yang telah berhasil ditangkap.
* **Battle System:** Mekanisme pertarungan antar monster untuk meningkatkan level dan *experience*.
* **Global Leaderboard:** Papan peringkat dinamis yang menampilkan daftar pemain dengan koleksi atau skor tertinggi.

---

  Prasyarat (Dependencies)
Sebelum menjalankan aplikasi, pastikan perangkat Anda telah menginstal:
* **Java Development Kit (JDK):** Versi 21 (LTS).
* **Maven:** Untuk manajemen dependensi.
* **Database:** H2 Database (Embedded, tidak perlu instalasi server *database* eksternal).
* **IDE:** IntelliJ IDEA (Disarankan) atau VS Code.

---

  Cara Menjalankan Aplikasi (Instalasi)

Karena aplikasi ini menggunakan arsitektur *Monorepo* (terbagi menjadi folder `frontend` dan `backend`), 
1. Kloning Repositori
git clone [https://github.com/Muhammad-haqi/UAS-PBO_-Monster-Collection-Game-_-HIJV-.git](https://github.com/Muhammad-haqi/UAS-PBO_-Monster-Collection-Game-_-HIJV-.git)
cd UAS-PBO_-Monster-Collection-Game-_-HIJV-

2. Menjalankan Backend (Spring Boot)
Backend harus berjalan lebih dahulu agar Frontend dapat mengambil data melalui API.
-Buka folder repositori di IDE (IntelliJ IDEA).
-Masuk ke direktori backend.
-Tunggu Maven menyelesaikan unduhan dependencies (JPA, Lombok, Spring Web, H2).
-Buka file src/main/java/.../ProjekGameApplication.java.
-Klik tombol Run (Segitiga Hijau). Server akan berjalan di localhost:8080.

3. Menjalankan Frontend (JavaFX)
-Buka tab atau window IDE baru.
-Masuk ke direktori frontend.
-Tunggu Maven menyelesaikan proses sinkronisasi JavaFX.
-Buka file src/main/java/.../Main.java.
-Klik tombol Run. Jendela permainan Monster Collection Game akan langsung terbuka!

LINK VIDEO YOUTUBE
##########################################

