import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MNISTYukle {
	String res_dos = "train-images.idx3-ubyte";
	String lab_dos = "train-labels.idx1-ubyte";
	
	public int yukle(double[][] x, int[][] t) {
		int ornekSayisi = -1;
		int tmp;
		
		int[] sinifSay = new int[10];
		
		try {
			DataInputStream in = new DataInputStream(new BufferedInputStream(new FileInputStream(res_dos)));
			DataInputStream in_lab = new DataInputStream(new BufferedInputStream(new FileInputStream(lab_dos)));

			int mn = in.readInt();
			ornekSayisi = in.readInt();
			int row = in.readInt();
			int col = in.readInt();
			
			int mn_lab = in_lab.readInt();
			int ornekSayisi_lab = in_lab.readInt();
			
			for(int orn = 0; orn < ornekSayisi; orn++) {
				tmp = in_lab.readUnsignedByte();
				t[orn][tmp] = 1;
				sinifSay[tmp]++;
				
				for(int j = 0; j < col; j++) {
					for(int i = 0; i < row; i++) {
						x[orn][j*col+i] = (double)in.readUnsignedByte()/255;
					}
				}
			}

			System.out.println("Dosya yüklendi!" + mn + " " + ornekSayisi + " " + row+ " " + col);
			for(int i = 0; i < 10; i++) {
				System.out.print(sinifSay[i] + " ");
			}
			System.out.println();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return ornekSayisi;
	}
}
