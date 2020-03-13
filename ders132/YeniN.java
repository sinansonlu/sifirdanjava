import java.util.Random;

public class YeniN {
	Random r;
	double ogrenmeOrani;
	
	int s_input;
	int s_output;
	int s_hidden;

	double [] input;
	double [] output;
	double [] hidden;
	
	double [][] w_hidden;
	double [] wb_hidden;
	
	double [][] w_output;
	double [] wb_output;
	
	double [] e_output;
	double [] e_hidden;
	double [] d_output;
	double [] d_hidden;
	double [][] d_w_hidden;
	double [] d_wb_hidden;
	double [][] d_w_output;
	double [] d_wb_output;

	
	public YeniN(int input, int hidden, int output, double ogrenmeOrani) {
		this.ogrenmeOrani = ogrenmeOrani;
		s_input = input;
		s_output = output;
		s_hidden = hidden;
		
		this.input = new double[input];
		this.output = new double[output];
		this.hidden = new double[hidden];
		
		w_hidden = new double[hidden][input];
		wb_hidden = new double[hidden];
		
		w_output = new double[output][hidden];
		wb_output = new double[output];
		
		e_output = new double[output];
		e_hidden = new double[hidden];
		d_output = new double[output];
		d_hidden = new double[hidden];
		d_w_hidden = new double[hidden][input];
		d_wb_hidden = new double[hidden];
		d_w_output = new double[output][hidden];
		d_wb_output = new double[output];
		
		r = new Random();
		
		for(int i = 0; i < hidden; i++) {
			wb_hidden[i] = GetInit();
			for(int j = 0; j < input; j++) {
				w_hidden[i][j] = GetInit();
			}
		}
		
		for(int i = 0; i < output; i++) {
			wb_output[i] = GetInit();
			for(int j = 0; j < hidden; j++) {
				w_output[i][j] = GetInit();
			}
		}
	}
	
	public void SetInput(double[] degerler) {
		for(int i = 0; i < s_input; i++) {
			input[i] = degerler[i];
		}
	}
	
	public void TahminEt() {
		for(int i = 0; i < s_hidden; i++) {
			hidden[i] = 0;
			for(int j = 0; j < s_input; j++) {
				hidden[i] += input[j] * w_hidden[i][j];
			}
			hidden[i] += wb_hidden[i];
			hidden[i] = act(hidden[i]);
		}
		
		for(int i = 0; i < s_output; i++) {
			output[i] = 0;
			for(int j = 0; j < s_hidden; j++) {
				output[i] += hidden[j] * w_output[i][j];
			}
			output[i] += wb_output[i];
			output[i] = sigmoid(output[i]);
		}
		
		double toplam_output = 0;
		for(int i = 0; i < s_output; i++) {
			toplam_output += output[i];
		}
		
		for(int i = 0; i < s_output; i++) {
			output[i] = output[i] / toplam_output;
		}
	}
	
	private double GetInit() {
		return r.nextFloat();
	}
	
	public void Egit(int[] t) {
		TahminEt();
		for(int i = 0; i < s_output; i++) {
			e_output[i] = (double) t[i] - output[i];
		}
		
		for(int i = 0; i < s_output; i++) {
			d_output[i] = e_output[i] * dsigmoid(output[i]);
		}
		
		for(int i = 0; i < s_output; i++) {
			for(int j = 0; j < s_hidden; j++) {
				d_w_output[i][j] = hidden[j] * d_output[i];
			}
			d_wb_output[i] = d_output[i];
		}
		
		// Hidden için yapýlan iþlemler
		for(int i = 0; i < s_hidden; i++) {
			e_hidden[i] = 0;
			for(int j = 0; j < s_output; j++) {
				e_hidden[i] += d_output[j] * w_output[j][i];
			}
		}
		
		for(int i = 0; i < s_hidden; i++) {
			d_hidden[i] = e_hidden[i] * dact(hidden[i]);
		}
		
		for(int i = 0; i < s_hidden; i++) {
			for(int j = 0; j < s_input; j++) {
				d_w_hidden[i][j] = input[j] * d_hidden[i];
			}
			d_wb_hidden[i] = d_hidden[i];
		}

		// Aðýrlýklarý güncelle
		for(int i = 0; i < s_output; i++) {
			for(int j = 0; j < s_hidden; j++) {
				w_output[i][j] += d_w_output[i][j] * ogrenmeOrani;
			}
			wb_output[i] += d_wb_output[i] * ogrenmeOrani;
		}
		
		for(int i = 0; i < s_hidden; i++) {
			for(int j = 0; j < s_input; j++) {
				w_hidden[i][j] += d_w_hidden[i][j] * ogrenmeOrani;
			}
			wb_hidden[i] += d_wb_hidden[i] * ogrenmeOrani;
		}
	}
	
	public int HangiSinif() {
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
	
	public double DogrulukOraniHesapla(double[][] veri, int veriSayisi, int[][] t) {
		double dog = 0;
		for(int i = 0; i < veriSayisi; i++) {
			
			SetInput(veri[i]);
			TahminEt();

			for(int j = 0; j < s_output; j++) {
				if(t[i][j] == 1) {
					if(HangiSinif() == j) {
						dog++;
					}
				}
			}
		}
		return dog / (double) veriSayisi;
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
		return Math.max(x*0.3, x);
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