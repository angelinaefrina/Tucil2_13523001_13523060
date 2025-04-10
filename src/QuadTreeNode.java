import strukturdata.Matriks;
import strukturdata.Pixel;
public class QuadTreeNode {
    Matriks block;
    int x, y, w, h, depth;
    private QuadTreeNode q1, q2, q3, q4;
    private boolean isParent;

    // ctor
    public QuadTreeNode(Matriks region, int x, int y, int w, int h, int depth) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.depth = depth;
        this.q1 = null;
        this.q2 = null;
        this.q3 = null;
        this.q4 = null;
        this.isParent = false;
        this.block = getBlockPixels(region, x, y, w, h);
    }

    public static QuadTreeNode createChildNode (Matriks region, int x, int y, int w, int h, int depth) {
        return new QuadTreeNode(region, x, y, w, h, depth);
    }

    public boolean isDivideable (double minblock_size, Matriks region, int error_method, double threshold) {
        int half_w = this.w / 2;
        int half_h = this.h / 2;
        boolean isError = ErrorMeasurement.checkImageError(this.block, error_method, threshold);

        // if (this.w * this.h <= minblock_size) {
        //     System.out.println("Block size is too small: " + this.w * this.h);
        // }
        // if (half_w * half_h <= minblock_size) {
        //     System.out.println("Half block size is too small: " + half_w * half_h);
        // }
        // if (!isError) {
        //     System.out.println("Error threshold exceeded");
        // }
        // System.out.println(" ");
        return (!this.isParent && this.w * this.h > minblock_size && half_w*half_h> minblock_size && isError);
    }

    public Matriks getBlockPixels (Matriks region, int x, int y, int w, int h) {
        Matriks block = new Matriks(h, w);
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                block.mat[i][j] = region.mat[y + i][x + j];
            }
        }
        return block;
    }

    public QuadTreeNode[] divideBlock (Matriks region, double minblock_size, int error_method, double threshold) {
        if (!isDivideable(minblock_size,region, error_method, threshold)) {
            return null;
        } 
        else {
            this.isParent = true;
            int half_w1 = this.w / 2;
            int half_w2 = this.w - half_w1;
            int half_h1 = this.h / 2;
            int half_h2 = this.h - half_h1;

            q1 = createChildNode(region, this.x, this.y, half_w1, half_h1, this.depth + 1);
            q2 = createChildNode(region, this.x + half_w1, this.y, half_w2, half_h1, this.depth + 1);
            q3 = createChildNode(region, this.x, this.y + half_h1, half_w1, half_h2, this.depth + 1);
            q4 = createChildNode(region, this.x + half_w1, this.y + half_h1, half_w2, half_h2, this.depth + 1);
            return new QuadTreeNode[] {q1, q2, q3, q4};
        }
    }

    public static void divideBlockRecursively(QuadTreeNode node, Matriks region, double minBlockSize, int error_method, double threshold) {
        if (node.isDivideable(minBlockSize,region, error_method, threshold) ) {
            QuadTreeNode[] children = node.divideBlock(region, minBlockSize, error_method, threshold);
            if (children != null) {
                for (QuadTreeNode child : children) {
                    divideBlockRecursively(child, region, minBlockSize, error_method, threshold);
                }
            }
        }
    }

    // WIP
    public Matriks createImage() {
        if (this.isParent && q1 != null && q2 != null && q3 != null && q4 != null) {
            Matriks image1 = q1.createImage();
            Matriks image2 = q2.createImage();
            Matriks image3 = q3.createImage();
            Matriks image4 = q4.createImage();

            Matriks atas = Matriks.ConcatHorizontally(image1, image2);
            Matriks bawah = Matriks.ConcatHorizontally(image3, image4);
            return Matriks.ConcatVertically(atas, bawah);
        } else {
            return normalizeBlockColor();
        }
    }

    private Matriks normalizeBlockColor() {
        int totalRed = 0, totalGreen = 0, totalBlue = 0;
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                totalRed += this.block.mat[i][j].getRed();
                totalGreen += this.block.mat[i][j].getGreen();
                totalBlue += this.block.mat[i][j].getBlue();
            }
        }
        int size = w * h;
        int meanRed = totalRed / size;
        int meanGreen = totalGreen / size;
        int meanBlue = totalBlue / size;

        Matriks N = new Matriks(h, w);
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                N.mat[i][j] = new Pixel(meanRed, meanGreen, meanBlue);
            }
        }
        return N;
    }

    public int countNodes() {
        if (!this.isParent) {
            return 1;
        } else {
            return 1 + q1.countNodes() + q2.countNodes() + q3.countNodes() + q4.countNodes();
        }
    }
    
    public int getMaxDepth() {
        if (!this.isParent) {
            return this.depth;
        } else {
            int d1 = q1.getMaxDepth();
            int d2 = q2.getMaxDepth();
            int d3 = q3.getMaxDepth();
            int d4 = q4.getMaxDepth();
            return Math.max(Math.max(d1, d2), Math.max(d3, d4));
        }
    }

    public Matriks createImageAtDepth(int targetDepth) {
        if (this.depth == targetDepth) {
            // Jika kedalaman saat ini sama dengan target, gunakan warna rata-rata blok
            return normalizeBlockColor();
        } else if (this.isParent) {
            // Rekursif untuk setiap child, jika child null gunakan warna dari node saat ini
            Matriks image1 = (q1 != null) ? q1.createImageAtDepth(targetDepth) : createFallbackBlock(this.h / 2, this.w / 2);
            Matriks image2 = (q2 != null) ? q2.createImageAtDepth(targetDepth) : createFallbackBlock(this.h / 2, this.w - this.w / 2);
            Matriks image3 = (q3 != null) ? q3.createImageAtDepth(targetDepth) : createFallbackBlock(this.h - this.h / 2, this.w / 2);
            Matriks image4 = (q4 != null) ? q4.createImageAtDepth(targetDepth) : createFallbackBlock(this.h - this.h / 2, this.w - this.w / 2);
    
            // Gabungkan matriks dari keempat kuadran
            Matriks atas = Matriks.ConcatHorizontally(image1, image2);
            Matriks bawah = Matriks.ConcatHorizontally(image3, image4);
            return Matriks.ConcatVertically(atas, bawah);
        } else {
            // Jika tidak ada child, gunakan warna dari lapisan terakhir yang tersedia
            return normalizeBlockColor();
        }
    }
    
    private Matriks createFallbackBlock(int height, int width) {
        // Membuat blok fallback dengan warna rata-rata dari node saat ini
        Matriks fallback = new Matriks(height, width);
        Pixel averageColor = new Pixel(
            this.block.mat[0][0].getRed(),
            this.block.mat[0][0].getGreen(),
            this.block.mat[0][0].getBlue()
        );
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                fallback.mat[i][j] = averageColor;
            }
        }
        return fallback;
    }

    // public static void main(String[] args) {

    //     // Matriks.saveMatrixToText(region, "C:/Users/Lenovo/Documents/Stima/Tucil2_13523001_13523060/test/image_data.txt");
    //     // // double m1 = ErrorMeasurement.variance(region); // 0
    //     // // System.out.println("Error: " + m1);
    //     // // double m2 = ErrorMeasurement.mean_absolute_deviation(region); // 0
    //     // // System.out.println("Error: " + m2);
    //     // // double m3 = ErrorMeasurement.max_pixel_difference(region);// 166
    //     // // System.out.println("Error: " + m3);
    //     // // double m4 = ErrorMeasurement.entropy(region); // 0
    //     // // System.out.println("Error: " + m4);

        
    // }

}