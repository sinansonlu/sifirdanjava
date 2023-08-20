import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

public class PixelAniJava extends JFrame implements ComponentListener, KeyListener {

    CalismaAlani ca;
    boolean tusaBasildi = false;

    public PixelAniJava() {
        setSize(400, 400);
        ca = new CalismaAlani(30, 30);
        ca.pencereBoyutGuncelle(getWidth(), getHeight());
        add(ca);
        addComponentListener(this);
        addKeyListener(this);
        setVisible(true);

        SpriteStackFrame ssf = new SpriteStackFrame(ca.resimleriVer());
        ca.SpriteStackiAl(ssf.spriteStack());
    }

    public static void main(String[] args) {
        PixelAniJava paj = new PixelAniJava();
    }

    @Override
    public void componentResized(ComponentEvent e) {
        if(ca != null) {
            ca.pencereBoyutGuncelle(ca.getWidth(), ca.getHeight());
        }
    }

    @Override
    public void componentMoved(ComponentEvent e) {
        
    }

    @Override
    public void componentShown(ComponentEvent e) {
        
    }

    @Override
    public void componentHidden(ComponentEvent e) {
        
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if(!tusaBasildi) {
                ca.ResimIleri();
            }
            tusaBasildi = true;
        }
        else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
            if(!tusaBasildi) {
                ca.ResimGeri();
            }
            tusaBasildi = true;
        }
        else if(e.getKeyCode() == KeyEvent.VK_SPACE) {
            if(!tusaBasildi) {
                ca.animasyonuBaslatVeyaDurdur();
            }
            tusaBasildi = true;
        }
        else if(e.getKeyCode() == KeyEvent.VK_UP) {
            if(!tusaBasildi) {
                ca.animasyonHizArtir();
            }
            tusaBasildi = true;
        }
        else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
            if(!tusaBasildi) {
                ca.animasyonHizYavaslat();
            }
            tusaBasildi = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        tusaBasildi = false;
    }

}