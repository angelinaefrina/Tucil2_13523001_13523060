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
}
