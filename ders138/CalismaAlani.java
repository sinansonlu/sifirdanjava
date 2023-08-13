import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

public class CalismaAlani extends JPanel implements MouseListener, MouseMotionListener, ActionListener {

    Timer timer = new Timer(50, this);

    ArrayList<BufferedImage> resimler;

    BufferedImage tekilResim;
    BufferedImage renkPaleti;

    int pencereX, pencereY;
    int resimBoyutX, resimBoyutY;

    float boyutlandirmaFaktoru = 1;
    int paletBoyutlandirmaFaktoru = 20;

    int eldekiRenk = Color.white.getRGB();
    int mevcutTus = -1;
    int mevcutResim = -1;

    boolean animasyonVar = false;

    public CalismaAlani(int boyutX, int boyutY) {
        resimBoyutX = boyutX;
        resimBoyutY = boyutY;

        resimler = new ArrayList<BufferedImage>();

        tekilResim = new BufferedImage(boyutX, boyutY, BufferedImage.TYPE_USHORT_565_RGB);
        resimler.add(tekilResim);
        mevcutResim = 0;

        try {
            renkPaleti = ImageIO.read(new File("bouba-satura-1x.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(tekilResim, 0, 0, (int) (tekilResim.getWidth() * boyutlandirmaFaktoru),
         (int) (tekilResim.getHeight() * boyutlandirmaFaktoru), null);

        if(renkPaleti != null) {
            g.drawImage(renkPaleti, paletBaslangicX(),
            paletBaslangicY(), paletGenislik(), paletYukseklik(), null);
        }
    }

    public void pencereBoyutGuncelle(int x, int y) {
        pencereX = x;
        pencereY = y;

        float fx = pencereX / tekilResim.getWidth();
        float fy = pencereY / tekilResim.getHeight();

        boyutlandirmaFaktoru = (fx > fy) ? fy : fx;
    }

    public int paletBaslangicX() {
        return pencereX - (renkPaleti.getWidth() * paletBoyutlandirmaFaktoru);
    }

    public int paletBaslangicY() {
        return pencereY - (renkPaleti.getHeight() * paletBoyutlandirmaFaktoru);
    }

    public int paletGenislik() {
        return renkPaleti.getWidth() * paletBoyutlandirmaFaktoru;
    }

    public int paletYukseklik() {
        return renkPaleti.getHeight() * paletBoyutlandirmaFaktoru;
    }

    public void fareIslemi(MouseEvent e) {
        if(!animasyonVar) {

            int x = (int) (e.getX()/boyutlandirmaFaktoru);
            int y = (int) (e.getY()/boyutlandirmaFaktoru);

            if(e.getX() >= paletBaslangicX()
                && e.getY() >= paletBaslangicY()
                && e.getX() < paletBaslangicX() + paletGenislik()
                && e.getY() < paletBaslangicY() + paletYukseklik()) {
                    eldekiRenk = renkPaleti.getRGB((int) ((e.getX()-paletBaslangicX())/paletBoyutlandirmaFaktoru),
                        (int) ((e.getY()-paletBaslangicY())/paletBoyutlandirmaFaktoru));
            }
            else if(0 <= x && 0 <= y && x < tekilResim.getWidth() && y < tekilResim.getHeight()) {
                tekilResim.setRGB(x, y, eldekiRenk);
                repaint();
            }
        }
    }

    public void ResimIleri() {
        if(!animasyonVar) {
            if(mevcutResim + 1 < resimler.size()) {
                mevcutResim++;
                tekilResim = resimler.get(mevcutResim);
            }
            else{
                mevcutResim++;
                tekilResim = new BufferedImage(resimBoyutX, resimBoyutY, 
                    BufferedImage.TYPE_USHORT_565_RGB);
                resimler.add(tekilResim);
            }
            repaint();
        }
    }

    public void ResimGeri() {
        if(!animasyonVar) {
            if(mevcutResim - 1 >= 0) {
                mevcutResim--;
                tekilResim = resimler.get(mevcutResim);
            } else{
                tekilResim = new BufferedImage(resimBoyutX, resimBoyutY, 
                    BufferedImage.TYPE_USHORT_565_RGB);
                resimler.add(0,tekilResim);
            }
            repaint();
        }
    }

    public void animasyonuBaslatVeyaDurdur() {
        animasyonVar = !animasyonVar;
        if(animasyonVar) {
            timer.start();
        }
        else{
            timer.stop();
        }
    }

    public void animasyonHizArtir() {
        int delay = timer.getDelay();
        delay -= 5;
        if(delay < 0) {
            delay = 0;
        }
        timer.setDelay(delay);
    }

    public void animasyonHizYavaslat() {
        int delay = timer.getDelay();
        delay += 5;
        timer.setDelay(delay);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1) {
            fareIslemi(e);
        }
        mevcutTus = e.getButton();
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

    @Override
    public void mouseDragged(MouseEvent e) {
        if(mevcutTus == MouseEvent.BUTTON1) {
            fareIslemi(e);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
 
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        mevcutResim = (mevcutResim + 1) % resimler.size();
        tekilResim = resimler.get(mevcutResim);
        repaint();
    }


    
}
