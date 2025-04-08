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

    // WIP
//     public Pixel[][] createImage() {
//         if (this.isParent && q1 != null && q2 != null && q3 != null && q4 != null) {
//             Pixel[][] image1 = q1.createImage();
//             Pixel[][] image2 = q2.createImage();
//             Pixel[][] image3 = q3.createImage();
//             Pixel[][] image4 = q4.createImage();

//             Pixel[][] top = concatHorizontal(image1, image2);
//             Pixel[][] bottom = concatHorizontal(image3, image4);
//             return concatVertical(top, bottom);
//         } else {
//             return fillBlockWithMeanColor();
//         }
//     }

//     private Pixel[][] fillBlockWithMeanColor() {
//         int totalR = 0, totalG = 0, totalB = 0;
//         for (int i = 0; i < h; i++) {
//             for (int j = 0; j < w; j++) {
//                 totalR += block[i][j].r;
//                 totalG += block[i][j].g;
//                 totalB += block[i][j].b;
//             }
//         }
//         int size = w * h;
//         int meanR = totalR / size;
//         int meanG = totalG / size;
//         int meanB = totalB / size;

//         Pixel[][] filled = new Pixel[h][w];
//         for (int i = 0; i < h; i++) {
//             for (int j = 0; j < w; j++) {
//                 filled[i][j] = new Pixel(meanR, meanG, meanB);
//             }
//         }
//         return filled;
//     }

//     private Pixel[][] concatHorizontal(Pixel[][] left, Pixel[][] right) {
//         int height = left.length;
//         int widthLeft = left[0].length;
//         int widthRight = right[0].length;
//         Pixel[][] result = new Pixel[height][widthLeft + widthRight];

//         for (int i = 0; i < height; i++) {
//             System.arraycopy(left[i], 0, result[i], 0, widthLeft);
//             System.arraycopy(right[i], 0, result[i], widthLeft, widthRight);
//         }
//         return result;
//     }

//     private Pixel[][] concatVertical(Pixel[][] top, Pixel[][] bottom) {
//         int heightTop = top.length;
//         int heightBottom = bottom.length;
//         int width = top[0].length;
//         Pixel[][] result = new Pixel[heightTop + heightBottom][width];

//         for (int i = 0; i < heightTop; i++) {
//             result[i] = top[i];
//         }
//         for (int i = 0; i < heightBottom; i++) {
//             result[heightTop + i] = bottom[i];
//         }
//         return result;
//     }

//     public void createImage(BufferedImage image) {
//         fillImageFromQuadTree(image, this);
//     }

//     private void fillImageFromQuadTree(BufferedImage image, QuadTreeNode node) {
//         if (node == null) return;

//         if (node.isDivideable(1)) {
//             QuadTreeNode[] children = node.divideBlock(node.block, 1);
//             if (children != null) {
//                 for (QuadTreeNode child : children) {
//                     fillImageFromQuadTree(image, child);
//                 }
//             }
//         } else {
//             for (int i = 0; i < node.h; i++) {
//                 for (int j = 0; j < node.w; j++) {
//                     Pixel p = node.block[i][j];
//                     int rgb = (p.r << 16) | (p.g << 8) | p.b;
//                     image.setRGB(node.x + j, node.y + i, rgb);
//                 }
//             }
//         }
//     }
// }

}