public class Kopek extends Hayvan implements Tirnakli, Kurklu {

    int lisansNumarasi;

    Kopek(int x, int y, int hiz, int lisansNumarasi) {
        super(x, y, hiz);
        this.lisansNumarasi = lisansNumarasi;
    }

    @Override
    public void tirmala() {
        System.out.println("Köpek 1 kere tirmaladi.");
    }

    @Override
    public void isit() {
        System.out.println("Köpek kendini 3 derece istti");
    }

    @Override
    void ekranCiz() {
        // super.ekranCiz();
        System.out.println("Köpek çizildi: " + konumBilgisi());
    }

    @Override
    public void yuru(int dx, int dy) {
        super.yuru(dx, dy);
        System.out.println("Köpeğin yeni konumu: " + konumBilgisi());
    }

}
