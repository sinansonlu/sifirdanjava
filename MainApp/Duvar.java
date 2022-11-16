public class Duvar extends OyunObjesi {

    Duvar(int x, int y) {
        super(x, y);
    }

    @Override
    void ekranCiz() {
        // super.ekranCiz();
        System.out.println("Duvar çizildi: " + konumBilgisi());
    }

}
