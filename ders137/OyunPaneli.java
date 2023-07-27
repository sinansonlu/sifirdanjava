import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class OyunPaneli extends JPanel implements MouseListener{

    JFrame sahip;
    Oyun oyun;

    public void oyunuAl(Oyun oyun, JFrame sahip) {
        this.oyun = oyun;
        this.sahip = sahip;
        addMouseListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Point p1 = sahip.getLocation();
        ArrayList<Top> toplar = oyun.toplariAl();
        for (Top top : toplar) {
            g.drawRect(top.x - (int)p1.getX(), top.y - (int)p1.getY(), top.r, top.r);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1) {
            Point p1 = sahip.getLocation();
            oyun.topEkle(e.getX() + (int)p1.getX(), e.getY() + (int)p1.getY());
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
    
}
