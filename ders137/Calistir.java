import java.util.Random;

public class Calistir {

    public static void main(String[] args) {
        Oyun oyun = new Oyun();
        Random rng = new Random();
        for (int i = 0; i < 5; i++) {
            new FrameBaglanti(oyun, rng.nextInt(500),rng.nextInt(500),150 + rng.nextInt(300),150 + rng.nextInt(300), new OyunPaneli());
        }
        for (int i = 0; i < 3; i++) {
            new FrameBaglanti(oyun, rng.nextInt(500),rng.nextInt(500),150 + rng.nextInt(300),150 + rng.nextInt(300), new DoluPanel());
        }
        oyun.pencereKonumuGuncelle();
        oyun.timerBaslat();
    }
}
