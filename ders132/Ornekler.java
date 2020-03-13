import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import javax.swing.JPanel;

public class Ornekler extends JPanel{

	public double[][] x;
	public int[][] t;
	public int ornekSayisi;
	
	public int[] tahminler;
	
	public double[] acc;
	public int curr_acc;
	int max_acc = 1000;
	
	float acc_w;
	
	public int ind = 0;

	float ekranMin = 0f;
	float ekranMax = 900f;
	
	Random r;
	
	public Ornekler() {
		acc = new double[max_acc];
		r = new Random();
		ornekSayisi = 1000;
		x = new double[ornekSayisi][2];
		t = new int[ornekSayisi][2];
		
		for(int i = 0; i < ornekSayisi; i++) {
			x[i][0] = (r.nextFloat() - 0.5f) * 20f;
			x[i][1] = (r.nextFloat() - 0.5f) * 20f;
			if(Math.pow(x[i][0], x[i][1]) < 3) {
				t[i][0] = 1; // 1 0
			} else {
				t[i][1] = 1; // 0 1
			}
		}
		
		acc_w = ekranMax / max_acc;
	}
	
	public void karistir() {
		int tempInd;
		double[] tempVal;
		int[] tempValInt;
		Color tempCol;
		
		for(int i = 0; i < ornekSayisi; i++) {
			tempInd = r.nextInt(ornekSayisi);
			
			tempVal = x[i];
			x[i] = x[tempInd];
			x[tempInd] = tempVal;
			
			tempValInt = t[i];
			t[i] = t[tempInd];
			t[tempInd] = tempValInt;
		}
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(Color.white);
		g.fillRect(0, 0, (int)ekranMax, (int)ekranMax);
		g.setColor(Color.black);
		for(int i = 0; i < max_acc -1; i++) {
			g.drawLine(Math.round(i * acc_w), Math.round((float)(1-acc[i]) * ekranMax),
					Math.round((i+1) * acc_w), Math.round((float)(1-acc[i+1]) * ekranMax));
		}
	}
	
	public void AccEkle(double deg) {
		acc[curr_acc] = deg;
		curr_acc++;
		if(curr_acc > max_acc-1) {
			for(int i = 1; i < max_acc; i++) {
				acc[i-1] = acc[i];
			}
			curr_acc--;
		}
	}
}