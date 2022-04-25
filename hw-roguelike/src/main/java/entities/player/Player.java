package entities.player;

import frame.RoomGenerator;

public class Player {
    private int coordX;
    private int coordY;
    private int healthPoints;
    private PlayerDirection playerDirection;
    private int hitEffectCountdown = 0;
    private int levelUpEffectCountdown = 0;
    private int xpGainEffectCountdown = 0;

    private final int attackPower;
    private final Level level;

    public Player() {
        this.healthPoints = 100;
        coordX = RoomGenerator.PLAYGROUND_WIDTH / 2;
        coordY = RoomGenerator.PLAYGROUND_HEIGHT / 2;
        playerDirection = PlayerDirection.RIGHT;
        attackPower = 25;
        level = new Level();
    }

    /**
     * @param delta of HP
     */
    public void changeHP(int delta) {
        System.out.println(healthPoints);
        healthPoints += delta;
        hitEffectCountdown = 5;
    }

    public int getHealthPoints() {
        return healthPoints;
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
        if (hitEffectCountdown != 0) {
            hitEffectCountdown--;
            return true;
        }

        return false;
    }

    public boolean isLeveledUp() {
        if (levelUpEffectCountdown != 0) {
            levelUpEffectCountdown--;
            return true;
        }

        return false;
    }

    public boolean isGainedXp() {
        if (xpGainEffectCountdown != 0) {
            xpGainEffectCountdown--;
            return true;
        }

        return false;
    }

    public int getAttackPower() {
        return attackPower + level.getDamageBonus();
    }

    public void addXP(int xp) {
        if (xp != 0) {
            xpGainEffectCountdown = 50;
        }

        if (level.addXP(xp)) {
            levelUpEffectCountdown = 100;
        }
    }

    public Level getCurrentLevel() {
        return level;
    }
}
