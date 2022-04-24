package entities.mobs;

import entities.Player;
import frame.Frame;

public abstract class BehaviourStrategyImpl implements BehaviourStrategy {
    private int countdown = 50;

    public boolean attackIfAble(Mob ofMob, Player toPlayer, Frame atFrame) {
        if (countdown > 0) {
            countdown--;
        } else if (Math.abs(toPlayer.getCoordX() - ofMob.getCoordX()) < 2 && Math.abs(toPlayer.getCoordY() - ofMob.getCoordY()) < 2) {
            toPlayer.changeHP(-ofMob.getPower());
            countdown = 15;
            return true;
        }
        return false;
    }
}
