import java.awt.Graphics;

public class Blok {
    public boolean aktifMi;

    public int i;
    public int j;

    public Blok(int i, int j) {
        this.i = i;
        this.j = j;
        aktifMi = true;
        if(Oyun.grid[i][j] == null && Oyun.grid[i+1][j] == null) {
            Oyun.grid[i][j] = this;
            Oyun.grid[i+1][j] = this;
        }
        else{
            System.out.println("Grid yerleştirme hatası!");
        }
    }

    public void beniSil() {
        Oyun.grid[i][j] = null;
        Oyun.grid[i+1][j] = null;
        aktifMi = false;
    }

    public void ciz(Graphics g) {
        g.fillRect(i * Oyun.BLOK_GENISLIGI, j * Oyun.BLOK_GENISLIGI, Oyun.BLOK_GENISLIGI * 2, Oyun.BLOK_GENISLIGI);
    }

}
