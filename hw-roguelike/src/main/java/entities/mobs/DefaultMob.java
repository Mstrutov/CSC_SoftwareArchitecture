package entities.mobs;

import entities.Player;
import frame.Frame;

public class DefaultMob implements Mob{
    private int healthPoints;
    private int power;

    private int coordX;
    private int coordY;

    private boolean isDead;
    private boolean isHit;
    private static final int RANGE = 1;

    private final BehaviourStrategy behaviourStrategy;

    public DefaultMob(int coordX, int coordY, int power) {
        this(coordX, coordY, power, new PassiveBehaviourStrategy());
    }

    public DefaultMob(int coordX, int coordY, int power, BehaviourStrategy behaviourStrategy) {
        this.power = power;
        this.coordX = coordX;
        this.coordY = coordY;
        healthPoints = 100;
        isDead = false;
        isHit = false;
        this.behaviourStrategy = behaviourStrategy;
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
        return behaviourStrategy.action(this, player, frame);
    }

    public int getRange() {
        return RANGE;
    }
}
