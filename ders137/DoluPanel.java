import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

public class DoluPanel extends OyunPaneli {
    @Override
    protected void paintComponent(Graphics g) {
        Point p1 = sahip.getLocation();
        ArrayList<Top> toplar = oyun.toplariAl();
        for (Top top : toplar) {
            g.fillOval(top.x - (int)p1.getX(), top.y - (int)p1.getY(), top.r, top.r);
        }
    }
}
