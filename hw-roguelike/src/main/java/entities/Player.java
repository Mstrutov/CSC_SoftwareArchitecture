package entities;

import frame.FrameGenerator;

public class Player {
    private int coordX;
    private int coordY;
    private int healthPoints;
    private PlayerDirection playerDirection;
    private int countdown = 0;

    public Player() {
        this.healthPoints = 100;
        coordX = FrameGenerator.PLAYGROUND_WIDTH / 2;
        coordY = FrameGenerator.PLAYGROUND_HEIGHT / 2;
        playerDirection = PlayerDirection.RIGHT;
    }

    /**
     *
     * @param delta of HP
     * @return true if character died
     */
    public void changeHP(int delta) {
        System.out.println(healthPoints);
        healthPoints += delta;
        countdown = 5;
    }

    public boolean isDead() {
        return healthPoints <= 0;
    }

    public int getCoordX() {
        return coordX;
    }

    public int getCoordY() {
        return coordY;
    }

    public PlayerDirection getDirection() {
        return playerDirection;
    }

    public void setDirection(PlayerDirection playerDirection) {
        this.playerDirection = playerDirection;
    }

    public void moveCharacter(int deltaX, int deltaY) {
        coordX += deltaX;
        coordY += deltaY;
    }

    public boolean isHit() {
        if (countdown != 0) {
            countdown--;
            return true;
        }

        return false;
    }
}
