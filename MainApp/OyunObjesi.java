public abstract class OyunObjesi {
    int x, y;

    OyunObjesi(int x, int y) {
        this.x = x;
        this.y = y;
    }

    void ekranCiz() {
        System.out.println("Oyun objesi çizildi: " + konumBilgisi());
    }

    String konumBilgisi() {
        return "(" + x + ", " + y + ")";
    }

}
