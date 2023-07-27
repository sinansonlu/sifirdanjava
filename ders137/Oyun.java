import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.Timer;

public class Oyun implements ActionListener{

    public int x;
    public int y;
    int w;
    int h;

    ArrayList<Top> toplarArrayList;
    ArrayList<JFrame> gorunumArrayList;

    Random rng;
    Timer t;

    public Oyun() {
        toplarArrayList = new ArrayList<>();
        gorunumArrayList = new ArrayList<>();
        rng = new Random();
        for (int i = 0; i < 10; i++) {
            topEkle(rng.nextInt(300), rng.nextInt(300));
        }
        t = new Timer(30, this);
    }

    public void timerBaslat() {
        t.start();
    }

    public void topEkle(int x, int y) {
        toplarArrayList.add(new Top(x, y, rng.nextInt(7)-3, rng.nextInt(7)-3, rng.nextInt(50)+10));
    }

    public ArrayList<Top> toplariAl() {
        return toplarArrayList;
    }

    public void pencereKonumuGuncelle() {
        x = Integer.MAX_VALUE;
        y = Integer.MAX_VALUE;
        w = 0;
        h = 0;
        for (JFrame jFrame : gorunumArrayList) {
            Rectangle r = jFrame.getBounds();
            if(r.getX() < x) {
                x = (int)r.getX();
            }
            if(r.getY() < y) {
                y = (int)r.getY();
            }
            if(x + w < r.getX() + r.getWidth()) {
                w = (int) (r.getX() + r.getWidth() - x);
            }
            if(y + h < r.getY() + r.getHeight()) {
                h = (int) (r.getY() + r.getHeight() - y);
            }
        }
    }

    public void gorunumEkle(JFrame f) {
        gorunumArrayList.add(f);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (Top top : toplarArrayList) {
            top.hareketEt(x, y, w, h);
        }
        for (JFrame jFrame : gorunumArrayList) {
            jFrame.repaint();
        }
    }


}
