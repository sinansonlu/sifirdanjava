public class Kedi extends Hayvan implements Tirnakli, Kurklu {

    Kedi(int x, int y, int hiz) {
        super(x, y, hiz);
    }

    @Override
    public void tirmala() {
        System.out.println("Kedi 2 kere tirmaladi.");
    }

    @Override
    void sesCikar() {
        super.sesCikar();
        System.out.println("Miyav!");
    }

    @Override
    public void isit() {
        System.out.println("Kedi kendini 2 derece istti.");
    }

    @Override
    void ekranCiz() {
        // super.ekranCiz();
        System.out.println("Kedi çizildi: " + konumBilgisi());
    }

    @Override
    public void yuru(int dx, int dy) {
        super.yuru(dx, dy);
        System.out.println("Kedinin yeni konumu: " + konumBilgisi());
    }
}
