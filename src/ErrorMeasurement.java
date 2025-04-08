import strukturdata.Matriks;
import strukturdata.QuadTreeNode;

public class ErrorMeasurement {
    //metode pengukuran error dengan variance
    public static double variance(QuadTreeNode newNode){
        Matriks newBlockPixels = newNode.getBlockPixels(null, 0, 0, 0, 0); 
        int panjang = newBlockPixels.kolom;
        int lebar = newBlockPixels.baris;  
        int N = panjang*lebar;

        int sum_red = 0;
        int sum_green = 0;
        int sum_blue = 0;
        for (int i=0;i<panjang;i++){
            for (int j = 0; j < lebar; j++){
                sum_red += newBlockPixels.mat[i][j].getRed();
                sum_green += newBlockPixels.mat[i][j].getGreen();
                sum_blue += newBlockPixels.mat[i][j].getBlue();
            }
        }

        double average_red = sum_red/N;
        double average_green = sum_green/N;
        double average_blue = sum_blue/N;

        double a = 0;
        double b = 0;
        double c = 0;
        for (int i=0;i<panjang;i++){
            for (int j=0;j<lebar;j++){
                a += Math.pow((newBlockPixels.mat[i][j].getRed()-average_red),2);
                b += Math.pow((newBlockPixels.mat[i][j].getGreen()-average_green),2);
                c += Math.pow((newBlockPixels.mat[i][j].getBlue()-average_blue),2);
            }
        }

        double variansi_red = (1/N)*a;
        double variansi_green = (1/N)*b;
        double variansi_blue = (1/N)*c;

        double variansi_rgb =  (variansi_red+variansi_green+variansi_blue)/3;
        return variansi_rgb;
    }

    //metode pengukuran error dengan Mean Absolute Deviation (MAD)
    public static double mean_absolute_deviation(QuadTreeNode newNode){
        Matriks newBlockPixels = newNode.getBlockPixels(null, 0, 0, 0, 0); 
        int panjang = newBlockPixels.kolom;
        int lebar = newBlockPixels.baris;  
        int N = panjang*lebar;

        int sum_red = 0;
        int sum_green = 0;
        int sum_blue = 0;
        for (int i=0;i<panjang;i++){
            for (int j=0;j<lebar;j++){
                sum_red += newBlockPixels.mat[i][j].getRed();
                sum_green += newBlockPixels.mat[i][j].getGreen();
                sum_blue += newBlockPixels.mat[i][j].getBlue();
            }
        }

        double average_red = sum_red/N;
        double average_green = sum_green/N;
        double average_blue = sum_blue/N;

        double a = 0;
        double b = 0;
        double c = 0;
        for (int i=0;i<panjang;i++){
            for (int j=0;j<lebar;j++){
                a += Math.abs(newBlockPixels.mat[i][j].getRed()-average_red);
                b += Math.abs(newBlockPixels.mat[i][j].getGreen()-average_green);
                c += Math.abs(newBlockPixels.mat[i][j].getBlue()-average_blue);
            }
        }

        double MAD_red = (1/N)*a;
        double MAD_green = (1/N)*b;
        double MAD_blue = (1/N)*c;

        double MAD_rgb = (MAD_red+MAD_green+MAD_blue)/3;
        return MAD_rgb;
    }

    //metode pengukuran error dengan Max Pixel Difference (MPD)
    public static double max_pixel_difference(QuadTreeNode newNode){
        Matriks newBlockPixels = newNode.getBlockPixels(null, 0, 0, 0, 0); 
        int panjang = newBlockPixels.kolom;
        int lebar = newBlockPixels.baris;

        int min_red = 99999;
        int max_red = -99999;
        int min_green = 99999;
        int max_green = -99999;
        int min_blue = 99999;
        int max_blue = -99999;
        for(int i=0;i<panjang;i++){
            for(int j=0;j<lebar;j++){
                int red_now = newBlockPixels.mat[i][j].getRed();
                int green_now = newBlockPixels.mat[i][j].getGreen();
                int blue_now = newBlockPixels.mat[i][j].getBlue();
                if(red_now>max_red){
                    max_red=red_now;
                }
                if(red_now<min_red){
                    min_red=red_now;
                }
                if(green_now>max_green){
                    max_green=green_now;
                }
                if(green_now<min_green){
                    min_green=green_now;
                }
                if(blue_now>max_blue){
                    max_blue=blue_now;
                }
                if(blue_now<min_blue){
                    min_blue=blue_now;
                }
            }
        }

        int d_red = max_red-min_red;
        int d_green = max_green-min_green;
        int d_blue = max_blue-min_blue;
        
        double d_rgb = (double)(d_red+d_green+d_blue)/3;
        return d_rgb;
    }

    //metode pengukuran error dengan Entropy
    public static double entropy(QuadTreeNode newNode){
        Matriks newBlockPixels = newNode.getBlockPixels(null, 0, 0, 0, 0); 
        int panjang = newBlockPixels.kolom;
        int lebar = newBlockPixels.baris;
        int N = panjang*lebar;

        int[] freq_red = new int[256];
        int[] freq_green = new int[256];    
        int[] freq_blue = new int[256];

        for(int i=0;i<panjang;i++){
            for(int j=0;j<lebar;j++){
                int red_now = newBlockPixels.mat[i][j].getRed();
                int green_now = newBlockPixels.mat[i][j].getGreen();
                int blue_now = newBlockPixels.mat[i][j].getBlue();
                freq_red[red_now]++;
                freq_green[green_now]++;
                freq_blue[blue_now]++;
            }
        }
        for(int i=0;i<256;i++){
            freq_red[i] = freq_red[i]/N;
            freq_green[i] = freq_green[i]/N;
            freq_blue[i] = freq_blue[i]/N;
        }

        double entropy_red = 0;
        double entropy_green = 0;
        double entropy_blue = 0;
        for(int i=0;i<panjang;i++){
            for(int j=0;j<lebar;j++){
                int red_now = newBlockPixels.mat[i][j].getRed();
                int green_now = newBlockPixels.mat[i][j].getGreen();
                int blue_now = newBlockPixels.mat[i][j].getBlue();
                if(freq_red[red_now] != 0){
                    entropy_red += freq_red[red_now]*(Math.log(freq_red[red_now])/Math.log(2));
                }
                if(freq_green[green_now] != 0){
                    entropy_green += freq_green[green_now]*(Math.log(freq_green[green_now])/Math.log(2));
                }
                if(freq_blue[blue_now] != 0){
                    entropy_blue += freq_blue[blue_now]*(Math.log(freq_blue[blue_now])/Math.log(2));
                }
            }
        }

        double entropy_rgb = -(entropy_red+entropy_green+entropy_blue)/3;
        return entropy_rgb;
    }
}
