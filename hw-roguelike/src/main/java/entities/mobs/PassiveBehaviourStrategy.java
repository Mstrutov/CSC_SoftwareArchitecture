package entities.mobs;

import entities.Player;
import frame.Frame;

public class PassiveBehaviourStrategy extends BehaviourStrategyImpl {
    @Override
    public boolean action(Mob ofMob, Player toPlayer, Frame atFrame) {
        return attackIfAble(ofMob, toPlayer, atFrame);
    }
}
