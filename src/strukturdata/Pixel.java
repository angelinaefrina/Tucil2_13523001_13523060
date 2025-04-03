package strukturdata;

public class Pixel {
    int red;
    int green;
    int blue;

    public Pixel(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public int getRed() {
        return this.red;
    }

    public int getGreen() {
        return this.green;
    }

    public int getBlue() {
        return this.blue;
    }

    public static Pixel JumlahPixel(Pixel p1, Pixel p2) {
        Pixel p = new Pixel(p1.getRed(), p1.getGreen(), p1.getBlue());
        p.red += p2.getRed();
        p.green += p2.getGreen();
        p.blue += p2.getBlue();

        return p;
    }

}
