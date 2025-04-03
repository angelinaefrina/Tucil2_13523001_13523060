package strukturdata;

import java.awt.Color;

public class QuadTreeNode {
    Color color;
    int x, y, w, h, depth;
    private QuadTreeNode q1, q2, q3, q4;
    private boolean isParent;

    // ctor
    public QuadTreeNode(int x, int y, int w, int h) {
        this.color = null;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.q1 = null;
        this.q2 = null;
        this.q3 = null;
        this.q4 = null;
        this.isParent = false;
    }

    public static QuadTreeNode createChildNode (int x, int y, int w, int h) {
        return new QuadTreeNode(x, y, w, h);
    }

    public boolean isDivideable (int minblock_size) {
        return (!this.isParent && this.w > minblock_size && this.h > minblock_size);
    }

    public QuadTreeNode[] divideBlock (int minblock_size) {
        if (!isDivideable(minblock_size)) {
            return null;
        } else {
            this.isParent = true;
            int half_w = this.w / 2;
            int half_h = this.h / 2;
            q1 = createChildNode(this.x, this.y, half_w, half_h);
            q2 = createChildNode(this.x + half_w, this.y, half_w, half_h);
            q3 = createChildNode(this.x, this.y + half_h, half_w, half_h);
            q4 = createChildNode(this.x + half_w, this.y + half_h, half_w, half_h);
            return new QuadTreeNode[] {q1, q2, q3, q4};
        }
    }

}