import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;

public class BasitPerceptronDene {
	
	public static void main(String[] args) {
		Ornekler orn = new Ornekler();
		
		NN21 bp = new NN21();
		bp.Hazirlan();
		
		orn.tahminler = bp.TahminSetiOlustur(orn.x0, orn.x1, orn.ornekSayisi);
		
		JFrame frame = new JFrame("Perceptron Dene");
		frame.add(orn);
		frame.setSize((int)orn.ekranMax,(int)orn.ekranMax);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		for(int epoch = 0; epoch < 10000; epoch++) {
			for(int ornek = 0; ornek < orn.ornekSayisi; ornek++) {
				bp.Egit(orn.x0[orn.ind], orn.x1[orn.ind], orn.t[orn.ind]);
			}
			
			// Bütün örnekler üzerinden geçildi
			// Doðruluk hesapla
		
		  orn.tahminler = bp.TahminSetiOlustur(orn.x0, orn.x1, orn.ornekSayisi);
		  double dogruluk = bp.dogrulukHesapla(orn.t, orn.tahminler, orn.ornekSayisi,
				  orn.renkler);
		  System.out.println("Accuracy: " + dogruluk + " Epoch: " + epoch);
			frame.repaint();

		  // Yeni "epoch" için önce karýþtýr
		  orn.karistir();
		  orn.ind = 0;	  
		}
	}

}