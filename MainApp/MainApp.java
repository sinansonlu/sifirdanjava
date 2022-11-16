import java.util.ArrayList;
import java.util.Random;

class MainApp {

    static Random random = new Random();
    static ArrayList<OyunObjesi> oyunObjesiListesi = new ArrayList<OyunObjesi>();
    static ArrayList<Hareketli> hareketlilerListesi = new ArrayList<Hareketli>();

    public static void yeniObjeEkle(int tur) {
        if (tur == 0) {
            Duvar d = new Duvar(random.nextInt(-10, 10), random.nextInt(-10, 10));
            oyunObjesiListesi.add(d);
        } else {
            if (tur > 0 && tur < 3) {
                Hayvan h;
                if (tur == 1) {
                    h = new Kedi(random.nextInt(-10, 10),
                            random.nextInt(-10, 10),
                            random.nextInt(3, 7));
                } else {
                    h = new Kopek(random.nextInt(-10, 10),
                            random.nextInt(-10, 10),
                            random.nextInt(3, 7),
                            random.nextInt(100));

                }
                oyunObjesiListesi.add(h);
                hareketlilerListesi.add(h);
            }
        }
    }

    public static void main(String[] args) {

        for (int i = 0; i < 5; i++) {
            yeniObjeEkle(0);
        }

        for (int i = 0; i < 3; i++) {
            yeniObjeEkle(1);
        }

        for (int i = 0; i < 4; i++) {
            yeniObjeEkle(2);
        }

        for (int i = 0; i < oyunObjesiListesi.size(); i++) {
            oyunObjesiListesi.get(i).ekranCiz();
        }

        for (int i = 0; i < hareketlilerListesi.size(); i++) {
            hareketlilerListesi.get(i).yuru(random.nextInt(-5, 5), random.nextInt(-5, 5));
        }

    }
}