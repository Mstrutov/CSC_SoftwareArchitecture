package entities.mobs;

import entities.player.Player;
import frame.Frame;

public interface Mob {
    int getHealthPoints();

    void setHealthPoints(int healthPoints);

    int getCoordX();

    int getCoordY();

    void setCoordX(int coordX);

    void setCoordY(int coordY);

    boolean isHit();

    boolean isDead();

    void recover();

    boolean occupiesCell(int coordX, int coordY);

    /**
     * Describes mob action depends on the player
     * @param player player
     * @return true if mob attacked
     */
    boolean action(Player player, Frame frame);

    int getRange();

    int getPower();

    int getXPCost();
}
