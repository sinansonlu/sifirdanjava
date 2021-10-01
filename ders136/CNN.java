import java.util.Random;

public class CNN {
	
	Random r;
	
	double ogrenmeOrani = 0.01d;
	
	int s_output = 10;
	int sayi_f = 64;
	int s_hidden = 26*26*64;
	
	Matrix input;
	Matrix[] filtreler;
	Matrix[] conv_sonuc;
	
	Matrix hadamard;
	
	Matrix[] e_filtreler;
	Matrix[] d_filtreler;
	
	double[] fc_ilk;
	
	double[] e_output;
	double[] d_output;
	double[][] d_w_output;
	double[] d_wb_output;
	
	double[] e_hidden;
	double[] d_hidden;

	double[] output;
	double[][] w_output;
	double[] wb_output;
	
	public CNN() {
		r = new Random();
		
		filtreler = new Matrix[sayi_f];
		conv_sonuc = new Matrix[sayi_f];
		e_filtreler = new Matrix[sayi_f];
		d_filtreler = new Matrix[sayi_f];

		for(int i = 0; i < sayi_f; i++) {
			filtreler[i] = new Matrix(3);
			filtreler[i].randomize();
		}
		
		output = new double[s_output];
		e_output = new double[s_output];
		d_output = new double[s_output];
		d_wb_output = new double[s_output];
		
		d_w_output = new double[s_output][s_hidden];

		w_output = new double[s_output][s_hidden];
		wb_output = new double[s_output];
		
		e_hidden = new double[s_hidden];
		d_hidden = new double[s_hidden];
		
		for(int i = 0; i < s_output; i++) {
			wb_output[i] = GetInit();
			for(int j = 0; j < s_hidden; j++) {
				w_output[i][j] = GetInit();
			}
		}
	}
	
	public void SetInput(double[] degerler) {
		input = new Matrix(degerler);
	}
	
	public void TahminEt() {
		// convolution katmaný
		for(int i = 0; i < sayi_f; i++) {
			conv_sonuc[i] = input.convolution(filtreler[i]);
			conv_sonuc[i].act();
		}
		
		/* hadamard = new Matrix(conv_sonuc[0].boyut());
		hadamard.add(conv_sonuc[0]);
		
		for(int i = 1; i < sayi_f; i++) {
			hadamard.multH(conv_sonuc[0]);
		}
		
		fc_ilk = hadamard.flatten();
		*/
		
		int index = 0;
		
		fc_ilk = new double[s_hidden];
		
		for(int i = 0; i < sayi_f; i++) {
			double[] p = conv_sonuc[0].flatten();
			for(int k = 0; k < p.length; k++) {
				fc_ilk[index] = p[k];
				index++;
			}
		}
		
		// output katmaný
		for(int i = 0; i < output.length; i++) {
			output[i] = 0;
			for(int j = 0; j < fc_ilk.length; j++) {
				output[i] += fc_ilk[j] * w_output[i][j];
			}
			output[i] += wb_output[i];
		}
		
		double toplam_output = 0;
		for(int i = 0; i < s_output; i++) {
			toplam_output += Math.exp(output[i]);
		}
		
		if(toplam_output != 0) {
			for(int i = 0; i < s_output; i++) {
				output[i] = Math.exp(output[i]) / toplam_output;
			}
		}		
	}
	
	public void Egit(int[] t) {
		TahminEt();
		
		for(int i = 0; i < s_output; i++) {
			e_output[i] = (double) t[i] - output[i];
		}
		
		for(int i = 0; i < s_output; i++) {
			d_output[i] = e_output[i];
		}

		for(int i = 0; i < s_output; i++) {
			for(int j = 0; j < s_hidden; j++) {
				d_w_output[i][j] = fc_ilk[j] * d_output[i];
			}
			d_wb_output[i] = d_output[i];
		}
		
		// FC_ilk hatasý hesapla
		for(int i = 0; i < s_hidden; i++) {
			e_hidden[i] = 0;
			for(int j = 0; j < s_output; j++) {
				e_hidden[i] += d_output[j] * w_output[j][i];
			}
		}
		
		for(int i = 0; i < s_hidden; i++) {
			d_hidden[i] = e_hidden[i] * dact(fc_ilk[i]);
		}
		
		int l = 26*26;
		
		for(int i = 0; i < sayi_f; i++) {
			e_filtreler[i] = new Matrix(d_hidden,i*l,i*(l+1)-1);
		}
		
		for(int i = 0; i < sayi_f; i++) {
			e_filtreler[i].dact();
			d_filtreler[i] = input.convolution(e_filtreler[i]);
		}
		
		// Aðýrlýklarý güncelle
		for(int i = 0; i < s_output; i++) {
			for(int j = 0; j < s_hidden; j++) {
				w_output[i][j] += d_w_output[i][j] * ogrenmeOrani;
			}
			wb_output[i] += d_wb_output[i] * ogrenmeOrani;
		}
		
		for(int i = 0; i < sayi_f; i++) {
			d_filtreler[i].mult(ogrenmeOrani);
			filtreler[i].add(d_filtreler[i]);
		}
	}
	
	public int tahminEttigimizSinif() {
		double maxDeg = 0;
		int maxInd = 0;
		
		for(int i = 0; i < s_output; i++) {
			if(output[i] >= maxDeg) {
				maxInd = i;
				maxDeg = output[maxInd];
			}
		}
		return maxInd;
	}
	
	public int olmasiGerekenSinif(int[][] t, int i) {
		for(int j = 0; j < s_output; j++) {
			if(t[i][j] == 1) {
				return j;
			}
		}
		return -1;
	}
	
	
	public double DogrulukOraniHesapla(double[][] veri, int veriSayisi, int[][] t, int[][] conf_mat) {
		if(conf_mat != null) {
			for(int i = 0; i < s_output; i++) {
				for(int j = 0; j < s_output; j++) {
					conf_mat[i][j] = 0;	
				}	
			}
		}
				
		double dog = 0;
		for(int i = 0; i < veriSayisi; i++) {
			
			SetInput(veri[i]);
			TahminEt();
			
			int tahmin = tahminEttigimizSinif();
			int gereken = olmasiGerekenSinif(t, i);
			
			if(conf_mat != null) {
				conf_mat[tahmin][gereken]++;
			}

			if(gereken == tahmin) {
				dog++;
			}
		}
		return dog / (double) veriSayisi;
	}
	
	private double GetInit() {
		return (r.nextDouble() - 0.5d) * 0.4d;
	}
	
	public double act(double x) {
		return relu_leaky(x);
	}
	
	public double dact(double x) {
		return drelu_leaky(x);
	}
	
	public double relu(double x) {
		return Math.max(0, x);
	}
	
	public double drelu(double x) {
		return (x > 0)?1:0;
	}
	
	double leaky_alpha = 0.3;
	
	public double relu_leaky(double x) {
		return Math.max(x*leaky_alpha, x);
	}
	
	public double drelu_leaky(double x) {
		return (x > 0)?1:leaky_alpha;
	}
	
	public double sigmoid(double x) {
		return 1 / (1 + Math.exp(-x));
	}
	
	public double dsigmoid(double x) {
		return x * (1-x);
	}

}
