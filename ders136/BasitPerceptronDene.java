import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;

public class BasitPerceptronDene {
	public static void main(String[] args) {
		double[][] x = new double[60000][28*28];
		int[][] t = new int[60000][10];
		
		MNISTYukle mn = new MNISTYukle();
		int ornekSayisi = mn.yukle(x, t);
		
		Ornekler orn = new Ornekler(x, t, ornekSayisi, 28*28, 10, 0.1d);
		
		CNN bp = new CNN();
		
		JFrame frame = new JFrame("Perceptron Dene");
		frame.add(orn);
		frame.setSize((int)orn.ekranMax,(int)orn.ekranMax);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		for(int epoch = 0; epoch < 20; epoch++) {
			for(int ornek = 0; ornek < orn.trainingSayisi; ornek++) {
				bp.SetInput(orn.x[ornek]);
				bp.Egit(orn.t[ornek]);
				orn.Goster(ornek);
				frame.repaint();
			}
			
			// Bütün örnekler üzerinden geçildi
			// Doðruluk hesapla
			double train_acc = bp.DogrulukOraniHesapla(orn.x, orn.trainingSayisi, orn.t, null);
			double val_acc = bp.DogrulukOraniHesapla(orn.x_v, orn.validationSayisi, orn.t_v, orn.conf_mat);

		  System.out.println(epoch + ". " + "Train Acc: " + train_acc + " Validation Acc: " + val_acc);

		  // Yeni "epoch" için önce karýþtýr
		  orn.AccEkle(train_acc);
		  orn.karistir();
		}
		
		double test_acc = bp.DogrulukOraniHesapla(orn.x_t, orn.testSayisi, orn.t_t, null);
		System.out.println("Test Acc: " + test_acc);
		
	}
	
	public static void main2(String[] args) {
		Matrix m = new Matrix(5);
		Matrix f = new Matrix(3);
		
		m.randomize();
		f.randomize();
		
		Matrix o = m.convolution(f);
		
		m.print();
		System.out.println();
		f.print();
		System.out.println();
		o.print();
		System.out.println();
		System.out.println();
		double[] dd = m.flatten();
		for(int i = 0; i < dd.length; i++) {
			System.out.print(dd[i] + " ");
		}
	}
	
	public static void main3(String[] args) {
		double[][] x = new double[60000][28*28];
		int[][] t = new int[60000][10];
		
		MNISTYukle mn = new MNISTYukle();
		int ornekSayisi = mn.yukle(x, t);
		
		Ornekler orn = new Ornekler(x, t, ornekSayisi, 28*28, 10, 0.1d);
		
		YeniN bp = new YeniN(orn.inSize, 32, orn.outSize, 0.01d);
				
		JFrame frame = new JFrame("Perceptron Dene");
		frame.add(orn);
		frame.setSize((int)orn.ekranMax,(int)orn.ekranMax);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		for(int epoch = 0; epoch < 20; epoch++) {
			for(int ornek = 0; ornek < orn.trainingSayisi; ornek++) {
				bp.SetInput(orn.x[ornek]);
				bp.Egit(orn.t[ornek]);
				orn.Goster(ornek);
				frame.repaint();
			}
			
			// Bütün örnekler üzerinden geçildi
			// Doðruluk hesapla
			double train_acc = bp.DogrulukOraniHesapla(orn.x, orn.trainingSayisi, orn.t, null);
			double val_acc = bp.DogrulukOraniHesapla(orn.x_v, orn.validationSayisi, orn.t_v, orn.conf_mat);

		  System.out.println(epoch + ". " + "Train Acc: " + train_acc + " Validation Acc: " + val_acc);

		  // Yeni "epoch" için önce karýþtýr
		  orn.AccEkle(train_acc);
		  orn.karistir();
		}
		
		double test_acc = bp.DogrulukOraniHesapla(orn.x_t, orn.testSayisi, orn.t_t, null);
		System.out.println("Test Acc: " + test_acc);
	}

}