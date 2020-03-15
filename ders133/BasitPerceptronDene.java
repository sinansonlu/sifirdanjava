import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;

public class BasitPerceptronDene {
	
	public static void main(String[] args) {
		double[][] x = new double[60000][28*28];
		int[][] t = new int[60000][10];
		
		MNISTYukle mn = new MNISTYukle();
		int ornekSayisi = mn.yukle(x, t);
		
		Ornekler orn = new Ornekler(x,t,ornekSayisi);
		
		YeniN bp = new YeniN(28*28,32,10, 0.01d);
				
		JFrame frame = new JFrame("Perceptron Dene");
		frame.add(orn);
		frame.setSize((int)orn.ekranMax,(int)orn.ekranMax);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		for(int epoch = 0; epoch < 10000; epoch++) {
			for(int ornek = 0; ornek < orn.ornekSayisi; ornek++) {
				bp.SetInput(orn.x[ornek]);
				bp.Egit(orn.t[ornek]);
				orn.Goster(ornek);
				frame.repaint();
			}
			
			// Bütün örnekler üzerinden geçildi
			// Doðruluk hesapla
			double dog = bp.DogrulukOraniHesapla(orn.x, orn.ornekSayisi, orn.t);
		  System.out.println("Accuracy: " + dog + " Epoch: " + epoch);

		  // Yeni "epoch" için önce karýþtýr
		  orn.AccEkle(dog);
		  orn.karistir();
		}
	}

}