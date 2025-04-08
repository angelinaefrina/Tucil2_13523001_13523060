package strukturdata;

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

    public boolean isDivideable (int minblock_size) {
        return (!this.isParent && this.w > minblock_size && this.h > minblock_size);
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

    public QuadTreeNode[] divideBlock (Matriks region, int minblock_size) {

        if (!isDivideable(minblock_size)) {
            return null;
        } 
        else {
            this.isParent = true;
            int half_w = this.w / 2;
            int half_h = this.h / 2;

            q1 = createChildNode(region, this.x, this.y, half_w, half_h, this.depth + 1);
            q2 = createChildNode(region, this.x + half_w, this.y, half_w, half_h, this.depth + 1);
            q3 = createChildNode(region, this.x, this.y + half_h, half_w, half_h, this.depth + 1);
            q4 = createChildNode(region, this.x + half_w, this.y + half_h, half_w, half_h, this.depth + 1);
            return new QuadTreeNode[] {q1, q2, q3, q4};
        }
    }

    public static void divideBlockRecursively(QuadTreeNode node, Matriks region, int minBlockSize) {
        if (node.isDivideable(minBlockSize)) {
            QuadTreeNode[] children = node.divideBlock(region, minBlockSize);
            if (children != null) {
                for (QuadTreeNode child : children) {
                    divideBlockRecursively(child, region, minBlockSize);
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

}