import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Random;

import javax.swing.JPanel;

public class Ornekler extends JPanel{

	public int goster;
	
	public double[][] x, x_v, x_t, x_tmp;
	public int[][] t, t_v, t_t, t_tmp;
		
	public int[] tahminler;
	
	public double[] acc;
	public int curr_acc;
	int max_acc = 1000;
	
	float acc_w;

	float ekranMin = 0f;
	float ekranMax = 900f;
	
	Random r;
	
	public int trainingSayisi;
	public int validationSayisi;
	public int testSayisi;
	
	public int [][] conf_mat;
	
	public int inSize;
	public int outSize;
	
	public Ornekler(double[][] x, int[][] t, int ornekSayisi, int inSize, int outSize, double oran) {
		r = new Random();
		
		this.x = x;
		this.t = t;
		this.inSize = inSize;
		this.outSize = outSize;
		
		trainingSayisi = ornekSayisi;
		
		karistir();
		
		// training - validation - test setlerini ayýr
		validationSayisi = (int) Math.floor(ornekSayisi * oran);
		testSayisi = (int) Math.floor(ornekSayisi * oran);
		trainingSayisi = ornekSayisi - (validationSayisi + testSayisi);
		
		x_v = new double[validationSayisi][28*28];
		t_v = new int[validationSayisi][10];
		x_t = new double[testSayisi][28*28];
		t_t = new int[testSayisi][10];
		x_tmp = new double[trainingSayisi][28*28];
		t_tmp = new int[trainingSayisi][10];
		
		for(int i = 0; i < validationSayisi; i++) {			
			x_v[i] = x[i];
			t_v[i] = t[i];
		}
		
		for(int i = 0; i < testSayisi; i++) {			
			x_t[i] = x[i+validationSayisi];
			t_t[i] = t[i+validationSayisi];
		}
		
		for(int i = 0; i < trainingSayisi; i++) {			
			x_tmp[i] = x[i+validationSayisi+testSayisi];
			t_tmp[i] = t[i+validationSayisi+testSayisi];
		}
		
		this.x = x_tmp;
		this.t = t_tmp;
		
		System.out.println("Training: " + trainingSayisi + " Validation: " + validationSayisi + " Test: "+ testSayisi);

		conf_mat = new int[outSize][outSize];

		acc = new double[max_acc];
		acc_w = ekranMax / max_acc;
	}
	
	public void Goster(int g) {
		goster = g;
	}
	
	public void karistir() {
		int tempInd;
		double[] tempVal;
		int[] tempValInt;
		Color tempCol;
		
		for(int i = 0; i < trainingSayisi; i++) {
			tempInd = r.nextInt(trainingSayisi);
			
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
		
		g.setFont(new Font("TimesRoman", Font.PLAIN, 32)); 
		
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 10; j++) {
				g.drawString("" + conf_mat[i][j], (int)(i * (ekranMax - 40) / 10 + 40) , (int)(j * (ekranMax - 40) / 10 + 40));
			}
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
	
	public void conf_mat_ciz() {
		
	}
}