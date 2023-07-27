class Top {
    
    public int x;
    public int y;

    public int r;

    int vx;
    int vy;

    public Top(int x, int y, int vx, int vy, int r) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.r = r;
    }

    public void hareketEt(int x, int y, int w, int h) {
        this.x += vx;
        if(this.x < x) {
            vx = -vx;
            this.x = x;
        }
        if(this.x + r > x + w) {
            vx = -vx;
            this.x = x + w - r;
        }
        this.y += vy;
        if(this.y < y) {
            vy = -vy;
            this.y = y;
        }
        if(this.y + r > y + h) {
            vy = -vy;
            this.y = y + h - r;
        }
    }
}