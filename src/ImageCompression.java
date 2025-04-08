import java.awt.im.InputContext;
import java.util.Scanner;

import strukturdata.Matriks;

public class ImageCompression {

    // public static boolean checkThresholdMethodError(int error_method) {
    //     if (error_method = 1) {
    //         // apa
    //     }
    //     if (error_method)
    //     return true;
    // }
    
    // Menghasilkan gambar yang sudah dikompresi dengan quadtree
    public static void generateCompressedImage() {

    }

    public static void main(String[] args) {
        
        // Membaca input file gambar
        String inputpath = InputOutputFile.inputFile();
        Matriks image_data = InputOutputFile.bacaFileGambar(inputpath);
        // if (image_data != null) {
        //     System.out.println("Matriks pixel dari gambar:");
        //     image_data.saveMatrixToText(image_data, "../src/image_data.txt");
        // } else {
        //     System.out.println("Gagal membaca file gambar.");
        // }

        // Meminta input pilihan metode error
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
            // Uncomment and implement the condition below if needed
            // if (!checkThresholdMethodError(error_method)) {
            //     System.out.println("Threshold tidak sesuai/invalid! Coba lagi.");
            //     continue;
            // } 
            break; 
        }

        // Meminta input minimum block size
        
        while (true) {
            int minblock_size;
            System.out.println("Masukkan ukuran minimum blok : ");
            minblock_size = scanner.nextInt();
            if (minblock_size <= 0 || minblock_size > image_data.baris || minblock_size > image_data.kolom) {
                System.out.println("Ukuran minimum blok tidak valid! Coba lagi.");
                continue;
            } else {
                break;
            }
        }

        // Output Testing
        
        InputOutputFile.outputFile(image_data, inputpath);


    }
}