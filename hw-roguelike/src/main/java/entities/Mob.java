package entities;

public class Mob {
    private int healthPoints;
    private int power;

    private int coordX;
    private int coordY;

    private boolean isDead;

    public Mob(int coordX, int coordY, int power) {
        this.power = power;
        this.coordX = coordX;
        this.coordY = coordY;
        healthPoints = 100;
        isDead = false;
    }

    public int getHealthPoints() {
        return healthPoints;
    }

    public void setHealthPoints(int healthPoints) {
        this.healthPoints = healthPoints;
        isDead = (this.healthPoints <= 0);
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getCoordX() {
        return coordX;
    }

    public void setCoordX(int coordX) {
        this.coordX = coordX;
    }

    public int getCoordY() {
        return coordY;
    }

    public void setCoordY(int coordY) {
        this.coordY = coordY;
    }

    public boolean isDead() {
        return isDead;
    }

    public boolean occupiesCell(int coordX, int coordY) {
        return getCoordX() == coordX && getCoordY() == coordY;
    }
}
