import java.awt.Color;
import java.util.Random;

public class NN21 {
	double w00, w01, w0b;
	double w10, w11, w1b;
	double wh0, wh1, whb;
	
	double h0, h1;

	Random r;
	float ogrenmeOrani = 0.001f;
	
	public void Hazirlan() {
		r = new Random();
		whb = (r.nextFloat() - 0.5f) * 2f;
		wh0 = (r.nextFloat() - 0.5f) * 2f;
		wh1 = (r.nextFloat() - 0.5f) * 2f;
		w0b = (r.nextFloat() - 0.5f) * 2f;
		w00 = (r.nextFloat() - 0.5f) * 2f;
		w01 = (r.nextFloat() - 0.5f) * 2f;
		w1b = (r.nextFloat() - 0.5f) * 2f;
		w10 = (r.nextFloat() - 0.5f) * 2f;
		w11 = (r.nextFloat() - 0.5f) * 2f;
	}
	
	public double TahminEt(float x0, float x1) {
		h0 = act(x0 * w00 + x1 * w01 + w0b);
		h1 = act(x0 * w10 + x1 * w11 + w1b);
		return sigmoid(h0 * wh0 + h1 * wh1 + whb);	
	}
	
	public int[] TahminSetiOlustur(float[] x0lar, float[] x1ler, int elemanSayisi) {
		int[] sonuclar = new int[elemanSayisi];
		for(int i = 0; i < elemanSayisi; i++) {
			double d = TahminEt(x0lar[i], x1ler[i]);
			if(d > 0.5d) {
				sonuclar[i] = 1;
			} else {
				sonuclar[i] = 0;
			}
		}
		return sonuclar;
	}
	
	public double dogrulukHesapla(int[] t, int[] y, int ornekSayisi, Color[] renkler) {
		double dogru = 0;
		for(int i = 0; i < ornekSayisi; i++) {
			if(y[i] == t[i]) {
				dogru++;
				renkler[i] = Color.white;
			}
			else {
				renkler[i] = Color.black;
			}
		}
		return dogru / (double)ornekSayisi;
	}
	
	double dwh0, dwh1, dwhb;
	double dw00, dw01, dw0b;
	double dw10, dw11, dw1b;
	
	public void Egit(float x0, float x1, int t) {
		// Mevcut aðýrlýklarla çýktý hesapla
		double y = TahminEt(x0, x1);
		
		// Bulunan çýktýnýn hatasýný hesapla
		double ey = (t - y);
		
		// Çýktýnýn ne kadar deðiþmesi gerektiðini hesapla
		double dy = ey * dsigmoid(y);
		
		// Fakat çýktýyý doðrudan deðiþtiremiyoruz
		// Çýktýnýn oluþmasýnda hangi parametreler ne kadar sorumluysa
		// O parametreler için deðiþim deðerleri (delta) hesapla
		dwh0 = h0 * dy;
		dwh1 = h1 * dy;
		dwhb = dy;
		
		// Hidden Layer'daki hatayý Y'nin deðiþmesi gereken miktar üzerinden hesapla
		double eh0 = dy * wh0;
		double eh1 = dy * wh1;
		
		// Hidden'daki hata üzerinden hidden output'larýn delta'sýný hesapla
		double dh0 = eh0 * dact(h0);
		double dh1 = eh1 * dact(h1);

		// H0 için deðiþim miktarlarýný sorumluluk üzerinden hesapla
		dw00 = x0 * dh0;
		dw01 = x1 * dh0;
		dw0b = dh0;
		
		// H1 için deðiþim miktarlarýný sorumluluk üzerinden hesapla
		dw10 = x0 * dh1;
		dw11 = x1 * dh1;
		dw1b = dh0;
		
		// Aðýrlýklarý güncelle
		wh0 += dwh0 * ogrenmeOrani;
		wh1 += dwh1 * ogrenmeOrani;
		whb += dwhb * ogrenmeOrani;
		
		w00 += dw00 * ogrenmeOrani;
		w01 += dw01 * ogrenmeOrani;
		w0b += dw0b * ogrenmeOrani;
		
		w10 += dw10 * ogrenmeOrani;
		w11 += dw11 * ogrenmeOrani;
		w1b += dw1b * ogrenmeOrani;
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