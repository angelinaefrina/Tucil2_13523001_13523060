import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Scanner;
import javax.imageio.ImageIO;
import strukturdata.*;

public class InputOutputFile {
    
    // Meminta input alamat absolut gambar
    public static Matriks inputFile(){

        System.out.println("=========================================");
        System.out.println("SELAMAT DATANG DI QUADTREE IMAGE COMPRESSION!");
        System.out.println("=========================================");
        System.out.println();

        Scanner scanner = new Scanner(System.in);
        String filepath;
        File file;

        while (true) {
            System.out.println("Masukkan alamat absolut gambar : ");
            filepath = scanner.nextLine().trim();
            if (filepath.isEmpty()) {
                System.out.println("Alamat gambar tidak boleh kosong! Coba lagi.");
                continue;
            }
            file = new File(filepath);
            if (!file.exists()) {
                System.out.println("File tidak ditemukan pada alamat tersebut! Coba lagi.");
                continue;
            } else {
                break;
            }
        }
        
        return bacaFileGambar(filepath);
        
    }

    // Membaca file gambar dan menyimpan data pixel pada matriks
    public static Matriks bacaFileGambar(String filepath) {
        try {
            File file = new File(filepath);
            BufferedImage image = ImageIO.read(file);
            int lebar = image.getWidth(); // lebar dalam pixel
            int tinggi = image.getHeight(); // tinggi dalam pixel
            Pixel[][] matriks_pixel = new Pixel[tinggi][lebar];

            // Mengisi matriks dengan nilai intensitas RGB tiap pixel secara horizontal
            for (int x = 0; x < lebar; x++) {
                for (int y = 0; y < tinggi; y++) {
                    int rgb = image.getRGB(x, y);
                    Color color = new Color(rgb);
                    matriks_pixel[x][y] = new Pixel(color.getRed(), color.getGreen(), color.getBlue());
                }
            }

            return new Matriks(matriks_pixel);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }


    // Menghasilkan output pada alamat absolut tujuan gambar yang sudah dikompresi
    public static void outputFile(){
        
    }

    // Menghasilkan gambar yang sudah dikompresi dengan quadtree
    public static void generateCompressedImage() {

    }


}
