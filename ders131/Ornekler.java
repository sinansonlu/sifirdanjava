import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import javax.swing.JPanel;

public class Ornekler extends JPanel{

	public float[] x0, x1;
	public int[] t;
	public int ornekSayisi;
	
	public int[] tahminler;
	
	public int ind = 0;
		
	public Color[] renkler;
	
	float ornekMin = -10f;
	float ornekMax = 10f;
	float ekranMin = 0f;
	float ekranMax = 900f;
	
	Random r;
	
	public Ornekler() {
		r = new Random();
		ornekSayisi = 300;
		x0 = new float[ornekSayisi];
		x1 = new float[ornekSayisi];
		t = new int[ornekSayisi];
		renkler = new Color[ornekSayisi];
		
		for(int i = 0; i < ornekSayisi; i++) {
			x0[i] = (r.nextFloat() - 0.5f) * 20f;
			x1[i] = (r.nextFloat() - 0.5f) * 20f;
			renkler[i] = Color.blue;
			if(Math.abs(x0[i] - x1[i]) < 3) {
				t[i] = 1;
			} else {
				t[i] = 0;
			}
		}
	}
	
	public void karistir() {
		int tempInd;
		float tempVal;
		Color tempCol;
		
		for(int i = 0; i < ornekSayisi; i++) {
			tempInd = r.nextInt(ornekSayisi);
			
			tempVal = x0[i];
			x0[i] = x0[tempInd];
			x0[tempInd] = tempVal;
			
			tempVal = x1[i];
			x1[i] = x1[tempInd];
			x1[tempInd] = tempVal;
			
			tempVal = t[i];
			t[i] = t[tempInd];
			t[tempInd] = (int)tempVal;
			
			tempCol = renkler[i];
			renkler[i] = renkler[tempInd];
			renkler[tempInd] = tempCol;
		}
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(Color.white);
		g.fillRect(0, 0, (int)ekranMax, (int)ekranMax);
		g.setColor(Color.black);
		g.drawLine((int)ekranMax/2, 0, (int)ekranMax/2, (int)ekranMax);
		g.drawLine(0, (int)ekranMax/2, (int)ekranMax, (int)ekranMax/2);
		g.drawLine(0, (int)ekranMax/2, (int)ekranMax, (int)ekranMax/2);
				
		for(int i = 0; i < ornekSayisi; i++) {
			if(t[i] == 1) {
				g.setColor(Color.green);
			} else {
				g.setColor(Color.red);
			}
			g.fillOval(AralikDegistir(x0[i]), (int)ekranMax - AralikDegistir(x1[i]), 18, 18);
		
			g.setColor(renkler[i]);
			g.fillOval(AralikDegistir(x0[i])+4, (int)ekranMax - AralikDegistir(x1[i])+4, 10, 10);
		}
	}
	
	public int AralikDegistir(float sayi) {
		return (int)(ekranMin + (ekranMax - ekranMin) * ((sayi - ornekMin) / (ornekMax - ornekMin)));
	}
}