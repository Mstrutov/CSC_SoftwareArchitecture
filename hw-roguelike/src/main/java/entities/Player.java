package entities;

import frame.FrameGenerator;

public class Player {
    private int coordX;
    private int coordY;
    private int healthPoints;

    public Player() {
        this.healthPoints = 100;
        coordX = FrameGenerator.PLAYGROUND_WIDTH / 2;
        coordY = FrameGenerator.PLAYGROUND_HEIGHT / 2;
    }

    /**
     *
     * @param delta of HP
     * @return true if character died
     */
    public boolean changeHP(int delta) {
        healthPoints += delta;
        return healthPoints <= 0;
    }

    public int getCoordX() {
        return coordX;
    }

    public int getCoordY() {
        return coordY;
    }

    public void moveCharacter(int deltaX, int deltaY) {
        coordX += deltaX;
        coordY += deltaY;
    }
}
