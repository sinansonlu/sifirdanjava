import java.awt.Graphics;

public class Cubuk {
    public float x;
    public float y;
    public int genislik = 60;
    public int yukseklik = 8;

    public boolean aktifMi;

    public Cubuk(int x, int y) {
        this.x = x;
        this.y = y;
        aktifMi = true;
    }

    public void hareketEt(int x) {
        this.x = x;
    }

    public void ciz(Graphics g) {
        g.fillRect((int)x, (int)y, genislik, yukseklik);
    }
}
