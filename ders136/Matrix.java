import java.util.Random;

public class Matrix {
	
	public double[][] matrix;
	private int boyut;
	
	public Matrix(int boyut) {
		this.boyut = boyut;
		matrix = new double[boyut][boyut];
	}
	
	public Matrix(double[] flat) {
		boyut = (int)(Math.sqrt(flat.length));
		
		matrix = new double[boyut][boyut];
		
		int index = 0;

		for(int i = 0; i < boyut; i++) {
			for(int j = 0; j < boyut; j++) {
				matrix[i][j] = flat[index];
				index++;
			}
		}
	}
	
	public Matrix(double[] flat, int s, int f) {
		boyut = (int)(Math.sqrt(f-s+1));
		
		matrix = new double[boyut][boyut];
		
		int index = s;

		for(int i = 0; i < boyut; i++) {
			for(int j = 0; j < boyut; j++) {
				matrix[i][j] = flat[index];
				index++;
			}
		}
	}
	
	public Matrix convolution(Matrix filtre) {
		Matrix cikti = new Matrix(boyut-(filtre.boyut()-1));

		for(int i = 0; i < cikti.boyut(); i++) {
			for(int j = 0; j < cikti.boyut(); j++) {
				
				// her bir çýktý pikseli için filtre döngüsü:
				for(int x = 0; x < filtre.boyut(); x++) {
					for(int y = 0; y < filtre.boyut(); y++) {
						cikti.matrix[i][j] += filtre.matrix[x][y]
								* matrix[i+x][j+y];
					}
				}
			}
		}
		return cikti;
	}
	
	public void randomize() {
		Random r = new Random();
		
		for(int i = 0; i < boyut; i++) {
			for(int j = 0; j < boyut; j++) {
				matrix[i][j] = (r.nextDouble() - 0.5d) * 0.4d;
			}
		}
	}
	
	public void print() {
		for(int j = 0; j < boyut; j++) {
			for(int i = 0; i < boyut; i++) {
				System.out.print(matrix[i][j] + " ");
			}
			System.out.println();
		}
	}
	
	public int boyut() {
		return boyut;
	}
	
	public double[] flatten( ) {
		double[] flat = new double[boyut*boyut];
		
		int index = 0;
		
		for(int i = 0; i < boyut; i++) {
			for(int j = 0; j < boyut; j++) {
				flat[index] = matrix[i][j];
				index++;
			}
		}
		
		return flat;
	}
	
	public void add(Matrix a) {
		for(int i = 0; i < boyut; i++) {
			for(int j = 0; j < boyut; j++) {
				matrix[i][j] += a.matrix[i][j];
			}
		}
	}
	
	public void mult(double a) {
		for(int i = 0; i < boyut; i++) {
			for(int j = 0; j < boyut; j++) {
				matrix[i][j] *= a;
			}
		}
	}
	
	public void multH(Matrix a) {
		for(int i = 0; i < boyut; i++) {
			for(int j = 0; j < boyut; j++) {
				matrix[i][j] *= a.matrix[i][j];
			}
		}
	}
	
	double leaky_alpha = 0.3;
	
	public void act() {
		for(int i = 0; i < boyut; i++) {
			for(int j = 0; j < boyut; j++) {
				matrix[i][j] = Math.max(matrix[i][j]*leaky_alpha, matrix[i][j]);
			}
		}
	}
	
	public void dact() {
		for(int i = 0; i < boyut; i++) {
			for(int j = 0; j < boyut; j++) {
				if(matrix[i][j] > 0) {
					matrix[i][j] = 1;
				}
				else {
					matrix[i][j] = leaky_alpha;
				}
			}
		}
	}	
}
