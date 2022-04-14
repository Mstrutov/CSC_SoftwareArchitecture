package entities;

public class Mob {
    private int healthPoints;
    private int power;

    private int coordX;
    private int coordY;

    public Mob(int coordX, int coordY, int power) {
        this.power = power;
        this.coordX = coordX;
        this.coordY = coordY;
        healthPoints = 100;
    }

    public int getHealthPoints() {
        return healthPoints;
    }

    public void setHealthPoints(int healthPoints) {
        this.healthPoints = healthPoints;
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
}
