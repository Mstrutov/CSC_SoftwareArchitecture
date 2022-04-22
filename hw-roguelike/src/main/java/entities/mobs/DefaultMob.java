package entities.mobs;

import entities.Player;
import frame.Frame;

public abstract class DefaultMob implements Mob{
    private int healthPoints;
    private int power;

    private int coordX;
    private int coordY;

    private boolean isDead;
    private boolean isHit;
    private static final int RANGE = 1;

    private int countdown = 50;

    public DefaultMob(int coordX, int coordY, int power) {
        this.power = power;
        this.coordX = coordX;
        this.coordY = coordY;
        healthPoints = 100;
        isDead = false;
        isHit = false;
    }

    public int getHealthPoints() {
        return healthPoints;
    }

    public void setHealthPoints(int healthPoints) {
        this.healthPoints = healthPoints;
        isDead = (this.healthPoints <= 0);
        isHit = true;
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

    public boolean isHit() {
        return isHit;
    }

    public void recover() {
        isHit = false;
    }

    public boolean occupiesCell(int coordX, int coordY) {
        return getCoordX() == coordX && getCoordY() == coordY;
    }

    public boolean action(Player player, Frame frame) {
        if (countdown > 0) {
            countdown--;
        } else if (Math.abs(player.getCoordX() - coordX) < 2 && Math.abs(player.getCoordY() - coordY) < 2) {
            player.changeHP(-power);
            countdown = 15;
            return true;
        }
        return false;
    }

    public int getRange() {
        return RANGE;
    }
}
