import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JFrame;

public class FrameBaglanti extends JFrame implements ComponentListener {
    Oyun oyun;
    OyunPaneli op;

    public FrameBaglanti(Oyun oyun, int x, int y, int w, int h, OyunPaneli op) {
        this.oyun = oyun;
        oyun.gorunumEkle(this);
        setBounds(x, y, w, h);
        op.oyunuAl(oyun, this);
        add(op);
        setVisible(true);
        addComponentListener(this);
    }

    public void pencereKonumuBildir() {
        oyun.pencereKonumuGuncelle();
    }

    @Override
    public void componentResized(ComponentEvent e) {
        pencereKonumuBildir();
    }

    @Override
    public void componentMoved(ComponentEvent e) {
        pencereKonumuBildir();
    }

    @Override
    public void componentShown(ComponentEvent e) {
    }

    @Override
    public void componentHidden(ComponentEvent e) {
    }
}
