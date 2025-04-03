package strukturdata;

public class QuadTreeNode {
    Pixel[][] block;
    int x, y, w, h, depth;
    private QuadTreeNode q1, q2, q3, q4;
    private boolean isParent;

    // ctor
    public QuadTreeNode(Pixel[][] region, int x, int y, int w, int h, int depth) {
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

    public static QuadTreeNode createChildNode (Pixel[][] region, int x, int y, int w, int h, int depth) {
        return new QuadTreeNode(region, x, y, w, h, depth);
    }

    public boolean isDivideable (int minblock_size) {
        return (!this.isParent && this.w > minblock_size && this.h > minblock_size);
    }

    public Pixel[][] getBlockPixels (Pixel[][] region, int x, int y, int w, int h) {
        Pixel[][] block = new Pixel[h][w];
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                block[i][j] = region[y + i][x + j];
            }
        }
        return block;
    }

    public QuadTreeNode[] divideBlock (Pixel[][] region, int minblock_size) {

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

}