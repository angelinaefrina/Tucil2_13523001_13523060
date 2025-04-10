import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;

import strukturdata.Matriks;
import strukturdata.Pixel;

public class InputOutputFile {
    
    // Meminta input alamat absolut gambar
    public static String inputFile(){

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
        
        return filepath;
        
    }

    // Membaca file gambar dan menyimpan data pixel pada matriks
    public static Matriks bacaFileGambar(String filepath) {
        try {
            File file = new File(filepath);
            BufferedImage image = ImageIO.read(file);
            int lebar = image.getWidth(); // lebar dalam pixel
            int tinggi = image.getHeight(); // tinggi dalam pixel
            Matriks matriks_pixel = new Matriks(tinggi, lebar);

            // Mengisi matriks dengan nilai intensitas RGB tiap pixel secara horizontal
            for (int x = 0; x < lebar; x++) {
                for (int y = 0; y < tinggi; y++) {
                    int rgb = image.getRGB(x, y);
                    Color color = new Color(rgb);
                    matriks_pixel.mat[y][x] = new Pixel(color.getRed(), color.getGreen(), color.getBlue());
                }
            }

            return matriks_pixel;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }


    public static String outputFile(Matriks compressed, String inputpath) {
        Scanner scanner = new Scanner(System.in);
        String outputpath;

        String input_format = "png";
        String output_format = "png";

        int input_dotIndex = inputpath.lastIndexOf('.');
        if (input_dotIndex != -1 && input_dotIndex < inputpath.length() - 1) {
            input_format = inputpath.substring(input_dotIndex + 1).toLowerCase();
        }
        
        File file;
        while (true) {
            System.out.println("Masukkan alamat absolut gambar hasil kompresi : ");
            outputpath = scanner.nextLine().trim();
            if (outputpath.isEmpty()) {
                System.out.println("Alamat gambar tidak boleh kosong! Coba lagi.");
                continue;
            } 
            int dotIndex = outputpath.lastIndexOf('.');
            output_format = outputpath.substring(dotIndex + 1).toLowerCase();
            if (!output_format.equals(input_format)) {
                System.out.println("Format output (" + output_format + ") tidak sesuai dengan format input (" + input_format + "). Coba lagi.");
                continue;
            }

            break;
        }

        int height = compressed.baris;
        int width = compressed.kolom;
        BufferedImage outputImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Pixel p = compressed.mat[i][j];
                int rgb = (p.getRed() << 16) | (p.getGreen() << 8) | p.getBlue();
                outputImage.setRGB(j, i, rgb);
            }
        }

        

        File outputFile = new File(outputpath);

        try {
            ImageIO.write(outputImage, output_format, outputFile);
            System.out.println("Gambar hasil kompresi berhasil disimpan!");
        } catch (IOException e) {
            System.err.println("Gagal menyimpan gambar: " + e.getMessage());
        }

        return outputFile.getAbsolutePath();
    }



}
