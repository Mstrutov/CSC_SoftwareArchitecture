package entities.mobs;

import entities.player.Player;
import frame.Frame;

public class PassiveBehaviourStrategy extends BehaviourStrategyImpl {
    @Override
    public boolean action(Mob ofMob, Player toPlayer, Frame atFrame) {
        return attackIfAble(ofMob, toPlayer, atFrame);
    }
}
