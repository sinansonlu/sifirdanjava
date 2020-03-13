import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;

public class BasitPerceptronDene {
	
	public static void main(String[] args) {
		Ornekler orn = new Ornekler();
		
		YeniN bp = new YeniN(2,16,2, 0.001d);
				
		JFrame frame = new JFrame("Perceptron Dene");
		frame.add(orn);
		frame.setSize((int)orn.ekranMax,(int)orn.ekranMax);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		for(int epoch = 0; epoch < 1000000; epoch++) {
			for(int ornek = 0; ornek < orn.ornekSayisi; ornek++) {
				bp.SetInput(orn.x[ornek]);
				bp.Egit(orn.t[ornek]);
			}
			
			// Bütün örnekler üzerinden geçildi
			// Doðruluk hesapla
			double dog = bp.DogrulukOraniHesapla(orn.x, orn.ornekSayisi, orn.t);
		  System.out.println("Accuracy: " + dog + " Epoch: " + epoch);

		  // Yeni "epoch" için önce karýþtýr
		  orn.AccEkle(dog);
		  orn.karistir();
		  orn.ind = 0;	
		  frame.repaint();
		}
	}

}