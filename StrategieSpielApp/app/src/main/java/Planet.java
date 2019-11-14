public class Planet {
    String name;
    int size;
    int x;
    int y;
    int metall;
    int platinen;
    int treibstoff;

    public Planet(String name, int size, int x, int y, int metall, int platinen, int treibstoff) {
        this.name = name;
        this.size = size;
        this.x = x;
        this.y = y;
        this.metall = metall;
        this.platinen = platinen;
        this.treibstoff = treibstoff;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getMetall() {
        return metall;
    }

    public void setMetall(int metall) {
        this.metall = metall;
    }

    public int getPlatinen() {
        return platinen;
    }

    public void setPlatinen(int platinen) {
        this.platinen = platinen;
    }

    public int getTreibstoff() {
        return treibstoff;
    }

    public void setTreibstoff(int treibstoff) {
        this.treibstoff = treibstoff;
    }
}
