import java.io.File;
import java.util.Scanner;
import strukturdata.Matriks;

public class ImageCompression {

    public static void main(String[] args) {
        String asciiArt = """
            _____                 _ _____               _____                           
           |  _  |               | |_   _|             |_   _|                          
           | | | |_   _  __ _  __| | | |_ __ ___  ___    | | _ __ ___   __ _  __ _  ___ 
           | | | | | | |/ _` |/ _` | | | '__/ _ \\/ _ \\   | || '_ ` _ \\ / _` |/ _` |/ _ \\
           \\ \\/' / |_| | (_| | (_| | | | | |  __/  __/  _| || | | | | | (_| | (_| |  __/
            \\_/\\_\\\\__,_|\\__,_|\\__,_| \\_/_|  \\___|\\___|  \\___/_| |_| |_|\\__,_|\\__, |\\___|
            _____                                         _                   __/ |     
           /  __ \\                                       (_)                 |___/      
           | /  \\/ ___  _ __ ___  _ __  _ __ ___  ___ ___ _  ___  _ __                  
           | |    / _ \\| '_ ` _ \\| '_ \\| '__/ _ \\/ __/ __| |/ _ \\| '_ \\                 
           | \\__/\\ (_) | | | | | | |_) | | |  __/\\__ \\__ \\ | (_) | | | |                
            \\____/\\___/|_| |_| |_| .__/|_|  \\___||___/___/_|\\___/|_| |_|                
                                 | |                                                  
                                 |_|                                                  
          """;

        System.out.println(asciiArt);
        // Meminta input file
        String inputpath = InputOutputFile.inputFile();
        Matriks matrix = InputOutputFile.bacaFileGambar(inputpath);
        if(matrix == null) {
            System.out.println("Gagal membaca gambar. Program dihentikan.");
            return;
        }

        // Meminta input metode perhitungan error
        Scanner scanner = new Scanner(System.in);
        System.out.println("Pilih metode perhitungan error:");
        System.out.println("1. Variance");
        System.out.println("2. Mean Absolute Difference");
        System.out.println("3. Max Pixel Difference");
        System.out.println("4. Entropy");
        int error_method = 0;
        while (true) { 
           System.out.print("Masukkan pilihan (1-4): ");
           error_method = scanner.nextInt();
           if (error_method < 1 || error_method > 4) {
               System.out.println("Pilihan tidak valid! Coba lagi.");
               continue;
           } else {
               break;
           }
        }

        // Meminta input threshold
        double threshold = 0.0;
        while (true) {
            System.out.println("Masukkan ambang batas (threshold) : ");
            threshold = scanner.nextDouble();
            if (error_method == 1){
                if (threshold < 10 || threshold > 1000) {
                    System.out.println("Threshold tidak sesuai/invalid! Coba lagi.");
                    continue;
                }
            }
            else if (error_method == 2){
                if (threshold < 5 || threshold > 20) {
                    System.out.println("Threshold tidak sesuai/invalid! Coba lagi.");
                    continue;
                }
            }
            else if (error_method == 3){
                if (threshold < 5 || threshold > 25) {
                    System.out.println("Threshold tidak sesuai/invalid! Coba lagi.");
                    continue;
                }
            }
            else if (error_method == 4){
                if (threshold <0.5 || threshold > 3.0) {
                    System.out.println("Threshold tidak sesuai/invalid! Coba lagi.");
                    continue;
                }
            }
            break; 
        }

        // Meminta input ukuran blok minimum
        double minBlockSize = 0.0;
        while (true) {
            System.out.println("Masukkan ukuran blok minimum (luas piksel) : ");
            minBlockSize = scanner.nextDouble();
            scanner.nextLine();
            if (minBlockSize < 4 || minBlockSize > matrix.baris || minBlockSize > matrix.kolom) {
                System.out.println("Ukuran minimum blok tidak valid! Coba lagi.");
                continue;
            } else {
                break;
            }
        }

        // Meminta alamat output gambar
        String outputpath;
        while (true) {
            System.out.println("Masukkan alamat absolut gambar hasil kompresi : ");
            outputpath = scanner.nextLine().trim();
            if (outputpath.isEmpty()) {
                System.out.println("Alamat gambar tidak boleh kosong! Coba lagi.");
                continue;
            }
            else {
                break;
            }
        }

        // Meminta alamat output gif
        String output_gif;
        while (true) { 
            System.out.println("Masukkan alamat absolut output GIF (contoh: path/compression.gif): ");
            output_gif = scanner.nextLine().trim();
            if (output_gif.isEmpty()) {
                System.out.println("Alamat GIF tidak boleh kosong! Coba lagi.");
                continue;
            }
            else {
                break;
            }
            
        }
        

        System.out.println();
        System.out.println("=========================================");
        System.out.println("Gambar sedang diproses...");

        // Inisialisasi
        int width = matrix.kolom; 
        int height = matrix.baris;
        QuadTreeNode root = new QuadTreeNode(matrix, 0, 0, width, height, 0);

        long start_time = System.currentTimeMillis();

        // Compression
        QuadTreeNode.divideBlockRecursively(root, matrix, minBlockSize, error_method, threshold); // divide n conquer              
        Matriks compressed = root.createImage(); // combine
        InputOutputFile.outputFile(compressed, inputpath, outputpath); // output

        long end_time = System.currentTimeMillis();


        System.out.println("Waktu eksekusi: " + (end_time - start_time) + " ms");
        File original = new File(inputpath);
        File hasil = new File(outputpath);
        double persentase = hitungPersentaseKompresi(original, hasil);
        System.out.printf("Persentase kompresi: %.2f%%\n", persentase);
        System.out.println("Banyak Simpul Quadtree: " + root.countNodes());
        System.out.println("Kedalaman QuadTree: " + root.getMaxDepth());

        // Membuat GIF dari hasil kompresi per kedalaman
        int maxDepth = root.getMaxDepth();

        // Membuat GIF
        Gif.createGif(root, maxDepth, output_gif);
    }

    public static double hitungPersentaseKompresi(File originalFile, File compressedFile) {
        if (!originalFile.exists() || !compressedFile.exists()) {
            throw new IllegalArgumentException("Salah satu file tidak ditemukan.");
        }

        long originalSize = originalFile.length();
        System.out.println("Ukuran gambar asli: " + originalSize + " bytes");
        long compressedSize = compressedFile.length();
        System.out.println("Ukuran gambar hasil kompresi: " + compressedSize + " bytes");

        if (originalSize == 0) {
            throw new IllegalArgumentException("Ukuran file asli tidak boleh nol.");
        }

        double ratio = 1.0 - ((double) compressedSize / originalSize);
        return ratio * 100;
    }
}