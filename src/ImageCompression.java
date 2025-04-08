import strukturdata.Matriks;
import java.util.Scanner;

public class ImageCompression {


    public static void main(String[] args) {
        String inputPath = InputOutputFile.inputFile();
        Matriks matrix = InputOutputFile.bacaFileGambar(inputPath);
        if(matrix == null) {
            System.out.println("Gagal membaca gambar. Program dihentikan.");
            return;
        }
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
            if (error_method==1){
                if (threshold <10 || threshold > 1000) {
                    System.out.println("Threshold tidak sesuai/invalid! Coba lagi.");
                    continue;
                }
            }
            else if (error_method==2){
                if (threshold <5 || threshold > 20) {
                    System.out.println("Threshold tidak sesuai/invalid! Coba lagi.");
                    continue;
                }
            }
            else if (error_method==3){
                if (threshold <5 || threshold > 25) {
                    System.out.println("Threshold tidak sesuai/invalid! Coba lagi.");
                    continue;
                }
            }
            else if (error_method==4){
                if (threshold <0.5 || threshold > 3.0) {
                    System.out.println("Threshold tidak sesuai/invalid! Coba lagi.");
                    continue;
                }
            }
            break; 
      }
        double minBlockSize = 0.0;
        while (true) {
            System.out.println("Masukkan ukuran blok minimum (luas piksel) : ");
            minBlockSize = scanner.nextDouble();
            if (minBlockSize <= 0 || minBlockSize > matrix.baris || minBlockSize > matrix.kolom) {
                System.out.println("Ukuran minimum blok tidak valid! Coba lagi.");
                continue;
            } else {
                break;
            }
        }
        int width = matrix.kolom; 
        int height = matrix.baris;
        QuadTreeNode root = new QuadTreeNode(matrix, 0, 0, width, height, 0);

        QuadTreeNode.divideBlockRecursively(root, matrix, minBlockSize, error_method, threshold);                
        Matriks compressed = root.createImage();
        InputOutputFile.outputFile(compressed, inputPath);
        System.out.println("Total nodes: " + root.countNodes());
        System.out.println("Max depth: " + root.getMaxDepth());
    }
}