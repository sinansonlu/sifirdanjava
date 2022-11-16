public abstract class Hayvan extends OyunObjesi implements Hareketli {

    int hiz;

    Hayvan(int x, int y, int hiz) {
        super(x, y);
        this.hiz = hiz;
    }

    void sesCikar() {
        System.out.println("Hayvan ses çıkarıyor!");
    }

    @Override
    public void yuru(int dx, int dy) {
        x += dx * hiz;
        y += dy * hiz;
    }
}
