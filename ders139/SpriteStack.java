import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.Timer;

public class SpriteStack extends JPanel implements ActionListener {
    
    ArrayList<BufferedImage> resimler;
    float uzaklik = 6;
    float aci = 0;
    float boyutCarpani = 4;
    float pencereX, pencereY;
    float resimX, resimY;
    int resimXB, resimYB;

    Timer timer = new Timer(10, this);

    public void resimleriAl(ArrayList<BufferedImage> resimler) {
        this.resimler = resimler;
        resimX = resimler.get(0).getWidth();
        resimY = resimler.get(0).getHeight();
        timer.start();
    }

    public void pencereGuncelle(int pencereX, int pencereY) {
        this.pencereX = pencereX;
        this.pencereY = pencereY;
        boyutCarpani = pencereX / resimX;
        resimXB = (int) (resimX * boyutCarpani) / 3;
        resimYB = (int) (resimY * boyutCarpani) / 3;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        float cizimBasX = pencereX/2;
        float cizimBasY = pencereY/2;

        g2d.translate(cizimBasX, cizimBasY);
        
        for(int i = 0; i < resimler.size(); i++) {
            g2d.translate(0, - i * uzaklik);
            g2d.rotate(aci);
            g2d.translate(-resimXB/2, -resimYB/2);
            g2d.drawImage(resimler.get(i), 0,0,resimXB,resimYB,null );
            g2d.translate(resimXB/2, resimYB/2);
            g2d.rotate(-aci);
            g2d.translate(0, i * uzaklik);
        }
        g2d.translate(-cizimBasX, -cizimBasY);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        aci += Math.PI/360;
        repaint();
    }
}
