import strukturdata.*;

public class ErrorMeasurement {
    //metode pengukuran error dengan variance
    public static double variance(QuadTreeNode newNode){
        Pixel[][] newBlockPixels = newNode.getBlockPixels(null, 0, 0, 0, 0); 
        int panjang = newBlockPixels.length;
        int lebar = newBlockPixels[0].length;  
        int N = panjang*lebar;

        int sum_red = 0;
        int sum_green = 0;
        int sum_blue = 0;
        for (int i=0;i<panjang;i++){
            for (int j=0;j<lebar;j++){
                sum_red += newBlockPixels[i][j].getRed();
                sum_green += newBlockPixels[i][j].getGreen();
                sum_blue += newBlockPixels[i][j].getBlue();
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
                a += Math.pow((newBlockPixels[i][j].getRed()-average_red),2);
                b += Math.pow((newBlockPixels[i][j].getGreen()-average_green),2);
                c += Math.pow((newBlockPixels[i][j].getBlue()-average_blue),2);
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
        Pixel[][] newBlockPixels = newNode.getBlockPixels(null, 0, 0, 0, 0); 
        int panjang = newBlockPixels.length;
        int lebar = newBlockPixels[0].length;  
        int N = panjang*lebar;

        int sum_red = 0;
        int sum_green = 0;
        int sum_blue = 0;
        for (int i=0;i<panjang;i++){
            for (int j=0;j<lebar;j++){
                sum_red += newBlockPixels[i][j].getRed();
                sum_green += newBlockPixels[i][j].getGreen();
                sum_blue += newBlockPixels[i][j].getBlue();
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
                a += Math.abs(newBlockPixels[i][j].getRed()-average_red);
                b += Math.abs(newBlockPixels[i][j].getGreen()-average_green);
                c += Math.abs(newBlockPixels[i][j].getBlue()-average_blue);
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
        Pixel[][] newBlockPixels = newNode.getBlockPixels(null, 0, 0, 0, 0); 
        int panjang = newBlockPixels.length;
        int lebar = newBlockPixels[0].length;

        int min_red = 99999;
        int max_red = -99999;
        int min_green = 99999;
        int max_green = -99999;
        int min_blue = 99999;
        int max_blue = -99999;
        for(int i=0;i<panjang;i++){
            for(int j=0;j<lebar;j++){
                int red_now = newBlockPixels[i][j].getRed();
                int green_now = newBlockPixels[i][j].getGreen();
                int blue_now = newBlockPixels[i][j].getBlue();
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
        
        double d_rgb = (d_red+d_green+d_blue)/3;
        return d_rgb;
    }
}
