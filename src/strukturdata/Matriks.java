package strukturdata;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Matriks { 
    Scanner scan = new Scanner (System.in); 
    public int baris; 
    public int kolom;
    public Pixel[][] mat; 
    public int barmin = 0;
    public int colmin = 0;
    public int barmax = 100;
    public int colmax = 100;

    /* Membuat matriks kosong */
    public Matriks (int baris, int kolom){ 
        this.baris = baris;  //parameter
        this.kolom = kolom;
        this.mat = new Pixel [baris][kolom];
    }

    /* Salin matriks */
    public Matriks (Pixel[][] mat){
        this.baris = mat.length;
        this.kolom = mat[0].length;
        this.mat = new Pixel [this.baris][this.kolom];

        for (int i =0; i< this.baris; i++){
            for (int j=0; j< this.kolom;j++){
                this.mat[i][j] = mat [i][j];
            }
        }
    }

    /*SELEKTOR*/
    public int GetFirstIdxBar (Matriks M){
        return barmin;
    }
    public int GetFirstIdxCol (Matriks M){
        return colmin;
    }
    public int GetLastIdxBar (Matriks M){
        return M.baris-1;
    } 
    public int GetLastIdxCol (Matriks M){
        return M.kolom-1;
    }
    public Pixel GetElement(int m, int n){
        return mat[m][n];
    }

    /* Tampilkan Matriks ke Layar */
    public void PrintMat (){
        for (int i=0;i<this.baris;i++){
            for (int j=0;j<this.kolom;j++){
                System.out.printf("%.2f",this.mat[i][j]); //printf karena ada format
            }
        System.out.printf("\n");
        }
    }

    // Melakukan operasi penjumlahan pada dua buah matriks
    public static Matriks JumlahMatriks(Matriks M1, Matriks M2){
        Matriks M = new Matriks(M1.baris, M2.kolom);

        for (int i = 0; i < M1.baris; i++){
            for (int j = 0; j < M1.kolom; j++){
                M.mat[i][j] = Pixel.JumlahPixel(M1.mat[i][j], M2.mat[i][j]);
            }
        }
        return M;
    }

    // Menghitung banyak elemen dalam matriks
    public static int BanyakElemenMatriks(Matriks M){
        return M.baris * M.kolom;
    }

    // Menggabung dua buah matriks secara horizontal
    public static Matriks ConcatHorizontally(Matriks M, Matriks N){
        Matriks Out = new Matriks(M.baris, M.kolom+N.kolom); // matriks baru memiliki kolom sebanyak gabungan kolom kedua matriks
        for (int i = 0; i < Out.baris; i++) {
            for (int j = 0; j < Out.kolom; j++) {
                if (j < M.kolom) {
                    Out.mat[i][j] = M.mat[i][j];
                } else {
                    Out.mat[i][j] = N.mat[i][j - M.kolom];
                }
            }
        }
        return Out;
    }

    // Menggabung dua buah matriks secara horizontal
    public static Matriks ConcatVertically(Matriks M, Matriks N) {
        Matriks Out = new Matriks(M.baris + N.baris, M.kolom); // matriks baru memiliki baris sebanyak gabungan baris kedua matriks
        for (int i = 0; i < Out.baris; i++) {
            for (int j = 0; j < Out.kolom; j++) {
                if (i < M.baris) {
                    Out.mat[i][j] = M.mat[i][j];
                } else {
                    Out.mat[i][j] = N.mat[i - M.baris][j];
                }
            }
        }
        return Out;
    }

    public void saveMatrixToText(Matriks M, String filePath) {
    try (FileWriter writer = new FileWriter(filePath)) {
        for (Pixel[] row : M.mat) {
            for (Pixel p : row) {
                writer.write("(" + p.getRed() + "," + p.getGreen() + "," + p.getBlue() + ") ");
            }
            writer.write("\n");
        }
        System.out.println("Matrix saved to: " + filePath);
    } catch (IOException e) {
        System.out.println("Error saving matrix to file: " + e.getMessage());
    }
}

}