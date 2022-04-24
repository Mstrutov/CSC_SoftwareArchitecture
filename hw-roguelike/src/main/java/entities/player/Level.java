package entities.player;

public class Level {
    private int levelIndex;
    private int levelXP;
    private int damageBonus;
    private int healthBonus;

    Level() {
        this.levelIndex = 0;
        this.levelXP = 0;
        this.damageBonus = 0;
        this.healthBonus = 0;
    }

    public int getDamageBonus() {
        return damageBonus;
    }

    public int getHealthBonus() {
        return healthBonus;
    }

    /**
     * Adds the given XP to the level progression. Levels up if current XP level gets over the threshold.
     * May level up multiple times if the amount of the given XP is enough.
     * @return true if leveled up at least once
     */
    public boolean addXP(int xp) {
        levelXP += xp;
        boolean hasLeveledUpAtLeastOnce = tryLevelUp();
        boolean hasLeveledUp = tryLevelUp();
        while (hasLeveledUp) {
            hasLeveledUp = tryLevelUp();
        }
        return hasLeveledUpAtLeastOnce;
    }

    private boolean tryLevelUp() {
        int currentLevelXPThreshold = getCurrentLevelXPThreshold();
        if (levelXP >= currentLevelXPThreshold) {
            levelXP -= currentLevelXPThreshold;
            damageBonus += 20;
            healthBonus += 25;
            levelIndex++;
            return true;
        }
        return false;
    }

    private int getCurrentLevelXPThreshold() {
        return 200;
    }
}
