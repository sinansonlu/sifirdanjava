import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Oyun extends JPanel implements MouseMotionListener, KeyListener{
    static Blok[][] grid;
    static final int EKRAN_GENISLIK = 520;
    static final int EKRAN_YUKSEKLIK = 500;
    static Cubuk cubuk;

    Blok[] bloklar;
    int gerekliBlokSayisi = 50;
    
    static int BLOK_GENISLIGI = 20;

    Top top;
    Timer timer;

    public Oyun() {
        grid = new Blok[EKRAN_GENISLIK/BLOK_GENISLIGI][EKRAN_YUKSEKLIK/BLOK_GENISLIGI];
        bloklar = new Blok[gerekliBlokSayisi];

        int ii = 0;
        int jj = 10;

        for (int i = 0; i < gerekliBlokSayisi; i++) {
            bloklar[i] = new Blok(ii, jj);

            ii += 2;
            if(ii >= EKRAN_GENISLIK/BLOK_GENISLIGI - 3) {
                ii = 0;
                jj++;
            }
        }

        top = new Top(250, 400);

        cubuk = new Cubuk(250, EKRAN_YUKSEKLIK - 20);

        timer = new Timer(10, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                oyunMantigi();
            }
            
        });

        timer.start();
    }

    public void oyunMantigi() {
        if(top.aktifMi) {
            top.hareketEt();
        }

        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        for (int i = 0; i < gerekliBlokSayisi; i++) {
            if(bloklar[i].aktifMi) {
                bloklar[i].ciz(g);
            }
        }

        if(top.aktifMi) {
            top.ciz(g);
        }

        if(cubuk.aktifMi) {
            cubuk.ciz(g);
        }

        repaint();
        
    }

    public static void main(String[] args) {
        JFrame jf = new JFrame("Duvar Yıkmaca");
        jf.setUndecorated(true);
        Oyun oyun = new Oyun();
        jf.add(oyun);
        jf.addMouseMotionListener(oyun);
        jf.addKeyListener(oyun);
        jf.setSize(EKRAN_GENISLIK,EKRAN_YUKSEKLIK);
        jf.setLocation(500, 200);
        jf.setVisible(true);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
  
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        cubuk.hareketEt(e.getX());
    }

    @Override
    public void keyTyped(KeyEvent e) {
       
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_R) {
            top = new Top(250, 400);
        }
        else if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }
}