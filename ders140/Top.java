import java.awt.Graphics;

public class Top {
    public float x;
    public float y;
    public float vx;
    public float vy;
    public boolean aktifMi;
    public boolean cubukTemas;

    public Top(int x, int y) {
        this.x = x;
        this.y = y;
        vy = 2;
        aktifMi = true;
        cubukTemas = false;
    }

    public void hareketEt() {
        x += vx;
        y += vy;
        if(x < 8 || x > Oyun.EKRAN_GENISLIK - 8) {
            vx = -vx;
        }

        if(y < 8) {
            vy = -vy;
        }

        if(x + 8 >= Oyun.cubuk.x && x - 8 < Oyun.cubuk.x + Oyun.cubuk.genislik) {
            if(y + 8 >= Oyun.cubuk.y && y - 8 <= Oyun.cubuk.y + Oyun.cubuk.yukseklik) {
                if(!cubukTemas) {
                    float dvx = (x - (Oyun.cubuk.x + (Oyun.cubuk.genislik / 2))) / Oyun.cubuk.genislik;
                    vx = (dvx * 3) + vx * 0.2f;
                    vy = -vy - 0.1f;
                }
                cubukTemas = true;
            }
            else{
                cubukTemas = false;
            }
        }
        else{
            cubukTemas = false;
        }


        if(y > Oyun.EKRAN_YUKSEKLIK - 8) {
            aktifMi = false;
        }

        int bi = (int) (x / Oyun.BLOK_GENISLIGI);
        int bj = (int) (y / Oyun.BLOK_GENISLIGI);
        Blok b = Oyun.grid[bi][bj];
        if(b != null && b.aktifMi) {
            b.beniSil();
            float bx = bi * Oyun.BLOK_GENISLIGI + Oyun.BLOK_GENISLIGI;
            // float by = bj * Oyun.BLOK_GENISLIGI + Oyun.BLOK_GENISLIGI / 2;
            
            float dvx = (x - bx) / Oyun.BLOK_GENISLIGI;
            vx += dvx;
            vy = -vy;
        }

        //vy += 0.3;
    }

    public void ciz(Graphics g) {
        g.fillOval((int)x-8, (int)y-8, 16, 16);
    }
}
